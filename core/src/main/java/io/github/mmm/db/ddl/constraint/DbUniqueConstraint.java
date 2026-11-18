/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.ddl.constraint;

import java.util.List;

import io.github.mmm.db.ddl.column.DbColumnReference;
import io.github.mmm.db.ddl.constraint.impl.DbUniqueConstraintImpl;
import io.github.mmm.db.ddl.constraint.state.DbConstraintState;
import io.github.mmm.db.ddl.table.DbTableReference;
import io.github.mmm.db.name.DbKeyword;

/// Unique [DbConstraint] ensuring that all values in the [column(s)][#getSourceColumns()] are different (no
/// duplicates).
///
/// @since 1.0.0
public interface DbUniqueConstraint extends DbConstraint {

  /// [Type][#getType()] {@value}.
  String TYPE = DbKeyword.UNIQUE;

  /// @see #getNamePrefix()
  String PREFIX = "UQ_";

  @Override
  default String getNamePrefix() {

    return PREFIX;
  }

  @Override
  default String getType() {

    return TYPE;
  }

  @Override
  DbUniqueConstraint withState(DbConstraintState newState);

  /// @param sourceTable the [source table][#getSourceTable()].
  /// @param sourceColumn the single [source column][#getSourceColumns()].
  /// @return the new [DbUniqueConstraint].
  static DbUniqueConstraint of(DbTableReference<?> sourceTable, DbColumnReference sourceColumn) {

    return of(null, sourceTable, sourceColumn);
  }

  /// @param sourceTable the [source table][#getSourceTable()].
  /// @param sourceColumns the [source columns][#getSourceColumns()].
  /// @return the new [DbUniqueConstraint].
  static DbUniqueConstraint of(DbTableReference<?> sourceTable, List<DbColumnReference> sourceColumns) {

    return of(null, sourceTable, sourceColumns);
  }

  /// @param name the [constraint name][#getName()].
  /// @param sourceTable the [source table][#getSourceTable()].
  /// @param sourceColumn the [source column][#getSourceColumns()].
  /// @return the new [DbUniqueConstraint].
  static DbUniqueConstraint of(String name, DbTableReference<?> sourceTable, DbColumnReference sourceColumn) {

    return of(name, sourceTable, sourceColumn, DbConstraintState.DEFAULT);
  }

  /// @param name the [constraint name][#getName()].
  /// @param sourceTable the [source table][#getSourceTable()].
  /// @param sourceColumns the [source columns][#getSourceColumns()].
  /// @return the new [DbUniqueConstraint].
  static DbUniqueConstraint of(String name, DbTableReference<?> sourceTable, List<DbColumnReference> sourceColumns) {

    return of(name, sourceTable, sourceColumns, DbConstraintState.DEFAULT);
  }

  /// @param name the [constraint name][#getName()].
  /// @param sourceTable the [source table][#getSourceTable()].
  /// @param sourceColumn the [source column][#getSourceColumns()].
  /// @param state the [state][#getState].
  /// @return the new [DbUniqueConstraint].
  static DbUniqueConstraint of(String name, DbTableReference<?> sourceTable, DbColumnReference sourceColumn,
      DbConstraintState state) {

    return new DbUniqueConstraintImpl(name, sourceTable, List.of(sourceColumn), state);
  }

  /// @param name the [constraint name][#getName()].
  /// @param sourceTable the [source table][#getSourceTable()].
  /// @param sourceColumns the [source columns][#getSourceColumns()].
  /// @param state the [state][#getState].
  /// @return the new [DbUniqueConstraint].
  static DbUniqueConstraint of(String name, DbTableReference<?> sourceTable, List<DbColumnReference> sourceColumns,
      DbConstraintState state) {

    return new DbUniqueConstraintImpl(name, sourceTable, sourceColumns, state);
  }
}
