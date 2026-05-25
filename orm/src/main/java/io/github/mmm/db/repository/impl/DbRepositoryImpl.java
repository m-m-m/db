/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.repository.impl;

import io.github.mmm.db.dialect.AbstractDbDialect;
import io.github.mmm.db.name.DbQualifiedName;
import io.github.mmm.db.repository.DbRepository;
import io.github.mmm.db.repository.operation.DbDdlOperations;
import io.github.mmm.db.repository.operation.EntityFindAllOperation;
import io.github.mmm.db.repository.spi.AbstractEntityRepository;
import io.github.mmm.db.sequence.IdSequencePooled;
import io.github.mmm.db.source.DbSource;
import io.github.mmm.db.source.DbSourceConfig;
import io.github.mmm.db.spi.access.AbstractDbAccess;
import io.github.mmm.db.spi.access.DbAccess;
import io.github.mmm.db.statement.DbStatement;
import io.github.mmm.db.statement.create.CreateIndexStatement;
import io.github.mmm.db.statement.create.CreateSequenceClause;
import io.github.mmm.db.statement.create.CreateSequenceStatement;
import io.github.mmm.db.statement.delete.DeleteStatement;
import io.github.mmm.db.statement.select.SelectStatement;
import io.github.mmm.db.statement.update.UpdateStatement;
import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.id.Id;
import io.github.mmm.entity.id.generator.IdGenerator;
import io.github.mmm.entity.id.generator.SequenceIdGenerator;
import io.github.mmm.entity.id.generator.UuidIdGenerator;
import io.github.mmm.entity.id.sequence.IdSequence;
import io.github.mmm.entity.property.id.FkProperty;
import io.github.mmm.entity.property.link.LinkProperty;
import io.github.mmm.property.WritableProperty;

/**
 * Abstract base implementation of {@link DbRepository}.
 *
 * @param <E> type of the managed {@link EntityBean}.
 * @since 1.0.0
 */
public class DbRepositoryImpl<E extends EntityBean> extends AbstractEntityRepository<E>
    implements DbRepository<E>, EntityFindAllOperation<E>, DbDdlOperations<E> {

  private final AbstractDbAccess dbAccess;

  private final DbSource source;

  private final DbSourceConfig sourceConfig;

  /** {@link IdGenerator} used to {@link IdGenerator#generate(Id) generate} new unique {@link Id}s. */
  private final IdGenerator idGenerator;

  private final String sequenceName;

  /**
   * The constructor.
   *
   * @param prototype the {@link #getPrototype() prototype}.
   * @param proxy the dynamic proxy instance of the {@link DbRepository} acting as wrapper of this internal instance.
   */
  public DbRepositoryImpl(E prototype, DbRepository<E> proxy) {

    super(prototype);
    this.source = proxy.getSource();
    this.sourceConfig = DbSourceConfig.of(this.source);
    this.dbAccess = (AbstractDbAccess) DbAccess.get(this.sourceConfig);
    AbstractDbDialect<?> dialect = this.dbAccess.getDialect();
    this.sequenceName = proxy.getSequenceName();
    if (dialect.isSupportingSequence()) {
      if (this.sequenceName == null) {
        this.idGenerator = new UuidIdGenerator();
      } else {
        IdSequence idSequence = this.dbAccess
            .createIdSequence(this.sourceConfig.getQualifiedNameTemplate().withName(this.sequenceName));
        int sequenceIncrement = this.sourceConfig.getSequenceIncrement();
        if (sequenceIncrement > 1) {
          idSequence = new IdSequencePooled(idSequence, sequenceIncrement);
        }
        this.idGenerator = new SequenceIdGenerator(idSequence);
      }
    } else {
      IdSequence idSequence = this.dbAccess.createIdSequence(new DbQualifiedName("none"));
      this.idGenerator = new SequenceIdGenerator(idSequence);
    }
  }

  /**
   * @return the {@link DbSourceConfig} for convenient access.
   */
  protected DbSourceConfig getSourceConfig() {

    return this.sourceConfig;
  }

  @Override
  public String getSequenceName() {

    return this.sequenceName;
  }

  @Override
  protected IdGenerator getIdGenerator() {

    return this.idGenerator;
  }

  @Override
  public E doFindById(Id<E> id) {

    return this.dbAccess.selectById(id, this.prototype);
  }

  @Override
  public E findOneByQuery(SelectStatement<E> statement) {

    verifyEntityClass(statement.getSelect().getResultBean().getType().getClass());
    return this.dbAccess.selectOne(statement);
  }

  @Override
  public Iterable<E> findByQuery(SelectStatement<E> statement) {

    verifyEntityClass(statement.getSelect().getResultBean().getJavaClass());
    return this.dbAccess.select(statement);
  }

  @Override
  public Iterable<E> findAll() {

    SelectStatement<E> statement = DbStatement.select(this.prototype).get();
    return this.dbAccess.select(statement);
  }

  @Override
  public long delete(DeleteStatement<E> statement) {

    return this.dbAccess.delete(statement);
  }

  @Override
  protected boolean doDeleteById(Id<E> id) {

    return this.dbAccess.deleteById(id, this.prototype);
  }

  @Override
  protected int doDeleteAllById(Iterable<Id<E>> ids) {

    return this.dbAccess.deleteAllById(ids, this.prototype);
  }

  @Override
  protected E doInsert(E entity) {

    return (E) this.dbAccess.insert(entity);
  }

  @Override
  protected E doUpdate(E entity) {

    return (E) this.dbAccess.update(entity);
  }

  @Override
  public long update(UpdateStatement<E> statement) {

    return this.dbAccess.update(statement);
  }

  /**
   * Create the table for the managed entity.
   */
  @Override
  public void createTable() {

    this.dbAccess.createTable(this.prototype);
  }

  @Override
  public void createIndexes() {

    for (WritableProperty<?> property : this.prototype.getProperties()) {
      CreateIndexStatement<E> createIndexStatement = createIndex(property);
      if (createIndexStatement != null) {
        this.dbAccess.createIndex(createIndexStatement);
      }
    }
  }

  @Override
  public CreateIndexStatement<E> createIndex(WritableProperty<?> property) {

    Class<?> targetEntity = null;
    if (property instanceof FkProperty<?> fkProperty) {
      targetEntity = fkProperty.get().getEntityClass();
    } else if (property instanceof LinkProperty<?> linkProperty) {
      targetEntity = linkProperty.getEntityClass();
    }
    if (targetEntity != null) {
      return DbStatement.createIndex().on(this.prototype).column(property).get();
    }
    return null;
  }

  @Override
  public void createSequence() {

    AbstractDbDialect<?> dialect = this.dbAccess.getDialect();
    if (!dialect.isSupportingSequence()) {
      return;
    }
    if (this.sequenceName == null) {
      return;
    }
    CreateSequenceStatement createSequenceStatement = new CreateSequenceClause(this.sequenceName)
        .incrementBy(this.sourceConfig.getSequenceIncrement()).startWith(1000000000000L).minValue(1000000000000L - 1)
        .maxValue(9123456789123456789L).nocycle().get();
    this.dbAccess.createSequence(createSequenceStatement);
  }

}
