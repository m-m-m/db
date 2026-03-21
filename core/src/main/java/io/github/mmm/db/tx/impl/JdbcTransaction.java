/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.tx.impl;

import java.sql.Connection;

import io.github.mmm.db.tx.DbTransaction;
import io.github.mmm.db.tx.DbTransactionConfig;
import io.github.mmm.db.tx.spi.AbstractDbTransaction;

/**
 * Abstract base implementation of {@link DbTransaction} for JDBC.
 */
public abstract class JdbcTransaction extends AbstractDbTransaction {

  final Connection connection;

  JdbcTransaction(Connection connection, DbTransactionConfig config, AbstractDbTransaction parent) {

    super(config, parent);
    this.connection = connection;
  }

  /**
   * @return the raw JDBC {@link Connection}.
   */
  @Override
  @SuppressWarnings("unchecked")
  public Connection getConnection() {

    return this.connection;
  }

  @Override
  public JdbcSubTransaction doCreateChild() {

    return new JdbcSubTransaction(this);
  }

}
