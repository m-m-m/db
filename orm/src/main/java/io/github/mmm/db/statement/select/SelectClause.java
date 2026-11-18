/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.statement.select;

import java.util.ArrayList;
import java.util.List;

import io.github.mmm.bean.WritableBean;
import io.github.mmm.db.statement.AbstractDbClause;
import io.github.mmm.db.statement.DbIncompleteStartClause;
import io.github.mmm.db.statement.DbStartClause;
import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.marshall.StructuredReader;
import io.github.mmm.property.criteria.CriteriaExpression;
import io.github.mmm.value.CriteriaObject;
import io.github.mmm.value.PropertyPath;

/// [DbStartClause] of a [SelectStatement] to query data from the database. As [SelectStatement]s are
/// rather complex, this class is abstract to allow property type-safety and guidance of fluent API and code completion
/// via derived sub-classes for different scenarios. For simplest usage you can use the static methods provided by
/// [SelectClause] to start your query (typing `Select.` and starting code completion). However, you are
/// still free to use regular instantiation like for other statements (typing `new Select` and starting code
/// completion).
///
/// **ATTENTION:** Please note that after
/// [unmarshalling][io.github.mmm.db.statement.DbStatementMarshalling#readObject(StructuredReader)] or
/// [parsing][io.github.mmm.db.statement.DbStatementParser#parse(String)] a [SelectStatement] the
/// [select][SelectStatement#getSelect()] clause may not be of any of the expected sub-classes such as
/// [SelectEntityClause], [SelectSingleClause], [SelectResultClause], or
/// [SelectProjectionClause]. Use `isSelect*` methods like [#isSelectEntity()] to determine the actual
/// selection type.
///
/// @param <R> type of the result of the selection.
/// @since 1.0.0
public abstract class SelectClause<R> extends AbstractDbClause implements DbIncompleteStartClause {

  /// [Result name][#getResultName()] for [SelectEntityClause].
  public static final String VALUE_RESULT_ENTITY = "entity";

  /// [Result name][#getResultName()] for [SelectSingleClause].
  public static final String VALUE_RESULT_SINLGE = "1";

  /// [Result name][#getResultName()] for [SelectSequenceNextValueClause].
  public static final String VALUE_RESULT_SEQ_NEXT_VAL = "seq.nextval";

  /// [Result name][#getResultName()] for [SelectResultClause].
  public static final String VALUE_RESULT_RESULT = "result";

  SelectStatement<R> statement;

  private final List<? extends CriteriaObject<?>> selections;

  private transient R resultBean;

  private String resultName;

  private boolean distinct;

  /// The constructor.
  ///
  /// @param resultBean the [result bean][#getResultBean()].
  protected SelectClause(R resultBean) {

    super();
    this.selections = new ArrayList<>();
    setResultBean(resultBean);
  }

  /// @return `true` for `DISTINCT` selection (filter out duplicates), `false` otherwise.
  public boolean isDistinct() {

    return this.distinct;
  }

  /// @return the [List] of selections. Only use for generic code. To build queries use fluent API methods.
  public List<? extends CriteriaObject<?>> getSelections() {

    return this.selections;
  }

  @Override
  public SelectStatement<R> get() {

    return this.statement;
  }

  /// @return the result [bean][io.github.mmm.bean.WritableBean] for [SelectProjectionClause] or
  ///         [SelectEntityClause], otherwise `null`.
  public R getResultBean() {

    return this.resultBean;
  }

  /// @param resultBean the new value of [#getResultBean()].
  protected void setResultBean(R resultBean) {

    if ((resultBean instanceof WritableBean) && (this.resultName == null)) {
      this.resultName = ((WritableBean) resultBean).getType().getStableName();
    }
    this.resultBean = resultBean;
  }

  /// @return the optional result name. Will be [#VALUE_RESULT_ENTITY] for [SelectEntityClause],
  ///         [#VALUE_RESULT_SINLGE] for [SelectSingleClause], and [#VALUE_RESULT_RESULT] for
  ///         [SelectResultClause].
  /// @see #getResultBean()
  /// @see io.github.mmm.db.statement.AbstractEntityClause#getName()
  public String getResultName() {

    return this.resultName;
  }

  /// @param resultName new value of [#getResultName()].
  protected void setResultName(String resultName) {

    this.resultName = resultName;
  }

  /// @return `true` if the [primary entity][SelectFromClause#getEntity()] is selected, `false`
  ///         otherwise.
  /// @see SelectEntityClause
  public boolean isSelectEntity() {

    return VALUE_RESULT_ENTITY.equals(this.resultName);
  }

  /// @return `true` if only a [single][SelectSingleClause] [selection][#getSelections()] is selected as
  ///         raw value, `false` otherwise.
  /// @see SelectSingleClause
  /// @see SelectSequenceNextValueClause
  public boolean isSelectSingle() {

    return VALUE_RESULT_SINLGE.equals(this.resultName);
  }

  /// @return `true` if only if the [main entity][SelectFromClause#getEntity()] is selected, `false`
  ///         otherwise.
  /// @see SelectEntityClause
  public boolean isSelectResult() {

    return VALUE_RESULT_SINLGE.equals(this.resultName);
  }

  /// Sets [DISTINCT][#isDistinct()] selection (filter out duplicates).
  ///
  /// @return this [SelectClause] for fluent API calls.
  public SelectClause<R> distinct() {

    this.distinct = true;
    return this;
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  void add(CriteriaObject<?> selection) {

    ((List) this.selections).add(selection);
  }

  /// @param expression the [CriteriaExpression] to add to the selection.
  /// @return this [SelectClause] for fluent API calls.
  protected SelectClause<R> and(CriteriaExpression<?> expression) {

    add(expression);
    return this;
  }

  /// @param expressions the [CriteriaExpression]s to add to the selection.
  /// @return this [SelectClause] for fluent API calls.
  protected SelectClause<R> and(CriteriaExpression<?>... expressions) {

    for (CriteriaExpression<?> aggregation : expressions) {
      add(aggregation);
    }
    return this;
  }

  /// @param property the [property][PropertyPath] to add to the selection.
  /// @return this [SelectClause] for fluent API calls.
  protected SelectClause<R> and(PropertyPath<?> property) {

    add(property);
    return this;
  }

  /// @param properties the [properties][PropertyPath] to add to the selection.
  /// @return this [SelectClause] for fluent API calls.
  protected SelectClause<R> and(PropertyPath<?>... properties) {

    for (PropertyPath<?> property : properties) {
      add(property);
    }
    return this;
  }

  /// @param <E> type of the [EntityBean].
  /// @param entity the [entity][EntityBean] to select from.
  /// @return the [SelectFromClause] for fluent API calls.
  protected <E extends EntityBean> SelectFromClause<R, E> from(E entity) {

    return new SelectFromClause<>(this, entity);
  }

  /// Alternative for `new SelectResult()`.
  ///
  /// @return the new [SelectResultClause] clause.
  public static SelectResultClause result() {

    return new SelectResultClause();
  }

}
