/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.tx.spi;

import java.util.function.Function;

import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.id.Id;

/// Session data for a specific [type][EntityBean#getType()] of [EntityBean]. Represents the internal
/// first-level cache and shall not be exposed externally.
///
/// @param <E> type of the managed [EntityBean].
///
/// @since 1.0.0
public interface DbEntitySession<E extends EntityBean> extends Iterable<DbEntityHolder<E>> {

  /// @return the [Class] reflecting the managed [EntityBean].
  Class<E> getEntityClass();

  /// @param id the [Id] of the requested [EntityBean].
  /// @return the [DbEntityHolder] containing the [EntityBean] with the given [Id] from the first-level
  ///         cache or `null` if not present.
  default DbEntityHolder<E> get(Id<E> id) {

    return getOrLoad(id, null);
  }

  /// @param id the [Id] of the requested [EntityBean].
  /// @param loader the [Function] to load the [EntityBean] by [Id] from the database. May return
  ///        `null` for [Id]s that do not exist in the database.
  /// @return the [DbEntityHolder] containing the [EntityBean] with the given [Id]. Will be taken from
  ///         first-level cache if present and otherwise loaded from DB and put into first-level cache. Result may still
  ///         be `null` if no entity exists for the given [Id].
  DbEntityHolder<E> getOrLoad(Id<E> id, Function<Id<E>, E> loader);

  /// @param entity the [EntityBean] to put into the first-level cache.
  /// @return the [DbEntityHolder] created for the given [EntityBean].
  default DbEntityHolder<E> put(E entity) {

    return put(entity, Id.from(entity));
  }

  /// @param managedEntity the managed [EntityBean] to put into the first-level cache.
  /// @param id the explicit new [Id] for the [EntityBean] (e.g. to ensure it is assigned only at the very
  ///        end on success)
  /// @return the [DbEntityHolder] created for the given [EntityBean].
  DbEntityHolder<E> put(E managedEntity, Id<E> id);

  /// @param id the [Id] of the [EntityBean] to remove.
  /// @return the [DbEntityHolder] that has just been removed or `null` if no entity available for the given
  ///         [Id].
  DbEntityHolder<E> remove(Id<E> id);

}
