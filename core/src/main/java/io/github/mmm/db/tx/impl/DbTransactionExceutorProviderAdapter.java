/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.tx.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

import io.github.mmm.base.service.ServiceHelper;
import io.github.mmm.db.source.DbSourceConfig;
import io.github.mmm.db.tx.DbTransactionExecutor;
import io.github.mmm.db.tx.spi.DbTransactionExecutorProvider;

/**
 * Adapter implementation of {@link DbTransactionExecutorProvider} that holds the {@link DbTransactionExecutor}s.
 *
 * @since 1.0.0
 */
public final class DbTransactionExceutorProviderAdapter implements DbTransactionExecutorProvider {

  /** The singleton instance. */
  public static final DbTransactionExceutorProviderAdapter INSTANCE = new DbTransactionExceutorProviderAdapter();

  private final List<DbTransactionExecutorProvider> providers;

  private final Map<String, DbTransactionExecutor> executors;

  /**
   * The constructor.
   */
  public DbTransactionExceutorProviderAdapter() {

    super();
    this.providers = new ArrayList<>();
    ServiceHelper.all(ServiceLoader.load(DbTransactionExecutorProvider.class), this.providers);
    this.executors = new ConcurrentHashMap<>();
  }

  @Override
  public DbTransactionExecutor create(DbSourceConfig config) {

    return this.executors.computeIfAbsent(config.getSource().getId(), _ -> doCreate(config));
  }

  private DbTransactionExecutor doCreate(DbSourceConfig config) {

    for (DbTransactionExecutorProvider provider : this.providers) {
      DbTransactionExecutor executor = provider.create(config);
      if (executor != null) {
        return executor;
      }
    }
    throw new IllegalStateException("Could not create DbTransactionExecutor after trying " + this.providers.size()
        + " provider(s): " + this.providers);
  }

}
