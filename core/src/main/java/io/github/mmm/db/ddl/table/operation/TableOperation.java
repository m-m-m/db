package io.github.mmm.db.ddl.table.operation;

/**
 * A single {@link TableOperation} of an ALTER TABLE clause.
 *
 * @since 1.0.0
 */
public abstract class TableOperation {

  /**
   * @return the {@link TableOperationType type} of this {@link TableOperation operation}.
   */
  public abstract TableOperationType getType();

  /**
   * @return the {@link TableOperationKind kind} of object this {@link TableOperation operation} is about.
   */
  public abstract TableOperationKind getKind();
}
