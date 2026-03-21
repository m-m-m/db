/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.type;

/**
 * {@link DbType} for a regular {@link Boolean}.
 */
public final class DbTypeBoolean extends DbTypeSimpleBoolean {

  static final DbTypeBoolean INSTANCE = new DbTypeBoolean(null, null, null, null, null, false, DbTypeRegistration.ALL);

  private DbTypeBoolean(String name, Integer size, Integer scale, String format, String suffix,
      boolean nativeTypeSupport, DbTypeRegistration registration) {

    super(name, size, scale, format, suffix, nativeTypeSupport, registration);
  }

  @Override
  protected DbTypeBoolean create(String newName, Integer newSize, Integer newScale, String newFormat, String newSuffix,
      boolean newNativeTypeSupport) {

    return new DbTypeBoolean(newName, newSize, newScale, newFormat, newSuffix, newNativeTypeSupport, null);
  }

}