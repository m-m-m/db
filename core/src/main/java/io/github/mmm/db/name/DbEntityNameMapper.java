/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.name;

import io.github.mmm.db.ddl.column.DbColumnReference;
import io.github.mmm.db.name.impl.DbEntityNameMapperImpl;
import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.property.ReadableProperty;

/**
 * Allows to get {@link DbEntityNameInfo} for {@link EntityBean}.
 *
 * @since 1.0.0
 */
public interface DbEntityNameMapper {

  /**
   * @param bean the {@link EntityBean}.
   * @return the {@link DbEntityNameInfo} for the given {@link EntityBean}.
   */
  DbEntityNameInfo getTable(EntityBean bean);

  /**
   * @param property the {@link ReadableProperty}.
   * @return the {@link DbColumnReference} for the given {@link ReadableProperty}.
   */
  DbColumnReference getColumn(ReadableProperty<?> property);

  /**
   * @return the instance of {@link DbEntityNameMapper}.
   */
  static DbEntityNameMapper get() {

    return DbEntityNameMapperImpl.INSTANCE;
  }
}
