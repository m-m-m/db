/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.statement.drop;

import io.github.mmm.db.statement.AbstractDbStatement;
import io.github.mmm.db.statement.AbstractEntityClause;
import io.github.mmm.db.statement.DbStatementType;
import io.github.mmm.db.statement.impl.DbStatementTypeImpl;

/// [AbstractDbStatement] to drop some object (table, index, sequence, etc.).
///
/// @param <E> type of the [entity][AbstractEntityClause#getEntity()].
/// @since 1.0.0
public abstract class DropStatement<E> extends AbstractDbStatement<E> {

  @Override
  public DbStatementType getType() {

    return DbStatementTypeImpl.DROP;
  }

}
