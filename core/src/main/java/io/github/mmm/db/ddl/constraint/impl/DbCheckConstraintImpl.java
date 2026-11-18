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
import io.github.mmm.db.name.DbQualifiedName;
import io.github.mmm.property.criteria.CriteriaPredicate;
import io.github.mmm.value.PropertyPath;

/// [DbConstraint] to check a [predicate][#getPredicate()] (condition).
///
/// @since 1.0.0
public final class DbCheckConstraintImpl extends DbConstraintImpl implements DbCheckConstraint {

  private final CriteriaPredicate predicate;

  /// The constructor.
  ///
  /// @param name the [constraint name][#getName()].
  /// @param sourceTable the [source table][#getSourceTable()].
  /// @param sourceColumns the [source columns][#getSourceColumns()].
  /// @param predicate the [predicate][#getPredicate()] to check.
  /// @param state the [state][#getState].
  public DbCheckConstraintImpl(String name, DbTableReference<?> sourceTable, List<DbColumnReference> sourceColumns,
      CriteriaPredicate predicate, DbConstraintState state) {

    this(DbQualifiedName.ofName(name), sourceTable, sourceColumns, predicate, state);
  }

  DbCheckConstraintImpl(DbQualifiedName name, DbTableReference<?> sourceTable, List<DbColumnReference> sourceColumns,
      CriteriaPredicate predicate, DbConstraintState state) {

    if (sourceColumns == null) {
      Collection<PropertyPath<?>> properties = predicate.collectProperties();
      sourceColumns = properties.stream().map(DbColumnReference::of).toList();
    }
    super(name, sourceTable, sourceColumns, state);
    Objects.requireNonNull(predicate);
    this.predicate = predicate;
  }

  /// @return the [CriteriaPredicate] to check.
  @Override
  public CriteriaPredicate getPredicate() {

    return this.predicate;
  }

  @Override
  public DbCheckConstraintImpl withState(DbConstraintState newState) {

    if (newState == this.state) {
      return this;
    }
    return new DbCheckConstraintImpl(this.rawName, this.sourceTable, this.sourceColumns, this.predicate, newState);
  }

}
