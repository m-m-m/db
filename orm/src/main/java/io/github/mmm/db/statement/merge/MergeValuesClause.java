/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.statement.merge;

import io.github.mmm.db.statement.ValuesClause;
import io.github.mmm.entity.bean.EntityBean;

/// [ValuesClause] of an [MergeStatement].
///
/// @param <E> type of the [entity][MergeIntoClause#getEntity()].
/// @since 1.0.0
public class MergeValuesClause<E extends EntityBean> extends ValuesClause<E, MergeValuesClause<E>> {

  private final MergeStatement<E> statement;

  /// The constructor.
  ///
  /// @param statement the owning [MergeStatement].
  public MergeValuesClause(MergeStatement<E> statement) {

    super();
    this.statement = statement;
  }

  @Override
  protected E getEntity() {

    return this.statement.getInto().getEntity();
  }

  @Override
  public MergeStatement<E> get() {

    return this.statement;
  }

}
