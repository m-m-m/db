/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.ddl.column;

import io.github.mmm.base.sort.SortOrder;

/// Qualified reference to a [column][DbColumn].
public interface DbColumnReferenceWithSortOrder extends DbColumnReference {

  /// @return the [SortOrder] or `null` if undefined (sequence is not supported or index is statistic).
  SortOrder getSortOrder();

  /// @param columnReference the [DbColumnReference] to convert.
  /// @return the given [DbColumnReference] as [DbColumnReferenceWithSortOrder].
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
