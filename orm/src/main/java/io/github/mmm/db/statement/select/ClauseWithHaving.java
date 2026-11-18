/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.statement.select;

import io.github.mmm.db.statement.DbMainClause;
import io.github.mmm.property.criteria.CriteriaPredicate;

/// [DbMainClause] allowing to [begin][#having(CriteriaPredicate)] a [HavingClause]-clause.
///
/// @param <R> type of the result of the selection.
/// @since 1.0.0
public interface ClauseWithHaving<R> extends DbMainClause<R> {

  @Override
  SelectStatement<R> get();

  /// @param predicate the [CriteriaPredicate] to add as [HavingClause]-clause.
  /// @return the [HavingClause]-clause for fluent API calls.
  default HavingClause<R> having(CriteriaPredicate predicate) {

    HavingClause<R> having = get().getHaving();
    having.and(predicate);
    return having;
  }

  /// @param predicates the [CriteriaPredicate]s to add as [HavingClause]-clause. They will be combined with
  ///        [AND][io.github.mmm.property.criteria.PredicateOperator#AND].
  /// @return the [HavingClause]-clause for fluent API calls.
  default HavingClause<R> having(CriteriaPredicate... predicates) {

    HavingClause<R> having = get().getHaving();
    having.and(predicates);
    return having;
  }

}
