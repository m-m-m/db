/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.statement;

import java.util.Locale;

import io.github.mmm.db.statement.impl.DbStatementTypeImpl;

/// Interface for the type of a [DbStatement].
///
/// @since 1.0.0
/// @see DbStatement#getType()
public interface DbStatementType {

  /// @return `true` for a data-definition-language statement such as e.g.
  ///         [io.github.mmm.db.statement.create.CreateTableStatement],
  ///         [io.github.mmm.db.statement.create.CreateIndexStatement],
  ///         [io.github.mmm.db.statement.alter.AlterTableStatement] or
  ///         [io.github.mmm.db.statement.drop.DropTableStatement], `false` otherwise.
  boolean isDdl();

  /// @return `true` for a data-manipulation-language statement such as e.g.
  ///         [io.github.mmm.db.statement.insert.InsertStatement],
  ///         [io.github.mmm.db.statement.update.UpdateStatement],
  ///         [io.github.mmm.db.statement.delete.DeleteStatement], or even a
  ///         [io.github.mmm.db.statement.select.SelectStatement], `false` otherwise.
  boolean isDml();

  /// @return `true` for a [io.github.mmm.db.statement.select.SelectStatement], `false` otherwise.
  boolean isQuery();

  /// @return the statement keyword (e.g. "SELECT" or "CREATE TABLE").
  String getKeyword();

  /// @param keyword the [keyword][#getKeyword()].
  /// @return the [DbStatementType] or `null` if not found.
  public static DbStatementType of(String keyword) {

    keyword = keyword.toUpperCase(Locale.ROOT);
    for (DbStatementTypeImpl type : DbStatementTypeImpl.values()) {
      if (type.getKeyword().equals(keyword)) {
        return type;
      }
    }
    return null;
  }

}
