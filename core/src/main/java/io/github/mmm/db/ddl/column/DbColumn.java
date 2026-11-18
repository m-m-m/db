/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.ddl.column;

import io.github.mmm.db.ddl.AttributeReadComment;
import io.github.mmm.db.ddl.column.impl.DbColumnImpl;
import io.github.mmm.db.type.DbType;
import io.github.mmm.property.ReadableProperty;

/// Meta-information about a column of a [table][io.github.mmm.db.ddl.table.DbTable].
///
/// @since 1.0.0
public interface DbColumn extends DbColumnReference, AttributeReadComment {

  /// @return a [Boolean#TRUE] if nullable, [Boolean#FALSE] if value cannot be `null`, and `null` if unknown.
  Boolean getNullable();

  /// @return the [DbType] of this column.
  DbType<?, ?, ?> getType();

  /// @param name the [name][#getName()].
  /// @param comment the optional [comment][#getComment()].
  /// @param nullable the optional [nullable flag][#getNullable()].
  /// @param type the [type][#getType()].
  /// @return the new [DbColumn].
  static DbColumn of(String name, String comment, Boolean nullable, DbType<?, ?, ?> type) {

    return of(name, null, comment, nullable, type);
  }

  /// @param name the [name][#getName()].
  /// @param comment the optional [comment][#getComment()].
  /// @param type the [type][#getType()].
  /// @return the new [DbColumn].
  static DbColumn of(String name, String comment, DbType<?, ?, ?> type) {

    return of(name, null, comment, null, type);
  }

  /// @param name the [name][#getName()].
  /// @param type the [type][#getType()].
  /// @return the new [DbColumn].
  static DbColumn of(String name, DbType<?, ?, ?> type) {

    return of(name, null, null, null, type);
  }

  /// @param property the [property][#getProperty()].
  /// @param comment the optional [comment][#getComment()].
  /// @param nullable the optional [nullable flag][#getNullable()].
  /// @param type the [type][#getType()].
  /// @return the new [DbColumn].
  static DbColumn of(ReadableProperty<?> property, String comment, Boolean nullable, DbType<?, ?, ?> type) {

    return new DbColumnImpl(null, property, comment, nullable, type);
  }

  /// @param property the [property][#getProperty()].
  /// @param comment the optional [comment][#getComment()].
  /// @param type the [type][#getType()].
  /// @return the new [DbColumn].
  static DbColumn of(ReadableProperty<?> property, String comment, DbType<?, ?, ?> type) {

    return new DbColumnImpl(null, property, comment, null, type);
  }

  /// @param property the [property][#getProperty()].
  /// @param type the [type][#getType()].
  /// @return the new [DbColumn].
  static DbColumn of(ReadableProperty<?> property, DbType<?, ?, ?> type) {

    return new DbColumnImpl(null, property, null, null, type);
  }

  /// @param name the [name][#getName()].
  /// @param property the [property][#getProperty()].
  /// @param comment the optional [comment][#getComment()].
  /// @param nullable the optional [nullable flag][#getNullable()].
  /// @param type the [type][#getType()].
  /// @return the new [DbColumn].
  static DbColumn of(String name, ReadableProperty<?> property, String comment, Boolean nullable, DbType<?, ?, ?> type) {

    return new DbColumnImpl(name, property, comment, nullable, type);
  }

}
