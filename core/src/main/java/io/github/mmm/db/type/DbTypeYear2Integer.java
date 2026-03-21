/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.type;

import java.time.Year;

/**
 * {@link DbType} that maps from {@link Year} to {@link Integer}.
 */
public final class DbTypeYear2Integer extends DbType2Integer<Year, DbTypeYear2Integer> {

  static final DbTypeYear2Integer INSTANCE = new DbTypeYear2Integer(null, null, null, null, null, false, DbTypeRegistration.TYPE);

  private DbTypeYear2Integer(String name, Integer size, Integer scale, String format, String suffix, boolean nativeTypeSupport,
      DbTypeRegistration registration) {

    super(name, size, scale, format, suffix, nativeTypeSupport, registration);
  }

  @Override
  public Class<Year> getSourceType() {

    return Year.class;
  }

  @Override
  public Integer toTarget(Year source) {

    return Integer.valueOf(source.getValue());
  }

  @Override
  public Year toSource(Integer target) {

    return Year.of(target.intValue());
  }

  @Override
  protected DbTypeYear2Integer create(String newName, Integer newSize, Integer newScale, String newFormat, String newSuffix,
      boolean newNativeTypeSupport) {

    return new DbTypeYear2Integer(newName, newSize, newScale, newFormat, newSuffix, newNativeTypeSupport, null);
  }
}