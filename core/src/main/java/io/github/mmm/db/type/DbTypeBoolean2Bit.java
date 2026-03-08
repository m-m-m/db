/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.type;

import java.sql.Types;
import java.util.Objects;

import io.github.mmm.db.name.DbKeyword;

/**
 * {@link DbType} for a regular {@link Boolean}.
 */
public final class DbTypeBoolean2Bit extends DbTypeSimpleBoolean {

  static final DbTypeBoolean2Bit INSTANCE = new DbTypeBoolean2Bit(null, null, null, null, false,
      DbTypeRegistration.NONE);

  private DbTypeBoolean2Bit(String name, Integer scale, String format, String suffix, boolean nativeTypeSupport,
      DbTypeRegistration registration) {

    super(name, 1, scale, format, suffix, nativeTypeSupport, registration);
  }

  @Override
  public int getCode() {

    return Types.BIT;
  }

  @Override
  String getDefaultName() {

    return DbKeyword.BIT;
  }

  @Override
  protected DbTypeBoolean2Bit create(String newName, Integer newSize, Integer newScale, String newFormat,
      String newSuffix, boolean newNativeTypeSupport) {

    assert (Objects.equals(newSize, 1));
    return new DbTypeBoolean2Bit(newName, newScale, newFormat, newSuffix, newNativeTypeSupport, null);
  }

}