/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.ddl.constraint.impl;

import java.util.List;

import io.github.mmm.db.ddl.column.DbColumnReference;
import io.github.mmm.db.ddl.constraint.DbNotNullConstraint;
import io.github.mmm.db.ddl.constraint.state.DbConstraintState;
import io.github.mmm.db.ddl.table.DbTableReference;
import io.github.mmm.db.name.DbQualifiedName;

/// Implementation of [DbNotNullConstraint].
///
/// @since 1.0.0
public final class DbNotNullConstraintImpl extends DbConstraintImpl implements DbNotNullConstraint {

  /// The constructor.
  ///
  /// @param name the [constraint name][#getName()].
  /// @param sourceTable the [source table][#getSourceTable()].
  /// @param sourceColumns the [source columns][#getSourceColumns()].
  /// @param state the [state][#getState].
  public DbNotNullConstraintImpl(String name, DbTableReference<?> sourceTable, List<DbColumnReference> sourceColumns,
      DbConstraintState state) {

    this(DbQualifiedName.ofName(name), sourceTable, sourceColumns, state);
  }

  DbNotNullConstraintImpl(DbQualifiedName name, DbTableReference<?> sourceTable, List<DbColumnReference> sourceColumns,
      DbConstraintState state) {

    super(name, sourceTable, sourceColumns, state);
  }

  @Override
  public DbNotNullConstraintImpl withState(DbConstraintState newState) {

    if (this.state == newState) {
      return this;
    }
    return new DbNotNullConstraintImpl(this.rawName, this.sourceTable, this.sourceColumns, newState);
  }

}
