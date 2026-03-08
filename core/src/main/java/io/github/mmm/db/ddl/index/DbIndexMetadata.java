/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.ddl.index;

import io.github.mmm.db.ddl.table.DbTable;

/**
 * Interface for an index of a {@link DbTable}.
 */
public interface DbIndexMetadata extends DbIndex {

  /**
   * @return the optional filter condition or {@code null} if not present.
   */
  String getFilterCondition();

  /**
   * @return {@code true} if this object identifies table statistics that are returned in conjunction with a table's
   *         index descriptions (not a real index), {@code false} otherwise.
   */
  boolean isStatistics();

  /**
   * @return the cardinality of this index. If {@link #isStatistics() statistics} the cardinality is the number of rows
   *         in the table, otherwise it is the number of unique values in the index.
   */
  long getCardinality();

}
