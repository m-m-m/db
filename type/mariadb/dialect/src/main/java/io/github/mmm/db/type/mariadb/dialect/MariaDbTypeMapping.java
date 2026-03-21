/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.type.mariadb.dialect;

import io.github.mmm.db.typemapping.DbTypeMapping;

/**
 * {@link DbTypeMapping} for MariaDB.
 *
 * @since 1.0.0
 */
public class MariaDbTypeMapping extends DbTypeMapping {

  @Override
  protected int getMaxSizeBigDecimal() {

    return 65;
  }

}
