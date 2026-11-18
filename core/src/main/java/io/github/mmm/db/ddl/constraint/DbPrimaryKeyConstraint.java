/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.ddl.constraint;

import java.util.List;

import io.github.mmm.db.ddl.column.DbColumnReference;
import io.github.mmm.db.ddl.constraint.impl.DbPrimaryKeyConstraintImpl;
import io.github.mmm.db.ddl.constraint.state.DbConstraintState;
import io.github.mmm.db.ddl.table.DbTableReference;
import io.github.mmm.db.name.DbKeyword;

/// Foreign key [DbConstraint] uniquely identifying a different [entity][io.github.mmm.entity.bean.EntityBean]
/// (row from another table).
///
/// @since 1.0.0
public interface DbPrimaryKeyConstraint extends DbConstraint {

  /// [Type][#getType()] {@value}.
  String TYPE = DbKeyword.PRIMARY_KEY;

  /// @see #getNamePrefix()
  String PREFIX = "PK_";

  @Override
  default String getNamePrefix() {

    return PREFIX;
  }

  @Override
  default String getType() {

    return TYPE;
  }

  @Override
  DbPrimaryKeyConstraint withState(DbConstraintState newState);

  /// @param sourceTable the [source table][#getSourceTable()].
  /// @param sourceColumn the [source column][#getSourceColumns()].
  /// @return the new [DbPrimaryKeyConstraint].
  static DbPrimaryKeyConstraint of(DbTableReference<?> sourceTable, DbColumnReference sourceColumn) {

    return of(null, sourceTable, sourceColumn);
  }

  /// @param sourceTable the [source table][#getSourceTable()].
  /// @param sourceColumns the [source columns][#getSourceColumns()].
  /// @return the new [DbPrimaryKeyConstraint].
  static DbPrimaryKeyConstraint of(DbTableReference<?> sourceTable, List<DbColumnReference> sourceColumns) {

    return of(null, sourceTable, sourceColumns, DbConstraintState.DEFAULT);
  }

  /// @param sourceTable the [source table][#getSourceTable()].
  /// @param sourceColumns the [source columns][#getSourceColumns()].
  /// @param state the [state][#getState].
  /// @return the new [DbPrimaryKeyConstraint].
  static DbPrimaryKeyConstraint of(DbTableReference<?> sourceTable, List<DbColumnReference> sourceColumns,
      DbConstraintState state) {

    return of(null, sourceTable, sourceColumns, state);
  }

  /// @param name the [constraint name][#getName()].
  /// @param sourceTable the [source table][#getSourceTable()].
  /// @param sourceColumn the [source column][#getSourceColumns()].
  /// @return the new [DbPrimaryKeyConstraint].
  static DbPrimaryKeyConstraint of(String name, DbTableReference<?> sourceTable, DbColumnReference sourceColumn) {

    return of(name, sourceTable, sourceColumn, DbConstraintState.DEFAULT);
  }

  /// @param name the [constraint name][#getName()].
  /// @param sourceTable the [source table][#getSourceTable()].
  /// @param sourceColumns the [source columns][#getSourceColumns()].
  /// @return the new [DbPrimaryKeyConstraint].
  static DbPrimaryKeyConstraint of(String name, DbTableReference<?> sourceTable,
      List<DbColumnReference> sourceColumns) {

    return of(name, sourceTable, sourceColumns, DbConstraintState.DEFAULT);
  }

  /// @param name the [constraint name][#getName()].
  /// @param sourceTable the [source table][#getSourceTable()].
  /// @param sourceColumn the [source column][#getSourceColumns()].
  /// @param state the [state][#getState].
  /// @return the new [DbPrimaryKeyConstraint].
  static DbPrimaryKeyConstraint of(String name, DbTableReference<?> sourceTable, DbColumnReference sourceColumn,
      DbConstraintState state) {

    return of(name, sourceTable, List.of(sourceColumn), state);
  }

  /// @param name the [constraint name][#getName()].
  /// @param sourceTable the [source table][#getSourceTable()].
  /// @param sourceColumns the [source columns][#getSourceColumns()].
  /// @param state the [state][#getState].
  /// @return the new [DbPrimaryKeyConstraint].
  static DbPrimaryKeyConstraint of(String name, DbTableReference<?> sourceTable, List<DbColumnReference> sourceColumns,
      DbConstraintState state) {

    return new DbPrimaryKeyConstraintImpl(name, sourceTable, sourceColumns, state);
  }

}
