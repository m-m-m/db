/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.ddl.column;

import io.github.mmm.base.sort.SortOrder;

/**
 * Qualified reference to a {@link DbColumn column}.
 */
public interface DbColumnReferenceWithSortOrder extends DbColumnReference {

  /**
   * @return the {@link SortOrder} or {@code null} if undefined (sequence is not supported or index is statistic).
   */
  SortOrder getSortOrder();

  /**
   * @param columnReference the {@link DbColumnReference} to convert.
   * @return the given {@link DbColumnReference} as {@link DbColumnReferenceWithSortOrder}.
   */
  static DbColumnReferenceWithSortOrder of(DbColumnReference columnReference) {

    if (columnReference == null) {
      return null;
    }
    if (columnReference instanceof DbColumnReferenceWithSortOrder columnWithSort) {
      return columnWithSort;
    }
    return columnReference.with(null);
  }

}
