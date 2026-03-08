/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.ddl.column.impl;

import java.util.Objects;

import io.github.mmm.base.io.UncheckedAppendable;
import io.github.mmm.db.ddl.column.DbColumn;
import io.github.mmm.db.name.DbKeyword;
import io.github.mmm.db.type.DbType;
import io.github.mmm.property.ReadableProperty;

/**
 * Implementation of {@link DbColumn}.
 *
 * @since 1.0.0
 */
public class DbColumnImpl extends DbColumnReferenceImpl implements DbColumn {

  private final String comment;

  private final Boolean nullable;

  private final DbType<?, ?, ?> type;

  /**
   * The constructor.
   *
   * @param name the {@link #getName() name}.
   * @param comment the {@link #getComment() comment}.
   * @param nullable the {@link #getNullable() nullable} flag.
   * @param type the {@link #getType() type}.
   */
  public DbColumnImpl(String name, String comment, Boolean nullable, DbType<?, ?, ?> type) {

    this(name, null, comment, nullable, type);
  }

  /**
   * The constructor.
   *
   * @param name the {@link #getName() name}.
   * @param property the {@link #getProperty() property}.
   * @param comment the {@link #getComment() comment}.
   * @param nullable the {@link #getNullable() nullable} flag.
   * @param type the {@link #getType() type}.
   */
  public DbColumnImpl(String name, ReadableProperty<?> property, String comment, Boolean nullable,
      DbType<?, ?, ?> type) {

    super(name, property);
    Objects.requireNonNull(type, "type");
    this.comment = comment;
    this.nullable = nullable;
    this.type = type;
  }

  @Override
  public String getComment() {

    return this.comment;
  }

  @Override
  public Boolean getNullable() {

    return this.nullable;
  }

  @Override
  public DbType<?, ?, ?> getType() {

    return this.type;
  }

  @Override
  public void toString(UncheckedAppendable sb, int mode) {

    sb.append(this.name);
    if (mode != 0) {
      sb.append(' ');
      sb.append(this.type.toString());

      if (Boolean.TRUE.equals(this.nullable)) {
        sb.append(' ');
        sb.append(DbKeyword.NULLABLE);
      } else if (Boolean.FALSE.equals(this.nullable)) {
        sb.append(' ');
        sb.append(DbKeyword.NOT_NULL);
      }
    }
  }

}
