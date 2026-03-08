/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.ddl.constraint;

import java.util.List;

import io.github.mmm.base.container.ContainerMap;
import io.github.mmm.base.io.UncheckedAppendable;
import io.github.mmm.db.ddl.column.DbColumnReference;
import io.github.mmm.db.ddl.constraint.state.DbConstraintState;
import io.github.mmm.db.ddl.table.DbTableReference;
import io.github.mmm.db.name.DbKeyword;
import io.github.mmm.db.name.DbObject;

/**
 * Represents a database constraint on one or multiple columns.
 *
 * @since 1.0.0
 */
public interface DbConstraint extends DbObject {

  /**
   * @return the name prefix (e.g. "FK_" for foreign key).
   */
  String getNamePrefix();

  /**
   * @return the type of this {@link DbConstraint} (e.g. "CHECK" or "FOREIGN KEY").
   */
  String getType();

  /**
   * @return the {@link DbTableReference} to the source table this constraint is defined on.
   */
  DbTableReference<?> getSourceTable();

  /**
   * @return the {@link ContainerMap} with the {@link DbColumnReference}s to the source columns this constraint is
   *         defined on.
   */
  List<DbColumnReference> getSourceColumns();

  /**
   * @return the {@link DbTableReference} to the target table the {@link #getTargetColumns() target columns} are
   *         pointing to. Typically the same as {@link #getSourceTable()} but will differ e.g. for
   *         {@link DbForeignKeyConstraint}.
   */
  DbTableReference<?> getTargetTable();

  /**
   * @return the {@link ContainerMap} with the {@link DbColumnReference}s to the target columns in the
   *         {@link #getTargetTable() target table}. Typically the same as {@link #getSourceColumns()} but will differ
   *         e.g. for {@link DbForeignKeyConstraint}.
   */
  List<DbColumnReference> getTargetColumns();

  /**
   * @return the {@link DbConstraintState} configuring e.g. if deferrable.
   */
  DbConstraintState getState();

  /**
   * @param newState the new {@link #getState() state}.
   * @return a copy of this {@link DbConstraint} with the given {@link #getState() state}.
   */
  DbConstraint withState(DbConstraintState newState);

  @Override
  default void toString(UncheckedAppendable sb, int mode) {

    sb.append(DbKeyword.CONSTRAINT);
    sb.append(' ');
    sb.append(getName());
    if (mode != 0) {
      return;
    }
    sb.append(' ');
    sb.append(getType());
    DbObject.append(getSourceColumns(), sb, mode);
    DbConstraintState state = getState();
    if (state != DbConstraintState.DEFAULT) {
      sb.append(' ');
      sb.append(state);
    }
  }

}
