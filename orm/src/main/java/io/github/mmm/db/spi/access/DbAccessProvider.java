package io.github.mmm.db.spi.access;

import io.github.mmm.db.source.DbSourceConfig;

/**
 * Provider of {@link DbAccess}.
 *
 * @since 1.0.0
 */
public interface DbAccessProvider {

  /**
   * @param source the {@link DbSourceConfig}.
   * @return the corresponding {@link DbAccess}.
   */
  DbAccess create(DbSourceConfig source);

}
