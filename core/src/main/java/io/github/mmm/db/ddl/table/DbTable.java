/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.ddl.table;

import java.util.List;

import io.github.mmm.db.ddl.column.DbColumn;
import io.github.mmm.db.ddl.constraint.DbForeignKeyConstraint;
import io.github.mmm.db.ddl.constraint.DbPrimaryKeyConstraint;
import io.github.mmm.db.ddl.index.DbIndexMetadata;

/// Meta-information about a database table (or view, materialized view, etc.). Extends [DbTableData] with advanced details like
/// [columns][#getColumns()], [primary key][#getPrimaryKey()], [foreign keys][#getForeignKeys()], and [indexes][#getIndexes()].
///
/// @since 1.0.0
public interface DbTable extends DbTableData {

  /// @return the [primary key][DbPrimaryKeyConstraint].
  DbPrimaryKeyConstraint getPrimaryKey();

  /// @return the [List] of [columns][DbColumn] of this table.
  List<DbColumn> getColumns();

  /// @return the [List] of [DbForeignKeyConstraint]s of this table.
  List<DbForeignKeyConstraint> getForeignKeys();

  /// @return the [List] of [indexes][DbIndexMetadata] of this table.
  List<DbIndexMetadata> getIndexes();

}
