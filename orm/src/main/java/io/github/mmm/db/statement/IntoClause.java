/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.statement;

import java.util.Objects;

import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.property.WritableProperty;

/// An `INTO`-[clause][DbClause] of a [DbStatement] such as
/// [INSERT][io.github.mmm.db.statement.insert.InsertStatement] or
/// [UPSERT][io.github.mmm.db.statement.upsert.UpsertStatement].
///
/// @param <E> type of the [entity][#getEntity()].
/// @param <V> type of the [ValuesClause].
/// @param <SELF> type of this class itself.
/// @since 1.0.0
public abstract class IntoClause<E extends EntityBean, V extends ValuesClause<E, ?>, SELF extends IntoClause<E, V, SELF>>
    extends AbstractEntityClause<E, E, SELF> implements ValuesFragment<E, V> {

  /// The constructor.
  ///
  /// @param aliasMap the [AliasMap].
  /// @param entity the [entity][#getEntity()] to operate on.
  /// @param entityName the [entity name][#getName()].
  protected IntoClause(AliasMap aliasMap, E entity, String entityName) {

    super(aliasMap, entity, entityName);
  }

  /// Sets all [properties][EntityBean#getProperties()] of the [entity][#getEntity()] that are not
  /// `null`.
  ///
  /// @return the [ValuesClause] for fluent API.
  public V valuesAll() {

    Objects.requireNonNull(this.entity);
    V values = null;
    for (WritableProperty<?> property : this.entity.getProperties()) {
      if (!property.isTransient()) {
        values = addValues(values, property);
      }
    }
    if (values == null) {
      throw new IllegalStateException("Entity must not be empty!");
    }
    return values;
  }

  private <T> V addValues(V values, WritableProperty<T> property) {

    T value = property.get();
    if (isIncludeNullValues() || (value != null)) {
      if (values == null) {
        values = value(property, value);
      } else {
        @SuppressWarnings("rawtypes")
        ValuesClause values2 = values.value(property, value);
        assert (values2 == values);
      }
    }
    return values;
  }

  /// @return `true` to include `null` values, `false` otherwise (to ignore them).
  protected boolean isIncludeNullValues() {

    return true;
  }

}
