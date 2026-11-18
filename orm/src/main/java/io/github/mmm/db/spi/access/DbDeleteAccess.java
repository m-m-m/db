/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.spi.access;

import io.github.mmm.db.statement.delete.DeleteStatement;
import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.id.Id;

/// Interface providing support for [#delete(DeleteStatement)].
///
/// @since 1.0.0
public interface DbDeleteAccess {

  /// @param statement the [DeleteStatement] to execute.
  /// @return the number of records that have been deleted.
  long delete(DeleteStatement<?> statement);

  /// @param <E> type of the [EntityBean] to delete.
  /// @param id the [Id] of the [EntityBean] to delete.
  /// @param prototype the (empty) [EntityBean] instance to act as template.
  /// @return `true` if the [EntityBean] with the given [Id] has been deleted, `false` otherwise
  ///         (no such entity exists).
  <E extends EntityBean> boolean deleteById(Id<E> id, E prototype);

  /// @param <E> type of the [EntityBean]s to delete.
  /// @param ids the [Iterable] with the [Id]s to delete.
  /// @param prototype the (empty) [EntityBean] instance to act as template.
  /// @return the actual number of [EntityBean]s that have been deleted.
  <E extends EntityBean> int deleteAllById(Iterable<Id<E>> ids, E prototype);

}
