/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.statement.drop;

import io.github.mmm.db.statement.AbstractEntityClause;
import io.github.mmm.db.statement.AliasMap;
import io.github.mmm.db.statement.DbClause;
import io.github.mmm.db.statement.DbStatement;
import io.github.mmm.db.statement.DbMainClause;
import io.github.mmm.db.statement.DbStartClause;
import io.github.mmm.entity.bean.EntityBean;

/**
 * A {@link DropTableClause}-{@link DbClause} of an SQL {@link DropTableStatement}.
 *
 * @param <E> type of the {@link #getEntity() entity}.
 * @since 1.0.0
 */
public class DropTableClause<E extends EntityBean> extends AbstractEntityClause<E, E, DropTableClause<E>>
    implements DbStartClause, DbMainClause<E> {

  /** Name of {@link DropTableClause} for marshaling. */
  public static final String NAME_DROP_TABLE = "DROP TABLE";

  private final DropTableStatement<E> statement;

  /**
   * The constructor.
   *
   * @param entity the {@link #getEntity() entity} to operate on.
   */
  public DropTableClause(E entity) {

    this(entity, null);
  }

  /**
   * The constructor.
   *
   * @param entity the {@link #getEntity() entity} to operate on.
   * @param entityName the {@link #getName() entity name}.
   */
  public DropTableClause(E entity, String entityName) {

    super(new AliasMap(), entity, entityName);
    this.statement = new DropTableStatement<>(this);
  }

  @Override
  public DbStatement<E> get() {

    return this.statement;
  }

  @Override
  // make visible
  protected AliasMap getAliasMap() {

    return super.getAliasMap();
  }

}
