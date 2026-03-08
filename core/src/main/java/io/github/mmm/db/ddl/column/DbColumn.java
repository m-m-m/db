/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.ddl.column;

import io.github.mmm.db.ddl.AttributeReadComment;
import io.github.mmm.db.ddl.column.impl.DbColumnImpl;
import io.github.mmm.db.ddl.table.DbTable;
import io.github.mmm.db.type.DbType;
import io.github.mmm.property.ReadableProperty;

/**
 * Meta-information about a column of a {@link DbTable table}.
 *
 * @since 1.0.0
 */
public interface DbColumn extends DbColumnReference, AttributeReadComment {

  /**
   * @return {@link Boolean#TRUE} if nullable, {@link Boolean#FALSE} if value cannot be {@code null}, and {@code null}
   *         if unknown.
   */
  Boolean getNullable();

  /**
   * @return the {@link DbType} of this column.
   */
  DbType<?, ?, ?> getType();

  /**
   * @param name the {@link #getName() name}.
   * @param comment the optional {@link #getComment() comment}.
   * @param nullable the optional {@link #getNullable() nullable flag}.
   * @param type the {@link #getType() type}.
   * @return the new {@link DbColumn}.
   */
  static DbColumn of(String name, String comment, Boolean nullable, DbType<?, ?, ?> type) {

    return of(name, null, comment, nullable, type);
  }

  /**
   * @param name the {@link #getName() name}.
   * @param comment the optional {@link #getComment() comment}.
   * @param type the {@link #getType() type}.
   * @return the new {@link DbColumn}.
   */
  static DbColumn of(String name, String comment, DbType<?, ?, ?> type) {

    return of(name, null, comment, null, type);
  }

  /**
   * @param name the {@link #getName() name}.
   * @param type the {@link #getType() type}.
   * @return the new {@link DbColumn}.
   */
  static DbColumn of(String name, DbType<?, ?, ?> type) {

    return of(name, null, null, null, type);
  }

  /**
   * @param property the {@link #getProperty() property}.
   * @param comment the optional {@link #getComment() comment}.
   * @param nullable the optional {@link #getNullable() nullable flag}.
   * @param type the {@link #getType() type}.
   * @return the new {@link DbColumn}.
   */
  static DbColumn of(ReadableProperty<?> property, String comment, Boolean nullable, DbType<?, ?, ?> type) {

    return new DbColumnImpl(null, property, comment, nullable, type);
  }

  /**
   * @param property the {@link #getProperty() property}.
   * @param comment the optional {@link #getComment() comment}.
   * @param type the {@link #getType() type}.
   * @return the new {@link DbColumn}.
   */
  static DbColumn of(ReadableProperty<?> property, String comment, DbType<?, ?, ?> type) {

    return new DbColumnImpl(null, property, comment, null, type);
  }

  /**
   * @param property the {@link #getProperty() property}.
   * @param type the {@link #getType() type}.
   * @return the new {@link DbColumn}.
   */
  static DbColumn of(ReadableProperty<?> property, DbType<?, ?, ?> type) {

    return new DbColumnImpl(null, property, null, null, type);
  }

  /**
   * @param name the {@link #getName() name}.
   * @param property the {@link #getProperty() property}.
   * @param comment the optional {@link #getComment() comment}.
   * @param nullable the optional {@link #getNullable() nullable flag}.
   * @param type the {@link #getType() type}.
   * @return the new {@link DbColumn}.
   */
  static DbColumn of(String name, ReadableProperty<?> property, String comment, Boolean nullable,
      DbType<?, ?, ?> type) {

    return new DbColumnImpl(name, property, comment, nullable, type);
  }

}
