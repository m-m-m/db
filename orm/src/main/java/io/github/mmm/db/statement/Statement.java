/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.statement;

/// A complete SQL statement that may be executed to the database.
///
/// @since 1.0.0
public abstract interface Statement {

  /// @return the [type][DbStatementType] of this [Statement].
  DbStatementType getType();

}
