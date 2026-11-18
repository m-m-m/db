/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.statement.alter;

import io.github.mmm.db.statement.AbstractDbStatement;
import io.github.mmm.db.statement.AbstractEntityClause;
import io.github.mmm.db.statement.DbStatement;
import io.github.mmm.db.statement.DbStatementType;
import io.github.mmm.db.statement.impl.DbStatementTypeImpl;

/// [DbStatement] to alter an object (table).
///
/// @param <E> type of the [entity][AbstractEntityClause#getEntity()].
/// @since 1.0.0
public abstract class AlterStatement<E> extends AbstractDbStatement<E> {

  @Override
  public DbStatementType getType() {

    return DbStatementTypeImpl.ALTER;
  }

}
