/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.orm.jdbc.pool.hikari;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import io.github.mmm.base.metainfo.MetaInfo;
import io.github.mmm.db.pool.spi.JdbcConnectionPoolProvider;
import io.github.mmm.db.source.DbSource;
import io.github.mmm.db.source.DbSourceConfig;

/**
 * Implementation of {@link JdbcConnectionPoolProvider} for {@link HikariDataSource hikari}.
 */
public final class HikariConnectionPoolProvider extends JdbcConnectionPoolProvider {

  @Override
  public String getId() {

    return "hikari";
  }

  @Override
  protected DataSource createDataSource(DbSourceConfig config) {

    HikariConfig hikariConfig = new HikariConfig();
    hikariConfig.setJdbcUrl(config.getUrl());
    hikariConfig.setUsername(config.getUser());
    hikariConfig.setPassword(config.getPassword());
    Integer poolSizeMin = config.getPoolSizeMin();
    if (poolSizeMin != null) {
      hikariConfig.setMinimumIdle(poolSizeMin.intValue());
    }
    Integer poolSizeMax = config.getPoolSizeMax();
    if (poolSizeMax != null) {
      hikariConfig.setMaximumPoolSize(poolSizeMax.intValue());
    }
    String driverClassName = config.getDriverClassName();
    if (driverClassName != null) {
      hikariConfig.setDriverClassName(driverClassName);
    }
    MetaInfo metaInfo = config.getMetaInfo();
    for (String key : metaInfo) {
      if (!DbSource.VARIABLES.contains(key)) {
        hikariConfig.addDataSourceProperty(key, metaInfo.get(key));
      }
    }
    return new HikariDataSource(hikariConfig);
  }

  @Override
  protected void close(DataSource dataSource) {

    HikariDataSource pool = (HikariDataSource) dataSource;
    pool.close();
  }

}
