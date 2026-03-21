package io.github.mmm.db.spi.access.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

import io.github.mmm.base.service.ServiceHelper;
import io.github.mmm.db.source.DbSourceConfig;
import io.github.mmm.db.spi.access.DbAccess;
import io.github.mmm.db.spi.access.DbAccessProvider;
import io.github.mmm.db.tx.DbTransactionExecutor;
import io.github.mmm.db.tx.spi.DbTransactionExecutorProvider;

/**
 * Adapter implementation of {@link DbTransactionExecutorProvider} that holds the {@link DbTransactionExecutor}s in
 * memory.
 *
 * @since 1.0.0
 */
public class DbAccessProviderAdapter implements DbAccessProvider {

  /** The singleton instance. */
  public static final DbAccessProviderAdapter INSTANCE = new DbAccessProviderAdapter();

  private final List<DbAccessProvider> providers;

  private final Map<String, DbAccess> accesses;

  /**
   * The constructor.
   */
  public DbAccessProviderAdapter() {

    super();
    this.providers = new ArrayList<>();
    ServiceHelper.all(ServiceLoader.load(DbAccessProvider.class), this.providers);
    this.accesses = new ConcurrentHashMap<>();
  }

  @Override
  public DbAccess create(DbSourceConfig config) {

    return this.accesses.computeIfAbsent(config.getSource().getId(), _ -> doCreate(config));
  }

  private DbAccess doCreate(DbSourceConfig config) {

    for (DbAccessProvider provider : this.providers) {
      DbAccess access = provider.create(config);
      if (access != null) {
        return access;
      }
    }
    throw new IllegalStateException(
        "Could not create DbAccess after trying " + this.providers.size() + " provider(s): " + this.providers);
  }

}
