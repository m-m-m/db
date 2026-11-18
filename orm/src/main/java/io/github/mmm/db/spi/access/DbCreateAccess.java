/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.spi.access;

import io.github.mmm.db.statement.create.CreateIndexStatement;
import io.github.mmm.db.statement.create.CreateSequenceStatement;
import io.github.mmm.db.statement.create.CreateTableClause;
import io.github.mmm.db.statement.create.CreateTableStatement;
import io.github.mmm.entity.bean.EntityBean;

/// Interface providing support for [createTable][#createTable(CreateTableStatement)],
/// [createIndex][#createIndex(CreateIndexStatement)], and [createSequence][#createSequence(CreateSequenceStatement)].
///
/// @since 1.0.0
public interface DbCreateAccess {

  /// @param statement the [CreateTableStatement] to execute.
  void createTable(CreateTableStatement<?> statement);

  /// @param entity the [EntityBean] prototype to create the database table for.
  default void createTable(EntityBean entity) {

    CreateTableStatement<EntityBean> statement = new CreateTableClause<>(entity).columns().get();
    createTable(statement);
  }

  /// Synchronise the database table for the given [EntityBean]. If the table does not exist, it will be
  /// [created][#createTable(EntityBean)], otherwise missing columns will be added. If all is up-to-date, nothing
  /// needs to be done.
  ///
  /// @param entity the [EntityBean] prototype to sync the database table for.
  void syncTable(EntityBean entity);

  /// @param statement the [CreateIndexStatement] to execute.
  void createIndex(CreateIndexStatement<?> statement);

  /// @param statement the [CreateSequenceStatement] to execute.
  void createSequence(CreateSequenceStatement statement);

}
