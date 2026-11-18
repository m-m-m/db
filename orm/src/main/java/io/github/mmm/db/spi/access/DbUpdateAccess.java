/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.spi.access;

import io.github.mmm.db.statement.update.UpdateStatement;
import io.github.mmm.entity.bean.EntityBean;

/// Interface providing support for [#update(UpdateStatement)].
public interface DbUpdateAccess {

  /// @param statement the [UpdateStatement] to execute.
  /// @return the number of records that have been deleted.
  long update(UpdateStatement<?> statement);

  /// @param <E> type of the [EntityBean].
  /// @param entity the [EntityBean] to update.
  /// @return the [EntityBean] that has been updated.
  <E extends EntityBean> E update(E entity);

  /// @param entities the [EntityBean]s to update as batch operation.
  default void updateAll(EntityBean... entities) {

    for (EntityBean entity : entities) {
      update(entity);
    }
  }

}
