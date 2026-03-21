/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.statement.merge;

import io.github.mmm.db.statement.AbstractDbClause;
import io.github.mmm.db.statement.DbClause;
import io.github.mmm.db.statement.DbIncompleteStartClause;
import io.github.mmm.entity.bean.EntityBean;

/**
 * {@link MergeClause}-{@link DbClause} to merge data in the database.
 *
 * @since 1.0.0
 */
public final class MergeClause extends AbstractDbClause implements DbIncompleteStartClause {

  /** Name of {@link MergeClause} for marshaling. */
  public static final String NAME_MERGE = "MERGE";

  private MergeStatement<?> statement;

  /**
   * The constructor.
   */
  public MergeClause() {

    super();
  }

  /**
   * @param <E> type of the {@link EntityBean}.
   * @param entity the {@link EntityBean entity} to merge into.
   * @return the {@link MergeIntoClause} for fluent API calls.
   */
  public <E extends EntityBean> MergeIntoClause<E> into(E entity) {

    MergeIntoClause<E> mergeInto = new MergeIntoClause<>(this, entity);
    this.statement = mergeInto.getStatement();
    return mergeInto;
  }

  @Override
  public MergeStatement<?> get() {

    return this.statement;
  }

}
