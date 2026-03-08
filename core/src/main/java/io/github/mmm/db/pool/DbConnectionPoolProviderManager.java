/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.pool;

import io.github.mmm.db.pool.impl.DbConnectionPoolProviderManagerImpl;

/**
 * Internal interface to get access to the registered {@link DbConnectionPoolProvider}s.
 */
public interface DbConnectionPoolProviderManager extends Iterable<DbConnectionPoolProvider<?>> {

  /**
   * @param <C> type of the database connection.
   * @param id the {@link DbConnectionPoolProvider#getId() identifier} of the {@link DbConnectionPoolProvider}.
   * @return the requested {@link DbConnectionPoolProvider}.
   * @see io.github.mmm.db.source.DbSource#KEY_POOL_ID
   */
  <C> DbConnectionPoolProvider<C> get(String id);

  /**
   * @return the instance of {@link DbConnectionPoolProviderManagerImpl}.
   */
  static DbConnectionPoolProviderManager get() {

    return DbConnectionPoolProviderManagerImpl.INSTANCE;
  }

}
