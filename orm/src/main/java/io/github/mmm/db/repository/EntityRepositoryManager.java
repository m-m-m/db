/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.repository;

import java.util.Objects;

import io.github.mmm.base.exception.ObjectNotFoundException;
import io.github.mmm.bean.ReadableBean;
import io.github.mmm.db.repository.impl.EntityRepositoryManagerImpl;
import io.github.mmm.entity.bean.EntityBean;

/**
 * Manager of all {@link EntityRepository repositories}.
 *
 * @see EntityRepository
 * @since 1.0.0
 */
public interface EntityRepositoryManager extends Iterable<EntityRepository<?>> {

  /**
   * @param <R> type of the {@link EntityRepository}.
   * @param repositoryClass the {@link Class} reflecting the requested {@link EntityRepository}.
   * @throws ObjectNotFoundException if the requested {@link EntityRepository} does not exist.
   * @return the requested {@link EntityRepository}.
   */
  default <R extends EntityRepository<?>> R get(Class<R> repositoryClass) throws ObjectNotFoundException {

    R repository = getOptional(repositoryClass);
    if (repository == null) {
      throw new ObjectNotFoundException(EntityRepository.class.getSimpleName(), repositoryClass);
    }
    return repository;
  }

  /**
   * @param <R> type of the {@link EntityRepository}.
   * @param repositoryClass the {@link Class} reflecting the requested {@link EntityRepository}.
   * @return the requested {@link EntityRepository} or {@code null} if not found.
   */
  <R extends EntityRepository<?>> R getOptional(Class<R> repositoryClass);

  /**
   * @param <E> type of the managed {@link EntityBean}.
   * @param entity the {@link EntityBean} to get the {@link EntityRepository} for.
   * @return the requested {@link EntityRepository}.
   * @throws ObjectNotFoundException if the requested {@link EntityRepository} does not exist.
   */
  default <E extends EntityBean> EntityRepository<E> getByEntity(E entity) throws ObjectNotFoundException {

    Objects.requireNonNull(entity);
    Class<E> entityClass = ReadableBean.getJavaClass(entity);
    return getByEntity(entityClass);
  }

  /**
   * @param <E> type of the managed {@link EntityBean}.
   * @param entityClass the {@link Class} reflecting the {@link EntityBean} to get the {@link EntityRepository} for.
   * @return the requested {@link EntityRepository} or {@code null} if not found.
   */
  default <E extends EntityBean> EntityRepository<E> getByEntity(Class<E> entityClass) {

    EntityRepository<E> repository = getByEntityOptional(entityClass);
    if (repository == null) {
      throw new ObjectNotFoundException(EntityRepository.class.getSimpleName(), entityClass);
    }
    return repository;
  }

  /**
   * @param <E> type of the managed {@link EntityBean}.
   * @param entityClass the {@link Class} reflecting the {@link EntityBean} to get the {@link EntityRepository} for.
   * @return the requested {@link EntityRepository} or {@code null} if not found.
   */
  <E extends EntityBean> EntityRepository<E> getByEntityOptional(Class<E> entityClass);

  /**
   * @return the {@link EntityRepositoryManager} instance.
   */
  static EntityRepositoryManager get() {

    return EntityRepositoryManagerImpl.INSTANCE;
  }

}
