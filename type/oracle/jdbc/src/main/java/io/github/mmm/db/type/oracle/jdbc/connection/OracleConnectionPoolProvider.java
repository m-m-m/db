/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.type.oracle.jdbc.connection;

import java.sql.SQLException;

import javax.sql.DataSource;

import io.github.mmm.db.pool.spi.JdbcConnectionPoolProvider;
import io.github.mmm.db.source.DbSourceConfig;
import oracle.jdbc.pool.OracleDataSource;
import oracle.ucp.jdbc.PoolDataSource;
import oracle.ucp.jdbc.PoolDataSourceFactory;

/**
 * Implementation of {@link JdbcConnectionPoolProvider} for Oracle UCP.
 *
 * @since 1.0.0
 */
public class OracleConnectionPoolProvider extends JdbcConnectionPoolProvider {

  @Override
  public String getId() {

    return "oracle";
  }

  @Override
  public boolean isResponsible(String url) {

    return url.startsWith("jdbc:oracle:");
  }

  @Override
  protected DataSource createDataSource(DbSourceConfig config) {

    try {
      PoolDataSource poolDataSource = PoolDataSourceFactory.getPoolDataSource();
      poolDataSource.setConnectionFactoryClassName(OracleDataSource.class.getName());
      poolDataSource.setURL(config.getUrl());
      poolDataSource.setUser(config.getUser());
      poolDataSource.setPassword(config.getPassword());
      Integer poolSizeMin = config.getPoolSizeMin();
      if (poolSizeMin != null) {
        poolDataSource.setMinPoolSize(poolSizeMin.intValue());
      }
      Integer poolSizeMax = config.getPoolSizeMax();
      if (poolSizeMax != null) {
        poolDataSource.setMaxPoolSize(poolSizeMax.intValue());
      }
      return poolDataSource;
    } catch (SQLException e) {
      throw new IllegalStateException(e);
    }
  }

  @Override
  protected void close(DataSource dataSource) {

    // PoolDataSource poolDataSource = (PoolDataSource) dataSource;
    // UCP does not seem to support dispose/close/shutdown/stop
  }

}
