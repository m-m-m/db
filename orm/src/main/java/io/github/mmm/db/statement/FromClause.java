/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.statement;

import io.github.mmm.entity.bean.EntityBean;

/// A `FROM`-[clause][DbClause] of a [DbStatement] such as
/// [io.github.mmm.db.statement.select.SelectClause] or [io.github.mmm.db.statement.delete.DeleteClause].
///
/// @param <R> type of the result. Only different from `<E>` for complex selects.
/// @param <E> type of the [entity][#getEntity()].
/// @param <SELF> type of this class itself.
/// @since 1.0.0
public abstract class FromClause<R, E extends EntityBean, SELF extends FromClause<R, E, SELF>>
    extends AbstractEntitiesClause<R, E, SELF> implements DbMainClause<R>, TypedClauseWithWhere<R> {

  /// The constructor.
  ///
  /// @param aliasMap the [AliasMap].
  /// @param entity the [entity][#getEntity()] to operate on.
  /// @param entityName the [entity name][#getName()].
  protected FromClause(AliasMap aliasMap, E entity, String entityName) {

    super(aliasMap, entity, entityName);
  }

}
