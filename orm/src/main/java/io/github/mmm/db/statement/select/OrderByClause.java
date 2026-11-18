/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.statement.select;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.github.mmm.db.statement.AbstractTypedClause;
import io.github.mmm.db.statement.DbClause;
import io.github.mmm.db.statement.DbMainClause;
import io.github.mmm.property.criteria.CriteriaOrdering;

/// A [OrderByClause]-[DbClause] of an SQL [SelectStatement].
///
/// @param <R> type of the result of the selection.
/// @since 1.0.0
public class OrderByClause<R> extends AbstractTypedClause<R, OrderByClause<R>> implements DbMainClause<R> {

  /// Name of [OrderByClause] for marshaling.
  public static final String NAME_ORDER_BY = "ORDER BY";

  private final SelectStatement<R> statement;

  private final List<CriteriaOrdering> orderings;

  /// The constructor.
  ///
  /// @param statement the owning [SelectStatement].
  public OrderByClause(SelectStatement<R> statement) {

    super();
    this.statement = statement;
    this.orderings = new ArrayList<>();
  }

  @Override
  public boolean isOmit() {

    return this.orderings.isEmpty();
  }

  /// @param ordering the [CriteriaOrdering] to add.
  /// @return this [OrderByClause]-class itself for fluent API calls.
  public OrderByClause<R> and(CriteriaOrdering ordering) {

    Objects.requireNonNull(ordering);
    this.orderings.add(ordering);
    return this;
  }

  /// @param criteriaOrderings the [CriteriaOrdering]s to add.
  /// @return this [OrderByClause]-class itself for fluent API calls.
  public OrderByClause<R> and(CriteriaOrdering... criteriaOrderings) {

    for (CriteriaOrdering ordering : criteriaOrderings) {
      and(ordering);
    }
    return this;
  }

  /// @return the [List] of [CriteriaOrdering]s to order by.
  public List<CriteriaOrdering> getOrderings() {

    return this.orderings;
  }

  @Override
  public SelectStatement<R> get() {

    return this.statement;
  }

}
