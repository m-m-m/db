/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.spi.access.impl;

import io.github.mmm.db.source.DbSourceConfig;
import io.github.mmm.db.spi.access.DbAccess;
import io.github.mmm.db.spi.access.DbAccessProvider;

/**
 * Implementation of {@link DbAccessProvider} for JDBC.
 *
 * @since 1.0.0
 */
public class JdbcAccessProvider implements DbAccessProvider {

  @Override
  public DbAccess create(DbSourceConfig config) {

    if (config.isKindJdbc()) {
      return new JdbcAccessImpl(config);
    }
    return null;
  }
}