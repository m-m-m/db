/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.statement;

import java.util.ArrayList;
import java.util.List;

import io.github.mmm.db.statement.select.HavingClause;
import io.github.mmm.db.statement.select.SelectWhere;
import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.property.criteria.CriteriaPredicate;
import io.github.mmm.property.criteria.PredicateOperator;

/// [DbClause] containing [predicates][#getPredicates()] like a [SelectWhere]- or [HavingClause]-clause.
///
/// @param <E> type of the [EntityBean] to query.
/// @param <SELF> type of this class itself.
/// @since 1.0.0
public abstract class PredicateClause<E, SELF extends PredicateClause<E, SELF>> extends AbstractTypedClause<E, SELF> {

  /// Name of the property [#getPredicates()] for marshaling.
  public static final String NAME_PREDICATES = "and";

  private final List<CriteriaPredicate> predicates;

  private transient boolean simplify;

  /// The constructor.
  public PredicateClause() {

    super();
    this.predicates = new ArrayList<>();
    this.simplify = true;
  }

  /// @param predicate the [CriteriaPredicate] to add. They will be combined with [AND][PredicateOperator#AND]
  ///        if called multiple times.
  /// @return this [DbClause] itself for fluent API calls.
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public SELF and(CriteriaPredicate predicate) {

    if (predicate != null) {
      if (this.simplify) {
        predicate = predicate.simplify();
        if (predicate.getOperator() == PredicateOperator.AND) {
          // AND should only be used here with predicated.
          // Boolean literal would not make sense in query as well after simplification.
          this.predicates.addAll((List) predicate.getArgs());
          return self();
        }
      }
      this.predicates.add(predicate);
    }
    return self();
  }

  /// @param criteriaPredicates the [CriteriaPredicate]s to add. They will be combined with
  ///        [AND][PredicateOperator#AND].
  /// @return this [DbClause] itself for fluent API calls.
  public SELF and(CriteriaPredicate... criteriaPredicates) {

    for (CriteriaPredicate predicate : criteriaPredicates) {
      and(predicate);
    }
    return self();
  }

  /// @return the [List] of [CriteriaPredicate]s.
  public List<CriteriaPredicate> getPredicates() {

    return this.predicates;
  }

  @Override
  public boolean isOmit() {

    return this.predicates.isEmpty();
  }

}
