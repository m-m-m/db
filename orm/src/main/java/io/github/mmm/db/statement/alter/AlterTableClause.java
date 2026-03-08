/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.statement.alter;

import io.github.mmm.db.ddl.column.DbColumnReference;
import io.github.mmm.db.ddl.constraint.DbConstraint;
import io.github.mmm.db.ddl.table.DbTableReference;
import io.github.mmm.db.statement.AbstractEntityClause;
import io.github.mmm.db.statement.AliasMap;
import io.github.mmm.db.statement.DbClause;
import io.github.mmm.db.statement.DbIncompleteStartClause;
import io.github.mmm.entity.bean.EntityBean;

/**
 * A {@link AlterTableClause}-{@link DbClause} of an SQL {@link AlterTableStatement}.
 *
 * @param <E> type of the {@link #getEntity() entity}.
 * @since 1.0.0
 */
public class AlterTableClause<E extends EntityBean> extends AbstractEntityClause<E, E, AlterTableClause<E>>
    implements DbIncompleteStartClause, DbAlterTableClause<E> {

  private final AlterTableStatement<E> statement;

  /**
   * The constructor.
   *
   * @param entity the {@link #getEntity() entity} to operate on.
   */
  public AlterTableClause(E entity) {

    this(entity, null);
  }

  /**
   * The constructor.
   *
   * @param entity the {@link #getEntity() entity} to operate on.
   * @param entityName the {@link #getName() entity name}.
   */
  public AlterTableClause(E entity, String entityName) {

    super(new AliasMap(), entity, entityName);
    this.statement = new AlterTableStatement<>(this);
  }

  @Override
  public DbTableReference<E> asTableReference() {

    return this;
  }

  /**
   * @param columns the {@link DbColumnReference column}s.
   * @return the {@link AlterTableOperationsClause} for fluent API calls.
   */
  public AlterTableOperationsClause<E> addColumns(DbColumnReference... columns) {

    AlterTableOperationsClause<E> operations = this.statement.getOperations();
    for (DbColumnReference column : columns) {
      operations.addColumn(column);
    }
    return operations;
  }

  @Override
  public AlterTableOperationsClause<E> addColumn(DbColumnReference column) {

    return this.statement.getOperations().addColumn(column);
  }

  @Override
  public AlterTableOperationsClause<E> addConstraint(DbConstraint constraint) {

    return this.statement.getOperations().addConstraint(constraint);
  }

  @Override
  public AlterTableOperationsClause<E> dropColumn(DbColumnReference column) {

    return this.statement.getOperations().dropColumn(column);
  }

  @Override
  public AlterTableOperationsClause<E> dropConstraint(DbConstraint constraint) {

    return this.statement.getOperations().dropConstraint(constraint);
  }

  @Override
  public AlterTableOperationsClause<E> dropConstraint(String constraint) {

    return this.statement.getOperations().dropConstraint(constraint);
  }

  @Override
  public AlterTableOperationsClause<E> renameColumn(DbColumnReference column, DbColumnReference newColumn) {

    return this.statement.getOperations().renameColumn(column, newColumn);
  }

  @Override
  public AlterTableOperationsClause<E> renameConstraint(String constraint, String newName) {

    return this.statement.getOperations().renameConstraint(constraint, newName);
  }

  @Override
  public AlterTableOperationsClause<E> renameConstraint(DbConstraint constraint, String newName) {

    return this.statement.getOperations().renameConstraint(constraint, newName);
  }

  @Override
  // make visible
  protected AliasMap getAliasMap() {

    return super.getAliasMap();
  }

  @Override
  public AlterTableStatement<E> get() {

    return this.statement;
  }

}
