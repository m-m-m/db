/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.repository.spi;

import io.github.mmm.db.repository.EntityRepository;
import io.github.mmm.db.repository.EntityRepositoryManager;
import io.github.mmm.entity.bean.EntityBean;

/// Factory to create internal implementations of [EntityRepository]. The [EntityRepositoryManager] will
/// create instances of [EntityRepository] as [dynamic proxy][java.lang.reflect.Proxy]. All non-static and
/// non-[default][java.lang.reflect.Method#isDefault()] [methods][java.lang.reflect.Method] will be delegated
/// to an internal implementation wrapped inside the proxy that is created via this factory.
///
/// @param <R> type of the [EntityRepository] managed by this factory.
///
/// @since 1.0.0
public interface EntityRepositoryFactory<R extends EntityRepository<?>> {

  /// @param repositoryType the [Class] reflecting the actual [EntityRepository] to instantiate.
  /// @return `true` if this [EntityRepositoryFactory] is applicable of the given [EntityRepository]
  ///         [Class].
  boolean isApplicable(Class<? extends EntityRepository<?>> repositoryType);

  /// @param repositoryType the [Class] reflecting the actual repository to instantiate.
  /// @param prototype the [EntityBean] instance used as prototype.
  /// @param proxy the [dynamic proxy][java.lang.reflect.Proxy] acting as public instance of the repository.
  /// @return the new instance of the [EntityRepository] or `null` if not handled by this factory.
  AbstractEntityRepository<?> create(EntityBean prototype, R proxy);

}
