/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.type.sqlserver.dialect;

import io.github.mmm.db.typemapping.DbTypeMapping;

/**
 * {@link DbTypeMapping} for <a href="https://docs.microsoft.com/en-us/sql/t-sql/data-types/data-types-transact-sql">MS
 * SQL Server</a>.
 *
 * @since 1.0.0
 */
public class SqlServerTypeMapper extends DbTypeMapping {

  @Override
  protected int getMaxSizeTemporal() {

    return 7;
  }

}
