/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.ddl.table;

import io.github.mmm.base.io.UncheckedAppendable;
import io.github.mmm.db.ddl.AttributeReadComment;
import io.github.mmm.entity.bean.EntityBean;

/**
 * Meta-information about a database table (or view, materialized view, etc.).
 *
 * @since 1.0.0
 */
public interface DbTableData extends DbTableReference<EntityBean>, AttributeReadComment {

  /**
   * @return the {@link DbTableType} such as {@link DbTableType#TABLE}, {@link DbTableType#VIEW}, etc.
   */
  DbTableType getType();

  @Override
  default void toString(UncheckedAppendable sb, int mode) {

    sb.append(getType());
    sb.append(' ');
    DbTableReference.super.toString(sb, mode);
    String comment = getComment();
    if ((mode != 1) && !comment.isEmpty()) {
      sb.append('(');
      sb.append(comment);
      sb.append(')');
    }
  }

}
