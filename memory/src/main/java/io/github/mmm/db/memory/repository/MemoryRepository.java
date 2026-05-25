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

/**
 * {@link EntityRepository} managing all your {@link EntityBean entities} in memory and saves them on the disc. Only use
 * for small applications.
 *
 * @param <E> type of the managed {@link EntityBean}.
 * @since 1.0.0
 */
public interface MemoryRepository<E extends EntityBean>
    extends EntityRepository<E>, EntityFindAllOperation<E>, EntityDiscOperations, DbIndexGetByName {

  /**
   * This internal method is called once to create all indexes and {@link Collection#add(Object) add} them to the given
   * {@link DbIndexRegistry}. Override as default-method to add custom indexes but not as abstract method.
   *
   * @param registry the {@link DbIndexRegistry} where to {@link DbIndexRegistry#add(DbIndex) add} your custom
   *        {@link MemoryIndex}.
   */
  default void createIndexes(DbIndexRegistry registry) {

    // nothing by default - override to create and register your indexes
  }

}
