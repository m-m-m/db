package io.github.mmm.db.ddl.table.operation;

import io.github.mmm.db.ddl.column.DbColumnReference;

/// [TableColumnOperation] to drop a column.
///
/// @since 1.0.0
public class TableDropColumnOperation extends TableColumnOperation {

  /// The constructor.
  ///
  /// @param column the [column][#getColumn()] to drop.
  public TableDropColumnOperation(DbColumnReference column) {

    super(column);
  }

  @Override
  public TableOperationType getType() {

    return TableOperationType.DROP;
  }

}
