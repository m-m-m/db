/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.statement;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.github.mmm.entity.bean.EntityBean;

/// A [AbstractEntitiesClause] is a [DbClause] of an SQL [DbStatement] that specifies the
/// [entity][#getEntity()] and/or [entity name][#getName()] (table) to operate on.
///
/// @param <R> type of the result. Only different from `<E>` for complex selects.
/// @param <E> type of the [entity][#getEntity()].
/// @param <SELF> type of this class itself.
/// @since 1.0.0
public abstract class AbstractEntitiesClause<R, E extends EntityBean, SELF extends AbstractEntitiesClause<R, E, SELF>>
    extends AbstractEntityClause<R, E, SELF> {

  /// Name of property [#getName()] for marshalling.
  public static final String NAME_ADDITIONAL_ENTITIES = "and";

  private final List<EntitySubClause<?, ?>> additionalEntities;

  /// The constructor.
  ///
  /// @param aliasMap the [aliasMap][#getAliasMap()].
  /// @param entity the [entity][#getEntity()] to operate on.
  /// @param entityName the [entity name][#getName()].
  protected AbstractEntitiesClause(AliasMap aliasMap, E entity, String entityName) {

    super(aliasMap, entity, entityName);
    this.additionalEntities = new ArrayList<>();
  }

  /// @param additionalEntity the additional [entity][EntitySubClause#getEntity()] to access (similar to a JOIN).
  /// @return this [DbClause] itself for fluent API calls.
  public SELF and(EntityBean additionalEntity) {

    return and(new EntitySubClause<>(getAliasMap(), additionalEntity, null, null));
  }

  /// @param additionalEntity the additional [entity][EntitySubClause#getEntity()] to access (similar to a JOIN).
  /// @param alias the [alias][EntitySubClause#getAlias()].
  /// @return this [DbClause] itself for fluent API calls.
  public SELF and(EntityBean additionalEntity, String alias) {

    return and(new EntitySubClause<>(getAliasMap(), additionalEntity, null, alias));
  }

  /// @param entityName the [entity name][EntitySubClause#getName()].
  /// @param additionalEntity the additional [entity][EntitySubClause#getEntity()] to access (similar to a JOIN).
  /// @param alias the [alias][EntitySubClause#getAlias()].
  /// @return this [DbClause] itself for fluent API calls.
  public SELF and(String entityName, EntityBean additionalEntity, String alias) {

    return and(new EntitySubClause<>(getAliasMap(), additionalEntity, entityName, alias));
  }

  /// @param entityFragment the [EntitySubClause] to add.
  /// @return this [DbClause] itself for fluent API calls.
  protected SELF and(EntitySubClause<?, ?> entityFragment) {

    Objects.requireNonNull(entityFragment);
    this.additionalEntities.add(entityFragment);
    return self();
  }

  @Override
  public SELF as(String entityAlias) {

    if (this.additionalEntities.isEmpty()) {
      return super.as(entityAlias);
    } else {
      EntitySubClause<?, ?> last = this.additionalEntities.get(this.additionalEntities.size() - 1);
      last.as(entityAlias);
      return self();
    }
  }

  /// @return the [List] of [EntitySubClause]s in addition to the [main entity][#getEntity()].
  public List<EntitySubClause<?, ?>> getAdditionalEntities() {

    return this.additionalEntities;
  }

}
