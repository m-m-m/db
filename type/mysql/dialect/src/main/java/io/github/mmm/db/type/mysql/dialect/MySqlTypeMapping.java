/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.type.mysql.dialect;

import io.github.mmm.db.typemapping.DbTypeMapping;

/**
 * {@link DbTypeMapping} for MySQL database.
 *
 * @since 1.0.0
 */
public class MySqlTypeMapping extends DbTypeMapping {

  @Override
  protected int getMaxSizeBigDecimal() {

    return 65;
  }

}
