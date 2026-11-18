package io.github.mmm.db.ddl.table.operation;

/// A single [TableOperation] of an ALTER TABLE clause.
///
/// @since 1.0.0
public abstract class TableOperation {

  /// @return the [type][TableOperationType] of this [operation][TableOperation].
  public abstract TableOperationType getType();

  /// @return the [kind][TableOperationKind] of object this [operation][TableOperation] is about.
  public abstract TableOperationKind getKind();
}
