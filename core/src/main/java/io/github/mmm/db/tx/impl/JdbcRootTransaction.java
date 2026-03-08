/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.tx.impl;

import java.sql.Connection;
import java.sql.SQLException;

import io.github.mmm.db.ddl.DbMetaData;
import io.github.mmm.db.ddl.impl.jdbc.JdbcMetaData;
import io.github.mmm.db.pool.DbConnectionPool;
import io.github.mmm.db.tx.DbTransactionConfig;
import io.github.mmm.db.tx.spi.AbstractDbTransaction;

/**
 * Database session data for a single transaction.
 */
public class JdbcRootTransaction extends JdbcTransaction {

  private final DbConnectionPool<Connection> pool;

  private JdbcMetaData metaData;

  JdbcRootTransaction(Connection connection, DbTransactionConfig config, AbstractDbTransaction parent,
      DbConnectionPool<Connection> pool) {

    super(connection, config, parent);
    this.pool = pool;
  }

  @Override
  public void commit() {

    try {
      this.connection.commit();
    } catch (SQLException e) {
      throw new IllegalStateException(e);
    }
  }

  @Override
  public void rollback() {

    try {
      this.connection.rollback();
    } catch (SQLException e) {
      throw new IllegalStateException(e);
    }
  }

  @Override
  protected void close() {

    this.pool.release(this.connection);
    super.close();
  }

  @Override
  public DbMetaData getMetaData() {

    if (this.metaData == null) {
      this.metaData = new JdbcMetaData(this.connection);
    }
    return this.metaData;
  }

}
