/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.tx.impl;

import java.sql.SQLException;
import java.sql.Savepoint;

import io.github.mmm.db.ddl.DbMetaData;

/**
 * {@link JdbcTransaction} implementing a sub-transaction.
 *
 * @since 1.0.0
 */
public class JdbcSubTransaction extends JdbcTransaction {

  private final Savepoint savepoint;

  JdbcSubTransaction(JdbcTransaction parent) {

    super(parent.getConnection(), parent.getConfig(), parent);
    try {
      this.savepoint = getConnection().setSavepoint();
    } catch (SQLException e) {
      throw new IllegalStateException(e);
    }
  }

  @Override
  public void commit() {

    // do nothing, we return to the parent transaction
  }

  @Override
  public void rollback() {

    try {
      this.connection.rollback(this.savepoint);
    } catch (SQLException e) {
      throw new IllegalStateException(e);
    }
  }

  @Override
  public DbMetaData getMetaData() {

    return this.parent.getMetaData();
  }

}
