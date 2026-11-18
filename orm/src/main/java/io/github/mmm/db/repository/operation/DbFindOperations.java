/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.repository.operation;

import io.github.mmm.db.statement.NonUniqueResultException;
import io.github.mmm.db.statement.select.SelectStatement;
import io.github.mmm.entity.bean.EntityBean;

/// Extends [EntityFindOperations] with operations for [SelectStatement]s.
///
/// @param <E> type of the managed [EntityBean].
/// @since 1.0.0
public interface DbFindOperations<E extends EntityBean> extends EntityFindOperations<E> {

  /// @param statement the [SelectStatement] to query the requested entities.
  /// @return an [Iterable] with the matching [entities][EntityBean].
  Iterable<E> findByQuery(SelectStatement<E> statement);

  /// @param statement the [SelectStatement] to query the requested entity. Should produce a single result or no
  ///        result.
  /// @return the matching [entity][EntityBean] or `null` if not found.
  /// @throws NonUniqueResultException if the given `statement` produced more than one result.
  E findOneByQuery(SelectStatement<E> statement);

}
