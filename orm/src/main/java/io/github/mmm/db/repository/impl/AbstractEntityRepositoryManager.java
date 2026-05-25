/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.repository.impl;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.mmm.base.collection.ReadOnlyIterator;
import io.github.mmm.base.exception.ObjectNotFoundException;
import io.github.mmm.base.resource.ResourceScanner;
import io.github.mmm.base.resource.ResourceScannerServiceResult;
import io.github.mmm.db.repository.EntityRepository;
import io.github.mmm.db.repository.EntityRepositoryManager;
import io.github.mmm.entity.bean.EntityBean;

/**
 * Abstract base implementation of {@link EntityRepositoryManager}.
 *
 * @since 1.0.0
 */
public abstract class AbstractEntityRepositoryManager implements EntityRepositoryManager {

  private static final Logger LOG = LoggerFactory.getLogger(AbstractEntityRepositoryManager.class);

  private final Map<Class<?>, EntityRepository<?>> repositoryMap;

  private final Map<Class<?>, EntityRepository<?>> entityMap;

  /**
   * The constructor.
   */
  public AbstractEntityRepositoryManager() {

    super();
    this.repositoryMap = new ConcurrentHashMap<>();
    this.entityMap = new ConcurrentHashMap<>();
  }

  /**
   * Scans the classpath for {@link EntityRepository} interfaces and creates an instance of each.
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  protected void init() {

    ResourceScannerServiceResult result = ResourceScanner.get().getResult().getRequired(EntityRepositoryScanner.class);
    Iterator<Class<?>> iterator = result.getClasses().iterator();
    while (iterator.hasNext()) {
      Class repositoryClass = iterator.next();
      LOG.debug("Found entity repository {}", repositoryClass);
      getOptional(repositoryClass);
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public <R extends EntityRepository<?>> R getOptional(Class<R> repositoryClass) {

    return (R) this.repositoryMap.computeIfAbsent(repositoryClass, this::create);
  }

  /**
   * @param repositoryClass the {@link EntityRepository} {@link Class} (interface).
   * @return the instance of the given {@link EntityRepository}.
   */
  protected abstract EntityRepository<?> createRepository(Class<? extends EntityRepository<?>> repositoryClass);

  @SuppressWarnings({ "rawtypes", "unchecked" })
  private EntityRepository<?> create(Class<?> repositoryClass) {

    EntityRepository<?> repository = createRepository((Class) repositoryClass);
    if (repository == null) {
      LOG.warn("Could not instanciate entity repository {}", repositoryClass);
    } else {
      LOG.info("Created instance of entity repository {}", repositoryClass);
    }
    if (repository != null) {
      Class<?> entityClass = getEntityClass(repository);
      this.entityMap.put(entityClass, repository);
    }
    return repository;
  }

  private static Class<?> getEntityClass(EntityRepository<?> repository) {

    Class<?> entityClass = repository.getEntityClass();
    if (entityClass == null) {
      throw new ObjectNotFoundException("EntityClass", repository);
    }
    return entityClass;
  }

  @SuppressWarnings("unchecked")
  @Override
  public <E extends EntityBean> EntityRepository<E> getByEntityOptional(Class<E> entityClass) {

    return (EntityRepository<E>) this.entityMap.get(entityClass);
  }

  @Override
  public Iterator<EntityRepository<?>> iterator() {

    return new ReadOnlyIterator<>(this.repositoryMap.values().iterator());
  }

}
