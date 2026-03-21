/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.source;

import java.time.Duration;

import io.github.mmm.base.metainfo.MetaInfo;
import io.github.mmm.db.name.DbQualifiedName;
import io.github.mmm.db.source.impl.DbSourceConfigManager;

/**
 * Interface for the configuration to a {@link DbSource}.
 */
public interface DbSourceConfig {

  /**
   * @return the original {@link DbSource} of this data.
   */
  DbSource getSource();

  /**
   * @return the configuration as {@link MetaInfo}.
   */
  MetaInfo getMetaInfo();

  /**
   * @return the {@link DbSource#KEY_URL database connection URL}.
   */
  default String getUrl() {

    return getMetaInfo().getRequired(DbSource.KEY_URL);
  }

  /**
   * @return the {@link DbSource#KEY_USER database connection user/login}.
   */
  default String getUser() {

    return getMetaInfo().getRequired(DbSource.KEY_USER);
  }

  /**
   * @return the {@link DbSource#KEY_USER database connection password}.
   */
  default String getPassword() {

    return getMetaInfo().getRequired(DbSource.KEY_PASSWORD);
  }

  /**
   * @return the {@link DbSource#KEY_KIND kind of database connection} (e.g. {@link DbSource#VALUE_KIND_JDBC "jdbc"}).
   *         Will be auto-configured if undefined.
   */
  default String getKind() {

    return getMetaInfo().get(DbSource.KEY_KIND);
  }

  /**
   * @return {@code true} if {@link #getKind() kind} is {@link DbSource#VALUE_KIND_JDBC jdbc}, {@code false} otherwise.
   */
  default boolean isKindJdbc() {

    return DbSource.VALUE_KIND_JDBC.equals(getKind());
  }

  /**
   * @return the {@link DbSource#KEY_TYPE type of database}. This is similar to the {@link #getDialect() dialect} but
   *         for the same database type potentially different dialects may exist (e.g. due to different versions of the
   *         database product). Will be auto-configured if undefined.
   */
  default String getType() {

    return getMetaInfo().get(DbSource.KEY_TYPE);
  }

  /**
   * @return the {@link DbSource#KEY_DIALECT database dialect}. This is the ID of the {@code DbDialect}. Such dialect
   *         translates to database specific syntax of statements (typically native SQL). This property will be
   *         auto-configured if undefined and defaults to the same value as {@link #getType() type}.
   */
  default String getDialect() {

    return getMetaInfo().get(DbSource.KEY_DIALECT);
  }

  /**
   * @return the {@link DbSource#KEY_DATABASE database name}. Typically configured as part of the {@link #getUrl() URL}.
   */
  default String getDatabase() {

    return getMetaInfo().get(DbSource.KEY_SCHEMA);
  }

  /**
   * @return the {@link DbSource#KEY_SCHEMA schema} or {@code null} if not configured.
   */
  default String getSchema() {

    return getMetaInfo().get(DbSource.KEY_SCHEMA);
  }

  /**
   * @return the {@link DbSource#KEY_CATALOG catalog} or {@code null} if not configured.
   */
  default String getCatalog() {

    return getMetaInfo().get(DbSource.KEY_CATALOG);
  }

  /**
   * @return the {@link DbSource#KEY_DRIVER_CLASS_NAME driver class-name} or {@code null} if not explicitly configured.
   */
  default String getDriverClassName() {

    return getMetaInfo().get(DbSource.KEY_DRIVER_CLASS_NAME);
  }

  /**
   * @return the {@link DbSource#KEY_POOL_ID pool ID}.
   */
  default String getPoolId() {

    return getMetaInfo().get(DbSource.KEY_POOL_ID);
  }

  /**
   * @return the {@link DbSource#KEY_POOL_SIZE_MIN minimum size} of connections kept in the pool (even if idle).
   */
  default Integer getPoolSizeMin() {

    return getMetaInfo().get(DbSource.KEY_POOL_SIZE_MIN);
  }

  /**
   * @return the {@link DbSource#KEY_POOL_SIZE_MAX maximum size} of connections in the pool. May be {@code null} what
   *         means unlimited connections (if supported by pool implementation).
   */
  default Integer getPoolSizeMax() {

    return getMetaInfo().get(DbSource.KEY_POOL_SIZE_MAX);
  }

  /**
   * @return the {@link DbSource#KEY_POOL_CONNECTION_TIMEOUT connection timeout} of the pool.
   */
  default Duration getPoolConnectionTimeout() {

    return getMetaInfo().get(DbSource.KEY_POOL_CONNECTION_TIMEOUT);
  }

  /**
   * @return the {@link DbQualifiedName} with {@link #getSchema() schema} and {@link #getCatalog() catalog} to use as
   *         template.
   * @see DbQualifiedName#withName(String)
   */
  DbQualifiedName getQualifiedNameTemplate();

  /**
   * @return the {@link DbSource#KEY_SEQUENCE_INCREMENT sequence increment} value.
   */
  default int getSequenceIncrement() {

    return getMetaInfo().getRequired(DbSource.KEY_SEQUENCE_INCREMENT).intValue();
  }

  /**
   * @param source the {@link DbSource}.
   * @return the {@link DbSourceConfig} for the given {@link DbSource}. Will be created on the first call with the same
   *         {@link DbSource}.
   */
  static DbSourceConfig of(DbSource source) {

    return DbSourceConfigManager.INSTANCE.getOrCreate(source);
  }

  /**
   * @param source the {@link DbSource}.
   * @return the {@link DbSourceConfig} for the given {@link DbSource} or {@code null} if not yet {@link #of(DbSource)
   *         created}.
   */
  static DbSourceConfig get(DbSource source) {

    return DbSourceConfigManager.INSTANCE.get(source);
  }

}
