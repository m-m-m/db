/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.type;

import java.math.BigDecimal;

/**
 * {@link DbType} for a regular {@link BigDecimal}.
 */
public final class DbTypeBigDecimal extends DbType2BigDecimal<BigDecimal, DbTypeBigDecimal> {

  static final DbTypeBigDecimal INSTANCE = new DbTypeBigDecimal(null, null, null, null, null, false,
      DbTypeRegistration.ALL);

  private DbTypeBigDecimal(String name, Integer size, Integer scale, String format, String suffix,
      boolean nativeTypeSupport, DbTypeRegistration registration) {

    super(name, size, scale, format, suffix, nativeTypeSupport, registration);
  }

  @Override
  public Class<BigDecimal> getSourceType() {

    return BigDecimal.class;
  }

  @Override
  public BigDecimal toTarget(BigDecimal source) {

    return source;
  }

  @Override
  public BigDecimal toSource(BigDecimal target) {

    return target;
  }

  @Override
  protected DbTypeBigDecimal create(String newName, Integer newSize, Integer newScale, String newFormat,
      String newSuffix, boolean newNativeTypeSupport) {

    return new DbTypeBigDecimal(newName, newSize, newScale, newFormat, newSuffix, newNativeTypeSupport, null);
  }

}
