/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.source.spi;

import java.util.Properties;

import io.github.mmm.db.source.DbSource;
import io.github.mmm.db.source.DbSourceConfig;

/// Interface to [auto-configure][#autoConfigure(Properties, DbSource)] [Properties] from [DbSource] for
/// [DbSourceConfig].
public interface DbSourceConfigurer {

  /// @param properties the [Properties] from [DbSource] to auto-configure for [DbSourceConfig].
  /// @param source the [DbSource].
  /// @return `true` if auto-configuration was successful, `false` otherwise (not responsible for this
  ///         source).
  boolean autoConfigure(Properties properties, DbSource source);

}
