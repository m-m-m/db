/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.type.sqlite.dialect;

import io.github.mmm.db.typemapping.DbTypeMapping;

/**
 * {@link DbTypeMapping} for SQLite database.
 *
 * @since 1.0.0
 */
public class SqliteTypeMapper extends DbTypeMapping {

  @Override
  protected int getMaxSizeBigDecimal() {

    return 1000;
  }

}
