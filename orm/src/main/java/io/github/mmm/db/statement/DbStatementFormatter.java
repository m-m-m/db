/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.statement;

import io.github.mmm.db.dialect.DbContext;
import io.github.mmm.db.impl.DbContextNone;

/// Interface of a formatter for [DbStatement]s.
///
/// @see BasicDbStatementFormatter
public interface DbStatementFormatter {

  /// @param statement the [DbStatement] to format.
  /// @param context the [DbContext].
  /// @return this [DbStatementFormatter] for fluent API calls.
  default DbNativeStatement formatStatement(DbStatement<?> statement) {

    return formatStatement(statement, DbContextNone.INSTANCE);
  }

  /// @param statement the [DbStatement] to format.
  /// @param context the [DbContext].
  /// @return this [DbStatementFormatter] for fluent API calls.
  DbNativeStatement formatStatement(DbStatement<?> statement, DbContext context);

  /// @return the [formatted statement][#formatStatement(DbStatement, DbContext)] as [String] (e.g. SQL).
  @Override
  String toString();
}
