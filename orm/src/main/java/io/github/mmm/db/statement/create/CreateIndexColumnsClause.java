/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.statement.create;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.github.mmm.db.ddl.column.DbColumnReference;
import io.github.mmm.db.ddl.column.DbColumnReferenceWithSortOrder;
import io.github.mmm.db.statement.AbstractTypedClause;
import io.github.mmm.db.statement.DbClause;
import io.github.mmm.db.statement.DbMainClause;
import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.property.ReadableProperty;

/**
 * A {@link CreateIndexColumnsClause}-{@link DbClause} of an SQL {@link CreateIndexStatement}.
 *
 * @param <E> type of the {@link io.github.mmm.db.statement.AbstractEntityClause#getEntity() entity}.
 * @since 1.0.0
 */
public class CreateIndexColumnsClause<E extends EntityBean> extends AbstractTypedClause<E, CreateIndexColumnsClause<E>>
    implements DbMainClause<E>, CreateIndexFragment<E> {

  private final CreateIndexStatement<E> statement;

  private final List<DbColumnReferenceWithSortOrder> columns;

  /**
   * The constructor.
   *
   * @param statement the {@link CreateIndexStatement}.
   */
  public CreateIndexColumnsClause(CreateIndexStatement<E> statement) {

    super();
    this.statement = statement;
    this.columns = new ArrayList<>();
  }

  @Override
  public CreateIndexStatement<E> get() {

    return this.statement;
  }

  @Override
  public CreateIndexColumnsClause<E> column(DbColumnReferenceWithSortOrder column) {

    Objects.requireNonNull(column);
    this.columns.add(column);
    return this;
  }

  @Override
  public CreateIndexColumnsClause<E> columns(DbColumnReference... cols) {

    for (DbColumnReference column : cols) {
      column(DbColumnReferenceWithSortOrder.of(column));
    }
    return this;
  }

  @Override
  public CreateIndexColumnsClause<E> columns(DbColumnReferenceWithSortOrder... cols) {

    for (DbColumnReferenceWithSortOrder column : cols) {
      column(column);
    }
    return this;
  }

  @Override
  public CreateIndexColumnsClause<E> columns(ReadableProperty<?>... properties) {

    for (ReadableProperty<?> property : properties) {
      column(property);
    }
    return this;
  }

  /**
   * @return the {@link List} of {@link DbColumnReferenceWithSortOrder}s.
   */
  public List<DbColumnReferenceWithSortOrder> getColumns() {

    return this.columns;
  }

}
