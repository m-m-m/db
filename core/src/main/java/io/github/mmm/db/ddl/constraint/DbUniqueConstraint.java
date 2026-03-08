/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.ddl.constraint;

import java.util.List;

import io.github.mmm.db.ddl.column.DbColumnReference;
import io.github.mmm.db.ddl.constraint.impl.DbUniqueConstraintImpl;
import io.github.mmm.db.ddl.constraint.state.DbConstraintState;
import io.github.mmm.db.ddl.table.DbTableReference;
import io.github.mmm.db.name.DbKeyword;

/**
 * Unique {@link DbConstraint} ensuring that all values in the {@link #getSourceColumns() column(s)} are different (no
 * duplicates).
 *
 * @since 1.0.0
 */
public interface DbUniqueConstraint extends DbConstraint {

  /** {@link #getType() Type} {@value}. */
  String TYPE = DbKeyword.UNIQUE;

  /** @see #getNamePrefix() */
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

  /**
   * @param sourceTable the {@link #getSourceTable() source table}.
   * @param sourceColumn the single {@link #getSourceColumns() source column}.
   * @return the new {@link DbUniqueConstraint}.
   */
  static DbUniqueConstraint of(DbTableReference<?> sourceTable, DbColumnReference sourceColumn) {

    return of(null, sourceTable, sourceColumn);
  }

  /**
   * @param sourceTable the {@link #getSourceTable() source table}.
   * @param sourceColumns the {@link #getSourceColumns() source columns}.
   * @return the new {@link DbUniqueConstraint}.
   */
  static DbUniqueConstraint of(DbTableReference<?> sourceTable, List<DbColumnReference> sourceColumns) {

    return of(null, sourceTable, sourceColumns);
  }

  /**
   * @param name the {@link #getName() constraint name}.
   * @param sourceTable the {@link #getSourceTable() source table}.
   * @param sourceColumn the {@link #getSourceColumns() source column}.
   * @return the new {@link DbUniqueConstraint}.
   */
  static DbUniqueConstraint of(String name, DbTableReference<?> sourceTable, DbColumnReference sourceColumn) {

    return of(name, sourceTable, sourceColumn, DbConstraintState.DEFAULT);
  }

  /**
   * @param name the {@link #getName() constraint name}.
   * @param sourceTable the {@link #getSourceTable() source table}.
   * @param sourceColumns the {@link #getSourceColumns() source columns}.
   * @return the new {@link DbUniqueConstraint}.
   */
  static DbUniqueConstraint of(String name, DbTableReference<?> sourceTable, List<DbColumnReference> sourceColumns) {

    return of(name, sourceTable, sourceColumns, DbConstraintState.DEFAULT);
  }

  /**
   * @param name the {@link #getName() constraint name}.
   * @param sourceTable the {@link #getSourceTable() source table}.
   * @param sourceColumn the {@link #getSourceColumns() source column}.
   * @param state the {@link #getState state}.
   * @return the new {@link DbUniqueConstraint}.
   */
  static DbUniqueConstraint of(String name, DbTableReference<?> sourceTable, DbColumnReference sourceColumn,
      DbConstraintState state) {

    return new DbUniqueConstraintImpl(name, sourceTable, List.of(sourceColumn), state);
  }

  /**
   * @param name the {@link #getName() constraint name}.
   * @param sourceTable the {@link #getSourceTable() source table}.
   * @param sourceColumns the {@link #getSourceColumns() source columns}.
   * @param state the {@link #getState state}.
   * @return the new {@link DbUniqueConstraint}.
   */
  static DbUniqueConstraint of(String name, DbTableReference<?> sourceTable, List<DbColumnReference> sourceColumns,
      DbConstraintState state) {

    return new DbUniqueConstraintImpl(name, sourceTable, sourceColumns, state);
  }
}
