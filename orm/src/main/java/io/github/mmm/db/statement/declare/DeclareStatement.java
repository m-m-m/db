/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.statement.declare;

import io.github.mmm.db.statement.AbstractDbStatement;
import io.github.mmm.db.statement.DbStatement;
import io.github.mmm.db.statement.DbStatementType;
import io.github.mmm.db.statement.impl.DbStatementTypeImpl;

/**
 * {@link DbStatement} to declare an object (table).
 *
 */
public abstract class DeclareStatement extends AbstractDbStatement<Void> {

  @Override
  public DbStatementType getType() {

    return DbStatementTypeImpl.DECLARE;
  }

}
