/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.orm;

import io.github.mmm.bean.WritableBean;
import io.github.mmm.db.mapping.DbBeanMapper;
import io.github.mmm.db.mapping.DbMapper;
import io.github.mmm.db.mapping.impl.DbSegmentMapper;
import io.github.mmm.db.name.DbNamingStrategy;
import io.github.mmm.db.statement.select.SelectClause;
import io.github.mmm.entity.bean.typemapping.TypeMapping;
import io.github.mmm.property.ReadableProperty;
import io.github.mmm.property.WritableProperty;
import io.github.mmm.property.criteria.ProjectionProperty;

/// Interface for ORM (object/relational mapping).
///
/// @since 1.0.0
public interface Orm {

  /// @param <B> type of the [WritableBean] to map.
  /// @param bean the prototype of the [WritableBean] to map.
  /// @return the [DbBeanMapper] to map from the [WritableBean] to [io.github.mmm.db.result.DbResult]
  ///         and vice-versa.
  default <B extends WritableBean> DbBeanMapper<B> createBeanMapper(B bean) {

    return createBeanMapper(bean, bean.getProperties());
  }

  /// @param <B> type of the [WritableBean] to map.
  /// @param bean the prototype of the [WritableBean] to map.
  /// @param properties the [Iterable] with the explicit [properties][WritableProperty] to map.
  /// @return the [DbBeanMapper] to map from the [WritableBean] to [io.github.mmm.db.result.DbResult]
  ///         and vice-versa.
  <B extends WritableBean> DbBeanMapper<B> createBeanMapper(B bean, Iterable<? extends ReadableProperty<?>> properties);

  /// @param <B> type of the [WritableBean] to map.
  /// @param bean the prototype of the [WritableBean] to map.
  /// @param properties the [Iterable] with the [projection properties][ProjectionProperty] to map.
  /// @return the [DbBeanMapper] to map from the [WritableBean] to [io.github.mmm.db.result.DbResult]
  ///         and vice-versa.
  <B extends WritableBean> DbBeanMapper<B> createBeanMapperProjection(B bean,
      Iterable<? extends ProjectionProperty<?>> properties);

  /// @param <V> type of the [property value][ReadableProperty#get()].
  /// @param property the [ReadableProperty].
  /// @return the [DbSegmentMapper] to map the given [property][ReadableProperty].
  <V> DbSegmentMapper<V, ?> createSegmentMapper(ReadableProperty<V> property);

  /// @param <V> type of the Java value to select.
  /// @param select the [SelectClause] clause.
  /// @return the [DbMapper] to map from the Java object to the [io.github.mmm.db.result.DbResult] and
  ///         vice-versa.
  <V> DbMapper<V> createMapper(SelectClause<V> select);

  /// @return the [DbNamingStrategy].
  DbNamingStrategy getNamingStrategy();

  /// @return the [TypeMapping].
  TypeMapping getTypeMapping();
}
