package io.github.mmm.db.ddl.table.operation;

import io.github.mmm.db.ddl.column.DbColumnReference;

/// [TableColumnOperation] to add a column.
///
/// @since 1.0.0
public class TableAddColumnOperation extends TableColumnOperation {

  /// The constructor.
  ///
  /// @param column the [column][#getColumn()] to add.
  public TableAddColumnOperation(DbColumnReference column) {

    super(column);
  }

  @Override
  public TableOperationType getType() {

    return TableOperationType.ADD;
  }

}
