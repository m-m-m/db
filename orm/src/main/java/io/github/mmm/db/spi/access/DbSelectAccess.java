/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.spi.access;

import io.github.mmm.db.statement.NonUniqueResultException;
import io.github.mmm.db.statement.select.SelectStatement;
import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.id.Id;

/// Interface providing support for [#select(SelectStatement)] and [#selectOne(SelectStatement)].
public interface DbSelectAccess {

  /// @param <R> type of the result objects to select.
  /// @param statement the [SelectStatement] to query the requested objects.
  /// @return an [Iterable] with the matching objects.
  <R> Iterable<R> select(SelectStatement<R> statement);

  /// @param <R> type of the result object to select.
  /// @param statement the [SelectStatement] to query the requested object. Should produce a single result or no
  ///        result.
  /// @return the matching object or `null` if not found.
  /// @throws NonUniqueResultException if the given [SelectStatement] produced more than one result.
  <R> R selectOne(SelectStatement<R> statement);

  /// @param <E> type of the [EntityBean] to select.
  /// @param id the [ID][EntityBean#Id()] of the [EntityBean] to select.
  /// @param prototype the (empty) [EntityBean] instance to act as template.
  /// @return the [EntityBean] with the given [Id] or `null` if no such [EntityBean] exists.
  <E extends EntityBean> E selectById(Id<E> id, E prototype);

}
