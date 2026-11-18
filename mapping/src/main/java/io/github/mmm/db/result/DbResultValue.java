/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.result;

import io.github.mmm.value.converter.TypeMapper;

/// A single value [value][#getValue()] with [name][#getName()] of a [DbResult].
///
/// @param <V> type of the [value][#getValue()].
public interface DbResultValue<V> {

  /// @return the database name of this entry. That is the name of the column, the explicit alias, or a [String]
  ///         representation derived from the [selection criteria][io.github.mmm.value.CriteriaObject].
  String getName();

  /// @return the optional [database declaration][TypeMapper#getDeclaration()].
  default String getDeclaration() {

    return "";
  }

  /// @return the result value from the database. May be `null`.
  V getValue();

  /// @param newValue the new [value][#getValue()].
  /// @return a new instance of [DbResultValue] with the same [name][#getName()] and [declaration][#getDeclaration()] but the given `value` as [value][#getValue()].
  DbResultValue<V> withValue(V newValue);

}