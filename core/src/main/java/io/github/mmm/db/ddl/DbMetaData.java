/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.ddl;

import io.github.mmm.db.ddl.table.DbTable;
import io.github.mmm.db.ddl.table.DbTableData;
import io.github.mmm.db.name.DbQualifiedName;

/// Meta-information about the database (DDL).
///
/// @since 1.0.0
public interface DbMetaData {

  /// @return an [Iterable] with the [DbTableData] of all tables in the current catalog/schema.
  default Iterable<DbTableData> getTables() {

    return getTables(null);
  }

  /// @param name the [name][DbQualifiedName#getName()] or name-pattern of the tables.
  /// @return an [Iterable] with the [DbTableData] of the matching tables in the current catalog/schema.
  Iterable<DbTableData> getTables(String name);

  /// @param catalog the [catalog][DbQualifiedName#getCatalog()]. May be `null`.
  /// @param schema the [schema][DbQualifiedName#getSchema()]. May be `null`.
  /// @param name the [name][DbQualifiedName#getName()] or name-pattern of the tables.
  /// @return an [Iterable] with the [DbTableData] of the matching tables.
  Iterable<DbTableData> getTables(String catalog, String schema, String name);

  /// @param name the [qualified table name][DbTable#getName()].
  /// @return the [DbTable] with the given [name][DbTable#getName()] or `null` if no such table exists.
  DbTable getTable(DbQualifiedName name);

  /// @param name the unqualified [table name][DbTable#getName()].
  /// @return the [DbTable] with the given [name][DbTable#getName()] or `null` if no such table exists.
  DbTable getTable(String name);

  /// @param tableData the [DbTableData] (from [#getTables(String, String, String)]).
  /// @return the [DbTable] populated with additional information.
  DbTable getTable(DbTableData tableData);

  /// @return the current [catalog][DbQualifiedName#getCatalog()].
  String getCurrentCatalog();

  /// @return the current [schema][DbQualifiedName#getSchema()].
  String getCurrentSchema();

  /// @param name the [DbQualifiedName].
  /// @return the given [DbQualifiedName] qualified with [current schema][#getCurrentSchema()] and
  ///         [current catalog][#getCurrentCatalog()] where not already qualified.
  default DbQualifiedName qualify(DbQualifiedName name) {

    if (name.getSchema() == null) {
      name = name.withSchema(getCurrentSchema());
    }
    if (name.getCatalog() == null) {
      name = name.withCatalog(getCurrentCatalog());
    }
    return name;
  }

}
