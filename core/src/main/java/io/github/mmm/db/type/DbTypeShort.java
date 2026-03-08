/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.type;

/**
 * {@link DbType} for a regular {@link Short}.
 */
public final class DbTypeShort extends DbType2Short<Short, DbTypeShort> {

  static final DbTypeShort INSTANCE = new DbTypeShort(null, null, null, null, null, false, DbTypeRegistration.ALL);

  private DbTypeShort(String name, Integer size, Integer scale, String format, String suffix, boolean nativeTypeSupport,
      DbTypeRegistration registration) {

    super(name, size, scale, format, suffix, nativeTypeSupport, registration);
  }

  @Override
  public Class<Short> getSourceType() {

    return Short.class;
  }

  @Override
  public Short toTarget(Short source) {

    return source;
  }

  @Override
  public Short toSource(Short target) {

    return target;
  }

  @Override
  protected DbTypeShort create(String newName, Integer newSize, Integer newScale, String newFormat, String newSuffix,
      boolean newNativeTypeSupport) {

    return new DbTypeShort(newName, newSize, newScale, newFormat, newSuffix, newNativeTypeSupport, null);
  }

}
