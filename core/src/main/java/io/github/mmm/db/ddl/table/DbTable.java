/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.ddl.table;

import java.util.List;

import io.github.mmm.db.ddl.column.DbColumn;
import io.github.mmm.db.ddl.constraint.DbForeignKeyConstraint;
import io.github.mmm.db.ddl.constraint.DbPrimaryKeyConstraint;
import io.github.mmm.db.ddl.index.DbIndexMetadata;

/**
 * Meta-information about a database table (or view, materialized view, etc.). Extends {@link DbTableData} with advanced
 * details like {@link #getColumns() columns}, {@link #getPrimaryKey() primary key}, {@link #getForeignKeys() foreign
 * keys}, and {@link #getIndexes() indexes}.
 *
 * @since 1.0.0
 */
public interface DbTable extends DbTableData {

  /**
   * @return the {@link DbPrimaryKeyConstraint primary key}.
   */
  DbPrimaryKeyConstraint getPrimaryKey();

  /**
   * @return the {@link List} of {@link DbColumn columns} of this table.
   */
  List<DbColumn> getColumns();

  /**
   * @return the {@link List} of {@link DbForeignKeyConstraint}s of this table.
   */
  List<DbForeignKeyConstraint> getForeignKeys();

  /**
   * @return the {@link List} of {@link DbIndexMetadata indexes} of this table.
   */
  List<DbIndexMetadata> getIndexes();

}
