/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.type;

/**
 * {@link DbType} for a regular {@link Integer}.
 */
public final class DbTypeInteger extends DbType2Integer<Integer, DbTypeInteger> {

  static final DbTypeInteger INSTANCE = new DbTypeInteger(null, null, null, null, null, false, DbTypeRegistration.ALL);

  private DbTypeInteger(String name, Integer size, Integer scale, String format, String suffix,
      boolean nativeTypeSupport, DbTypeRegistration registration) {

    super(name, size, scale, format, suffix, nativeTypeSupport, registration);
  }

  @Override
  public Class<Integer> getSourceType() {

    return Integer.class;
  }

  @Override
  public Integer toSource(Integer target) {

    return target;
  }

  @Override
  public Integer toTarget(Integer source) {

    return source;
  }

  @Override
  protected DbTypeInteger create(String newName, Integer newSize, Integer newScale, String newFormat, String newSuffix,
      boolean newNativeTypeSupport) {

    return new DbTypeInteger(newName, newSize, newScale, newFormat, newSuffix, newNativeTypeSupport, null);
  }

}