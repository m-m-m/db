package io.github.mmm.db.ddl.table.operation;

import io.github.mmm.db.ddl.column.DbColumnReference;

/**
 * {@link TableOperation} on a {@link DbColumnReference column}.
 *
 * @since 1.0.0
 */
public abstract class TableColumnOperation extends TableOperation {

  private final DbColumnReference column;

  /**
   * The constructor.
   *
   * @param column the {@link #getColumn() column} to alter.
   */
  public TableColumnOperation(DbColumnReference column) {

    super();
    this.column = column;
  }

  /**
   * @return the {@link DbColumnReference column} to alter according to the {@link #getType() type}.
   */
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
