/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.statement.select;

import java.util.List;
import java.util.Objects;

import io.github.mmm.bean.WritableBean;
import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.property.criteria.CriteriaAggregation;
import io.github.mmm.property.criteria.ProjectionProperty;
import io.github.mmm.value.PropertyPath;

/// [SelectClause] to query a projection. This is called a _constructor query_ in JPA such as
///
/// ```
/// SELECT new com.example.ResultBean(sum(i.price), count(i.price)) FROM Item AS i GROUP BY i.order
/// ```
///
/// As analogy we can create `ResultBean` as [WritableBean]:
///
/// ```
/// public interface ResultBean extends [WritableBean] {
///   [io.github.mmm.property.number.bigdecimal.BigDecimalProperty] Total();
///   [io.github.mmm.property.number.integers.IntegerProperty] Count();
///   static Result of() {
///     return [io.github.mmm.bean.BeanFactory#get()].[create][io.github.mmm.bean.BeanFactory#create(Class)](Result.class);
///   }
/// }
/// ```
///
/// Now we can create the above JPA query example as following:
///
/// ```
/// ResultBean result = ResultBean.of();
/// Item item = Item.of();
/// SelectStatement<ResultBean> query = new [SelectProjectionClause]<>(result, item.Price().count()).from(item).as("i").groupBy(i.Order()).get();
/// ```
///
/// @param <R> type of the result of the selection.
/// @since 1.0.0
public final class SelectProjectionClause<R extends WritableBean> extends SelectClause<R> {

  /// The constructor.
  ///
  /// @param resultBean the [result bean][#getResultBean()] to project to (via "constructor query").
  public SelectProjectionClause(R resultBean) {

    super(resultBean);
  }

  /// The constructor.
  ///
  /// @param <V> type of the type of the [property value][PropertyPath#get()].
  /// @param resultBean the [result bean][#getResultBean()] to project to (via "constructor query").
  /// @param selection the [PropertyPath] to add to the selection.
  /// @param property the [projection property][ProjectionProperty#getProperty()] to map to.
  public <V> SelectProjectionClause(R resultBean, PropertyPath<V> selection, PropertyPath<V> property) {

    this(resultBean);
    and(selection, property);
  }

  /// The constructor.
  ///
  /// @param <V> type of the type of the [property value][PropertyPath#get()].
  /// @param resultBean the [result bean][#getResultBean()] to project to (via "constructor query").
  /// @param selection the [CriteriaAggregation] to add to the selection.
  /// @param property the [projection property][ProjectionProperty#getProperty()] to map to.
  public <V extends Number & Comparable<V>> SelectProjectionClause(R resultBean, CriteriaAggregation<V> selection,
      PropertyPath<V> property) {

    this(resultBean);
    and(selection, property);
  }

  /// @param <V> type of the type of the [property value][PropertyPath#get()].
  /// @param selection the [PropertyPath] to add to the selection.
  /// @param property the [projection property][ProjectionProperty#getProperty()] to map to.
  /// @return this [SelectClause] for fluent API calls.
  public <V> SelectProjectionClause<R> and(PropertyPath<V> selection, PropertyPath<V> property) {

    ProjectionProperty<V> projectionProperty = ProjectionProperty.of(selection, property);
    return and(projectionProperty);
  }

  /// @param <V> type of the type of the [property value][PropertyPath#get()].
  /// @param selection the [CriteriaAggregation] to add to the selection.
  /// @param property the [projection property][ProjectionProperty#getProperty()] to map to.
  /// @return this [SelectClause] for fluent API calls.
  public <V extends Number & Comparable<V>> SelectProjectionClause<R> and(CriteriaAggregation<V> selection,
      PropertyPath<V> property) {

    ProjectionProperty<V> projectionProperty = ProjectionProperty.of(selection, property);
    return and(projectionProperty);
  }

  /// @param projectionProperty the [ProjectionProperty] to add to the selection.
  /// @return this [SelectProjectionClause] for fluent API calls.
  public SelectProjectionClause<R> and(ProjectionProperty<?> projectionProperty) {

    Objects.requireNonNull(projectionProperty, "projectionProperty");
    add(projectionProperty);
    return this;
  }

  /// @param projectionProperties the [ProjectionProperty] instances to add to the selection.
  /// @return this [SelectProjectionClause] for fluent API calls.
  public SelectProjectionClause<R> and(ProjectionProperty<?>... projectionProperties) {

    for (ProjectionProperty<?> property : projectionProperties) {
      add(property);
    }
    return this;
  }

  @Override
  public SelectProjectionClause<R> distinct() {

    super.distinct();
    return this;
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<ProjectionProperty<?>> getSelections() {

    return (List<ProjectionProperty<?>>) super.getSelections();
  }

  @Override
  public <E extends EntityBean> SelectFromClause<R, E> from(E entity) {

    if (getSelections().isEmpty()) {
      throw new IllegalStateException("Selections must not be empty! Call 'and' method at least once!");
    }
    return super.from(entity);
  }

  @Override
  public boolean isSelectEntity() {

    return false;
  }

  @Override
  public boolean isSelectResult() {

    return false;
  }

  @Override
  public boolean isSelectSingle() {

    return false;
  }
}
