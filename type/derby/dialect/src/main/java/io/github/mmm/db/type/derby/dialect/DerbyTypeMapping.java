/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.type.derby.dialect;

import io.github.mmm.db.typemapping.DbTypeMapping;

/**
 * {@link DbTypeMapping} for <a href="https://db.apache.org/derby/docs/10.13/ref/crefsqlj31068.html">Apache Derby
 * database</a>.
 *
 * @since 1.0.0
 */
public class DerbyTypeMapping extends DbTypeMapping {

  @Override
  protected int getMaxSizeBigDecimal() {

    return 100000;
  }

}
