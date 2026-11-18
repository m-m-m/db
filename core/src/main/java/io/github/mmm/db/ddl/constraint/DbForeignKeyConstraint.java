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

/// Foreign key [DbConstraint] uniquely identifying a different [entity][io.github.mmm.entity.bean.EntityBean]
/// (row from another table).
///
/// @since 1.0.0
public interface DbForeignKeyConstraint extends DbConstraint {

  /// [Type][#getType()] {@value}.
  String TYPE = DbKeyword.FOREIGN_KEY;

  /// @see #getNamePrefix()
  String PREFIX = "FK_";

  /// @return the [on delete][DbConstraintOnDelete] behaviour.
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

  /// @param sourceTable the [source table][#getSourceTable()].
  /// @param sourceColumn the [source column][#getSourceColumns()].
  /// @param targetTable the [target table][#getTargetTable()] where the foreign key is pointing to.
  /// @param targetColumn the [target column][#getTargetColumns()].
  /// @return the new [DbForeignKeyConstraint].
  static DbForeignKeyConstraint of(DbTableReference<?> sourceTable, DbColumnReference sourceColumn,
      DbTableReference<?> targetTable, DbColumnReference targetColumn) {

    return of(null, sourceTable, sourceColumn, targetTable, targetColumn);
  }

  /// @param sourceTable the [source table][#getSourceTable()].
  /// @param sourceColumns the [source columns][#getSourceColumns()].
  /// @param targetTable the [target table][#getTargetTable()] where the foreign key is pointing to.
  /// @param targetColumns the [target columns][#getTargetColumns()].
  /// @return the new [DbForeignKeyConstraint].
  static DbForeignKeyConstraint of(DbTableReference<?> sourceTable, List<DbColumnReference> sourceColumns,
      DbTableReference<?> targetTable, List<DbColumnReference> targetColumns) {

    return of(null, sourceTable, sourceColumns, targetTable, targetColumns);
  }

  /// @param name the [constraint name][#getName()].
  /// @param sourceColumn the [source column][#getSourceColumns()].
  /// @param targetColumn the [target column][#getTargetColumns()].
  /// @return the new [DbForeignKeyConstraint].
  static DbForeignKeyConstraint of(String name, DbColumnReference sourceColumn, DbColumnReference targetColumn) {

    return of(name, sourceColumn, targetColumn, DbConstraintState.DEFAULT);
  }

  /// @param name the [constraint name][#getName()].
  /// @param sourceTable the [source table][#getSourceTable()].
  /// @param sourceColumn the [source column][#getSourceColumns()].
  /// @param targetTable the [target table][#getTargetTable()] where the foreign key is pointing to.
  /// @param targetColumn the [target column][#getTargetColumns()].
  /// @return the new [DbForeignKeyConstraint].
  static DbForeignKeyConstraint of(String name, DbTableReference<?> sourceTable, DbColumnReference sourceColumn,
      DbTableReference<?> targetTable, DbColumnReference targetColumn) {

    return of(name, sourceTable, sourceColumn, targetTable, targetColumn, DbConstraintState.DEFAULT);
  }

  /// @param name the [constraint name][#getName()].
  /// @param sourceTable the [source table][#getSourceTable()].
  /// @param sourceColumns the [source columns][#getSourceColumns()].
  /// @param targetTable the [target table][#getTargetTable()] where the foreign key is pointing to.
  /// @param targetColumns the [target columns][#getTargetColumns()].
  /// @return the new [DbForeignKeyConstraint].
  static DbForeignKeyConstraint of(String name, DbTableReference<?> sourceTable, List<DbColumnReference> sourceColumns,
      DbTableReference<?> targetTable, List<DbColumnReference> targetColumns) {

    return of(name, sourceTable, sourceColumns, targetTable, targetColumns, DbConstraintState.DEFAULT);
  }

  /// @param name the [constraint name][#getName()].
  /// @param sourceColumn the [source column][#getSourceColumns()].
  /// @param targetColumn the [target column][#getTargetColumns()].
  /// @param state the [state][#getState].
  /// @return the new [DbForeignKeyConstraint].
  static DbForeignKeyConstraint of(String name, DbColumnReference sourceColumn, DbColumnReference targetColumn,
      DbConstraintState state) {

    return of(name, sourceColumn, targetColumn, state, DbConstraintOnDelete.DEFAULT);
  }

  /// @param name the [constraint name][#getName()].
  /// @param sourceTable the [source table][#getSourceTable()].
  /// @param sourceColumn the [source column][#getSourceColumns()].
  /// @param targetTable the [target table][#getTargetTable()] where the foreign key is pointing to.
  /// @param targetColumn the [target column][#getTargetColumns()].
  /// @param state the [state][#getState].
  /// @return the new [DbForeignKeyConstraint].
  static DbForeignKeyConstraint of(String name, DbTableReference<?> sourceTable, DbColumnReference sourceColumn,
      DbTableReference<?> targetTable, DbColumnReference targetColumn, DbConstraintState state) {

    return of(name, sourceTable, sourceColumn, targetTable, targetColumn, state, DbConstraintOnDelete.DEFAULT);
  }

  /// @param name the [constraint name][#getName()].
  /// @param sourceTable the [source table][#getSourceTable()].
  /// @param sourceColumns the [source columns][#getSourceColumns()].
  /// @param targetTable the [target table][#getTargetTable()] where the foreign key is pointing to.
  /// @param targetColumns the [target columns][#getTargetColumns()].
  /// @param state the [state][#getState].
  /// @return the new [DbForeignKeyConstraint].
  static DbForeignKeyConstraint of(String name, DbTableReference<?> sourceTable, List<DbColumnReference> sourceColumns,
      DbTableReference<?> targetTable, List<DbColumnReference> targetColumns, DbConstraintState state) {

    return of(name, sourceTable, sourceColumns, targetTable, targetColumns, state, DbConstraintOnDelete.DEFAULT);
  }

  /// @param name the [constraint name][#getName()].
  /// @param sourceColumn the [source column][#getSourceColumns()].
  /// @param targetColumn the [target column][#getTargetColumns()].
  /// @param state the [state][#getState].
  /// @param onDelete the [on delete behaviour][#getOnDelete()].
  /// @return the new [DbForeignKeyConstraint].
  static DbForeignKeyConstraint of(String name, DbColumnReference sourceColumn, DbColumnReference targetColumn,
      DbConstraintState state, DbConstraintOnDelete onDelete) {

    return of(name, DbTableReference.of(sourceColumn.getProperty()), sourceColumn,
        DbTableReference.of(targetColumn.getProperty()), targetColumn, state, onDelete);
  }

  /// @param name the [constraint name][#getName()].
  /// @param sourceTable the [source table][#getSourceTable()].
  /// @param sourceColumn the [source column][#getSourceColumns()].
  /// @param targetTable the [target table][#getTargetTable()] where the foreign key is pointing to.
  /// @param targetColumn the [target column][#getTargetColumns()].
  /// @param state the [state][#getState].
  /// @param onDelete the [on delete behaviour][#getOnDelete()].
  /// @return the new [DbForeignKeyConstraint].
  static DbForeignKeyConstraint of(String name, DbTableReference<?> sourceTable, DbColumnReference sourceColumn,
      DbTableReference<?> targetTable, DbColumnReference targetColumn, DbConstraintState state,
      DbConstraintOnDelete onDelete) {

    return of(name, sourceTable, List.of(sourceColumn), targetTable, List.of(targetColumn), state, onDelete);
  }

  /// @param name the [constraint name][#getName()].
  /// @param sourceTable the [source table][#getSourceTable()].
  /// @param sourceColumns the [source columns][#getSourceColumns()].
  /// @param targetTable the [target table][#getTargetTable()] where the foreign key is pointing to.
  /// @param targetColumns the [target columns][#getTargetColumns()].
  /// @param state the [state][#getState].
  /// @param onDelete the [on delete behaviour][#getOnDelete()].
  /// @return the new [DbForeignKeyConstraint].
  static DbForeignKeyConstraint of(String name, DbTableReference<?> sourceTable, List<DbColumnReference> sourceColumns,
      DbTableReference<?> targetTable, List<DbColumnReference> targetColumns, DbConstraintState state,
      DbConstraintOnDelete onDelete) {

    return new DbForeignKeyConstraintImpl(name, sourceTable, sourceColumns, targetTable, targetColumns, state,
        onDelete);
  }

}
