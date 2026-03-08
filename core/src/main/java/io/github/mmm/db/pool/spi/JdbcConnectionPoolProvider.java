/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.pool.spi;

import java.sql.Connection;

import javax.sql.DataSource;

import io.github.mmm.base.metainfo.MetaInfo;
import io.github.mmm.db.pool.DbConnectionPool;
import io.github.mmm.db.pool.DbConnectionPoolProvider;
import io.github.mmm.db.source.DbSource;
import io.github.mmm.db.source.DbSourceConfig;

/**
 * Abstract base implementation of {@link DbConnectionPoolProvider} for JDBC.
 *
 * @since 1.0.0
 */
public abstract class JdbcConnectionPoolProvider implements DbConnectionPoolProvider<Connection> {

  @Override
  public String getKind() {

    return "jdbc";
  }

  @SuppressWarnings("exports")
  @Override
  public DbConnectionPool<Connection> create(DbSourceConfig config) {

    try {
      DataSource dataSource = createDataSource(config);
      return new JdbcConnectionPool(config, dataSource, this);
    } catch (Exception e) {
      // avoid exception if not configured so we do not call config.getUrl() and config.getUser()
      MetaInfo metaInfo = config.getMetaInfo();
      String url = metaInfo.get(DbSource.KEY_URL);
      String user = metaInfo.get(DbSource.KEY_USER);
      throw new IllegalStateException("Unable to create " + getId() + " connection pool to URL " + url + " for user "
          + user + ". " + "Check your configuration (" + metaInfo.getKeyPrefix() + "*)!", e);
    }
  }

  /**
   * @param config the configuration as {@link MetaInfo}.
   * @return the connection pool as {@link DataSource}.
   * @throws Exception on error.
   */
  protected abstract DataSource createDataSource(DbSourceConfig config) throws Exception;

  /**
   * @param dataSource the {@link DataSource} that was created by {@link #createDataSource(DbSourceConfig)} and is now
   *        to be closed to free resources as the connection pool is no longer needed.
   */
  protected abstract void close(DataSource dataSource);

}
