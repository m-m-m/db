/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.type;

/**
 * {@link DbType} for a regular {@link String}.
 */
public final class DbTypeString extends DbTypeSimpleString {

  static final DbTypeString INSTANCE = new DbTypeString(null, null, null, null, null, false, DbTypeRegistration.ALL);

  private DbTypeString(String name, Integer size, Integer scale, String format, String suffix,
      boolean nativeTypeSupport, DbTypeRegistration registration) {

    super(name, size, scale, format, suffix, nativeTypeSupport, registration);
  }

  @Override
  protected DbTypeString create(String newName, Integer newSize, Integer newScale, String newFormat, String newSuffix,
      boolean newNativeTypeSupport) {

    return new DbTypeString(newName, newSize, newScale, newFormat, newSuffix, newNativeTypeSupport, null);
  }

}
