/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.pool.spi;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import io.github.mmm.db.pool.DbConnectionPool;
import io.github.mmm.db.source.DbSource;
import io.github.mmm.db.source.DbSourceConfig;

/**
 * Implementation of {@link DbConnectionPool} for JDBC {@link Connection}s based on {@link DataSource}.
 */
@SuppressWarnings("exports")
public class JdbcConnectionPool extends AbstractDbConnectionPool<Connection> {

  private DataSource dataSource;

  private final JdbcConnectionPoolProvider provider;

  /**
   * The constructor.
   *
   * @param config the {@link DbSource}.
   * @param dataSource the {@link DataSource} pre-configured as connection pool.
   * @param provider the {@link JdbcConnectionPoolProvider} who created this pool.
   */
  public JdbcConnectionPool(DbSourceConfig config, DataSource dataSource, JdbcConnectionPoolProvider provider) {

    super(config);
    this.dataSource = dataSource;
    this.provider = provider;
  }

  @Override
  public Connection acquire() {

    try {
      Connection connection = this.dataSource.getConnection();
      connection.setAutoCommit(false);
      return connection;
    } catch (SQLException e) {
      throw new IllegalStateException("Unable to acquire connection!", e);
    }
  }

  @Override
  public void release(Connection connection) {

    try {
      connection.close();
    } catch (SQLException e) {
      throw new IllegalStateException("Unable to release connection!", e);
    }
  }

  @Override
  public void close() {

    if (this.dataSource != null) {
      this.provider.close(this.dataSource);
      this.dataSource = null;
    }
  }

}
