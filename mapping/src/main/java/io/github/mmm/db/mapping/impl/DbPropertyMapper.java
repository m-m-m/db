/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.mapping.impl;

import java.util.Iterator;

import io.github.mmm.bean.ReadableBean;
import io.github.mmm.bean.WritableBean;
import io.github.mmm.db.result.DbResultValue;
import io.github.mmm.db.result.impl.DbResultPojo;
import io.github.mmm.property.ReadableProperty;
import io.github.mmm.property.WritableProperty;

/// Interface to map a [property][WritableProperty] to the database and vice versa.
///
/// @param <V> type of the [value][WritableProperty#get()] to map.
/// @since 1.0.0
public interface DbPropertyMapper<V> {

  /// @return the [property name][ReadableProperty#getName()].
  String getPropertyName();

  /// @param javaBean the [ReadableBean] to map.
  /// @param dbResult the [DbResultPojo] where to [add][DbResultPojo#add(io.github.mmm.db.result.DbResultValue)] the collected
  ///        [database values][io.github.mmm.db.result.DbResultValue].
  void java2db(ReadableBean javaBean, DbResultPojo dbResult);

  /// @param javaProperty the [ReadableProperty] to map.
  /// @param dbResult the [DbResultPojo] where to [add][DbResultPojo#add(io.github.mmm.db.result.DbResultValue)] the collected
  ///        [database values][io.github.mmm.db.result.DbResultValue].
  default void java2db(ReadableProperty<V> javaProperty, DbResultPojo dbResult) {

    V value = null;
    if (javaProperty != null) {
      javaProperty.get();
    }
    java2dbValue(value, dbResult);
  }

  /// @param javaValue the Java value to map.
  /// @param dbResult the [DbResultPojo] where to [add][DbResultPojo#add(io.github.mmm.db.result.DbResultValue)] the collected
  ///        [database values][io.github.mmm.db.result.DbResultValue].
  void java2dbValue(V javaValue, DbResultPojo dbResult);

  /// @param dbIterator the [Iterator] of the [database values][DbResultValue] to convert to Java.
  /// @param javaBean the [WritableBean] where to map the [DbResultValue]s to.
  void db2java(Iterator<DbResultValue<?>> dbIterator, WritableBean javaBean);

}
