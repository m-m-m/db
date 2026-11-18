package io.github.mmm.db.ddl.table.operation;

import io.github.mmm.db.ddl.constraint.DbConstraint;

/// [TableConstraintOperation] to add a constraint.
///
/// @since 1.0.0
public class TableAddConstraintOperation extends TableConstraintOperation {

  /// The constructor.
  ///
  /// @param constraint the [constraint][#getConstraint()] to add.
  public TableAddConstraintOperation(DbConstraint constraint) {

    super(constraint);
  }

  @Override
  public TableOperationType getType() {

    return TableOperationType.ADD;
  }

}
