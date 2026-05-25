/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.repository.operation;

import io.github.mmm.db.repository.EntityRepository;
import io.github.mmm.entity.bean.EntityBean;

/**
 * Interface for the save operations of an {@link EntityRepository}.
 *
 * @param <E> type of the managed {@link EntityBean}.
 * @since 1.0.0
 */
public interface EntitySaveOperations<E extends EntityBean> {

  /**
   * @param entity the {@link EntityBean} to save. If transient, it will be inserted, otherwise it will be updated.
   * @return the saved {@link EntityBean} that has been inserted or updated. This will be a different instance than the
   *         one provided as argument.
   */
  E save(E entity);

  /**
   * @param entities the array of {@link EntityBean entities} to {@link #save(EntityBean) save}.
   */
  @SuppressWarnings("unchecked")
  default void saveAll(E... entities) {

    // this is a naive default implementation. Real database backed implementations shall override with bulk operation.
    for (E entity : entities) {
      save(entity);
    }
  }

  /**
   * @param entities the {@link Iterable} of {@link EntityBean entities} to {@link #save(EntityBean) save}.
   */
  default void saveAll(Iterable<E> entities) {

    // this is a naive default implementation. Real database backed implementations shall override with bulk operation.
    for (E entity : entities) {
      save(entity);
    }
  }

}
