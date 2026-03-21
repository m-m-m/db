/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.type;

import java.sql.Array;

/**
 * {@link DbType} for a regular {@link Array}.
 */
@SuppressWarnings("exports")
public final class DbTypeArray extends DbType2Array<Array, DbTypeArray> {

  static final DbTypeArray INSTANCE = new DbTypeArray(null, null, null, null, null, false, DbTypeRegistration.ALL);

  private DbTypeArray(String name, Integer size, Integer scale, String format, String suffix, boolean nativeTypeSupport,
      DbTypeRegistration registration) {

    super(name, size, scale, format, suffix, nativeTypeSupport, registration);
  }

  @Override
  public Class<Array> getSourceType() {

    return Array.class;
  }

  @Override
  public Array toTarget(Array source) {

    return source;
  }

  @Override
  public Array toSource(Array target) {

    return target;
  }

  @Override
  protected DbTypeArray create(String newName, Integer newSize, Integer newScale, String newFormat, String newSuffix,
      boolean newNativeTypeSupport) {

    return new DbTypeArray(newName, newSize, newScale, newFormat, newSuffix, newNativeTypeSupport, null);
  }

}
