/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.memory.repository.impl;

import io.github.mmm.db.memory.repository.MemoryRepository;
import io.github.mmm.db.repository.EntityRepository;
import io.github.mmm.db.repository.spi.EntityRepositoryFactory;
import io.github.mmm.entity.bean.EntityBean;

/**
 * Implementation of {@link EntityRepositoryFactory} for {@link MemoryRepository}.
 */
public class MemoryRepositoryFactory implements EntityRepositoryFactory<MemoryRepository<?>> {

  @Override
  public boolean isApplicable(Class<? extends EntityRepository<?>> repositoryType) {

    return MemoryRepository.class.isAssignableFrom(repositoryType);
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public MemoryRepositoryImpl<?> create(EntityBean prototype, MemoryRepository<?> proxy) {

    return new MemoryRepositoryImpl(prototype, proxy);
  }

}
