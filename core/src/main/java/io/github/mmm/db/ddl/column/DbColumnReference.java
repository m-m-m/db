/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.ddl.column;

import io.github.mmm.base.io.UncheckedAppendable;
import io.github.mmm.base.sort.SortOrder;
import io.github.mmm.db.ddl.column.impl.DbColumnReferenceImpl;
import io.github.mmm.db.ddl.column.impl.DbColumnReferenceWithSortOrderImpl;
import io.github.mmm.db.name.DbObject;
import io.github.mmm.property.ReadableProperty;
import io.github.mmm.value.PropertyPath;

/**
 * Reference to a {@link DbColumn column}.
 */
public interface DbColumnReference extends DbObject {

  /** Empty {@link DbColumnReference} array. */
  static final DbColumnReference[] NO_COLUMNS = new DbColumnReference[0];

  /**
   * @return the name of the property.
   */
  ReadableProperty<?> getProperty();

  /**
   * @param order the {@link SortOrder}.
   * @return this column reference with the given {@link SortOrder} as {@link DbColumnReferenceWithSortOrder}.
   */
  default DbColumnReferenceWithSortOrder with(SortOrder order) {

    return new DbColumnReferenceWithSortOrderImpl(this, order);
  }

  @Override
  default void toString(UncheckedAppendable sb, int mode) {

    sb.append(getName());
  }

  /**
   * @param name the {@link #getName() name}.
   * @return the new {@link DbColumnReference}.
   */
  static DbColumnReference of(String name) {

    return of(name, null);
  }

  /**
   * @param property the {@link #getProperty() property}.
   * @return the new {@link DbColumnReference}.
   */
  static DbColumnReference of(ReadableProperty<?> property) {

    return of(null, property);
  }

  /**
   * @param path the {@link PropertyPath}.
   * @return the new {@link DbColumnReference}.
   */
  static DbColumnReference of(PropertyPath<?> path) {

    if (path instanceof ReadableProperty<?> property) {
      return of(null, property);
    }
    return of(path.getName(), null);
  }

  /**
   * @param name the {@link #getName() name}.
   * @param property the {@link #getProperty() property}.
   * @return the new {@link DbColumnReference}.
   */
  static DbColumnReference of(String name, ReadableProperty<?> property) {

    return new DbColumnReferenceImpl(name, property);
  }

}
