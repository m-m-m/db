/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.type;

import java.util.Objects;

import io.github.mmm.db.name.DbKeyword;

/**
 * {@link DbType} that maps from {@link Boolean} to {@link Integer}.
 */
public final class DbTypeBoolean2Integer extends DbType2Integer<Boolean, DbTypeBoolean2Integer> {

  static final DbTypeBoolean2Integer INSTANCE = new DbTypeBoolean2Integer(DbKeyword.NUMBER, null, null,
      "CHECK (${COLUMN} IN (0,1))", false, DbTypeRegistration.NONE);

  private static final Integer TRUE = Integer.valueOf(1);

  private static final Integer FALSE = Integer.valueOf(0);

  DbTypeBoolean2Integer(String name, Integer scale, String format, String suffix, boolean nativeTypeSupport,
      DbTypeRegistration registration) {

    super(name, 1, scale, format, suffix, nativeTypeSupport, registration);
  }

  @Override
  public Class<Boolean> getSourceType() {

    return Boolean.class;
  }

  @Override
  public Integer toTarget(Boolean source) {

    if (source.booleanValue()) {
      return TRUE;
    } else {
      return FALSE;
    }
  }

  @Override
  public Boolean toSource(Integer target) {

    if (TRUE.equals(target)) {
      return Boolean.TRUE;
    } else if (FALSE.equals(target)) {
      return Boolean.FALSE;
    }
    throw new IllegalArgumentException(target.toString());
  }

  @Override
  protected DbTypeBoolean2Integer create(String newName, Integer newSize, Integer newScale, String newFormat,
      String newSuffix, boolean newNativeTypeSupport) {

    assert (Objects.equals(newSize, 1));
    return new DbTypeBoolean2Integer(newName, newScale, newFormat, newSuffix, newNativeTypeSupport, null);
  }
}