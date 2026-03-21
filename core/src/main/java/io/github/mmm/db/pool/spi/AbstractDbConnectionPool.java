/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.pool.spi;

import java.sql.Connection;

import javax.sql.DataSource;

import io.github.mmm.db.pool.DbConnectionPool;
import io.github.mmm.db.source.DbSource;
import io.github.mmm.db.source.DbSourceConfig;

/**
 * Implementation of {@link DbConnectionPool} for JDBC {@link Connection}s based on {@link DataSource}.
 *
 * @param <C> type of the database connection.
 */
public abstract class AbstractDbConnectionPool<C> implements DbConnectionPool<C> {

  private final DbSourceConfig config;

  /**
   * The constructor.
   *
   * @param config the {@link DbSource}.
   */
  public AbstractDbConnectionPool(DbSourceConfig config) {

    super();
    this.config = config;
  }

  /**
   * @return the {@link DbSourceConfig}.
   */
  public DbSourceConfig getConfig() {

    return this.config;
  }

}
