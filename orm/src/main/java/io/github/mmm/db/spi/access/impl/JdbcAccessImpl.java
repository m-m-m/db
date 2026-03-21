/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.spi.access.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.mmm.bean.ReadableBean;
import io.github.mmm.db.name.DbQualifiedName;
import io.github.mmm.db.param.AbstractCriteriaParameters;
import io.github.mmm.db.result.DbResult;
import io.github.mmm.db.result.impl.JdbcResult;
import io.github.mmm.db.source.DbSourceConfig;
import io.github.mmm.db.spi.access.AbstractDbAccess;
import io.github.mmm.db.spi.access.DbAccess;
import io.github.mmm.db.statement.DbPlainStatement;
import io.github.mmm.db.statement.DbStatement;
import io.github.mmm.db.statement.NonUniqueResultException;
import io.github.mmm.db.statement.insert.InsertClause;
import io.github.mmm.db.statement.insert.InsertStatement;
import io.github.mmm.db.statement.select.SelectEntityClause;
import io.github.mmm.db.statement.select.SelectStatement;
import io.github.mmm.db.statement.update.UpdateClause;
import io.github.mmm.db.statement.update.UpdateSetClause;
import io.github.mmm.db.statement.update.UpdateStatement;
import io.github.mmm.db.tx.spi.DbEntityHolder;
import io.github.mmm.db.tx.spi.DbEntitySession;
import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.id.GenericId;
import io.github.mmm.entity.id.Id;
import io.github.mmm.entity.id.OptimisicLockException;
import io.github.mmm.entity.id.sequence.IdSequence;
import io.github.mmm.entity.property.id.PkProperty;
import io.github.mmm.property.WritableProperty;
import io.github.mmm.property.criteria.CriteriaPredicate;
import io.github.mmm.property.criteria.PredicateOperator;
import io.github.mmm.property.criteria.PropertyAssignment;
import io.github.mmm.value.SimplePath;

/**
 * Implementation of {@link DbAccess} for JDBC.
 */
public class JdbcAccessImpl extends AbstractDbAccess {

  private static final Logger LOG = LoggerFactory.getLogger(JdbcAccessImpl.class);

  /**
   * The constructor.
   *
   * @param sourceConfig the {@link DbSourceConfig}.
   */
  public JdbcAccessImpl(DbSourceConfig sourceConfig) {

    super(sourceConfig);
  }

  @Override
  public void insert(EntityBean entity) {

    doInsert(entity);
  }

  private <E extends EntityBean> void doInsert(E entity) {

    InsertStatement<E> insert = new InsertClause().into(entity).valuesAll().get();
    long rowCount = insert(insert);
    assert (rowCount == 1);
    E managed = ReadableBean.copy(entity);
    DbEntitySession<E> entitySession = getEntitySession(entity);
    entitySession.put(managed);
  }

  @Override
  public <E extends EntityBean> E selectById(Id<E> id, E prototype) {

    DbEntitySession<E> entitySession = getEntitySession(prototype);
    DbEntityHolder<E> holder = entitySession.get(id);
    E entity;
    if (holder == null) {
      SelectStatement<E> select = new SelectEntityClause<>(prototype).from().where(prototype.Id().eq(id)).get();
      entity = selectOne(select);
    } else {
      entity = holder.getExternal();
    }
    return entity;
  }

  @Override
  public void update(EntityBean entity) {

    doUpdate(entity);
  }

  private <E extends EntityBean> void doUpdate(E entity) {

    PkProperty pk = entity.Id();
    Id<E> id = Id.from(entity);
    if (id.isTransient()) {
      throw new IllegalStateException(
          "Cannot update entity of type " + entity.getType().getQualifiedName() + " because it is transient.");
    }
    DbEntitySession<E> entitySession = getEntitySession(entity);
    DbEntityHolder<E> holder = entitySession.get(id);
    if (holder == null) {
      throw new IllegalStateException("Cannot update entity of type " + entity.getType().getQualifiedName()
          + " with ID " + id + " because it is not managed in the current transaction!");
    }
    E managed = holder.getInternal();
    if (managed.getId().getRevision() != id.getRevision()) {
      throw new OptimisicLockException(id, entity.getType().getQualifiedName());
    }
    Id<?> newId = ((GenericId<E, ?, ?, ?>) id).updateRevision();
    UpdateClause<EntityBean> updateEntity = DbStatement.update(entity);
    SimplePath rev = new SimplePath(pk.parentPath(), Id.COLUMN_REVISION);
    // setAll sets all assignments given as varargs
    // we provide no arguments just to get access to the SET clause (UpdateSet)
    // this allows us to build the clause dynamically
    // otherwise we would need to add the first assignment to UpdateClause
    // and then all following assignments to the returned UpdateSet.
    UpdateSetClause<EntityBean> set = updateEntity.setAll();
    for (WritableProperty<?> property : entity.getProperties()) {
      if (!property.isTransient()) {
        PropertyAssignment<?> assignment;
        if (property == pk) {
          assignment = PropertyAssignment.of(rev, newId.getRevision());
        } else {
          Object managedValue = managed.get(property.getName());
          Object updateValue = property.get();
          if (Objects.equals(updateValue, managedValue)) {
            continue;
          }
          assignment = PropertyAssignment.ofValue(property);
        }
        set = set.set(assignment);
      }
    }
    if (set == null) {
      LOG.debug("Omitting update of {} with ID {} because nothing has changed.", entity.getType().getStableName(), id);
      return;
    }
    UpdateStatement<EntityBean> update = set.where(pk.eq(id))
        .and(CriteriaPredicate.of(rev, PredicateOperator.EQ, id.getRevision())).get();
    long updateCount = update(update);
    if (updateCount == 0) {
      throw new OptimisicLockException(id, entity.getType().getQualifiedName());
    }
    assert (updateCount == 1);
    pk.set(newId);
    holder.update(entity);
  }

  @Override
  protected long executeSql(DbPlainStatement plainStatement, Consumer<DbResult> receiver, boolean unique) {

    Objects.requireNonNull(plainStatement);
    Connection connection = getTx().getConnection();
    DbPlainStatement current = plainStatement;
    long count = 0;
    boolean resultReceived = false;
    while (current != null) {
      String sql = current.getStatement();
      LOG.debug("Executing SQL:\n{}", sql);
      try (PreparedStatement jdbcStatement = connection.prepareStatement(sql)) {
        AbstractCriteriaParameters parameters = current.getParameters().cast();
        parameters.apply(jdbcStatement, connection);
        if (receiver == null) {
          count += jdbcStatement.executeLargeUpdate();
        } else {
          boolean dataResult = jdbcStatement.execute();
          if (dataResult) {
            resultReceived = true;
            JdbcResult jdbcResult = null;
            do {
              try (ResultSet resultSet = jdbcStatement.getResultSet()) {
                if (jdbcResult == null) {
                  jdbcResult = new JdbcResult(resultSet);
                } else {
                  jdbcResult.setResultSet(resultSet);
                }
                while (resultSet.next()) {
                  receiver.accept(jdbcResult);
                  count++;
                  if (unique) {
                    resultSet.last();
                    int size = resultSet.getRow();
                    throw new NonUniqueResultException(size, sql);
                  }
                }
              }
            } while (jdbcStatement.getMoreResults());
          } else {
            count = jdbcStatement.getUpdateCount();
          }
        }
      } catch (SQLException e) {
        // TODO proper custom runtinme exception class and error message including the SQL
        throw new IllegalStateException("Failed to execute SQL: " + sql, e);
      }
      current = current.getNext();
    }
    if (receiver != null) {
      assert resultReceived : "No result received!";
    }
    return count;
  }

  @Override
  public IdSequence createIdSequence(DbQualifiedName sequenceName) {

    return new JdbcSequence(sequenceName, this.sourceConfig);
  }

}
