/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.ddl.table.operation;

/// Enum with the available kinds of a [TableOperation].
///
/// @see TableOperation#getKind()
public enum TableOperationKind {

  /// Operation kind for [column][io.github.mmm.db.ddl.column.DbColumnReference].
  COLUMN,

  /// Operation kind for [constraint][io.github.mmm.db.ddl.constraint.DbConstraint].
  CONSTRAINT;

}
