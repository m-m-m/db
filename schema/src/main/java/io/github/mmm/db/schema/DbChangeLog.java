/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.schema;

import java.util.stream.Stream;

import io.github.mmm.db.source.DbSource;

/// Interface for the [DbChangeLog].
public interface DbChangeLog {

  /// @param source the [database source identifier][DbSource#getId()].
  /// @return the requested [DbChangeLogSource] or `null` if not available.
  DbChangeLogSource get(String source);

  /// @param source the [DbSource].
  /// @return the requested [DbChangeLogSource] or `null` if not available.
  default DbChangeLogSource get(DbSource source) {

    return get(source.getId());
  }

  /// @return the default [DbChangeLogSource].
  default DbChangeLogSource get() {

    return get(DbSource.get());
  }

  /// @return the [Stream] of available [DbSource]s for which a [children are available][#get(DbSource)].
  Stream<DbSource> getSources();

}
