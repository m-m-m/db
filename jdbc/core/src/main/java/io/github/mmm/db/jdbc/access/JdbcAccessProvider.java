package io.github.mmm.db.jdbc.access;

import io.github.mmm.db.source.DbSource;
import io.github.mmm.orm.spi.access.DbAccess;
import io.github.mmm.orm.spi.access.DbAccessProvider;

/**
 * Implementation of {@link DbAccessProvider}.
 *
 * @since 1.0.0
 */
public class JdbcAccessProvider implements DbAccessProvider {

  @Override
  public DbAccess create(DbSource source) {

    return new JdbcAccess();
  }
}
