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

/// [DbConstraint] checking a given [predicate][#getPredicate()].
///
/// @since 1.0.0
public interface DbCheckConstraint extends DbConstraint {

  /// [Type][#getType()] {@value}.
  String TYPE = DbKeyword.CHECK;

  /// @see #getNamePrefix()
  String PREFIX = "CK_";

  /// @return the [CriteriaPredicate] to check.
  CriteriaPredicate getPredicate();

  @Override
  default String getNamePrefix() {

    return PREFIX;
  }

  @Override
  default String getType() {

    return TYPE;
  }

  /// @param name the [constraint name][#getName()].
  /// @param sourceTable the [source table][#getSourceTable()].
  /// @param predicate the [predicate][#getPredicate()] to check.
  /// @return the new [DbCheckConstraint].
  static DbCheckConstraint of(String name, DbTableReference<?> sourceTable, CriteriaPredicate predicate) {

    return of(name, sourceTable, predicate, DbConstraintState.DEFAULT);
  }

  /// @param name the [constraint name][#getName()].
  /// @param sourceTable the [source table][#getSourceTable()].
  /// @param sourceColumns the [source columns][#getSourceColumns()].
  /// @param predicate the [predicate][#getPredicate()] to check.
  /// @param state the [state][#getState].
  /// @return the new [DbCheckConstraint].
  static DbCheckConstraint of(String name, DbTableReference<?> sourceTable, CriteriaPredicate predicate,
      DbConstraintState state) {

    return of(name, sourceTable, null, predicate, state);
  }

  /// @param name the [constraint name][#getName()].
  /// @param sourceTable the [source table][#getSourceTable()].
  /// @param sourceColumns the [source columns][#getSourceColumns()].
  /// @param predicate the [predicate][#getPredicate()] to check.
  /// @return the new [DbCheckConstraint].
  static DbCheckConstraint of(String name, DbTableReference<?> sourceTable, List<DbColumnReference> sourceColumns,
      CriteriaPredicate predicate) {

    return of(name, sourceTable, sourceColumns, predicate, DbConstraintState.DEFAULT);
  }

  /// @param name the [constraint name][#getName()].
  /// @param sourceTable the [source table][#getSourceTable()].
  /// @param sourceColumns the [source columns][#getSourceColumns()].
  /// @param predicate the [predicate][#getPredicate()] to check.
  /// @param state the [state][#getState].
  /// @return the new [DbCheckConstraint].
  static DbCheckConstraint of(String name, DbTableReference<?> sourceTable, List<DbColumnReference> sourceColumns,
      CriteriaPredicate predicate, DbConstraintState state) {

    return new DbCheckConstraintImpl(name, sourceTable, sourceColumns, predicate, state);
  }

}
