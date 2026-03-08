/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.source.impl;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.mmm.base.exception.ObjectNotFoundException;
import io.github.mmm.base.metainfo.MetaInfo;
import io.github.mmm.db.pool.DbConnectionPool;
import io.github.mmm.db.pool.DbConnectionPoolProvider;
import io.github.mmm.db.pool.DbConnectionPoolProviderManager;
import io.github.mmm.db.source.DbSource;
import io.github.mmm.db.source.DbSourceConfig;

/**
 * Manager of {@link DbSourceConfig}.
 *
 * @since 1.0.0
 */
public final class DbSourceConfigManager {

  private static final Logger LOG = LoggerFactory.getLogger(DbSourceConfigManager.class);

  /** The singleton instance. */
  public static final DbSourceConfigManager INSTANCE = new DbSourceConfigManager();

  private final Map<String, DbSourceConfigImpl> data;

  private DbSourceConfigManager() {

    super();
    this.data = new ConcurrentHashMap<>();
  }

  /**
   * @param source the {@link DbSource}.
   * @return the existing {@link DbSourceConfigImpl} or {@code null} if not {@link #getOrCreate(DbSource) created}
   *         before.
   */
  public DbSourceConfigImpl get(DbSource source) {

    return this.data.get(source.getId());
  }

  /**
   * @param source the {@link DbSource}.
   * @return the corresponding {@link DbSourceConfigImpl}. Will be created if not yet exists.
   * @see DbSourceConfig#of(DbSource)
   */
  public DbSourceConfigImpl getOrCreate(DbSource source) {

    return this.data.computeIfAbsent(source.getId(), _ -> create(source));
  }

  private DbSourceConfigImpl create(DbSource source) {

    MetaInfo metaInfo = MetaInfo.config().with(source.getPropertyPrefix());
    Properties properties = metaInfo.asProperties();
    metaInfo = MetaInfo.empty().with(properties);
    DbSourceConfigImpl config = new DbSourceConfigImpl(source, metaInfo);
    DbSourceConfigurerAdapter.INSTANCE.autoConfigure(properties, source);
    DbConnectionPool<?> pool = createPool(config, properties);
    config.setConnectionPool(pool);
    return config;
  }

  private DbConnectionPool<?> createPool(DbSourceConfig config, Properties properties) {

    String poolId = config.getPoolId();
    DbConnectionPoolProviderManager manager = DbConnectionPoolProviderManager.get();
    DbConnectionPoolProvider<?> provider = null;
    String url = config.getUrl();
    if (poolId == null) {
      for (DbConnectionPoolProvider<?> currentProvider : manager) {
        if (currentProvider.isResponsible(url)) {
          if (provider == null) {
            provider = currentProvider;
          } else {
            boolean logWarning = true;
            if (currentProvider.isPool()) {
              if (!provider.isPool()) {
                provider = currentProvider;
                logWarning = false;
              }
            } else if (provider.isPool()) {
              logWarning = false;
            }
            if (logWarning) {
              LOG.warn(
                  "Connection pool provider is ambiguous: already found {} but also found {}. Please specify property {} explicitly.",
                  provider.getId(), currentProvider.getId(),
                  config.getSource().getPropertyKey(DbSource.KEY_POOL_ID.getName()));
            }
          }
        }
      }
      if (provider != null) {
        poolId = provider.getId();
        properties.setProperty(DbSource.KEY_POOL_ID.getName(), poolId);
      }
    } else {
      provider = manager.get(poolId);
    }
    if (provider == null) {
      throw new ObjectNotFoundException("DbConnectionPoolProvider", poolId);
    }
    LOG.info("Creating connection pool of ID " + poolId + " for " + url);
    return provider.create(config);
  }

}
