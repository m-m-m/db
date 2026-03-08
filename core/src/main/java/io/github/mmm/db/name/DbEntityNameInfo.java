/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.name;

import io.github.mmm.db.ddl.column.DbColumnReference;
import io.github.mmm.db.ddl.table.DbTable;
import io.github.mmm.db.ddl.table.DbTableReference;
import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.property.ReadableProperty;

/**
 * Contains information to map {@link EntityBean} name a database {@link DbTable table}.
 *
 * @since 1.0.0
 */
public interface DbEntityNameInfo extends DbTableReference<EntityBean> {

  /**
   * @param property the {@link ReadableProperty} to get the database name information for. Must be a property of the
   *        {@link #getEntity() entity} of this {@link DbEntityNameInfo}.
   * @return the {@link DbColumnReference} to map the column name.
   */
  DbColumnReference getColumnInfo(ReadableProperty<?> property);

}
