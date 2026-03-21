/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.pool;

import io.github.mmm.db.source.DbSource;
import io.github.mmm.db.source.DbSourceConfig;

/**
 * Service provider interface (SPI) to {@link #create(DbSourceConfig) create} instances of {@link DbConnectionPool} for
 * a specific provider implementation.
 *
 * @param <C> type of the database connection.
 */
public interface DbConnectionPoolProvider<C> {

  /**
   * @return the identifier of this provider.
   */
  String getId();

  /**
   * @return the {@link DbSource#KEY_KIND kind} of database connection.
   */
  String getKind();

  /**
   * @param url the database connection URL (e.g. JDBC URL).
   * @return {@code true} if this {@link DbConnectionPoolProvider} is responsible for the given {@code url},
   *         {@code false} otherwise.
   */
  default boolean isResponsible(String url) {

    return url.startsWith(getKind());
  }

  /**
   * @return {@code true} if a real connection pool is provided, {@code false} otherwise.
   */
  default boolean isPool() {

    return true;
  }

  /**
   * @param config the {@link DbSourceConfig}.
   * @return the new {@link DbConnectionPool} instance for the given configuration.
   */
  DbConnectionPool<C> create(DbSourceConfig config);

}
