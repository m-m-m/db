/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.type;

import io.github.mmm.db.name.DbKeyword;

/**
 * {@link DbType} for a regular {@link String}.
 */
public final class DbTypeText extends DbTypeSimpleString {

  static final DbTypeText INSTANCE = new DbTypeText(null, null, null, null, null, false, DbTypeRegistration.NAME);

  private DbTypeText(String name, Integer size, Integer scale, String format, String suffix, boolean nativeTypeSupport,
      DbTypeRegistration registration) {

    super(name, size, scale, format, suffix, nativeTypeSupport, registration);
  }

  @Override
  String getDefaultName() {

    return DbKeyword.TEXT;
  }

  @Override
  protected DbTypeText create(String newName, Integer newSize, Integer newScale, String newFormat, String newSuffix,
      boolean newNativeTypeSupport) {

    return new DbTypeText(newName, newSize, newScale, newFormat, newSuffix, newNativeTypeSupport, null);
  }

}
