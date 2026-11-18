/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.dialect;

import io.github.mmm.base.exception.ObjectNotFoundException;
import io.github.mmm.db.impl.DbDialectProviderImpl;

/// Provider for [DbDialect]s.
///
/// @since 1.0.0
public interface DbDialectProvider extends Iterable<DbDialect> {

  /// @param id the [identifier][DbDialect#getId()] of the requested [DbDialect].
  /// @return the requested [DbDialect].
  /// @throws ObjectNotFoundException if no [DbDialect] could be found for the given `name`.
  DbDialect get(String id);

  /// @param url the database connection URL (e.g. JDBC URL).
  /// @return the [DbDialect] that is responsible for this URL.
  /// @throws ObjectNotFoundException if no [DbDialect] could be found for the given `url`.
  DbDialect getByDbUrl(String url);

  /// @param name the [name][DbDialect#getId()] of the [DbDialect] to find.
  /// @return `true` if the [DbDialect] with the given [name][DbDialect#getId()] is present,
  ///         `false` otherwise.
  boolean has(String name);

  /// @return the singleton instance of this [DbDialectProvider].
  static DbDialectProvider get() {

    return DbDialectProviderImpl.INSTANCE;
  }

}
