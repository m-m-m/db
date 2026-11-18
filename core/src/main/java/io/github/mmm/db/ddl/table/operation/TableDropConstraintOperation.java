package io.github.mmm.db.ddl.table.operation;

import io.github.mmm.db.ddl.constraint.DbConstraint;

/// [TableConstraintOperation] to drop a constraint.
///
/// @since 1.0.0
public class TableDropConstraintOperation extends TableConstraintOperation {

  /// The constructor.
  ///
  /// @param name the name of the [constraint][#getConstraint()] to drop.
  public TableDropConstraintOperation(String name) {

    super(name);
  }

  /// The constructor.
  ///
  /// @param constraint the [constraint][#getConstraint()] to drop.
  public TableDropConstraintOperation(DbConstraint constraint) {

    super(constraint);
  }

  @Override
  public TableOperationType getType() {

    return TableOperationType.DROP;
  }

}
