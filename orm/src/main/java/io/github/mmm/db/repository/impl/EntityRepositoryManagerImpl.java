/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import io.github.mmm.base.service.ServiceHelper;
import io.github.mmm.db.repository.EntityRepository;
import io.github.mmm.db.repository.EntityRepositoryManager;
import io.github.mmm.db.repository.spi.EntityRepositoryFactory;

/**
 * Implementation of {@link EntityRepositoryManager}.
 *
 * @since 1.0.0
 */
public class EntityRepositoryManagerImpl extends AbstractEntityRepositoryManager {

  /** The singleton instance. */
  public static final EntityRepositoryManagerImpl INSTANCE = new EntityRepositoryManagerImpl();

  private final List<EntityRepositoryFactory<?>> factories;

  /**
   * The constructor.
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public EntityRepositoryManagerImpl() {

    super();
    this.factories = new ArrayList<>();
    ServiceHelper.all(ServiceLoader.load(EntityRepositoryFactory.class), (List) this.factories);
    init();
  }

  @Override
  protected EntityRepository<?> createRepository(Class<? extends EntityRepository<?>> repositoryClass) {

    for (EntityRepositoryFactory<?> factory : this.factories) {
      if (factory.isApplicable(repositoryClass)) {
        EntityRepositoryProxy<?> wrapper = EntityRepositoryProxy.of(repositoryClass, factory);
        if (wrapper != null) {
          return wrapper.getProxy();
        }
      }
    }
    return null;
  }

}
