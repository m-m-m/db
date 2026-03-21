/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.ddl.column.impl;

import io.github.mmm.base.io.UncheckedAppendable;
import io.github.mmm.base.sort.SortOrder;
import io.github.mmm.db.ddl.column.DbColumnReference;
import io.github.mmm.db.ddl.column.DbColumnReferenceWithSortOrder;
import io.github.mmm.property.ReadableProperty;

/**
 * Implementation of {@link DbColumnReferenceWithSortOrder}.
 *
 * @since 1.0.0
 */
public class DbColumnReferenceWithSortOrderImpl extends DbColumnReferenceImpl
    implements DbColumnReferenceWithSortOrder {

  private final SortOrder sortOrder;

  /**
   * The constructor.
   *
   * @param columnName the {@link #getName() column name}.
   * @param sortOrder the {@link #getSortOrder() sort order}.
   */
  public DbColumnReferenceWithSortOrderImpl(String columnName, SortOrder sortOrder) {

    super(columnName);
    this.sortOrder = sortOrder;
  }

  /**
   * The constructor.
   *
   * @param property the {@link #getProperty() property}.
   * @param sortOrder the {@link #getSortOrder() sort order}.
   */
  public DbColumnReferenceWithSortOrderImpl(ReadableProperty<?> property, SortOrder sortOrder) {

    super(property);
    this.sortOrder = sortOrder;
  }

  /**
   * The constructor.
   *
   * @param reference the {@link DbColumnReference} to copy.
   * @param sortOrder the {@link #getSortOrder() sort order}.
   */
  public DbColumnReferenceWithSortOrderImpl(DbColumnReference reference, SortOrder sortOrder) {

    super(reference);
    this.sortOrder = sortOrder;
  }

  @Override
  public SortOrder getSortOrder() {

    return this.sortOrder;
  }

  @Override
  public DbColumnReferenceWithSortOrder with(SortOrder order) {

    if (this.sortOrder == order) {
      return this;
    }
    return super.with(order);
  }

  @Override
  public void toString(UncheckedAppendable sb, int mode) {

    super.toString(sb, mode);
    if (this.sortOrder != null) {
      sb.append(' ');
      sb.append(this.sortOrder);
    }
  }

}
