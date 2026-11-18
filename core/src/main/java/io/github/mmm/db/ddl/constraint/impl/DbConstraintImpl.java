/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.ddl.constraint.impl;

import java.util.List;
import java.util.Objects;

import io.github.mmm.base.io.UncheckedAppendable;
import io.github.mmm.db.ddl.column.DbColumnReference;
import io.github.mmm.db.ddl.constraint.DbConstraint;
import io.github.mmm.db.ddl.constraint.DbForeignKeyConstraint;
import io.github.mmm.db.ddl.constraint.state.DbConstraintState;
import io.github.mmm.db.ddl.table.DbTableReference;
import io.github.mmm.db.name.AbstractDbObject;
import io.github.mmm.db.name.DbKeyword;
import io.github.mmm.db.name.DbObject;
import io.github.mmm.db.name.DbQualifiedName;

/// Implementation of [DbConstraint].
///
/// @since 1.0.0
public abstract class DbConstraintImpl extends AbstractDbObject implements DbConstraint {

  /// @see #getSourceColumns()
  protected final List<DbColumnReference> sourceColumns;

  /// @see #getSourceTable()
  protected final DbTableReference<?> sourceTable;

  /// @see #getState()
  protected final DbConstraintState state;

  /// The constructor.
  ///
  /// @param name the [constraint name][#getName()].
  /// @param sourceTable the [source table][#getSourceTable()].
  /// @param sourceColumns the [source columns][#getSourceColumns()].
  /// @param state the [state][#getState].
  public DbConstraintImpl(String name, DbTableReference<?> sourceTable, List<DbColumnReference> sourceColumns, DbConstraintState state) {

    // constraint names are strictly tied to their parent table and can never be qualified
    this(DbQualifiedName.ofName(name), sourceTable, sourceColumns, state);
  }

  /// The constructor.
  ///
  /// @param name the [constraint name][#getName()].
  /// @param sourceTable the [source table][#getSourceTable()].
  /// @param sourceColumns the [source columns][#getSourceColumns()].
  /// @param state the [state][#getState].
  public DbConstraintImpl(DbQualifiedName name, DbTableReference<?> sourceTable, List<DbColumnReference> sourceColumns, DbConstraintState state) {

    super(name);
    Objects.requireNonNull(state);
    Objects.requireNonNull(sourceColumns);
    Objects.requireNonNull(sourceTable);
    if (sourceColumns.isEmpty()) {
      throw new IllegalStateException("Need at least one column for constraint");
    }
    this.sourceTable = sourceTable;
    this.state = state;
    this.sourceColumns = sourceColumns;
  }

  /// @return the [DbTableReference] to the source table this constraint is defined on.
  @Override
  public DbTableReference<?> getSourceTable() {

    return this.sourceTable;
  }

  @Override
  public List<DbColumnReference> getSourceColumns() {

    return this.sourceColumns;
  }

  /// @return the [DbTableReference] to the target table the [target columns][#getTargetColumns()] are pointing to. Typically the same as
  ///         [#getSourceTable()] but will differ e.g. for [DbForeignKeyConstraint].
  @Override
  public DbTableReference<?> getTargetTable() {

    return this.sourceTable;
  }

  /// @return the [List] of [DbColumnReference]s to the target columns in the [target table][#getTargetTable()]. Typically the same as [#getSourceColumns()] but
  ///         will differ e.g. for [DbForeignKeyConstraint].
  @Override
  public List<DbColumnReference> getTargetColumns() {

    return this.sourceColumns;
  }

  /// @return the [DbConstraintState] configuring e.g. if deferrable.
  @Override
  public DbConstraintState getState() {

    return this.state;
  }

  /// @param newState the new [state][#getState()].
  /// @return a copy of this [DbConstraintImpl] with the given [state][#getState()].
  @Override
  public abstract DbConstraintImpl withState(DbConstraintState newState);

  @Override
  public void toString(UncheckedAppendable sb, int mode) {

    sb.append(DbKeyword.CONSTRAINT);
    sb.append(' ');
    sb.append(getName());
    if (mode != 0) {
      return;
    }
    sb.append(' ');
    sb.append(getType());
    DbObject.append(this.sourceColumns, sb, mode);
    if (this.state != DbConstraintState.DEFAULT) {
      sb.append(' ');
      sb.append(this.state);
    }
  }

}
