/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.ddl.constraint;

import java.util.List;

import io.github.mmm.db.ddl.column.DbColumnReference;
import io.github.mmm.db.ddl.constraint.impl.DbPrimaryKeyConstraintImpl;
import io.github.mmm.db.ddl.constraint.state.DbConstraintState;
import io.github.mmm.db.ddl.table.DbTableReference;
import io.github.mmm.db.name.DbKeyword;

/**
 * Foreign key {@link DbConstraint} uniquely identifying a different {@link io.github.mmm.entity.bean.EntityBean entity}
 * (row from another table).
 *
 * @since 1.0.0
 */
public interface DbPrimaryKeyConstraint extends DbConstraint {

  /** {@link #getType() Type} {@value}. */
  String TYPE = DbKeyword.PRIMARY_KEY;

  /** @see #getNamePrefix() */
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

  /**
   * @param sourceTable the {@link #getSourceTable() source table}.
   * @param sourceColumn the {@link #getSourceColumns() source column}.
   * @return the new {@link DbPrimaryKeyConstraint}.
   */
  static DbPrimaryKeyConstraint of(DbTableReference<?> sourceTable, DbColumnReference sourceColumn) {

    return of(null, sourceTable, sourceColumn);
  }

  /**
   * @param sourceTable the {@link #getSourceTable() source table}.
   * @param sourceColumns the {@link #getSourceColumns() source columns}.
   * @return the new {@link DbPrimaryKeyConstraint}.
   */
  static DbPrimaryKeyConstraint of(DbTableReference<?> sourceTable, List<DbColumnReference> sourceColumns) {

    return of(null, sourceTable, sourceColumns, DbConstraintState.DEFAULT);
  }

  /**
   * @param sourceTable the {@link #getSourceTable() source table}.
   * @param sourceColumns the {@link #getSourceColumns() source columns}.
   * @param state the {@link #getState state}.
   * @return the new {@link DbPrimaryKeyConstraint}.
   */
  static DbPrimaryKeyConstraint of(DbTableReference<?> sourceTable, List<DbColumnReference> sourceColumns,
      DbConstraintState state) {

    return of(null, sourceTable, sourceColumns, state);
  }

  /**
   * @param name the {@link #getName() constraint name}.
   * @param sourceTable the {@link #getSourceTable() source table}.
   * @param sourceColumn the {@link #getSourceColumns() source column}.
   * @return the new {@link DbPrimaryKeyConstraint}.
   */
  static DbPrimaryKeyConstraint of(String name, DbTableReference<?> sourceTable, DbColumnReference sourceColumn) {

    return of(name, sourceTable, sourceColumn, DbConstraintState.DEFAULT);
  }

  /**
   * @param name the {@link #getName() constraint name}.
   * @param sourceTable the {@link #getSourceTable() source table}.
   * @param sourceColumns the {@link #getSourceColumns() source columns}.
   * @return the new {@link DbPrimaryKeyConstraint}.
   */
  static DbPrimaryKeyConstraint of(String name, DbTableReference<?> sourceTable,
      List<DbColumnReference> sourceColumns) {

    return of(name, sourceTable, sourceColumns, DbConstraintState.DEFAULT);
  }

  /**
   * @param name the {@link #getName() constraint name}.
   * @param sourceTable the {@link #getSourceTable() source table}.
   * @param sourceColumn the {@link #getSourceColumns() source column}.
   * @param state the {@link #getState state}.
   * @return the new {@link DbPrimaryKeyConstraint}.
   */
  static DbPrimaryKeyConstraint of(String name, DbTableReference<?> sourceTable, DbColumnReference sourceColumn,
      DbConstraintState state) {

    return of(name, sourceTable, List.of(sourceColumn), state);
  }

  /**
   * @param name the {@link #getName() constraint name}.
   * @param sourceTable the {@link #getSourceTable() source table}.
   * @param sourceColumns the {@link #getSourceColumns() source columns}.
   * @param state the {@link #getState state}.
   * @return the new {@link DbPrimaryKeyConstraint}.
   */
  static DbPrimaryKeyConstraint of(String name, DbTableReference<?> sourceTable, List<DbColumnReference> sourceColumns,
      DbConstraintState state) {

    return new DbPrimaryKeyConstraintImpl(name, sourceTable, sourceColumns, state);
  }

}
