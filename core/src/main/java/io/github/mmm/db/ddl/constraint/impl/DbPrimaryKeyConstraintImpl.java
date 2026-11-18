/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.ddl.constraint.impl;

import java.util.List;

import io.github.mmm.db.ddl.column.DbColumnReference;
import io.github.mmm.db.ddl.constraint.DbPrimaryKeyConstraint;
import io.github.mmm.db.ddl.constraint.state.DbConstraintState;
import io.github.mmm.db.ddl.table.DbTableReference;
import io.github.mmm.db.name.DbQualifiedName;

/// Implementation of [DbPrimaryKeyConstraint].
///
/// @since 1.0.0
public final class DbPrimaryKeyConstraintImpl extends DbConstraintImpl implements DbPrimaryKeyConstraint {

  /// The constructor.
  ///
  /// @param name the [constraint name][#getName()].
  /// @param sourceTable the [source table][#getSourceTable()].
  /// @param sourceColumns the [source columns][#getSourceColumns()].
  /// @param state the [state][#getState].
  public DbPrimaryKeyConstraintImpl(String name, DbTableReference<?> sourceTable, List<DbColumnReference> sourceColumns,
      DbConstraintState state) {

    this(DbQualifiedName.ofName(name), sourceTable, sourceColumns, state);
  }

  DbPrimaryKeyConstraintImpl(DbQualifiedName name, DbTableReference<?> sourceTable,
      List<DbColumnReference> sourceColumns, DbConstraintState state) {

    super(name, sourceTable, sourceColumns, state);
  }

  @Override
  public DbPrimaryKeyConstraintImpl withState(DbConstraintState newState) {

    if (this.state == newState) {
      return this;
    }
    return new DbPrimaryKeyConstraintImpl(this.rawName, this.sourceTable, this.sourceColumns, newState);
  }

}
