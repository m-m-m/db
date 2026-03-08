/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import io.github.mmm.db.name.DbKeyword;

/**
 * {@link DbType} with {@link #getTargetType() DB target type} of {@link Byte}.
 *
 * @param <J> type of {@link #getSourceType() Java source type}.
 * @param <SELF> type of this class itself for fluent API calls.
 *
 * @since 1.0.0
 */
@SuppressWarnings("exports")
public abstract class DbType2Byte<J, SELF extends DbType2Byte<J, SELF>> extends DbType<J, Byte, SELF> {

  DbType2Byte(String name, Integer size, Integer scale, String format, String suffix, boolean nativeTypeSupport,
      DbTypeRegistration registration) {

    super(name, size, scale, format, suffix, nativeTypeSupport, registration);
  }

  @Override
  public int getCode() {

    return Types.TINYINT;
  }

  @Override
  String getDefaultName() {

    return DbKeyword.TINYINT;
  }

  @Override
  public Class<Byte> getTargetType() {

    return Byte.class;
  }

  @Override
  protected void setDbValueNonNull(PreparedStatement statement, int index, Byte value) throws SQLException {

    statement.setByte(index, value.byteValue());
  }

  @Override
  public Byte getDbValue(ResultSet resultSet, int columnIndex) throws SQLException {

    return resultSet.getByte(columnIndex);
  }

  @Override
  public Byte getDbValue(ResultSet resultSet, String columnLabel) throws SQLException {

    return resultSet.getByte(columnLabel);
  }

}