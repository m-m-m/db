/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.type.h2.dialect;

import io.github.mmm.db.typemapping.DbTypeMapping;

/**
 * {@link DbTypeMapping} for <a href="https://www.h2database.com/html/datatypes.html">H2 database</a>.
 *
 * @since 1.0.0
 */
public class H2TypeMapping extends DbTypeMapping {

  /**
   * The constructor.
   */
  public H2TypeMapping() {

    super();
    // add(new DbTypeCharacter("CHAR"));
    // add(new DbTypeUuid("UUID"));
  }

  @Override
  protected int getMaxSizeBigDecimal() {

    return 100000;
  }

}
