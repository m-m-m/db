package io.github.mmm.db.ddl.table.operation;

import io.github.mmm.db.ddl.constraint.DbConstraint;

/// [TableColumnOperation] to rename a constraint.
///
/// @since 1.0.0
public class TableRenameConstraintOperation extends TableConstraintOperation {

  private final String newName;

  /// The constructor.
  ///
  /// @param name the [name of the constraint][#getName()] to rename.
  /// @param newName the [new name][#getNewName()].
  public TableRenameConstraintOperation(String name, String newName) {

    super(name);
    this.newName = newName;
  }

  /// The constructor.
  ///
  /// @param constraint the [constraint][#getConstraint()] to rename.
  /// @param newName the [new name][#getNewName()].
  public TableRenameConstraintOperation(DbConstraint constraint, String newName) {

    super(constraint);
    this.newName = newName;
  }

  /// @return the new name to rename the constraint to.
  public String getNewName() {

    return this.newName;
  }

  @Override
  public TableOperationType getType() {

    return TableOperationType.RENAME;
  }

  @Override
  public String toString() {

    return super.toString() + " TO " + this.newName;
  }

}
