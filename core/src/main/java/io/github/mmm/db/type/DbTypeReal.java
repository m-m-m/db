/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.type;

/**
 * {@link DbType} for a regular {@link Double}.
 */
public final class DbTypeReal extends DbType2DoubleReal<Double, DbTypeReal> {

  static final DbTypeReal INSTANCE = new DbTypeReal(null, null, null, null, null, false, DbTypeRegistration.CODE_NAME);

  DbTypeReal(String name, Integer size, Integer scale, String format, String suffix, boolean nativeTypeSupport,
      DbTypeRegistration registration) {

    super(name, size, scale, format, suffix, nativeTypeSupport, registration);
  }

  @Override
  public Class<Double> getSourceType() {

    return Double.class;
  }

  @Override
  public Double toTarget(Double source) {

    return source;
  }

  @Override
  public Double toSource(Double target) {

    return target;
  }

  @Override
  protected DbTypeReal create(String newName, Integer newSize, Integer newScale, String newFormat, String newSuffix,
      boolean newNativeTypeSupport) {

    return new DbTypeReal(newName, newSize, newScale, newFormat, newSuffix, newNativeTypeSupport, null);
  }

}
