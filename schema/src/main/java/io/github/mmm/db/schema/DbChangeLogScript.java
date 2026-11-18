/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.schema;

import io.github.mmm.base.resource.ResourceFile;

/// A single script [file][#getFile()] wrapped with additional information such as [database type][#getDbType()] or [extension][#getExtension()].
public interface DbChangeLogScript extends Comparable<DbChangeLogScript> {

  /// The default [extension][#getExtension()].
  String EXTENSION_SQL = "sql";

  /// @return the [ResourceFile] containing the migration script.
  ResourceFile getFile();

  /// @return the optional database type (e.g. "postgres" or "h2") or `null` if generic. Most scripts are generic and can be applied to any type of database so
  ///         they will return `null` here. Otherwise the script needs to be skipped if your current database does not match this specific database type.
  String getDbType();

  /// @return the script extension. Should be [#EXTENSION_SQL] but in the future other extensions could also be supported.
  String getExtension();

  @Override
  default int compareTo(DbChangeLogScript other) {

    if (other == null) {
      return 1;
    }
    return getFile().compareTo(other.getFile());
  }

}
