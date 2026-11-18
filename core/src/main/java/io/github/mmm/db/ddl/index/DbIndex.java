/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.ddl.index;

import java.util.List;

import io.github.mmm.base.io.UncheckedAppendable;
import io.github.mmm.db.ddl.column.DbColumnReferenceWithSortOrder;
import io.github.mmm.db.ddl.table.DbTable;
import io.github.mmm.db.ddl.table.DbTableReference;
import io.github.mmm.db.name.DbKeyword;
import io.github.mmm.db.name.DbObject;
import io.github.mmm.db.name.DbQualifiedName;
import io.github.mmm.entity.bean.EntityBean;

/// Index on a [table][DbTable].
public interface DbIndex extends DbIndexKind, DbObject {

  /// @return the [table reference][DbTableReference].
  DbTableReference<? extends EntityBean> getTable();

  /// @return the [List] of indexed [columns][DbColumnReferenceWithSortOrder]. A simple index is only indexing
  ///         a single column. A composite index that indexes multiple columns.
  List<DbColumnReferenceWithSortOrder> getColumns();

  @Override
  default void toString(UncheckedAppendable sb, int mode) {

    boolean detailed = (mode == 0);
    if (detailed) {
      DbIndexKind.super.toString(sb, mode);
    } else {
      sb.append(DbKeyword.INDEX);
    }
    DbQualifiedName indexName = getRawName();
    if (indexName != null) {
      sb.append(' ');
      sb.append(indexName);
    }
    if (detailed) {
      sb.append(' ');
      DbObject.append(getColumns(), sb, mode);
    }
  }

}
