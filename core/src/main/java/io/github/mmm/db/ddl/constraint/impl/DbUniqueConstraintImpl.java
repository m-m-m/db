/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.ddl.constraint.impl;

import java.util.List;

import io.github.mmm.db.ddl.column.DbColumnReference;
import io.github.mmm.db.ddl.constraint.DbUniqueConstraint;
import io.github.mmm.db.ddl.constraint.state.DbConstraintState;
import io.github.mmm.db.ddl.table.DbTableReference;

/**
 * Implementation of {@link DbUniqueConstraint}.
 *
 * @since 1.0.0
 */
public final class DbUniqueConstraintImpl extends DbConstraintImpl implements DbUniqueConstraint {

  /**
   * The constructor.
   *
   * @param name the {@link #getName() constraint name}.
   * @param sourceTable the {@link #getSourceTable() source table}.
   * @param sourceColumns the {@link #getSourceColumns() source columns}.
   * @param state the {@link #getState state}.
   */
  public DbUniqueConstraintImpl(String name, DbTableReference<?> sourceTable, List<DbColumnReference> sourceColumns,
      DbConstraintState state) {

    super(name, sourceTable, sourceColumns, state);
  }

  @Override
  public String getNamePrefix() {

    return PREFIX;
  }

  @Override
  public String getType() {

    return TYPE;
  }

  @Override
  public DbUniqueConstraintImpl withState(DbConstraintState newState) {

    if (this.state == newState) {
      return this;
    }
    return new DbUniqueConstraintImpl(this.name, this.sourceTable, this.sourceColumns, newState);
  }

}
