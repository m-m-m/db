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

/**
 * Interface for ORM (object/relational mapping).
 *
 * @since 1.0.0
 */
public interface Orm {

  /**
   * @param <B> type of the {@link WritableBean} to map.
   * @param bean the prototype of the {@link WritableBean} to map.
   * @return the {@link DbBeanMapper} to map from the {@link WritableBean} to {@link io.github.mmm.db.result.DbResult}
   *         and vice-versa.
   */
  default <B extends WritableBean> DbBeanMapper<B> createBeanMapper(B bean) {

    return createBeanMapper(bean, bean.getProperties());
  }

  /**
   * @param <B> type of the {@link WritableBean} to map.
   * @param bean the prototype of the {@link WritableBean} to map.
   * @param properties the {@link Iterable} with the explicit {@link WritableProperty properties} to map.
   * @return the {@link DbBeanMapper} to map from the {@link WritableBean} to {@link io.github.mmm.db.result.DbResult}
   *         and vice-versa.
   */
  <B extends WritableBean> DbBeanMapper<B> createBeanMapper(B bean, Iterable<? extends ReadableProperty<?>> properties);

  /**
   * @param <B> type of the {@link WritableBean} to map.
   * @param bean the prototype of the {@link WritableBean} to map.
   * @param properties the {@link Iterable} with the {@link ProjectionProperty projection properties} to map.
   * @return the {@link DbBeanMapper} to map from the {@link WritableBean} to {@link io.github.mmm.db.result.DbResult}
   *         and vice-versa.
   */
  <B extends WritableBean> DbBeanMapper<B> createBeanMapperProjection(B bean,
      Iterable<? extends ProjectionProperty<?>> properties);

  /**
   * @param <V> type of the {@link ReadableProperty#get() property value}.
   * @param property the {@link ReadableProperty}.
   * @return the {@link DbSegmentMapper} to map the given {@link ReadableProperty property}.
   */
  <V> DbSegmentMapper<V, ?> createSegmentMapper(ReadableProperty<V> property);

  /**
   * @param <V> type of the Java value to select.
   * @param select the {@link SelectClause} clause.
   * @return the {@link DbMapper} to map from the Java object to the {@link io.github.mmm.db.result.DbResult} and
   *         vice-versa.
   */
  <V> DbMapper<V> createMapper(SelectClause<V> select);

  /**
   * @return the {@link DbNamingStrategy}.
   */
  DbNamingStrategy getNamingStrategy();

  /**
   * @return the {@link TypeMapping}.
   */
  TypeMapping getTypeMapping();
}
