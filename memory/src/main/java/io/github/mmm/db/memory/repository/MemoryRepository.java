/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.memory.repository;

import java.util.Collection;

import io.github.mmm.db.ddl.index.DbIndex;
import io.github.mmm.db.ddl.index.DbIndexGetByName;
import io.github.mmm.db.ddl.index.DbIndexRegistry;
import io.github.mmm.db.memory.index.MemoryIndex;
import io.github.mmm.db.repository.EntityRepository;
import io.github.mmm.db.repository.operation.EntityDiscOperations;
import io.github.mmm.db.repository.operation.EntityFindAllOperation;
import io.github.mmm.entity.bean.EntityBean;

/// [EntityRepository] managing all your [entities][EntityBean] in memory and saves them on the disc. Only use
/// for small applications.
///
/// @param <E> type of the managed [EntityBean].
/// @since 1.0.0
public interface MemoryRepository<E extends EntityBean>
    extends EntityRepository<E>, EntityFindAllOperation<E>, EntityDiscOperations, DbIndexGetByName {

  /// This internal method is called once to create all indexes and [add][Collection#add(Object)] them to the given
  /// [DbIndexRegistry]. Override as default-method to add custom indexes but not as abstract method.
  ///
  /// @param registry the [DbIndexRegistry] where to [add][DbIndexRegistry#add(DbIndex)] your custom
  ///        [MemoryIndex].
  default void createIndexes(DbIndexRegistry registry) {

    // nothing by default - override to create and register your indexes
  }

}
