/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.name;

import io.github.mmm.db.ddl.column.DbColumnReference;
import io.github.mmm.db.name.impl.DbEntityNameMapperImpl;
import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.property.ReadableProperty;

/// Allows to get [DbEntityNameInfo] for [EntityBean].
///
/// @since 1.0.0
public interface DbEntityNameMapper {

  /// @param bean the [EntityBean].
  /// @return the [DbEntityNameInfo] for the given [EntityBean].
  DbEntityNameInfo getTable(EntityBean bean);

  /// @param property the [ReadableProperty].
  /// @return the [DbColumnReference] for the given [ReadableProperty].
  DbColumnReference getColumn(ReadableProperty<?> property);

  /// @return the instance of [DbEntityNameMapper].
  static DbEntityNameMapper get() {

    return DbEntityNameMapperImpl.INSTANCE;
  }
}
