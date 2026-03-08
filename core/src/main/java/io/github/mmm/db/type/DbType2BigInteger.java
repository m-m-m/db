/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.type;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import io.github.mmm.db.name.DbKeyword;

/**
 * {@link DbType} with {@link #getTargetType() DB target type} of {@link BigInteger}.
 *
 * @param <J> type of {@link #getSourceType() Java source type}.
 * @param <SELF> type of this class itself for fluent API calls.
 *
 * @since 1.0.0
 */
@SuppressWarnings("exports")
public abstract class DbType2BigInteger<J, SELF extends DbType2BigInteger<J, SELF>>
    extends DbType<J, BigInteger, SELF> {

  DbType2BigInteger(String name, Integer size, Integer scale, String format, String suffix, boolean nativeTypeSupport,
      DbTypeRegistration registration) {

    super(name, size, scale, format, suffix, nativeTypeSupport, registration);
  }

  @Override
  public int getCode() {

    return Types.NUMERIC;
  }

  @Override
  String getDefaultName() {

    return DbKeyword.NUMBER;
  }

  @Override
  public Class<BigInteger> getTargetType() {

    return BigInteger.class;
  }

  @Override
  protected void setDbValueNonNull(PreparedStatement statement, int index, BigInteger value) throws SQLException {

    if (isNativeTypeSupport()) {
      statement.setObject(index, value);
    } else {
      statement.setBigDecimal(index, new BigDecimal(value));
    }
  }

  @Override
  public BigInteger getDbValue(ResultSet resultSet, int columnIndex) throws SQLException {

    if (isNativeTypeSupport()) {
      return convertDbValue(resultSet.getObject(columnIndex));
    } else {
      BigDecimal bigDec = resultSet.getBigDecimal(columnIndex);
      if (bigDec == null) {
        return null;
      }
      return bigDec.toBigInteger();
    }
  }

  @Override
  public BigInteger getDbValue(ResultSet resultSet, String columnLabel) throws SQLException {

    if (isNativeTypeSupport()) {
      return convertDbValue(resultSet.getObject(columnLabel));
    } else {
      BigDecimal bigDec = resultSet.getBigDecimal(columnLabel);
      if (bigDec == null) {
        return null;
      }
      return bigDec.toBigInteger();
    }
  }

  private BigInteger convertDbValue(Object value) {

    if (value == null) {
      return null;
    } else if (value instanceof BigInteger bigInt) {
      return bigInt;
    } else if (value instanceof BigDecimal bigDec) {
      return bigDec.toBigInteger();
    } else if (value instanceof Number num) {
      return BigInteger.valueOf(num.longValue());
    } else {
      throw new IllegalStateException("Cannot convert " + value.getClass().getName() + " to BigInteger");
    }
  }

}