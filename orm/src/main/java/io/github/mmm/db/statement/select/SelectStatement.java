/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.statement.select;

import java.util.function.Consumer;

import io.github.mmm.db.statement.AbstractDbClause;
import io.github.mmm.db.statement.AbstractDbStatement;
import io.github.mmm.db.statement.AliasMap;
import io.github.mmm.db.statement.DbClause;
import io.github.mmm.db.statement.DbStatement;
import io.github.mmm.db.statement.DbStatementType;
import io.github.mmm.db.statement.impl.DbStatementTypeImpl;

/// [DbStatement] to query data from the database using a [SelectClause].
///
/// @param <R> type of the result of the selection.
/// @since 1.0.0
public class SelectStatement<R> extends AbstractDbStatement<R> {

  private final SelectClause<R> select;

  private final SelectFromClause<R, ?> from;

  private final SelectWhere<R> where;

  private final GroupByClause<R> groupBy;

  private final HavingClause<R> having;

  private final OrderByClause<R> orderBy;

  /// The constructor.
  ///
  /// @param select the [starting][#getSelect()] [SELECT][SelectClause].
  /// @param from the [FROM][#getFrom()].
  protected SelectStatement(SelectClause<R> select, SelectFromClause<R, ?> from) {

    super();
    this.select = select;
    this.from = from;
    this.where = new SelectWhere<>(this);
    this.groupBy = new GroupByClause<>(this);
    this.having = new HavingClause<>(this);
    this.orderBy = new OrderByClause<>(this);
  }

  /// @deprecated use [#getSelect()] to make it more explicit.
  @Deprecated
  @Override
  public SelectClause<R> getStart() {

    return this.select;
  }

  /// @return the opening [SelectClause].
  public SelectClause<R> getSelect() {

    return this.select;
  }

  /// @return the [FROM][SelectFromClause] [DbClause].
  public SelectFromClause<R, ?> getFrom() {

    return this.from;
  }

  /// @return the [Where][SelectWhere]-[DbClause].
  public SelectWhere<R> getWhere() {

    return this.where;
  }

  /// @return the [GroupByClause]-[DbClause].
  public GroupByClause<R> getGroupBy() {

    return this.groupBy;
  }

  /// @return the [HavingClause]-[DbClause].
  public HavingClause<R> getHaving() {

    return this.having;
  }

  /// @return the [OrderByClause]-[DbClause].
  public OrderByClause<R> getOrderBy() {

    return this.orderBy;
  }

  @Override
  protected void addClauses(Consumer<AbstractDbClause> consumer) {

    consumer.accept(this.select);
    consumer.accept(this.from);
    consumer.accept(this.where);
    consumer.accept(this.groupBy);
    consumer.accept(this.having);
    consumer.accept(this.orderBy);
  }

  @Override
  public DbStatementType getType() {

    return DbStatementTypeImpl.SELECT;
  }

  @Override
  protected AliasMap getAliasMap() {

    return this.from.getAliasMap();
  }

}
