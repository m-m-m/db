/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.repository.spi;

import io.github.mmm.db.repository.EntityRepository;
import io.github.mmm.db.repository.EntityRepositoryManager;
import io.github.mmm.entity.bean.EntityBean;

/**
 * Factory to create internal implementations of {@link EntityRepository}. The {@link EntityRepositoryManager} will
 * create instances of {@link EntityRepository} as {@link java.lang.reflect.Proxy dynamic proxy}. All non-static and
 * non-{@link java.lang.reflect.Method#isDefault() default} {@link java.lang.reflect.Method methods} will be delegated
 * to an internal implementation wrapped inside the proxy that is created via this factory.
 *
 * @param <R> type of the {@link EntityRepository} managed by this factory.
 *
 * @since 1.0.0
 */
public interface EntityRepositoryFactory<R extends EntityRepository<?>> {

  /**
   * @param repositoryType the {@link Class} reflecting the actual {@link EntityRepository} to instantiate.
   * @return {@code true} if this {@link EntityRepositoryFactory} is applicable of the given {@link EntityRepository}
   *         {@link Class}.
   */
  boolean isApplicable(Class<? extends EntityRepository<?>> repositoryType);

  /**
   * @param repositoryType the {@link Class} reflecting the actual repository to instantiate.
   * @param prototype the {@link EntityBean} instance used as prototype.
   * @param proxy the {@link java.lang.reflect.Proxy dynamic proxy} acting as public instance of the repository.
   * @return the new instance of the {@link EntityRepository} or {@code null} if not handled by this factory.
   */
  AbstractEntityRepository<?> create(EntityBean prototype, R proxy);

}
