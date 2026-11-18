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

/// A complete SQL statement that may be executed to the database.
///
/// @param <E> type of the entity or object this statement primarily operates on.
/// @since 1.0.0
public abstract interface DbStatement<E> extends Statement, MarshallingObject {

  /// @return the [DbStartClause]
  DbStartClause getStart();

  /// @return the [List] of [DbClause]s this [DbStatement] is composed of. Please note that this is a
  ///         generic API. Specific sub-classes implementing [DbStatement] will have dedicated getters for each
  ///         type of [DbClause].
  List<? extends DbClause> getClauses();

  /// Alternative for `new SelectEntity<>(entity).from()`.
  ///
  /// @param <E> type of the [EntityBean] to select.
  /// @param entity the [EntityBean] to select.
  /// @return the new [SelectEntityClause] clause.
  public static <E extends EntityBean> SelectFromClause<E, E> select(E entity) {

    return new SelectEntityClause<>(entity).from();
  }

  /// Alternative for `new SelectSingle<>(selection)`.
  ///
  /// @param <R> type of the result of the selection.
  /// @param selection the single [CriteriaObject] to select.
  /// @return the new [SelectSingleClause] clause.
  public static <R> SelectSingleClause<R> select(CriteriaObject<R> selection) {

    return new SelectSingleClause<>(selection);
  }

  /// Alternative for `new SelectProjection<>(bean)`.
  ///
  /// @param <R> type of the [WritableBean] to select.
  /// @param bean the [WritableBean] to select.
  /// @return the new [SelectProjectionClause] clause.
  public static <R extends WritableBean> SelectProjectionClause<R> selectProjection(R bean) {

    return new SelectProjectionClause<>(bean);
  }

  /// @param sequenceName the name of the sequence to select.
  /// @return the new [SelectStatement] to select the next value from the specified sequence.
  public static SelectStatement<Long> selectSeqNextVal(String sequenceName) {

    return selectSeqNextVal(new DbQualifiedName(sequenceName, null, null));
  }

  /// @param sequenceName the [DbQualifiedName] of the sequence to select.
  /// @return the new [SelectStatement] to select the next value from the specified sequence.
  public static SelectStatement<Long> selectSeqNextVal(DbQualifiedName sequenceName) {

    return new SelectSequenceNextValueClause(sequenceName).get();
  }

  /// Alternative for `new Delete().from(entity)`.
  ///
  /// @param <E> type of the [EntityBean] to delete.
  /// @param entity the [EntityBean] to delete.
  /// @return the new [DeleteFromClause] clause.
  public static <E extends EntityBean> DeleteFromClause<E> delete(E entity) {

    return new DeleteClause().from(entity);
  }

  /// Alternative for `new Insert().into(entity)`.
  ///
  /// @param <E> type of the [EntityBean] to insert.
  /// @param entity the [EntityBean] to insert.
  /// @return the new [InsertIntoClause] clause.
  public static <E extends EntityBean> InsertIntoClause<E> insert(E entity) {

    return new InsertClause().into(entity);
  }

  /// Alternative for `new Update<>(entity)`.
  ///
  /// @param <E> type of the [EntityBean] to update.
  /// @param entity the [EntityBean] to update.
  /// @return the new [UpdateClause] clause.
  public static <E extends EntityBean> UpdateClause<E> update(E entity) {

    return new UpdateClause<>(entity);
  }

  /// Alternative for `new Upsert().into(entity)`.
  ///
  /// @param <E> type of the [EntityBean] to update.
  /// @param entity the [EntityBean] to update.
  /// @return the new [UpsertInto] clause.
  public static <E extends EntityBean> UpsertInto<E> upset(E entity) {

    return new UpsertClause().into(entity);
  }

  /// Alternative for `new Merge().into(entity)`.
  ///
  /// @param <E> type of the [EntityBean] to update.
  /// @param entity the [EntityBean] to update.
  /// @return the new [UpsertInto] clause.
  public static <E extends EntityBean> MergeIntoClause<E> merge(E entity) {

    return new MergeClause().into(entity);
  }

  /// Alternative for `new CreateTable<>(entity)`.
  ///
  /// @param <E> type of the [EntityBean] to create the table for.
  /// @param entity the [EntityBean] to create the table for.
  /// @return the new [CreateTableClause] clause.
  public static <E extends EntityBean> CreateTableClause<E> createTable(E entity) {

    return new CreateTableClause<>(entity);
  }

  /// Alternative for `new DropTable<>(entity)`.
  ///
  /// @param <E> type of the [EntityBean] to drop the table.
  /// @param entity the [EntityBean] to create drop the table.
  /// @return the new [DropTableClause] clause.
  public static <E extends EntityBean> DropTableClause<E> dropTable(E entity) {

    return new DropTableClause<>(entity);
  }

  /// Creates a default [CreateIndexClause].
  ///
  /// @return the new [CreateIndexClause].
  /// @see CreateIndexClause#on(EntityBean)
  /// @see #createIndex(String)
  public static CreateIndexClause createIndex() {

    return createIndex((String) null);
  }

  /// Creates a default [CreateIndexClause] with the given name.
  ///
  /// @param indexName the [the name of the index][CreateIndexClause#getName()].
  /// @return the new [CreateIndexClause].
  /// @see CreateIndexClause#on(EntityBean)
  public static CreateIndexClause createIndex(String indexName) {

    return new CreateIndexClause(DbIndexKindType.DEFAULT, indexName);
  }

  /// Creates a [CreateIndexClause] with the given [DbIndexKindType].
  ///
  /// @param kind the [DbIndexKindType].
  /// @return the new [CreateIndexClause].
  /// @see CreateIndexClause#on(EntityBean)
  public static CreateIndexClause createIndex(DbIndexKindType kind) {

    return new CreateIndexClause(kind, "");
  }

  /// Creates a [CreateIndexClause] with the given [DbIndexKindType] and name.
  ///
  /// @param kind the [DbIndexKindType].
  /// @param indexName the [the name of the index][CreateIndexClause#getName()].
  /// @return the new [CreateIndexClause].
  /// @see CreateIndexClause#on(EntityBean)
  public static CreateIndexClause createIndex(DbIndexKindType kind, String indexName) {

    return new CreateIndexClause(kind, indexName);
  }

  /// Creates a [unique][io.github.mmm.db.ddl.index.DbIndexKind#isUnique()] [CreateIndexClause].
  ///
  /// @return the new [CreateIndexClause].
  /// @see CreateIndexClause#on(EntityBean)
  /// @see #createUniqueIndex(String)
  public static CreateIndexClause createUniqueIndex() {

    return createIndex(DbIndexKindType.DEFAULT_UNIQUE);
  }

  /// Creates a [unique][io.github.mmm.db.ddl.index.DbIndexKind#isUnique()] [CreateIndexClause] with the given
  /// name.
  ///
  /// @param indexName the [the name of the index][CreateIndexClause#getName()].
  /// @return the new [CreateIndexClause].
  /// @see CreateIndexClause#on(EntityBean)
  public static CreateIndexClause createUniqueIndex(String indexName) {

    return createIndex(DbIndexKindType.DEFAULT_UNIQUE, indexName);
  }

  /// Creates a [clustered][io.github.mmm.db.ddl.index.DbIndexKind#isClustered()] [CreateIndexClause].
  ///
  /// @return the new [CreateIndexClause].
  /// @see CreateIndexClause#on(EntityBean)
  /// @see #createUniqueIndex(String)
  public static CreateIndexClause createClusteredIndex() {

    return createIndex(DbIndexKindType.DEFAULT_CLUSTERED);
  }

  /// Creates a [clustered][io.github.mmm.db.ddl.index.DbIndexKind#isClustered()] [CreateIndexClause] with the
  /// given name.
  ///
  /// @param indexName the [the name of the index][CreateIndexClause#getName()].
  /// @return the new [CreateIndexClause].
  /// @see CreateIndexClause#on(EntityBean)
  public static CreateIndexClause createClusteredIndex(String indexName) {

    return createIndex(DbIndexKindType.DEFAULT_UNIQUE, indexName);
  }
}
