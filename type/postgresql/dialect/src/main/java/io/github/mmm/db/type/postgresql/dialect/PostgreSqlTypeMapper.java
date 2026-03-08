/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.type.postgresql.dialect;

import io.github.mmm.db.typemapping.DbTypeMapping;

/**
 * {@link DbTypeMapping} for <a href="https://www.postgresql.org/docs/current/datatype.html">PostgreSQL</a>.
 *
 * @since 1.0.0
 */
public class PostgreSqlTypeMapper extends DbTypeMapping {

  @Override
  protected int getMaxSizeBigDecimal() {

    return 131072;
  }

  @Override
  protected int getMaxScaleBigDecimal() {

    return 16383;
  }

}
