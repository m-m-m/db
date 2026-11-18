package io.github.mmm.db.statement;

import io.github.mmm.property.criteria.CriteriaPredicate;
import io.github.mmm.property.criteria.PredicateOperator;

/// A [DbTypedClause] that supports a [WHERE][#where(CriteriaPredicate)] [DbClause].
///
/// @param <E> type of the [entity][AbstractEntityClause#getEntity()].
/// @since 1.0.0
public interface TypedClauseWithWhere<E> extends DbTypedClause<E> {

  /// @param predicate the [CriteriaPredicate] to add as [WhereClause]-clause.
  /// @return the [WhereClause]-[DbClause] for fluent API calls.
  WhereClause<E, ?> where(CriteriaPredicate predicate);

  /// @param predicates the [CriteriaPredicate]s to add as [WhereClause]-clause. They will be combined with
  ///        [AND][PredicateOperator#AND].
  /// @return the [WhereClause]-[DbClause] for fluent API calls.
  WhereClause<E, ?> where(CriteriaPredicate... predicates);

}
