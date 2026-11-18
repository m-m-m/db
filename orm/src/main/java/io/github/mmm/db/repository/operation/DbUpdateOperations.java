/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.repository.operation;

import io.github.mmm.db.statement.select.SelectStatement;
import io.github.mmm.db.statement.update.UpdateStatement;
import io.github.mmm.entity.bean.EntityBean;

/// Extends [EntityFindOperations] with operations for [SelectStatement]s.
///
/// @param <E> type of the managed [EntityBean].
/// @since 1.0.0
public interface DbUpdateOperations<E extends EntityBean> extends EntityFindOperations<E> {

  /// @param statement the [UpdateStatement] to execute.
  /// @return the number of [entities][EntityBean] that have been updated.
  long update(UpdateStatement<E> statement);

}
