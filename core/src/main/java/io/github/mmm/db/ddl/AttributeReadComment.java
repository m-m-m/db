/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.ddl;

import io.github.mmm.db.ddl.column.DbColumn;
import io.github.mmm.db.ddl.table.DbTable;

/// Object having a [comment][#getComment()].
///
/// @see DbTable
/// @see DbColumn
public interface AttributeReadComment {

  /// @return the optional comment. Will be [empty][String#isEmpty()] if undefined but never `null`.
  String getComment();

}
