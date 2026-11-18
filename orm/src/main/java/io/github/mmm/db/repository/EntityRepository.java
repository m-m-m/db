/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.repository;

import io.github.mmm.db.repository.operation.EntityDeleteOperations;
import io.github.mmm.db.repository.operation.EntityFindOperations;
import io.github.mmm.db.repository.operation.EntitySaveOperations;
import io.github.mmm.entity.bean.EntityBean;

/// Interface for a repository that gives access to [save][#save(EntityBean)], [delete][#delete(EntityBean)] an
/// [EntityBean].
///
/// @param <E> type of the managed [EntityBean].
/// @since 1.0.0
public interface EntityRepository<E extends EntityBean>
    extends EntityFindOperations<E>, EntitySaveOperations<E>, EntityDeleteOperations<E> {

  /// @return an instance of the managed [entity][EntityBean] to be used as template. Please note that this is an
  ///         internal method of the implementation that should only be used by default methods within the repository
  ///         interface or by framework code.
  E getPrototype();

}
