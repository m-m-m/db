/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.type.h2.jdbc.connection;

import javax.sql.DataSource;

import org.h2.jdbcx.JdbcDataSource;

import io.github.mmm.db.pool.spi.JdbcConnectionPoolProvider;
import io.github.mmm.db.source.DbSourceConfig;

/**
 * Implementation of {@link JdbcConnectionPoolProvider} for H2 database.
 *
 * @since 1.0.0
 */
public class H2ConnectionPoolProvider extends JdbcConnectionPoolProvider {

  @Override
  public String getId() {

    return "h2";
  }

  @Override
  public boolean isResponsible(String url) {

    return url.startsWith("jdbc:h2:");
  }

  @Override
  public boolean isPool() {

    return false;
  }

  @Override
  protected DataSource createDataSource(DbSourceConfig config) {

    JdbcDataSource dataSource = new JdbcDataSource();
    dataSource.setURL(config.getUrl());
    dataSource.setUser(config.getUser());
    dataSource.setPassword(config.getPassword());
    return dataSource;
  }

  @Override
  protected void close(DataSource dataSource) {

    // nothing to do - no pool
  }

}
