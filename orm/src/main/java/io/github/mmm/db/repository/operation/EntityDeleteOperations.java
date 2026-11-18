/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.repository.operation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.github.mmm.db.repository.EntityRepository;
import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.id.Id;
import io.github.mmm.entity.link.Link;

/// Interface for the delete operations of an [EntityRepository].
///
/// @param <E> type of the managed [EntityBean].
/// @since 1.0.0
public interface EntityDeleteOperations<E extends EntityBean> {

  /// @param entity the [EntityBean] to remove from this repository.
  /// @return `true` if the given [entity][EntityBean] has been successfully deleted from this repository,
  ///         `false` otherwise (was not [saved][EntityRepository#save(EntityBean)] before).
  default boolean delete(E entity) {

    return deleteById(Id.from(entity));
  }

  /// @param id the [Id] (primary key) of the [entity][EntityBean] to delete.
  /// @return `true` if the entity with the given [Id] has been successfully deleted from this repository,
  ///         `false` otherwise (no entity is persistent with this [Id]).
  boolean deleteById(Id<E> id);

  /// @param link the [Link] to the [entity][EntityBean] to delete.
  /// @return `true` if the entity for the given [Link] has been successfully deleted from this repository,
  ///         `false` otherwise (no entity is persistent with this [Id]).
  default boolean deleteByLink(Link<E> link) {

    if (link == null) {
      return false;
    }
    if (link.isResolved()) {
      return delete(link.getEntity());
    } else {
      return deleteById(link.getId());
    }
  }

  /// @param entities the [Iterable] of [entities][EntityBean] to [delete][#delete(EntityBean)].
  /// @return the number of entities that have been physically deleted.
  default int deleteAll(Iterable<E> entities) {

    int capacity = 12;
    if (entities instanceof Collection<E> c) {
      capacity = c.size();
    }
    List<Id<E>> ids = new ArrayList<>(capacity);
    for (E entity : entities) {
      if (entity != null) { // robustness, should not happen
        Id<E> id = Id.from(entity);
        if (id != null) { // robustness, should not happen
          ids.add(id);
        }
      }
    }
    return deleteAllById(ids);
  }

  /// @param ids the [Iterable] of [Id]s to [delete][#deleteById(Id)].
  /// @return the number of entities that have been physically deleted.
  default int deleteAllById(Iterable<Id<E>> ids) {

    // this is a naive default implementation. Real database backed implementations shall override with bulk operation.
    int deletionCount = 0;
    for (Id<E> id : ids) {
      boolean deleted = deleteById(id);
      if (deleted) {
        deletionCount++;
      }
    }
    return deletionCount;
  }

  /// @param links the [Iterable] of [Link]s to [delete by][#deleteByLink(Link)].
  /// @return the number of entities that have been physically deleted.
  default int deleteAllByLink(Iterable<Link<E>> links) {

    int capacity = 12;
    if (links instanceof Collection<Link<E>> c) {
      capacity = c.size();
    }
    List<Id<E>> ids = new ArrayList<>(capacity);
    for (Link<E> link : links) {
      if (link != null) { // robustness, should not happen
        Id<E> id = link.getId();
        if (id != null) { // robustness, should not happen
          ids.add(id);
        }
      }
    }
    return deleteAllById(ids);
  }

}
