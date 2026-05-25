/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.spi.access;

import io.github.mmm.db.statement.insert.InsertStatement;
import io.github.mmm.entity.bean.EntityBean;

/**
 * Interface providing support for {@link #insert(InsertStatement)}.
 *
 * @since 1.0.0
 */
public interface DbInsertAccess {

  /**
   * @param statement the {@link InsertStatement} to execute.
   * @return the number of rows that have been inserted. Typically {@code 1}.
   */
  long insert(InsertStatement<?> statement);

  /**
   * @param <E> type of the {@link EntityBean}.
   * @param entity the {@link EntityBean} to insert.
   * @return the {@link EntityBean} that has been inserted.
   */
  <E extends EntityBean> E insert(E entity);

  /**
   * @param entities the {@link EntityBean}s to insert as batch operation.
   */
  default void insertAll(EntityBean... entities) {

    for (EntityBean entity : entities) {
      insert(entity);
    }
  }

}
