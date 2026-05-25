/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.spi.access;

import io.github.mmm.db.statement.update.UpdateStatement;
import io.github.mmm.entity.bean.EntityBean;

/**
 * Interface providing support for {@link #update(UpdateStatement)}.
 */
public interface DbUpdateAccess {

  /**
   * @param statement the {@link UpdateStatement} to execute.
   * @return the number of records that have been deleted.
   */
  long update(UpdateStatement<?> statement);

  /**
   * @param <E> type of the {@link EntityBean}.
   * @param entity the {@link EntityBean} to update.
   * @return the {@link EntityBean} that has been updated.
   */
  <E extends EntityBean> E update(E entity);

  /**
   * @param entities the {@link EntityBean}s to update as batch operation.
   */
  default void updateAll(EntityBean... entities) {

    for (EntityBean entity : entities) {
      update(entity);
    }
  }

}
