/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.schema;

import java.util.stream.Stream;

import io.github.mmm.db.source.DbSource;

/**
 * Interface for the {@link DbChangeLog}.
 */
public interface DbChangeLog {

  /**
   * @param source the {@link DbSource#getId() database source identifier}.
   * @return the requested {@link DbChangeLogSource} or {@code null} if not available.
   */
  DbChangeLogSource get(String source);

  /**
   *
   * @param source the {@link DbSource}.
   * @return the requested {@link DbChangeLogSource} or {@code null} if not available.
   */
  default DbChangeLogSource get(DbSource source) {

    return get(source.getId());
  }

  /**
   * @return the default {@link DbChangeLogSource}.
   */
  default DbChangeLogSource get() {

    return get(DbSource.get());
  }

  /**
   * @return the {@link Stream} of available {@link DbSource}s for which a {@link #get(DbSource) children are
   *         available}.
   */
  Stream<DbSource> getSources();

}
