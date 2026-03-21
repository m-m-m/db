/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.type;

/**
 * {@link DbType} for a regular {@link Long}.
 */
public final class DbTypeLong extends DbType2Long<Long, DbTypeLong> {

  static final DbTypeLong INSTANCE = new DbTypeLong(null, null, null, null, null, false, DbTypeRegistration.ALL);

  private DbTypeLong(String name, Integer size, Integer scale, String format, String suffix, boolean nativeTypeSupport,
      DbTypeRegistration registration) {

    super(name, size, scale, format, suffix, nativeTypeSupport, registration);
  }

  @Override
  public Class<Long> getSourceType() {

    return Long.class;
  }

  @Override
  public Long toTarget(Long source) {

    return source;
  }

  @Override
  public Long toSource(Long target) {

    return target;
  }

  @Override
  protected DbTypeLong create(String newName, Integer newSize, Integer newScale, String newFormat, String newSuffix,
      boolean newNativeTypeSupport) {

    return new DbTypeLong(newName, newSize, newScale, newFormat, newSuffix, newNativeTypeSupport, null);
  }

}
