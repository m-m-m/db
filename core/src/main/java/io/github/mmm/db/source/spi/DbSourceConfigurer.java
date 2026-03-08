/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.source.spi;

import java.util.Properties;

import io.github.mmm.db.source.DbSource;
import io.github.mmm.db.source.DbSourceConfig;

/**
 * Interface to {@link #autoConfigure(Properties, DbSource) auto-configure} {@link Properties} from {@link DbSource} for
 * {@link DbSourceConfig}.
 */
public interface DbSourceConfigurer {

  /**
   * @param properties the {@link Properties} from {@link DbSource} to auto-configure for {@link DbSourceConfig}.
   * @param source the {@link DbSource}.
   * @return {@code true} if auto-configuration was successful, {@code false} otherwise (not responsible for this
   *         source).
   */
  boolean autoConfigure(Properties properties, DbSource source);

}
