/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.statement;

import io.github.mmm.db.statement.alter.AlterTableClause;
import io.github.mmm.db.statement.update.UpdateSetClause;
import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.property.criteria.PropertyAssignment;
import io.github.mmm.value.PropertyPath;

/// Interface for a fragment or clause to [set][#set(PropertyAssignment)] [assignments][PropertyAssignment].
///
/// @param <E> type of the [entity][AlterTableClause#getEntity()].
/// @param <C> type of [DbClause] to return for fluent API.
/// @since 1.0.0
public interface SetFragment<E extends EntityBean, C extends DbClause> {

  /// @param assignment the [PropertyAssignment] to add.
  /// @return the [DbClause] for fluent API calls.
  C set(PropertyAssignment<?> assignment);

  /// Convenience method for `[set][#set(PropertyAssignment)]([PropertyAssignment].[ofValue(property)][PropertyAssignment#ofValue(PropertyPath)]).`
  ///
  /// @param <V> type of the [value][PropertyPath#get()].
  /// @param property the [property][PropertyPath] to set.
  /// @return the [DbClause] for fluent API calls.
  default <V> C set(PropertyPath<V> property) {

    return set(PropertyAssignment.ofValue(property));
  }

  /// Convenience method for `[set][#set(PropertyAssignment)]([PropertyAssignment].[of(property, value)][PropertyAssignment#of(PropertyPath, Object)]).`
  ///
  /// @param <V> type of the [value][PropertyPath#get()].
  /// @param property the [property][PropertyPath] to set.
  /// @param value the [io.github.mmm.property.criteria.Literal] value to insert (assign the `property` to).
  /// @return the [DbClause] for fluent API calls.
  default <V> C set(PropertyPath<V> property, V value) {

    return set(PropertyAssignment.of(property, value));
  }

  /// Convenience method for `[set][#set(PropertyAssignment)]([PropertyAssignment].[of(property, valueProperty)][PropertyAssignment#of(PropertyPath,
  /// PropertyPath)]).`
  ///
  /// @param <V> type of the [value][PropertyPath#get()].
  /// @param property the [property][PropertyPath] to set.
  /// @param valueProperty the [property][PropertyPath] from where to read the value to set.
  /// @return the [UpdateSetClause] for fluent API.
  default <V> C set(PropertyPath<V> property, PropertyPath<V> valueProperty) {

    return set(PropertyAssignment.of(property, valueProperty));
  }

  /// @param assignments the [PropertyAssignment]s to add.
  /// @return the [DbClause] for fluent API calls.
  C setAll(PropertyAssignment<?>... assignments);

}
