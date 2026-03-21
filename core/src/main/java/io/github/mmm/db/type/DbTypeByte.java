/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.type;

/**
 * {@link DbType} for a regular {@link Byte}.
 */
public final class DbTypeByte extends DbType2Byte<Byte, DbTypeByte> {

  static final DbTypeByte INSTANCE = new DbTypeByte(null, null, null, null, null, false, DbTypeRegistration.ALL);

  private DbTypeByte(String name, Integer size, Integer scale, String format, String suffix, boolean nativeTypeSupport,
      DbTypeRegistration registration) {

    super(name, size, scale, format, suffix, nativeTypeSupport, registration);
  }

  @Override
  public Class<Byte> getSourceType() {

    return Byte.class;
  }

  @Override
  public Byte toTarget(Byte source) {

    return source;
  }

  @Override
  public Byte toSource(Byte target) {

    return target;
  }

  @Override
  protected DbTypeByte create(String newName, Integer newSize, Integer newScale, String newFormat, String newSuffix,
      boolean newNativeTypeSupport) {

    return new DbTypeByte(newName, newSize, newScale, newFormat, newSuffix, newNativeTypeSupport, null);
  }

}
