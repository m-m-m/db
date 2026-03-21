/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.statement.create;

import io.github.mmm.base.sort.SortOrder;
import io.github.mmm.db.ddl.column.DbColumnReference;
import io.github.mmm.db.ddl.column.DbColumnReferenceWithSortOrder;
import io.github.mmm.db.statement.alter.AlterTableClause;
import io.github.mmm.db.statement.alter.AlterTableOperationsClause;
import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.property.ReadableProperty;

/**
 * Interface for a fragment or clause to add {@link DbColumnReference columns}.
 *
 * @param <E> type of the {@link AlterTableClause#getEntity() entity}.
 */
public interface CreateIndexFragment<E extends EntityBean> {

  /**
   * @param property the {@link ReadableProperty property} to to create an index on.
   * @return this {@link AlterTableOperationsClause} for fluent API calls.
   */
  default CreateIndexColumnsClause<E> column(ReadableProperty<?> property) {

    return column(property, null);
  }

  /**
   * @param property the {@link ReadableProperty property} to to create an index on.
   * @param order the preferred {@link SortOrder} for this column in the index.
   * @return this {@link AlterTableOperationsClause} for fluent API calls.
   */
  default CreateIndexColumnsClause<E> column(ReadableProperty<?> property, SortOrder order) {

    return column(DbColumnReference.of(property).with(order));
  }

  /**
   * @param column the {@link DbColumnReference} to the column where to create an index on.
   * @return this {@link AlterTableOperationsClause} for fluent API calls.
   */
  default CreateIndexColumnsClause<E> column(DbColumnReference column) {

    return column(DbColumnReferenceWithSortOrder.of(column));
  }

  /**
   * @param column the {@link DbColumnReferenceWithSortOrder} to the column where to create an index on.
   * @return this {@link AlterTableOperationsClause} for fluent API calls.
   */
  CreateIndexColumnsClause<E> column(DbColumnReferenceWithSortOrder column);

  /**
   * @param properties the {@link ReadableProperty}s of the columns to create an index on.
   * @return the {@link CreateIndexColumnsClause} for fluent API calls.
   */
  CreateIndexColumnsClause<E> columns(ReadableProperty<?>... properties);

  /**
   * @param columns the {@link DbColumnReference}s of the columns to create an index on.
   * @return the {@link CreateIndexColumnsClause} for fluent API calls.
   */
  CreateIndexColumnsClause<E> columns(DbColumnReference... columns);

  /**
   * @param columns the {@link DbColumnReferenceWithSortOrder}s of the columns to create an index on.
   * @return this {@link AlterTableOperationsClause} for fluent API calls.
   */
  CreateIndexColumnsClause<E> columns(DbColumnReferenceWithSortOrder... columns);

}
