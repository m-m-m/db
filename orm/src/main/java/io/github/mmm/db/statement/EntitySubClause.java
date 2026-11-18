/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.statement;

import io.github.mmm.entity.bean.EntityBean;

/// A fragment for an additional [entity][#getEntity()] selection. It is a sub-[clause][DbClause] and shall
/// never be part of [DbStatement#getClauses()].
///
/// @param <R> type of the result. Only different from `<E>` for complex selects.
/// @param <E> type of the [entity][#getEntity()].
/// @since 1.0.0
public class EntitySubClause<R, E extends EntityBean> extends AbstractEntityClause<R, E, EntitySubClause<R, E>> {

  /// The constructor.
  ///
  /// @param aliasMap the [AliasMap].
  /// @param entity the [entity][#getEntity()] to operate on.
  public EntitySubClause(AliasMap aliasMap, E entity) {

    this(aliasMap, entity, null, null);
  }

  /// The constructor.
  ///
  /// @param aliasMap the [AliasMap].
  /// @param entity the [entity][#getEntity()] to operate on.
  /// @param entityName the [entity name][#getName()].
  public EntitySubClause(AliasMap aliasMap, E entity, String entityName) {

    this(aliasMap, entity, entityName, null);
  }

  /// The constructor.
  ///
  /// @param aliasMap the [AliasMap].
  /// @param entity the [entity][#getEntity()] to operate on.
  /// @param entityName the [entity name][#getName()].
  /// @param alias the [alias][#getAlias()].
  public EntitySubClause(AliasMap aliasMap, E entity, String entityName, String alias) {

    super(aliasMap, entity, entityName);
    if ((alias != null) && !alias.isEmpty()) {
      as(alias);
    }
  }

}
