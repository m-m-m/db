/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.ddl.constraint.impl;

import java.util.List;
import java.util.Objects;

import io.github.mmm.db.ddl.column.DbColumnReference;
import io.github.mmm.db.ddl.constraint.DbForeignKeyConstraint;
import io.github.mmm.db.ddl.constraint.state.DbConstraintOnDelete;
import io.github.mmm.db.ddl.constraint.state.DbConstraintState;
import io.github.mmm.db.ddl.table.DbTableReference;

/**
 * Implementation of {@link DbForeignKeyConstraint}.
 *
 * @since 1.0.0
 */
public final class DbForeignKeyConstraintImpl extends DbConstraintImpl implements DbForeignKeyConstraint {

  private final DbTableReference<?> targetTable;

  /** @see #getTargetColumns() */
  protected final List<DbColumnReference> targetColumns;

  private final DbConstraintOnDelete onDelete;

  /**
   * The constructor.
   *
   * @param name the {@link #getName() constraint name}.
   * @param sourceTable the {@link #getSourceTable() source table}.
   * @param sourceColumns the {@link #getSourceColumns() source columns}.
   * @param targetTable the {@link #getTargetTable() target table} where the foreign key is pointing to.
   * @param targetColumns the {@link #getTargetColumns() target columns}.
   * @param state the {@link #getState state}.
   * @param onDelete the {@link #getOnDelete() on delete behaviour}.
   */
  public DbForeignKeyConstraintImpl(String name, DbTableReference<?> sourceTable, List<DbColumnReference> sourceColumns,
      DbTableReference<?> targetTable, List<DbColumnReference> targetColumns, DbConstraintState state,
      DbConstraintOnDelete onDelete) {

    super(name, sourceTable, sourceColumns, state);
    Objects.requireNonNull(onDelete);
    Objects.requireNonNull(targetTable);
    Objects.requireNonNull(targetColumns);
    if (targetColumns.isEmpty()) {
      throw new IllegalArgumentException("targetColumns must not be empty.");
    }
    this.targetTable = targetTable;
    this.targetColumns = targetColumns;
    this.onDelete = onDelete;
  }

  @Override
  public DbTableReference<?> getTargetTable() {

    return this.targetTable;
  }

  @Override
  public List<DbColumnReference> getTargetColumns() {

    return this.targetColumns;
  }

  /**
   * @return the {@link DbConstraintOnDelete on delete} behaviour.
   */
  @Override
  public DbConstraintOnDelete getOnDelete() {

    return this.onDelete;
  }

  @Override
  public DbForeignKeyConstraintImpl withState(DbConstraintState newState) {

    if (this.state == newState) {
      return this;
    }
    return new DbForeignKeyConstraintImpl(this.name, this.sourceTable, this.sourceColumns, this.targetTable,
        this.targetColumns, newState, this.onDelete);
  }

}
