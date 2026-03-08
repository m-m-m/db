/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.ddl;

import io.github.mmm.db.ddl.column.DbColumn;
import io.github.mmm.db.ddl.table.DbTable;

/**
 * Object having a {@link #getComment() comment}.
 *
 * @see DbTable
 * @see DbColumn
 */
public interface AttributeReadComment {

  /**
   * @return the optional comment. Will be {@link String#isEmpty() empty} if undefined but never {@code null}.
   */
  String getComment();

}
