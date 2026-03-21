/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.statement;

import java.util.List;

import io.github.mmm.bean.WritableBean;
import io.github.mmm.db.ddl.index.DbIndexKindType;
import io.github.mmm.db.name.DbQualifiedName;
import io.github.mmm.db.statement.create.CreateIndexClause;
import io.github.mmm.db.statement.create.CreateTableClause;
import io.github.mmm.db.statement.delete.DeleteClause;
import io.github.mmm.db.statement.delete.DeleteFromClause;
import io.github.mmm.db.statement.drop.DropTableClause;
import io.github.mmm.db.statement.insert.InsertClause;
import io.github.mmm.db.statement.insert.InsertIntoClause;
import io.github.mmm.db.statement.merge.MergeClause;
import io.github.mmm.db.statement.merge.MergeIntoClause;
import io.github.mmm.db.statement.select.SelectEntityClause;
import io.github.mmm.db.statement.select.SelectFromClause;
import io.github.mmm.db.statement.select.SelectProjectionClause;
import io.github.mmm.db.statement.select.SelectSequenceNextValueClause;
import io.github.mmm.db.statement.select.SelectSingleClause;
import io.github.mmm.db.statement.select.SelectStatement;
import io.github.mmm.db.statement.update.UpdateClause;
import io.github.mmm.db.statement.upsert.UpsertClause;
import io.github.mmm.db.statement.upsert.UpsertInto;
import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.marshall.MarshallingObject;
import io.github.mmm.value.CriteriaObject;

/**
 * A complete SQL statement that may be executed to the database.
 *
 * @param <E> type of the entity or object this statement primarily operates on.
 * @since 1.0.0
 */
public abstract interface DbStatement<E> extends MarshallingObject {

  /**
   * @return the {@link DbStartClause}
   */
  DbStartClause getStart();

  /**
   * @return the {@link List} of {@link DbClause}s this {@link DbStatement} is composed of. Please note that this is a
   *         generic API. Specific sub-classes implementing {@link DbStatement} will have dedicated getters for each
   *         type of {@link DbClause}.
   */
  List<? extends DbClause> getClauses();

  /**
   * @return the {@link DbStatementType type} of this {@link DbStatement statement}.
   */
  DbStatementType getType();

  /**
   * Alternative for {@code new SelectEntity<>(entity).from()}.
   *
   * @param <E> type of the {@link EntityBean} to select.
   * @param entity the {@link EntityBean} to select.
   * @return the new {@link SelectEntityClause} clause.
   */
  public static <E extends EntityBean> SelectFromClause<E, E> select(E entity) {

    return new SelectEntityClause<>(entity).from();
  }

  /**
   * Alternative for {@code new SelectSingle<>(selection)}.
   *
   * @param <R> type of the result of the selection.
   * @param selection the single {@link CriteriaObject} to select.
   * @return the new {@link SelectSingleClause} clause.
   */
  public static <R> SelectSingleClause<R> select(CriteriaObject<R> selection) {

    return new SelectSingleClause<>(selection);
  }

  /**
   * Alternative for {@code new SelectProjection<>(bean)}.
   *
   * @param <R> type of the {@link WritableBean} to select.
   * @param bean the {@link WritableBean} to select.
   * @return the new {@link SelectProjectionClause} clause.
   */
  public static <R extends WritableBean> SelectProjectionClause<R> selectProjection(R bean) {

    return new SelectProjectionClause<>(bean);
  }

  /**
   * @param sequenceName the name of the sequence to select.
   * @return the new {@link SelectStatement} to select the next value from the specified sequence.
   */
  public static SelectStatement<Long> selectSeqNextVal(String sequenceName) {

    return selectSeqNextVal(new DbQualifiedName(sequenceName, null, null));
  }

  /**
   * @param sequenceName the {@link DbQualifiedName} of the sequence to select.
   * @return the new {@link SelectStatement} to select the next value from the specified sequence.
   */
  public static SelectStatement<Long> selectSeqNextVal(DbQualifiedName sequenceName) {

    return new SelectSequenceNextValueClause(sequenceName).get();
  }

  /**
   * Alternative for {@code new Delete().from(entity)}.
   *
   * @param <E> type of the {@link EntityBean} to delete.
   * @param entity the {@link EntityBean} to delete.
   * @return the new {@link DeleteFromClause} clause.
   */
  public static <E extends EntityBean> DeleteFromClause<E> delete(E entity) {

    return new DeleteClause().from(entity);
  }

  /**
   * Alternative for {@code new Insert().into(entity)}.
   *
   * @param <E> type of the {@link EntityBean} to insert.
   * @param entity the {@link EntityBean} to insert.
   * @return the new {@link InsertIntoClause} clause.
   */
  public static <E extends EntityBean> InsertIntoClause<E> insert(E entity) {

    return new InsertClause().into(entity);
  }

  /**
   * Alternative for {@code new Update<>(entity)}.
   *
   * @param <E> type of the {@link EntityBean} to update.
   * @param entity the {@link EntityBean} to update.
   * @return the new {@link UpdateClause} clause.
   */
  public static <E extends EntityBean> UpdateClause<E> update(E entity) {

    return new UpdateClause<>(entity);
  }

  /**
   * Alternative for {@code new Upsert().into(entity)}.
   *
   * @param <E> type of the {@link EntityBean} to update.
   * @param entity the {@link EntityBean} to update.
   * @return the new {@link UpsertInto} clause.
   */
  public static <E extends EntityBean> UpsertInto<E> upset(E entity) {

    return new UpsertClause().into(entity);
  }

  /**
   * Alternative for {@code new Merge().into(entity)}.
   *
   * @param <E> type of the {@link EntityBean} to update.
   * @param entity the {@link EntityBean} to update.
   * @return the new {@link UpsertInto} clause.
   */
  public static <E extends EntityBean> MergeIntoClause<E> merge(E entity) {

    return new MergeClause().into(entity);
  }

  /**
   * Alternative for {@code new CreateTable<>(entity)}.
   *
   * @param <E> type of the {@link EntityBean} to create the table for.
   * @param entity the {@link EntityBean} to create the table for.
   * @return the new {@link CreateTableClause} clause.
   */
  public static <E extends EntityBean> CreateTableClause<E> createTable(E entity) {

    return new CreateTableClause<>(entity);
  }

  /**
   * Alternative for {@code new DropTable<>(entity)}.
   *
   * @param <E> type of the {@link EntityBean} to drop the table.
   * @param entity the {@link EntityBean} to create drop the table.
   * @return the new {@link DropTableClause} clause.
   */
  public static <E extends EntityBean> DropTableClause<E> dropTable(E entity) {

    return new DropTableClause<>(entity);
  }

  /**
   * Creates a default {@link CreateIndexClause}.
   *
   * @return the new {@link CreateIndexClause}.
   * @see CreateIndexClause#on(EntityBean)
   * @see #createIndex(String)
   */
  public static CreateIndexClause createIndex() {

    return createIndex((String) null);
  }

  /**
   * Creates a default {@link CreateIndexClause} with the given name.
   *
   * @param indexName the {@link CreateIndexClause#getName() the name of the index}.
   * @return the new {@link CreateIndexClause}.
   * @see CreateIndexClause#on(EntityBean)
   */
  public static CreateIndexClause createIndex(String indexName) {

    return new CreateIndexClause(DbIndexKindType.DEFAULT, indexName);
  }

  /**
   * Creates a {@link CreateIndexClause} with the given {@link DbIndexKindType}.
   *
   * @param kind the {@link DbIndexKindType}.
   * @return the new {@link CreateIndexClause}.
   * @see CreateIndexClause#on(EntityBean)
   */
  public static CreateIndexClause createIndex(DbIndexKindType kind) {

    return new CreateIndexClause(kind, "");
  }

  /**
   * Creates a {@link CreateIndexClause} with the given {@link DbIndexKindType} and name.
   *
   * @param kind the {@link DbIndexKindType}.
   * @param indexName the {@link CreateIndexClause#getName() the name of the index}.
   * @return the new {@link CreateIndexClause}.
   * @see CreateIndexClause#on(EntityBean)
   */
  public static CreateIndexClause createIndex(DbIndexKindType kind, String indexName) {

    return new CreateIndexClause(kind, indexName);
  }

  /**
   * Creates a {@link io.github.mmm.db.ddl.index.DbIndexKind#isUnique() unique} {@link CreateIndexClause}.
   *
   * @return the new {@link CreateIndexClause}.
   * @see CreateIndexClause#on(EntityBean)
   * @see #createUniqueIndex(String)
   */
  public static CreateIndexClause createUniqueIndex() {

    return createIndex(DbIndexKindType.DEFAULT_UNIQUE);
  }

  /**
   * Creates a {@link io.github.mmm.db.ddl.index.DbIndexKind#isUnique() unique} {@link CreateIndexClause} with the given
   * name.
   *
   * @param indexName the {@link CreateIndexClause#getName() the name of the index}.
   * @return the new {@link CreateIndexClause}.
   * @see CreateIndexClause#on(EntityBean)
   */
  public static CreateIndexClause createUniqueIndex(String indexName) {

    return createIndex(DbIndexKindType.DEFAULT_UNIQUE, indexName);
  }

  /**
   * Creates a {@link io.github.mmm.db.ddl.index.DbIndexKind#isClustered() clustered} {@link CreateIndexClause}.
   *
   * @return the new {@link CreateIndexClause}.
   * @see CreateIndexClause#on(EntityBean)
   * @see #createUniqueIndex(String)
   */
  public static CreateIndexClause createClusteredIndex() {

    return createIndex(DbIndexKindType.DEFAULT_CLUSTERED);
  }

  /**
   * Creates a {@link io.github.mmm.db.ddl.index.DbIndexKind#isClustered() clustered} {@link CreateIndexClause} with the
   * given name.
   *
   * @param indexName the {@link CreateIndexClause#getName() the name of the index}.
   * @return the new {@link CreateIndexClause}.
   * @see CreateIndexClause#on(EntityBean)
   */
  public static CreateIndexClause createClusteredIndex(String indexName) {

    return createIndex(DbIndexKindType.DEFAULT_UNIQUE, indexName);
  }
}
