/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.pool;

import java.io.Closeable;

/// This is the interface for a pool of connections (for JDBC see [java.sql.Connection]). It is an internal API
/// that allows to [#acquire()] a `connection` from the pool. After it has been used, it has to be
/// [released][#release(Object)]. To avoid resource leaks this needs to be handled with care so you should always
/// use a `try` block and [release][#release(Object)] in `finally`.
///
/// @param <C> type of the database connection.
public interface DbConnectionPool<C> extends Closeable {

  /// @return the acquired `connection` that has been borrowed from this pool. The caller has to guarantee to give
  ///         it back after usage via [#release(Object)].
  C acquire();

  /// @param connection the `connection` to return back to this pool so it can be reused by others again via
  ///        [#acquire()].
  void release(C connection);

  @Override
  void close();

}
