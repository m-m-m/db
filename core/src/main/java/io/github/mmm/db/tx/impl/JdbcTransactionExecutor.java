/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.tx.impl;

import java.sql.Connection;

import io.github.mmm.db.pool.spi.JdbcConnectionPool;
import io.github.mmm.db.source.DbSourceConfig;
import io.github.mmm.db.source.impl.DbSourceConfigImpl;
import io.github.mmm.db.tx.DbTransactionConfig;
import io.github.mmm.db.tx.spi.AbstractDbTransaction;
import io.github.mmm.db.tx.spi.AbstractDbTransactionExecutor;

/**
 * Implementation of {@link AbstractDbTransactionExecutor} for JDBC.
 */
public class JdbcTransactionExecutor extends AbstractDbTransactionExecutor {

  private final DbSourceConfig config;

  /**
   * The constructor.
   *
   * @param config the {@link DbSourceConfig}.
   */
  public JdbcTransactionExecutor(DbSourceConfig config) {

    super();
    this.config = config;
  }

  @Override
  protected AbstractDbTransaction createNewTransaction(DbTransactionConfig txConfig, AbstractDbTransaction parent) {

    Connection connection = null;
    DbSourceConfigImpl configImpl = (DbSourceConfigImpl) this.config;
    JdbcConnectionPool connectionPool = (JdbcConnectionPool) configImpl.getConnectionPool();
    connection = connectionPool.acquire();
    return new JdbcRootTransaction(connection, txConfig, parent, connectionPool);
  }

}
