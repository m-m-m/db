/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.type.sqlite.jdbc.pool;

import javax.sql.DataSource;

import org.sqlite.SQLiteDataSource;

import io.github.mmm.db.pool.spi.JdbcConnectionPoolProvider;
import io.github.mmm.db.source.DbSourceConfig;

/**
 * Implementation of {@link JdbcConnectionPoolProvider} for SQLite.
 *
 * @since 1.0.0
 */
public class SqliteConnectionPoolProvider extends JdbcConnectionPoolProvider {

  @Override
  public String getId() {

    return "sqlite";
  }

  @Override
  public boolean isResponsible(String url) {

    return url.startsWith("jdbc:sqlite:");
  }

  @Override
  public boolean isPool() {

    return false;
  }

  @Override
  protected DataSource createDataSource(DbSourceConfig config) {

    SQLiteDataSource dataSource = new SQLiteDataSource();
    dataSource.setUrl(config.getUrl());
    // no login data (username/password)
    return dataSource;
  }

  @Override
  protected void close(DataSource dataSource) {

    // nothing to do - no pool
  }

}
