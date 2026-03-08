/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.type;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Types;

import io.github.mmm.db.name.DbKeyword;

/**
 * {@link DbType} mapping {@link BigDecimal} via {@link BigDecimal}.
 */
public final class DbTypeBigInteger2BigDecimal extends DbType2BigDecimal<BigInteger, DbTypeBigInteger2BigDecimal> {

  static final DbTypeBigInteger2BigDecimal INSTANCE = new DbTypeBigInteger2BigDecimal(null, null, null, null, false,
      DbTypeRegistration.ALL);

  private DbTypeBigInteger2BigDecimal(String name, Integer size, String format, String suffix,
      boolean nativeTypeSupport, DbTypeRegistration registration) {

    super(name, size, null, format, suffix, nativeTypeSupport, registration);
  }

  @Override
  public int getCode() {

    return Types.NUMERIC;
  }

  @Override
  String getDefaultName() {

    return DbKeyword.NUMERIC;
  }

  @Override
  public Class<BigInteger> getSourceType() {

    return BigInteger.class;
  }

  @Override
  public BigDecimal toTarget(BigInteger source) {

    return new BigDecimal(source);
  }

  @Override
  public BigInteger toSource(BigDecimal target) {

    return target.toBigInteger();
  }

  @Override
  protected DbTypeBigInteger2BigDecimal create(String newName, Integer newSize, Integer newScale, String newFormat,
      String newSuffix, boolean newNativeTypeSupport) {

    assert (newScale == null);
    return new DbTypeBigInteger2BigDecimal(newName, newSize, newFormat, newSuffix, newNativeTypeSupport, null);
  }

}
