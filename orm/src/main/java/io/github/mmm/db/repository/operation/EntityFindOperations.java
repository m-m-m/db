/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.repository.operation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.github.mmm.db.repository.EntityRepository;
import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.id.Id;
import io.github.mmm.entity.id.PkId;
import io.github.mmm.entity.link.Link;

/// Interface for the find operations of an [EntityRepository].
///
/// @param <E> type of the managed [EntityBean].
/// @since 1.0.0
public interface EntityFindOperations<E extends EntityBean> {

  /// @return the [Class] reflecting the managed [EntityBean].
  Class<E> getEntityClass();

  /// @param id the [Id] of the requested [entity][EntityBean].
  /// @return the requested [entity][EntityBean] or `null` if no such entity exists.
  E findById(Id<E> id);

  /// @param pk the [primary key][Id#getPk()] of the requested [entity][EntityBean].
  /// @return the requested [entity][EntityBean] or `null` if no such entity exists.
  default E findByPk(Object pk) {

    if (pk == null) {
      return null;
    }
    PkId<E, ?, ?> id = PkId.of(getEntityClass(), pk);
    return findById(id);
  }

  /// @param link the [Link] to the requested [entity][EntityBean].
  /// @return the requested [entity][EntityBean] or `null` if no such entity exists.
  default E findByLink(Link<E> link) {

    if (link == null) {
      return null;
    }
    // even if link is resolved, we load the entity by id
    // check link.isResolved() yourself before calling this method.
    return findById(link.getId());
  }

  /// @param ids the [Iterable] of [Id]s to [retrieve][#findById(Id)].
  /// @return an [List] with the [retrieved][#findById(Id)] [entities][EntityBean].
  default List<E> findAllById(Iterable<Id<E>> ids) {

    // this is a naive default implementation. Real database backed implementations shall override with bulk operation.
    int capacity = 16;
    if (ids instanceof Collection) {
      capacity = ((Collection<?>) ids).size();
    }
    List<E> entities = new ArrayList<>(capacity);
    for (Id<E> id : ids) {
      E entity = findById(id);
      if (entity != null) {
        entities.add(entity);
      }
    }
    return entities;
  }

}
