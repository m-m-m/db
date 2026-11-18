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

/// Reference to a [column][DbColumn].
public interface DbColumnReference extends DbObject {

  /// Empty [DbColumnReference] array.
  static final DbColumnReference[] NO_COLUMNS = new DbColumnReference[0];

  /// @return the name of the property.
  ReadableProperty<?> getProperty();

  /// @param order the [SortOrder].
  /// @return this column reference with the given [SortOrder] as [DbColumnReferenceWithSortOrder].
  default DbColumnReferenceWithSortOrder with(SortOrder order) {

    return new DbColumnReferenceWithSortOrderImpl(this, order);
  }

  @Override
  default void toString(UncheckedAppendable sb, int mode) {

    sb.append(getName());
  }

  /// @param name the [name][#getName()].
  /// @return the new [DbColumnReference].
  static DbColumnReference of(String name) {

    return of(name, null);
  }

  /// @param property the [property][#getProperty()].
  /// @return the new [DbColumnReference].
  static DbColumnReference of(ReadableProperty<?> property) {

    return of(null, property);
  }

  /// @param path the [PropertyPath].
  /// @return the new [DbColumnReference].
  static DbColumnReference of(PropertyPath<?> path) {

    if (path instanceof ReadableProperty<?> property) {
      return of(null, property);
    }
    return of(path.getName(), null);
  }

  /// @param name the [name][#getName()].
  /// @param property the [property][#getProperty()].
  /// @return the new [DbColumnReference].
  static DbColumnReference of(String name, ReadableProperty<?> property) {

    return new DbColumnReferenceImpl(name, property);
  }

}
