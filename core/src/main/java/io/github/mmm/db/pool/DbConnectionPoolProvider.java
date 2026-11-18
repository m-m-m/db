/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.pool;

import io.github.mmm.db.source.DbSource;
import io.github.mmm.db.source.DbSourceConfig;

/// Service provider interface (SPI) to [create][#create(DbSourceConfig)] instances of [DbConnectionPool] for
/// a specific provider implementation.
///
/// @param <C> type of the database connection.
public interface DbConnectionPoolProvider<C> {

  /// @return the identifier of this provider.
  String getId();

  /// @return the [kind][DbSource#KEY_KIND] of database connection.
  String getKind();

  /// @param url the database connection URL (e.g. JDBC URL).
  /// @return `true` if this [DbConnectionPoolProvider] is responsible for the given `url`,
  ///         `false` otherwise.
  default boolean isResponsible(String url) {

    return url.startsWith(getKind());
  }

  /// @return `true` if a real connection pool is provided, `false` otherwise.
  default boolean isPool() {

    return true;
  }

  /// @param config the [DbSourceConfig].
  /// @return the new [DbConnectionPool] instance for the given configuration.
  DbConnectionPool<C> create(DbSourceConfig config);

}
