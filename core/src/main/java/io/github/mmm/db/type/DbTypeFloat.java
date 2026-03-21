/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.type;

/**
 * {@link DbType} for a regular {@link Float}.
 */
public final class DbTypeFloat extends DbType2Float<Float, DbTypeFloat> {

  static final DbTypeFloat INSTANCE = new DbTypeFloat(null, null, null, null, null, false, DbTypeRegistration.ALL);

  private DbTypeFloat(String name, Integer size, Integer scale, String format, String suffix, boolean nativeTypeSupport,
      DbTypeRegistration registration) {

    super(name, size, scale, format, suffix, nativeTypeSupport, registration);
  }

  @Override
  public Class<Float> getSourceType() {

    return Float.class;
  }

  @Override
  public Float toTarget(Float source) {

    return source;
  }

  @Override
  public Float toSource(Float target) {

    return target;
  }

  @Override
  protected DbTypeFloat create(String newName, Integer newSize, Integer newScale, String newFormat, String newSuffix,
      boolean newNativeTypeSupport) {

    return new DbTypeFloat(newName, newSize, newScale, newFormat, newSuffix, newNativeTypeSupport, null);
  }

}
