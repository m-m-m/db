/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.type.postgresql.jdbc.pool;

import javax.sql.DataSource;

import org.postgresql.ds.PGSimpleDataSource;

import io.github.mmm.db.pool.spi.JdbcConnectionPoolProvider;
import io.github.mmm.db.source.DbSourceConfig;

/**
 * Implementation of {@link JdbcConnectionPoolProvider} for PostGreSQL.
 *
 * @since 1.0.0
 */
public class PostgreSqlConnectionPoolProvider extends JdbcConnectionPoolProvider {

  @Override
  public String getId() {

    return "postgresql";
  }

  @Override
  public boolean isResponsible(String url) {

    return url.startsWith("jdbc:postgresql:");
  }

  @Override
  public boolean isPool() {

    return false;
  }

  @Override
  protected DataSource createDataSource(DbSourceConfig config) {

    PGSimpleDataSource dataSource = new PGSimpleDataSource();
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
