/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.tx.impl;

import io.github.mmm.db.source.DbSource;
import io.github.mmm.db.source.DbSourceConfig;
import io.github.mmm.db.tx.DbTransactionExecutor;
import io.github.mmm.db.tx.spi.DbTransactionExecutorProvider;

/**
 * Implementation of {@link DbTransactionExecutorProvider} for JDBC.
 */
public class JdbcTransactionExecutorProvider implements DbTransactionExecutorProvider {

  @Override
  public DbTransactionExecutor create(DbSourceConfig config) {

    if (DbSource.VALUE_KIND_JDBC.equals(config.getKind())) {
      return new JdbcTransactionExecutor(config);
    }
    return null;
  }

}
