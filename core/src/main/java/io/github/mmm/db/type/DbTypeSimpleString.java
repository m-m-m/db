/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.type;

/**
 * {@link DbType} for a regular {@link String}.
 */
public abstract class DbTypeSimpleString extends DbType2String<String, DbTypeSimpleString> {

  DbTypeSimpleString(String name, Integer size, Integer scale, String format, String suffix, boolean nativeTypeSupport,
      DbTypeRegistration registration) {

    super(name, size, scale, format, suffix, nativeTypeSupport, registration);
  }

  @Override
  public Class<String> getSourceType() {

    return String.class;
  }

  @Override
  public String toTarget(String source) {

    return source;
  }

  @Override
  public String toSource(String target) {

    return target;
  }

}
