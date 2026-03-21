package io.github.mmm.db.ddl.table.operation;

import io.github.mmm.db.ddl.column.DbColumnReference;

/**
 * {@link TableColumnOperation} to modify a column (change its type).
 *
 * @since 1.0.0
 */
public class TableModifyColumnOperation extends TableColumnOperation {

  /**
   * The constructor.
   *
   * @param column the {@link #getColumn() column} to modify.
   */
  public TableModifyColumnOperation(DbColumnReference column) {

    super(column);
  }

  @Override
  public TableOperationType getType() {

    return TableOperationType.MODIFY;
  }

}
