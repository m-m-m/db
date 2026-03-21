package io.github.mmm.db.ddl.table.operation;

import io.github.mmm.db.ddl.column.DbColumnReference;
import io.github.mmm.db.name.DbKeyword;

/**
 * {@link TableColumnOperation} to rename a column.
 *
 * @since 1.0.0
 */
public class TableRenameColumnOperation extends TableColumnOperation {

  private final DbColumnReference newColumn;

  /**
   * The constructor.
   *
   * @param column the {@link #getColumn() column} to rename.
   * @param newColumn the {@link #getNewColumn() new column} to rename to.
   */
  public TableRenameColumnOperation(DbColumnReference column, DbColumnReference newColumn) {

    super(column);
    this.newColumn = newColumn;
  }

  /**
   * @return the new {@link DbColumnReference column} to rename to.
   */
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
