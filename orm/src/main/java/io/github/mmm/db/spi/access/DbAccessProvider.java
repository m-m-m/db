package io.github.mmm.db.spi.access;

import io.github.mmm.db.source.DbSourceConfig;

/// Provider of [DbAccess].
///
/// @since 1.0.0
public interface DbAccessProvider {

  /// @param source the [DbSourceConfig].
  /// @return the corresponding [DbAccess].
  DbAccess create(DbSourceConfig source);

}
