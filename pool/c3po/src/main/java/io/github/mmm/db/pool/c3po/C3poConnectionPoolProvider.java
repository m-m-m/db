/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.pool.c3po;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import io.github.mmm.db.pool.spi.JdbcConnectionPoolProvider;
import io.github.mmm.db.source.DbSourceConfig;

/**
 * Implementation of {@link JdbcConnectionPoolProvider} for C3Po.
 */
public final class C3poConnectionPoolProvider extends JdbcConnectionPoolProvider {

  @Override
  public String getId() {

    return "c3po";
  }

  @Override
  protected DataSource createDataSource(DbSourceConfig config) throws Exception {

    ComboPooledDataSource pool = new ComboPooledDataSource();
    pool.setProperties(config.getMetaInfo().asProperties());
    pool.setJdbcUrl(config.getUrl());
    pool.setUser(config.getUser());
    pool.setPassword(config.getPassword());
    Integer poolSizeMin = config.getPoolSizeMin();
    if (poolSizeMin != null) {
      pool.setMinPoolSize(poolSizeMin.intValue());
    }
    Integer poolSizeMax = config.getPoolSizeMax();
    if (poolSizeMax != null) {
      pool.setMaxPoolSize(poolSizeMax.intValue());
    }
    String driverClassName = config.getDriverClassName();
    if (driverClassName != null) {
      pool.setDriverClass(driverClassName);
    }
    return pool;
  }

  @Override
  protected void close(DataSource dataSource) {

    ComboPooledDataSource pool = (ComboPooledDataSource) dataSource;
    pool.close();
  }

}
