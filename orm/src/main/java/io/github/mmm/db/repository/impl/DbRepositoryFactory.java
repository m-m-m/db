/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.repository.impl;

import io.github.mmm.db.repository.DbRepository;
import io.github.mmm.db.repository.EntityRepository;
import io.github.mmm.db.repository.spi.EntityRepositoryFactory;
import io.github.mmm.entity.bean.EntityBean;

/**
 * Implementation of {@link EntityRepositoryFactory} for {@link DbRepository}.
 *
 * @since 1.0.0
 */
public class DbRepositoryFactory implements EntityRepositoryFactory<DbRepository<?>> {

  @Override
  public boolean isApplicable(Class<? extends EntityRepository<?>> repositoryType) {

    return DbRepository.class.isAssignableFrom(repositoryType);
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public DbRepositoryImpl<?> create(EntityBean prototype, DbRepository<?> proxy) {

    return new DbRepositoryImpl(prototype, proxy);
  }

}
