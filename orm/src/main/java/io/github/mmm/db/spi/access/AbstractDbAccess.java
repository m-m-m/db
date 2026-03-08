package io.github.mmm.db.spi.access;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

import io.github.mmm.db.ddl.DbMetaData;
import io.github.mmm.db.ddl.table.DbTable;
import io.github.mmm.db.dialect.AbstractDbDialect;
import io.github.mmm.db.dialect.DbDialect;
import io.github.mmm.db.dialect.DbDialectProvider;
import io.github.mmm.db.mapping.DbMapper;
import io.github.mmm.db.mapping.DbMapper2Java;
import io.github.mmm.db.name.DbQualifiedName;
import io.github.mmm.db.orm.Orm;
import io.github.mmm.db.result.DbResult;
import io.github.mmm.db.source.DbSourceConfig;
import io.github.mmm.db.spi.access.impl.DbMapperRetrievalAdapter;
import io.github.mmm.db.statement.BasicDbStatementFormatter;
import io.github.mmm.db.statement.DbPlainStatement;
import io.github.mmm.db.statement.DbStatement;
import io.github.mmm.db.statement.create.CreateIndexStatement;
import io.github.mmm.db.statement.create.CreateSequenceStatement;
import io.github.mmm.db.statement.create.CreateTableStatement;
import io.github.mmm.db.statement.delete.DeleteClause;
import io.github.mmm.db.statement.delete.DeleteStatement;
import io.github.mmm.db.statement.insert.InsertStatement;
import io.github.mmm.db.statement.merge.MergeStatement;
import io.github.mmm.db.statement.select.SelectClause;
import io.github.mmm.db.statement.select.SelectStatement;
import io.github.mmm.db.statement.update.UpdateStatement;
import io.github.mmm.db.statement.upsert.UpsertStatement;
import io.github.mmm.db.tx.spi.AbstractDbTransaction;
import io.github.mmm.db.tx.spi.DbEntitySession;
import io.github.mmm.db.tx.spi.DbEntitySessionFactory;
import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.id.Id;
import io.github.mmm.entity.id.sequence.IdSequence;

/**
 * Abstract base implementation of {@link DbAccess}.
 *
 * @since 1.0.0
 */
public abstract class AbstractDbAccess implements DbAccess {

  /** The {@link DbSourceConfig}. */
  protected final DbSourceConfig sourceConfig;

  /** @see #getDialect() */
  protected final AbstractDbDialect<?> dialect;

  private final DbEntitySessionFactory factory;

  /**
   * The constructor.
   *
   * @param sourceConfig the {@link DbSourceConfig}.
   */
  public AbstractDbAccess(DbSourceConfig sourceConfig) {

    this(sourceConfig, DbEntitySessionFactory.get());
  }

  /**
   * The constructor.
   *
   * @param sourceConfig the {@link DbSourceConfig}.
   * @param factory the {@link DbEntitySessionFactory}.
   */
  public AbstractDbAccess(DbSourceConfig sourceConfig, DbEntitySessionFactory factory) {

    super();
    this.sourceConfig = sourceConfig;
    this.factory = factory;
    this.dialect = (AbstractDbDialect<?>) DbDialectProvider.get().get(sourceConfig.getDialect());
  }

  /**
   * @return the current database transaction.
   */
  protected AbstractDbTransaction getTx() {

    return AbstractDbTransaction.getRequired(this.sourceConfig.getSource());
  }

  /**
   * @param <E> type of the {@link EntityBean}.
   * @param entity the {@link EntityBean}.
   * @return the {@link DbEntitySession} will be created via {@link DbEntitySessionFactory factory} if not yet exist.
   */
  protected <E extends EntityBean> DbEntitySession<E> getEntitySession(E entity) {

    return getTx().getEntitySession(entity, this.factory);
  }

  /**
   * @return the {@link DbDialect}.
   */
  public AbstractDbDialect<?> getDialect() {

    return this.dialect;
  }

  /**
   * Generic method to execute a {@link DbStatement}. Convenient for statements that do not return a {@link DbResult
   * result}. Otherwise use {@link #executeStatement(DbStatement, Consumer, boolean)}.
   *
   * @param statement the {@link DbStatement} to execute.
   * @return the number of rows that have been updated or selected.
   * @see #executeStatement(DbStatement, Consumer, boolean)
   */
  protected long executeStatement(DbStatement<?> statement) {

    return executeStatement(statement, null, true);
  }

  /**
   * Generic method to execute a {@link DbStatement}.
   *
   * @param statement the {@link DbStatement} to execute.
   * @param receiver the {@link Consumer} of a potential {@link DbResult} (e.g. for SELECT) or {@code null} if no result
   *        is expected (e.g. for INSERT or DELETE).
   * @param unique {@code true} if a unique {@link DbResult} is expected and an
   *        {@link io.github.mmm.db.statement.NonUniqueResultException} should be thrown if multiple results have been
   *        received.
   * @return the number of rows that have been updated or selected.
   * @see #executeStatement(DbStatement)
   * @see #executeSql(DbPlainStatement, Consumer, boolean)
   */
  protected long executeStatement(DbStatement<?> statement, Consumer<DbResult> receiver, boolean unique) {

    BasicDbStatementFormatter formatter = getDialect().createFormatter();
    DbPlainStatement plainStatement = formatter.formatStatement(statement);
    return executeSql(plainStatement, receiver, unique);
  }

  /**
   * Low-level and DB/orm specific method method to execute a query.
   *
   * @param statement the {@link DbPlainStatement}.
   * @param receiver the {@link Consumer} of a potential {@link DbResult} (e.g. for SELECT) or {@code null} if no result
   *        is expected (e.g. for INSERT or DELETE).
   * @param unique {@code true} if a unique {@link DbResult} is expected and an
   *        {@link io.github.mmm.db.statement.NonUniqueResultException} should be thrown if multiple results have been
   *        received.
   * @return the number of rows that have been updated or selected.
   */
  protected abstract long executeSql(DbPlainStatement statement, Consumer<DbResult> receiver, boolean unique);

  @Override
  public void createTable(CreateTableStatement<?> statement) {

    executeStatement(statement);
  }

  @Override
  public void createIndex(CreateIndexStatement<?> statement) {

    executeStatement(statement);
  }

  @Override
  public void createSequence(CreateSequenceStatement statement) {

    executeStatement(statement);
  }

  @Override
  public long delete(DeleteStatement<?> statement) {

    return executeStatement(statement);
  }

  @Override
  public <E extends EntityBean> boolean deleteById(Id<E> id, E prototype) {

    if ((id == null) || (id.getPk() == null)) {
      return false;
    }
    DeleteStatement<E> statement = new DeleteClause().from(prototype).where(prototype.Id().eq(id)).get();
    long count = delete(statement);
    if (count > 0) {
      assert (count == 1);
      return true;
    } else {
      assert (count == 0);
      return false;
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public <E extends EntityBean> int deleteAllById(Iterable<Id<E>> ids, E prototype) {

    if (ids == null) {
      return 0;
    }
    Collection<Id<?>> idCollection;
    if (ids instanceof Collection c) {
      idCollection = c;
    } else {
      idCollection = new ArrayList<>();
      for (Id<E> id : ids) {
        idCollection.add(id);
      }
    }
    if (idCollection.isEmpty()) {
      return 0;
    }
    DeleteStatement<E> statement = new DeleteClause().from(prototype).where(prototype.Id().in(idCollection)).get();
    long count = executeStatement(statement);
    assert (count >= 0) && (count < Integer.MAX_VALUE);
    return (int) count;
  }

  @Override
  public long insert(InsertStatement<?> statement) {

    return executeStatement(statement);
  }

  @Override
  public long execute(MergeStatement<?> statement) {

    return executeStatement(statement);
  }

  @Override
  public long update(UpdateStatement<?> statement) {

    return executeStatement(statement);
  }

  @Override
  public long execute(UpsertStatement<?> statement) {

    return executeStatement(statement);
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public <R> Iterable<R> select(SelectStatement<R> statement) {

    Orm orm = getDialect().getOrm();
    SelectClause<R> select = statement.getSelect();
    DbMapper2Java<R> mapper = orm.createMapper(select);
    if (select.isSelectEntity()) {
      DbEntitySession dbEntitySession = getEntitySession((EntityBean) select.getResultBean());
      mapper = new DbMapperRetrievalAdapter(mapper, dbEntitySession);
    }
    DbResultReceiverMultiple<R> receiver = new DbResultReceiverMultiple<>(mapper);
    executeStatement(statement, receiver, false);
    return receiver.getResults();
  }

  @Override
  public <R> R selectOne(SelectStatement<R> statement) {

    Orm orm = getDialect().getOrm();
    SelectClause<R> select = statement.getSelect();
    DbMapper<R> mapper = orm.createMapper(select);
    DbResultReceiverSingle<R> receiver = new DbResultReceiverSingle<>(mapper);
    executeStatement(statement, receiver, true);
    return receiver.getResult();
  }

  /**
   * @param sequenceName the {@link DbQualifiedName} of the database sequence.
   * @return the {@link IdSequence} implementation.
   */
  public abstract IdSequence createIdSequence(DbQualifiedName sequenceName);

  @Override
  public void syncTable(EntityBean entity) {

    DbMetaData metaData = getTx().getMetaData();
    DbQualifiedName tableName = getDialect().getNamingStrategy().getTableName(entity);
    DbTable table = metaData.getTable(tableName);
    if (table == null) {
      createTable(entity);
    } else {
      // alterTable
      // executeStatement();
    }
  }
}
