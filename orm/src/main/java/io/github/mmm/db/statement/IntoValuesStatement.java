/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.statement;

import io.github.mmm.entity.bean.EntityBean;

/// [AbstractDbStatement] for "... INTO ... VALUES" statements like
/// [io.github.mmm.db.statement.insert.InsertStatement], [io.github.mmm.db.statement.merge.MergeStatement],
/// or [io.github.mmm.db.statement.upsert.UpsertStatement].
///
/// @param <E> type of the [entity][AbstractEntityClause#getEntity()].
/// @since 1.0.0
public abstract class IntoValuesStatement<E extends EntityBean> extends AbstractDbStatement<E> {

  /// @return the [IntoClause].
  public abstract IntoClause<E, ?, ?> getInto();

  /// @return the [ValuesClause].
  public abstract ValuesClause<E, ?> getValues();
}
