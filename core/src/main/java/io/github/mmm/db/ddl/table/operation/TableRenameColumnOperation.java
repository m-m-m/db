package io.github.mmm.db.ddl.table.operation;

import io.github.mmm.db.ddl.column.DbColumnReference;
import io.github.mmm.db.name.DbKeyword;

/// [TableColumnOperation] to rename a column.
///
/// @since 1.0.0
public class TableRenameColumnOperation extends TableColumnOperation {

  private final DbColumnReference newColumn;

  /// The constructor.
  ///
  /// @param column the [column][#getColumn()] to rename.
  /// @param newColumn the [new column][#getNewColumn()] to rename to.
  public TableRenameColumnOperation(DbColumnReference column, DbColumnReference newColumn) {

    super(column);
    this.newColumn = newColumn;
  }

  /// @return the new [column][DbColumnReference] to rename to.
  public DbColumnReference getNewColumn() {

    return this.newColumn;
  }

  @Override
  public TableOperationType getType() {

    return TableOperationType.RENAME;
  }

  @Override
  public String toString() {

    return super.toString() + " " + DbKeyword.TO + " " + this.newColumn.getName();
  }

}
