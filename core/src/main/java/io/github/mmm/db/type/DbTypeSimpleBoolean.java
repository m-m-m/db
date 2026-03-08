/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.type;

/**
 * {@link DbType} for a regular {@link Boolean}.
 */
public abstract class DbTypeSimpleBoolean extends DbType2Boolean<Boolean, DbTypeSimpleBoolean> {

  DbTypeSimpleBoolean(String name, Integer size, Integer scale, String format, String suffix, boolean nativeTypeSupport,
      DbTypeRegistration registration) {

    super(name, size, scale, format, suffix, nativeTypeSupport, registration);
  }

  @Override
  public Class<Boolean> getSourceType() {

    return Boolean.class;
  }

  @Override
  public Boolean toSource(Boolean target) {

    return target;
  }

  @Override
  public Boolean toTarget(Boolean source) {

    return source;
  }

}