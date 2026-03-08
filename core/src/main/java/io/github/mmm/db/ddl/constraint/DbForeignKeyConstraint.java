/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.ddl.constraint;

import java.util.List;

import io.github.mmm.base.io.UncheckedAppendable;
import io.github.mmm.db.ddl.column.DbColumnReference;
import io.github.mmm.db.ddl.constraint.impl.DbForeignKeyConstraintImpl;
import io.github.mmm.db.ddl.constraint.state.DbConstraintOnDelete;
import io.github.mmm.db.ddl.constraint.state.DbConstraintState;
import io.github.mmm.db.ddl.table.DbTableReference;
import io.github.mmm.db.name.DbKeyword;
import io.github.mmm.db.name.DbObject;

/**
 * Foreign key {@link DbConstraint} uniquely identifying a different {@link io.github.mmm.entity.bean.EntityBean entity}
 * (row from another table).
 *
 * @since 1.0.0
 */
public interface DbForeignKeyConstraint extends DbConstraint {

  /** {@link #getType() Type} {@value}. */
  String TYPE = DbKeyword.FOREIGN_KEY;

  /** @see #getNamePrefix() */
  String PREFIX = "FK_";

  /**
   * @return the {@link DbConstraintOnDelete on delete} behaviour.
   */
  DbConstraintOnDelete getOnDelete();

  @Override
  default String getNamePrefix() {

    return PREFIX;
  }

  @Override
  default String getType() {

    return TYPE;
  }

  @Override
  DbForeignKeyConstraint withState(DbConstraintState newState);

  @Override
  default void toString(UncheckedAppendable sb, int mode) {

    DbConstraint.super.toString(sb, mode);
    if (mode != 0) {
      return;
    }
    sb.append(' ');
    sb.append(DbKeyword.REFERENCES);
    sb.append(' ');
    // getTargetTable().toString(sb, mode);
    sb.append(getTargetTable());
    DbObject.append(getTargetColumns(), sb, mode);
    DbConstraintOnDelete onDelete = getOnDelete();
    if (onDelete != DbConstraintOnDelete.DEFAULT) {
      sb.append(' ');
      sb.append(onDelete.toString());
    }
  }

  /**
   * @param sourceTable the {@link #getSourceTable() source table}.
   * @param sourceColumn the {@link #getSourceColumns() source column}.
   * @param targetTable the {@link #getTargetTable() target table} where the foreign key is pointing to.
   * @param targetColumn the {@link #getTargetColumns() target column}.
   * @return the new {@link DbForeignKeyConstraint}.
   */
  static DbForeignKeyConstraint of(DbTableReference<?> sourceTable, DbColumnReference sourceColumn,
      DbTableReference<?> targetTable, DbColumnReference targetColumn) {

    return of(null, sourceTable, sourceColumn, targetTable, targetColumn);
  }

  /**
   * @param sourceTable the {@link #getSourceTable() source table}.
   * @param sourceColumns the {@link #getSourceColumns() source columns}.
   * @param targetTable the {@link #getTargetTable() target table} where the foreign key is pointing to.
   * @param targetColumns the {@link #getTargetColumns() target columns}.
   * @return the new {@link DbForeignKeyConstraint}.
   */
  static DbForeignKeyConstraint of(DbTableReference<?> sourceTable, List<DbColumnReference> sourceColumns,
      DbTableReference<?> targetTable, List<DbColumnReference> targetColumns) {

    return of(null, sourceTable, sourceColumns, targetTable, targetColumns);
  }

  /**
   * @param name the {@link #getName() constraint name}.
   * @param sourceColumn the {@link #getSourceColumns() source column}.
   * @param targetColumn the {@link #getTargetColumns() target column}.
   * @return the new {@link DbForeignKeyConstraint}.
   */
  static DbForeignKeyConstraint of(String name, DbColumnReference sourceColumn, DbColumnReference targetColumn) {

    return of(name, sourceColumn, targetColumn, DbConstraintState.DEFAULT);
  }

  /**
   * @param name the {@link #getName() constraint name}.
   * @param sourceTable the {@link #getSourceTable() source table}.
   * @param sourceColumn the {@link #getSourceColumns() source column}.
   * @param targetTable the {@link #getTargetTable() target table} where the foreign key is pointing to.
   * @param targetColumn the {@link #getTargetColumns() target column}.
   * @return the new {@link DbForeignKeyConstraint}.
   */
  static DbForeignKeyConstraint of(String name, DbTableReference<?> sourceTable, DbColumnReference sourceColumn,
      DbTableReference<?> targetTable, DbColumnReference targetColumn) {

    return of(name, sourceTable, sourceColumn, targetTable, targetColumn, DbConstraintState.DEFAULT);
  }

  /**
   * @param name the {@link #getName() constraint name}.
   * @param sourceTable the {@link #getSourceTable() source table}.
   * @param sourceColumns the {@link #getSourceColumns() source columns}.
   * @param targetTable the {@link #getTargetTable() target table} where the foreign key is pointing to.
   * @param targetColumns the {@link #getTargetColumns() target columns}.
   * @return the new {@link DbForeignKeyConstraint}.
   */
  static DbForeignKeyConstraint of(String name, DbTableReference<?> sourceTable, List<DbColumnReference> sourceColumns,
      DbTableReference<?> targetTable, List<DbColumnReference> targetColumns) {

    return of(name, sourceTable, sourceColumns, targetTable, targetColumns, DbConstraintState.DEFAULT);
  }

  /**
   * @param name the {@link #getName() constraint name}.
   * @param sourceColumn the {@link #getSourceColumns() source column}.
   * @param targetColumn the {@link #getTargetColumns() target column}.
   * @param state the {@link #getState state}.
   * @return the new {@link DbForeignKeyConstraint}.
   */
  static DbForeignKeyConstraint of(String name, DbColumnReference sourceColumn, DbColumnReference targetColumn,
      DbConstraintState state) {

    return of(name, sourceColumn, targetColumn, state, DbConstraintOnDelete.DEFAULT);
  }

  /**
   * @param name the {@link #getName() constraint name}.
   * @param sourceTable the {@link #getSourceTable() source table}.
   * @param sourceColumn the {@link #getSourceColumns() source column}.
   * @param targetTable the {@link #getTargetTable() target table} where the foreign key is pointing to.
   * @param targetColumn the {@link #getTargetColumns() target column}.
   * @param state the {@link #getState state}.
   * @return the new {@link DbForeignKeyConstraint}.
   */
  static DbForeignKeyConstraint of(String name, DbTableReference<?> sourceTable, DbColumnReference sourceColumn,
      DbTableReference<?> targetTable, DbColumnReference targetColumn, DbConstraintState state) {

    return of(name, sourceTable, sourceColumn, targetTable, targetColumn, state, DbConstraintOnDelete.DEFAULT);
  }

  /**
   * @param name the {@link #getName() constraint name}.
   * @param sourceTable the {@link #getSourceTable() source table}.
   * @param sourceColumns the {@link #getSourceColumns() source columns}.
   * @param targetTable the {@link #getTargetTable() target table} where the foreign key is pointing to.
   * @param targetColumns the {@link #getTargetColumns() target columns}.
   * @param state the {@link #getState state}.
   * @return the new {@link DbForeignKeyConstraint}.
   */
  static DbForeignKeyConstraint of(String name, DbTableReference<?> sourceTable, List<DbColumnReference> sourceColumns,
      DbTableReference<?> targetTable, List<DbColumnReference> targetColumns, DbConstraintState state) {

    return of(name, sourceTable, sourceColumns, targetTable, targetColumns, state, DbConstraintOnDelete.DEFAULT);
  }

  /**
   * @param name the {@link #getName() constraint name}.
   * @param sourceColumn the {@link #getSourceColumns() source column}.
   * @param targetColumn the {@link #getTargetColumns() target column}.
   * @param state the {@link #getState state}.
   * @param onDelete the {@link #getOnDelete() on delete behaviour}.
   * @return the new {@link DbForeignKeyConstraint}.
   */
  static DbForeignKeyConstraint of(String name, DbColumnReference sourceColumn, DbColumnReference targetColumn,
      DbConstraintState state, DbConstraintOnDelete onDelete) {

    return of(name, DbTableReference.of(sourceColumn.getProperty()), sourceColumn,
        DbTableReference.of(targetColumn.getProperty()), targetColumn, state, onDelete);
  }

  /**
   * @param name the {@link #getName() constraint name}.
   * @param sourceTable the {@link #getSourceTable() source table}.
   * @param sourceColumn the {@link #getSourceColumns() source column}.
   * @param targetTable the {@link #getTargetTable() target table} where the foreign key is pointing to.
   * @param targetColumn the {@link #getTargetColumns() target column}.
   * @param state the {@link #getState state}.
   * @param onDelete the {@link #getOnDelete() on delete behaviour}.
   * @return the new {@link DbForeignKeyConstraint}.
   */
  static DbForeignKeyConstraint of(String name, DbTableReference<?> sourceTable, DbColumnReference sourceColumn,
      DbTableReference<?> targetTable, DbColumnReference targetColumn, DbConstraintState state,
      DbConstraintOnDelete onDelete) {

    return of(name, sourceTable, List.of(sourceColumn), targetTable, List.of(targetColumn), state, onDelete);
  }

  /**
   * @param name the {@link #getName() constraint name}.
   * @param sourceTable the {@link #getSourceTable() source table}.
   * @param sourceColumns the {@link #getSourceColumns() source columns}.
   * @param targetTable the {@link #getTargetTable() target table} where the foreign key is pointing to.
   * @param targetColumns the {@link #getTargetColumns() target columns}.
   * @param state the {@link #getState state}.
   * @param onDelete the {@link #getOnDelete() on delete behaviour}.
   * @return the new {@link DbForeignKeyConstraint}.
   */
  static DbForeignKeyConstraint of(String name, DbTableReference<?> sourceTable, List<DbColumnReference> sourceColumns,
      DbTableReference<?> targetTable, List<DbColumnReference> targetColumns, DbConstraintState state,
      DbConstraintOnDelete onDelete) {

    return new DbForeignKeyConstraintImpl(name, sourceTable, sourceColumns, targetTable, targetColumns, state,
        onDelete);
  }

}
