/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.ddl.table;

import java.util.Objects;

import io.github.mmm.base.io.UncheckedAppendable;
import io.github.mmm.bean.ReadableBean;
import io.github.mmm.db.ddl.table.impl.DbTableReferenceImpl;
import io.github.mmm.db.name.DbObject;
import io.github.mmm.db.name.DbQualifiedName;
import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.property.ReadableProperty;

/// Reference to a [database table][DbTable] (or view, materialized view, etc.).
///
/// @param <E> type of the referenced [EntityBean].
/// @since 1.0.0
public interface DbTableReference<E extends EntityBean> extends DbObject {

  /// @return the optional [EntityBean] from where to get the data and to operate on.
  E getEntity();

  @Override
  default void toString(UncheckedAppendable sb, int mode) {

    sb.append(getName());
  }

  /// @param property the [ReadableProperty] of an [EntityBean].
  /// @return the new [DbTableReference].
  static DbTableReference<EntityBean> of(ReadableProperty<?> property) {

    Objects.requireNonNull(property);
    ReadableBean bean = ReadableBean.from(property);
    if (bean instanceof EntityBean entity) {
      return of(entity);
    }
    throw new IllegalArgumentException("Property " + property.getClass().getSimpleName() + "[" + property.toString()
        + "] is from bean " + bean.getType() + " that is not an EntityBean.");
  }

  /// @param <E> type of the [entity][#getEntity()].
  /// @param entity the [entity][#getEntity()].
  /// @return the new [DbTableReference].
  static <E extends EntityBean> DbTableReference<E> of(E entity) {

    return new DbTableReferenceImpl<>(null, entity);
  }

  /// @param <E> type of the [entity][#getEntity()].
  /// @param name the unqualified [name][#getName()].
  /// @param entity the [entity][#getEntity()].
  /// @return the new [DbTableReference].
  static <E extends EntityBean> DbTableReference<E> of(String name, E entity) {

    return of(DbQualifiedName.ofParsed(name), entity);
  }

  /// @param <E> type of the [entity][#getEntity()].
  /// @param name the [qualified name][#getName()].
  /// @param entity the [entity][#getEntity()].
  /// @return the new [DbTableReference].
  static <E extends EntityBean> DbTableReference<E> of(DbQualifiedName name, E entity) {

    return new DbTableReferenceImpl<>(name, entity);
  }

}
