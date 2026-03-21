/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.repository;

import io.github.mmm.db.repository.operation.EntityDeleteOperations;
import io.github.mmm.db.repository.operation.EntityFindOperations;
import io.github.mmm.db.repository.operation.EntitySaveOperations;
import io.github.mmm.entity.bean.EntityBean;

/**
 * Interface for a repository that gives access to {@link #save(EntityBean) save}, {@link #delete(EntityBean) delete} an
 * {@link EntityBean}.
 *
 * @param <E> type of the managed {@link EntityBean}.
 * @since 1.0.0
 */
public interface EntityRepository<E extends EntityBean>
    extends EntityFindOperations<E>, EntitySaveOperations<E>, EntityDeleteOperations<E> {

}
