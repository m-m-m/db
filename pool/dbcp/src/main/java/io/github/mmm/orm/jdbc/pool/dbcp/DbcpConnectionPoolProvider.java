/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.orm.jdbc.pool.dbcp;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;

import io.github.mmm.db.pool.spi.JdbcConnectionPoolProvider;
import io.github.mmm.db.source.DbSourceConfig;

/**
 * Implementation of {@link JdbcConnectionPoolProvider} for Apache commons DBCP.
 */
public final class DbcpConnectionPoolProvider extends JdbcConnectionPoolProvider {

  @Override
  public String getId() {

    return "dbcp";
  }

  @Override
  protected DataSource createDataSource(DbSourceConfig config) throws Exception {

    BasicDataSource pool = BasicDataSourceFactory.createDataSource(config.getMetaInfo().asProperties());
    pool.setUrl(config.getUrl());
    pool.setUsername(config.getUser());
    pool.setPassword(config.getPassword());
    pool.setDefaultSchema(config.getSchema());
    pool.setDefaultCatalog(config.getCatalog());
    Integer poolSizeMin = config.getPoolSizeMin();
    if (poolSizeMin != null) {
      pool.setMinIdle(poolSizeMin.intValue());
    }
    Integer poolSizeMax = config.getPoolSizeMax();
    if (poolSizeMax != null) {
      pool.setMaxTotal(poolSizeMax.intValue());
    }
    String driverClassName = config.getDriverClassName();
    if (driverClassName != null) {
      pool.setDriverClassName(driverClassName);
    }
    return pool;
  }

  @Override
  protected void close(DataSource dataSource) {

    try {
      BasicDataSource pool = (BasicDataSource) dataSource;
      pool.close();
    } catch (SQLException e) {
      throw new IllegalStateException("Failed to close DBCP connection pool!", e);
    }
  }
}
