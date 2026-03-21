/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.ddl.constraint;

import java.util.List;

import io.github.mmm.db.ddl.column.DbColumnReference;
import io.github.mmm.db.ddl.constraint.impl.DbCheckConstraintImpl;
import io.github.mmm.db.ddl.constraint.state.DbConstraintState;
import io.github.mmm.db.ddl.table.DbTableReference;
import io.github.mmm.db.name.DbKeyword;
import io.github.mmm.property.criteria.CriteriaPredicate;

/**
 * {@link DbConstraint} checking a given {@link #getPredicate() predicate}.
 *
 * @since 1.0.0
 */
public interface DbCheckConstraint extends DbConstraint {

  /** {@link #getType() Type} {@value}. */
  String TYPE = DbKeyword.CHECK;

  /** @see #getNamePrefix() */
  String PREFIX = "CK_";

  /**
   * @return the {@link CriteriaPredicate} to check.
   */
  CriteriaPredicate getPredicate();

  @Override
  default String getNamePrefix() {

    return PREFIX;
  }

  @Override
  default String getType() {

    return TYPE;
  }

  /**
   * @param name the {@link #getName() constraint name}.
   * @param sourceTable the {@link #getSourceTable() source table}.
   * @param predicate the {@link #getPredicate() predicate} to check.
   * @return the new {@link DbCheckConstraint}.
   */
  static DbCheckConstraint of(String name, DbTableReference<?> sourceTable, CriteriaPredicate predicate) {

    return of(name, sourceTable, predicate, DbConstraintState.DEFAULT);
  }

  /**
   * @param name the {@link #getName() constraint name}.
   * @param sourceTable the {@link #getSourceTable() source table}.
   * @param sourceColumns the {@link #getSourceColumns() source columns}.
   * @param predicate the {@link #getPredicate() predicate} to check.
   * @param state the {@link #getState state}.
   * @return the new {@link DbCheckConstraint}.
   */
  static DbCheckConstraint of(String name, DbTableReference<?> sourceTable, CriteriaPredicate predicate,
      DbConstraintState state) {

    return of(name, sourceTable, null, predicate, state);
  }

  /**
   * @param name the {@link #getName() constraint name}.
   * @param sourceTable the {@link #getSourceTable() source table}.
   * @param sourceColumns the {@link #getSourceColumns() source columns}.
   * @param predicate the {@link #getPredicate() predicate} to check.
   * @return the new {@link DbCheckConstraint}.
   */
  static DbCheckConstraint of(String name, DbTableReference<?> sourceTable, List<DbColumnReference> sourceColumns,
      CriteriaPredicate predicate) {

    return of(name, sourceTable, sourceColumns, predicate, DbConstraintState.DEFAULT);
  }

  /**
   * @param name the {@link #getName() constraint name}.
   * @param sourceTable the {@link #getSourceTable() source table}.
   * @param sourceColumns the {@link #getSourceColumns() source columns}.
   * @param predicate the {@link #getPredicate() predicate} to check.
   * @param state the {@link #getState state}.
   * @return the new {@link DbCheckConstraint}.
   */
  static DbCheckConstraint of(String name, DbTableReference<?> sourceTable, List<DbColumnReference> sourceColumns,
      CriteriaPredicate predicate, DbConstraintState state) {

    return new DbCheckConstraintImpl(name, sourceTable, sourceColumns, predicate, state);
  }

}
