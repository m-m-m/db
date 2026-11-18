/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.source;

import java.time.Duration;

import io.github.mmm.base.metainfo.MetaInfo;
import io.github.mmm.db.name.DbQualifiedName;
import io.github.mmm.db.source.impl.DbSourceConfigManager;

/// Interface for the configuration to a [DbSource].
public interface DbSourceConfig {

  /// @return the original [DbSource] of this data.
  DbSource getSource();

  /// @return the configuration as [MetaInfo].
  MetaInfo getMetaInfo();

  /// @return the [database connection URL][DbSource#KEY_URL].
  default String getUrl() {

    return getMetaInfo().getRequired(DbSource.KEY_URL);
  }

  /// @return the [database connection user/login][DbSource#KEY_USER].
  default String getUser() {

    return getMetaInfo().getRequired(DbSource.KEY_USER);
  }

  /// @return the [database connection password][DbSource#KEY_USER].
  default String getPassword() {

    return getMetaInfo().getRequired(DbSource.KEY_PASSWORD);
  }

  /// @return the [kind of database connection][DbSource#KEY_KIND] (e.g. ["jdbc"][DbSource#VALUE_KIND_JDBC]).
  ///         Will be auto-configured if undefined.
  default String getKind() {

    return getMetaInfo().get(DbSource.KEY_KIND);
  }

  /// @return `true` if [kind][#getKind()] is [jdbc][DbSource#VALUE_KIND_JDBC], `false` otherwise.
  default boolean isKindJdbc() {

    return DbSource.VALUE_KIND_JDBC.equals(getKind());
  }

  /// @return the [type of database][DbSource#KEY_TYPE]. This is similar to the [dialect][#getDialect()] but
  ///         for the same database type potentially different dialects may exist (e.g. due to different versions of the
  ///         database product). Will be auto-configured if undefined.
  default String getType() {

    return getMetaInfo().get(DbSource.KEY_TYPE);
  }

  /// @return the [database dialect][DbSource#KEY_DIALECT]. This is the ID of the `DbDialect`. Such dialect
  ///         translates to database specific syntax of statements (typically native SQL). This property will be
  ///         auto-configured if undefined and defaults to the same value as [type][#getType()].
  default String getDialect() {

    return getMetaInfo().get(DbSource.KEY_DIALECT);
  }

  /// @return the [database name][DbSource#KEY_DATABASE]. Typically configured as part of the [URL][#getUrl()].
  default String getDatabase() {

    return getMetaInfo().get(DbSource.KEY_SCHEMA);
  }

  /// @return the [schema][DbSource#KEY_SCHEMA] or `null` if not configured.
  default String getSchema() {

    return getMetaInfo().get(DbSource.KEY_SCHEMA);
  }

  /// @return the [catalog][DbSource#KEY_CATALOG] or `null` if not configured.
  default String getCatalog() {

    return getMetaInfo().get(DbSource.KEY_CATALOG);
  }

  /// @return the [driver class-name][DbSource#KEY_DRIVER_CLASS_NAME] or `null` if not explicitly configured.
  default String getDriverClassName() {

    return getMetaInfo().get(DbSource.KEY_DRIVER_CLASS_NAME);
  }

  /// @return the [pool ID][DbSource#KEY_POOL_ID].
  default String getPoolId() {

    return getMetaInfo().get(DbSource.KEY_POOL_ID);
  }

  /// @return the [minimum size][DbSource#KEY_POOL_SIZE_MIN] of connections kept in the pool (even if idle).
  default Integer getPoolSizeMin() {

    return getMetaInfo().get(DbSource.KEY_POOL_SIZE_MIN);
  }

  /// @return the [maximum size][DbSource#KEY_POOL_SIZE_MAX] of connections in the pool. May be `null` what
  ///         means unlimited connections (if supported by pool implementation).
  default Integer getPoolSizeMax() {

    return getMetaInfo().get(DbSource.KEY_POOL_SIZE_MAX);
  }

  /// @return the [connection timeout][DbSource#KEY_POOL_CONNECTION_TIMEOUT] of the pool.
  default Duration getPoolConnectionTimeout() {

    return getMetaInfo().get(DbSource.KEY_POOL_CONNECTION_TIMEOUT);
  }

  /// @return the [DbQualifiedName] with [schema][#getSchema()] and [catalog][#getCatalog()] to use as
  ///         template.
  /// @see DbQualifiedName#withName(String)
  DbQualifiedName getQualifiedNameTemplate();

  /// @return the [sequence increment][DbSource#KEY_SEQUENCE_INCREMENT] value.
  default int getSequenceIncrement() {

    return getMetaInfo().getRequired(DbSource.KEY_SEQUENCE_INCREMENT).intValue();
  }

  /// @param source the [DbSource].
  /// @return the [DbSourceConfig] for the given [DbSource]. Will be created on the first call with the same
  ///         [DbSource].
  static DbSourceConfig of(DbSource source) {

    return DbSourceConfigManager.INSTANCE.getOrCreate(source);
  }

  /// @param source the [DbSource].
  /// @return the [DbSourceConfig] for the given [DbSource] or `null` if not yet [created][#of(DbSource)].
  static DbSourceConfig get(DbSource source) {

    return DbSourceConfigManager.INSTANCE.get(source);
  }

}
