/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.name;

import io.github.mmm.db.ddl.column.DbColumnReference;
import io.github.mmm.db.ddl.table.DbTable;
import io.github.mmm.db.ddl.table.DbTableReference;
import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.property.ReadableProperty;

/// Contains information to map [EntityBean] name a database [table][DbTable].
///
/// @since 1.0.0
public interface DbEntityNameInfo extends DbTableReference<EntityBean> {

  /// @param property the [ReadableProperty] to get the database name information for. Must be a property of the
  ///        [entity][#getEntity()] of this [DbEntityNameInfo].
  /// @return the [DbColumnReference] to map the column name.
  DbColumnReference getColumnInfo(ReadableProperty<?> property);

}
