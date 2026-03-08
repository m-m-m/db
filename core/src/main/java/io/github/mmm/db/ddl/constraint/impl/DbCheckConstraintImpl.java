/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.ddl.constraint.impl;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import io.github.mmm.db.ddl.column.DbColumnReference;
import io.github.mmm.db.ddl.constraint.DbCheckConstraint;
import io.github.mmm.db.ddl.constraint.DbConstraint;
import io.github.mmm.db.ddl.constraint.state.DbConstraintState;
import io.github.mmm.db.ddl.table.DbTableReference;
import io.github.mmm.property.criteria.CriteriaPredicate;
import io.github.mmm.value.PropertyPath;

/**
 * {@link DbConstraint} to check a {@link #getPredicate() predicate} (condition).
 *
 * @since 1.0.0
 */
public final class DbCheckConstraintImpl extends DbConstraintImpl implements DbCheckConstraint {

  private final CriteriaPredicate predicate;

  /**
   * The constructor.
   *
   * @param name the {@link #getName() constraint name}.
   * @param sourceTable the {@link #getSourceTable() source table}.
   * @param sourceColumns the {@link #getSourceColumns() source columns}.
   * @param predicate the {@link #getPredicate() predicate} to check.
   * @param state the {@link #getState state}.
   */
  public DbCheckConstraintImpl(String name, DbTableReference<?> sourceTable, List<DbColumnReference> sourceColumns,
      CriteriaPredicate predicate, DbConstraintState state) {

    if (sourceColumns == null) {
      Collection<PropertyPath<?>> properties = predicate.collectProperties();
      sourceColumns = properties.stream().map(DbColumnReference::of).toList();
    }
    super(name, sourceTable, sourceColumns, state);
    Objects.requireNonNull(predicate);
    this.predicate = predicate;
  }

  /**
   * @return the {@link CriteriaPredicate} to check.
   */
  @Override
  public CriteriaPredicate getPredicate() {

    return this.predicate;
  }

  @Override
  public DbCheckConstraintImpl withState(DbConstraintState newState) {

    if (newState == this.state) {
      return this;
    }
    return new DbCheckConstraintImpl(this.name, this.sourceTable, this.sourceColumns, this.predicate, newState);
  }

}
