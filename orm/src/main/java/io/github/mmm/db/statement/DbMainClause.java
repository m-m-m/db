/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.statement;

/// [DbTypedClause] that can be an ending [DbClause] of an SQL [DbStatement] and allows to
/// [get][#get()] the resulting [DbStatement].
///
/// @param <E> type of this [DbClause] (typically the [entity][AbstractEntityClause#getEntity()]).
/// @since 1.0.0
public interface DbMainClause<E> extends DbTypedClause<E>, DbClauseWithStatement {

  /// @return the actual [DbStatement] containing all [DbClause]s and representing your entire SQL.
  @Override
  DbStatement<E> get();

}
