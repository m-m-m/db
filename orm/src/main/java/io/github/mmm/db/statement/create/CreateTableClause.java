/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.statement.create;

import io.github.mmm.db.ddl.column.DbColumnReference;
import io.github.mmm.db.ddl.constraint.DbConstraint;
import io.github.mmm.db.ddl.table.DbTableReference;
import io.github.mmm.db.name.DbKeyword;
import io.github.mmm.db.statement.AbstractEntityClause;
import io.github.mmm.db.statement.AliasMap;
import io.github.mmm.db.statement.DbIncompleteStartClause;
import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.property.ReadableProperty;

/**
 * A {@link CreateTableClause} of a {@link CreateTableStatement}.
 *
 * @param <E> type of the {@link #getEntity() entity}.
 * @since 1.0.0
 */
public class CreateTableClause<E extends EntityBean> extends AbstractEntityClause<E, E, CreateTableClause<E>>
    implements DbIncompleteStartClause, DbCreateTableClause<E> {

  /** Name of {@link CreateTableClause} for marshaling. */
  public static final String NAME_CREATE_TABLE = DbKeyword.CREATE_TABLE;

  private final CreateTableStatement<E> statement;

  /**
   * The constructor.
   *
   * @param entity the {@link #getEntity() entity} to operate on.
   */
  public CreateTableClause(E entity) {

    this(entity, null);
  }

  /**
   * The constructor.
   *
   * @param entity the {@link #getEntity() entity} to operate on.
   * @param entityName the {@link #getName() entity name}.
   */
  public CreateTableClause(E entity, String entityName) {

    super(new AliasMap(), entity, entityName);
    this.statement = new CreateTableStatement<>(this);
  }

  @Override
  public DbTableReference<E> asTableReference() {

    return this;
  }

  @Override
  public CreateTableContentsClause<E> column(DbColumnReference column) {

    return this.statement.getContents().column(column);
  }

  @Override
  public CreateTableContentsClause<E> constraint(DbConstraint constraint) {

    return this.statement.getContents().constraint(constraint);
  }

  /**
   * @param columns the {@link DbColumnReference column}s.
   * @return the {@link CreateTableContentsClause} for fluent API calls.
   */
  public CreateTableContentsClause<E> columns(DbColumnReference... columns) {

    CreateTableContentsClause<E> contents = this.statement.getContents();
    for (DbColumnReference column : columns) {
      contents.column(column);
    }
    return contents;
  }

  /**
   * Creates {@link #column(ReadableProperty) columns} for all {@link EntityBean#getProperties() properties} of the
   * {@link #getEntity() entity}.
   *
   * @return the {@link CreateTableContentsClause} for fluent API calls.
   */
  public CreateTableContentsClause<E> columns() {

    CreateTableContentsClause<E> columns = this.statement.getContents();
    for (ReadableProperty<?> property : getEntity().getProperties()) {
      columns.column(property, true);
    }
    return columns;
  }

  @Override
  // make visible
  protected AliasMap getAliasMap() {

    return super.getAliasMap();
  }

  @Override
  public CreateTableStatement<E> get() {

    return this.statement;
  }

}
