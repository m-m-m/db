/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.repository.operation;

import io.github.mmm.db.statement.create.CreateIndexStatement;
import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.property.WritableProperty;

/**
 * Interface for the DDL (data-definition language) operations of an {@link EntityBean}.
 *
 * @param <E> type of the managed {@link EntityBean}.
 * @since 1.0.0
 */
public interface DbDdlOperations<E extends EntityBean> {

  /**
   * Create the table for the managed entity.
   */
  void createTable();

  /**
   * Create the table for the managed entity.
   *
   * @param autoIndex {@code true} to automatically
   */
  default void createTable(boolean autoIndex) {

    createTable();
    if (autoIndex) {
      createIndexes();
    }
  }

  /**
   * Create automatic indexes for the table. By default, for each foreign key an index will be created. You can override
   * this entire method or {@link #createIndex(WritableProperty)} in order to replace, extend or adapt the default
   * behaviour.
   */
  void createIndexes();

  /**
   * @param property the {@link WritableProperty} to potentially generate an automatic index for.
   * @return the {@link CreateIndexStatement} or {@code null} for no index.
   */
  CreateIndexStatement<E> createIndex(WritableProperty<?> property);

  /**
   * Create the ID sequence for the managed entity.
   */
  void createSequence();
}
