/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.ddl.constraint.impl;

import java.util.List;

import io.github.mmm.db.ddl.column.DbColumnReference;
import io.github.mmm.db.ddl.constraint.DbNotNullConstraint;
import io.github.mmm.db.ddl.constraint.state.DbConstraintState;
import io.github.mmm.db.ddl.table.DbTableReference;

/**
 * Implementation of {@link DbNotNullConstraint}.
 *
 * @since 1.0.0
 */
public final class DbNotNullConstraintImpl extends DbConstraintImpl implements DbNotNullConstraint {

  /**
   * The constructor.
   *
   * @param name the {@link #getName() constraint name}.
   * @param sourceTable the {@link #getSourceTable() source table}.
   * @param sourceColumns the {@link #getSourceColumns() source columns}.
   * @param state the {@link #getState state}.
   */
  public DbNotNullConstraintImpl(String name, DbTableReference<?> sourceTable, List<DbColumnReference> sourceColumns,
      DbConstraintState state) {

    super(name, sourceTable, sourceColumns, state);
  }

  @Override
  public DbNotNullConstraintImpl withState(DbConstraintState newState) {

    if (this.state == newState) {
      return this;
    }
    return new DbNotNullConstraintImpl(this.name, this.sourceTable, this.sourceColumns, newState);
  }

}
