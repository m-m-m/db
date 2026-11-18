/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.statement.create;

import java.util.ArrayList;
import java.util.List;

import io.github.mmm.db.ddl.column.DbColumnReference;
import io.github.mmm.db.ddl.constraint.DbConstraint;
import io.github.mmm.db.ddl.table.DbTableReference;
import io.github.mmm.db.statement.AbstractTypedClause;
import io.github.mmm.db.statement.DbClause;
import io.github.mmm.db.statement.DbMainClause;
import io.github.mmm.entity.bean.EntityBean;

/// A [CreateTableContentsClause]-[DbClause] of an SQL [CreateTableStatement].
///
/// @param <E> type of the [entity][io.github.mmm.db.statement.AbstractEntityClause#getEntity()].
/// @since 1.0.0
public class CreateTableContentsClause<E extends EntityBean>
    extends AbstractTypedClause<E, CreateTableContentsClause<E>> implements DbMainClause<E>, DbCreateTableClause<E> {

  private final CreateTableStatement<E> statement;

  private final List<DbColumnReference> columns;

  private final List<DbConstraint> constraints;

  /// The constructor.
  ///
  /// @param statement the [CreateTableStatement].
  public CreateTableContentsClause(CreateTableStatement<E> statement) {

    super();
    this.statement = statement;
    this.columns = new ArrayList<>();
    this.constraints = new ArrayList<>();
  }

  @Override
  public CreateTableStatement<E> get() {

    return this.statement;
  }

  @Override
  public DbTableReference<E> asTableReference() {

    return this.statement.getCreateTable();
  }

  /// @return the [List] of [DbColumnReference]s.
  public List<DbColumnReference> getColumns() {

    return this.columns;
  }

  /// @return the [List] of [DbConstraint]s.
  public List<DbConstraint> getConstraints() {

    return this.constraints;
  }

  @Override
  public CreateTableContentsClause<E> column(DbColumnReference column) {

    this.columns.add(column);
    return this;
  }

  /// @param constraint the [DbConstraint] to add.
  /// @return this [CreateTableContentsClause] for fluent API calls.
  @Override
  public CreateTableContentsClause<E> constraint(DbConstraint constraint) {

    this.constraints.add(constraint);
    return this;
  }

}
