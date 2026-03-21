/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.ddl.index;

import io.github.mmm.base.io.UncheckedAppendable;
import io.github.mmm.base.lang.ToString;
import io.github.mmm.db.name.DbKeyword;

/**
 * Interface for the kind of an index with its {@link #getType() type} and flags such as {@link #isUnique() unique} and
 * {@link #isClustered() clustered}.
 */
public interface DbIndexKind extends ToString {

  /**
   * @return {@code true} if index values must be unique, {@code false} otherwise. Please note that in JDBC a NON_UNIQUE
   *         column is defined, the method gets the inverse of that result.
   */
  boolean isUnique();

  /**
   * @return {@code true} if the index is
   *         <a href="https://en.wikipedia.org/wiki/Database_index#Clustered">clustered</a>, {@code false} otherwise.
   *         Please note that not all databases support clustered indexes including famous products such as postgresql.
   *         If the database does not support this, it will simply be ignored.
   */
  boolean isClustered();

  /**
   * @return the {@link DbIndexType}. Returns {@link DbIndexType#NONE} for no special type but never {@code null}.
   */
  DbIndexType getType();

  @Override
  default void toString(UncheckedAppendable sb, int mode) {

    if (isUnique()) {
      sb.append(DbKeyword.UNIQUE);
      sb.append(" ");
    }
    if (isClustered()) {
      sb.append(DbKeyword.CLUSTERED);
      sb.append(" ");
    }
    DbIndexType type = getType();
    if (type != DbIndexType.NONE) {
      sb.append(type);
      sb.append(' ');
    }
    sb.append(DbKeyword.INDEX);
  }

}
