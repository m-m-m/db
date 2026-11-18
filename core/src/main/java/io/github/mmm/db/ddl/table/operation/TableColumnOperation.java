package io.github.mmm.db.ddl.table.operation;

import io.github.mmm.db.ddl.column.DbColumnReference;

/// [TableOperation] on a [column][DbColumnReference].
///
/// @since 1.0.0
public abstract class TableColumnOperation extends TableOperation {

  private final DbColumnReference column;

  /// The constructor.
  ///
  /// @param column the [column][#getColumn()] to alter.
  public TableColumnOperation(DbColumnReference column) {

    super();
    this.column = column;
  }

  /// @return the [column][DbColumnReference] to alter according to the [type][#getType()].
  public DbColumnReference getColumn() {

    return this.column;
  }

  @Override
  public TableOperationKind getKind() {

    return TableOperationKind.COLUMN;
  }

  @Override
  public String toString() {

    return getType().name() + " COLUMN " + this.column.getName();
  }

}
