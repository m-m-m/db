/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.statement.select;

import io.github.mmm.db.statement.DbMainClause;
import io.github.mmm.property.criteria.CriteriaOrdering;

/// [DbMainClause] allowing to [begin][#orderBy(CriteriaOrdering)] a [HavingClause]-clause.
///
/// @param <R> type of the result of the selection.
/// @since 1.0.0
public interface ClauseWithOrderBy<R> extends DbMainClause<R> {

  @Override
  SelectStatement<R> get();

  /// @param ordering the [CriteriaOrdering] to add as [OrderByClause]-clause.
  /// @return the [OrderByClause]-clause for fluent API calls.
  default OrderByClause<R> orderBy(CriteriaOrdering ordering) {

    OrderByClause<R> orderBy = get().getOrderBy();
    orderBy.and(ordering);
    return orderBy;
  }

  /// @param orderings the [CriteriaOrdering]s to add as [OrderByClause]-clause.
  /// @return the [OrderByClause]-clause for fluent API calls.
  default OrderByClause<R> orderBy(CriteriaOrdering... orderings) {

    OrderByClause<R> orderBy = get().getOrderBy();
    orderBy.and(orderings);
    return orderBy;
  }

}
