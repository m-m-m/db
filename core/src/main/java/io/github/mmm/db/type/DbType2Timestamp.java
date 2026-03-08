/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;

import io.github.mmm.db.name.DbKeyword;

/**
 * {@link DbType} with {@link #getTargetType() DB target type} of {@link Timestamp}.
 *
 * @param <J> type of {@link #getSourceType() Java source type}.
 * @param <SELF> type of this class itself for fluent API calls.
 *
 * @since 1.0.0
 */
@SuppressWarnings("exports")
public abstract class DbType2Timestamp<J, SELF extends DbType2Timestamp<J, SELF>> extends DbType<J, Timestamp, SELF> {

  DbType2Timestamp(String name, Integer size, Integer scale, String format, String suffix, boolean nativeTypeSupport,
      DbTypeRegistration registration) {

    super(name, size, scale, format, suffix, nativeTypeSupport, registration);
  }

  @Override
  public int getCode() {

    return Types.TIMESTAMP;
  }

  @Override
  String getDefaultName() {

    return DbKeyword.TIMESTAMP;
  }

  @Override
  public Class<Timestamp> getTargetType() {

    return Timestamp.class;
  }

  @Override
  protected void setDbValueNonNull(PreparedStatement statement, int index, Timestamp value) throws SQLException {

    statement.setTimestamp(index, value);
  }

  @Override
  public Timestamp getDbValue(ResultSet resultSet, int columnIndex) throws SQLException {

    return resultSet.getTimestamp(columnIndex);
  }

  @Override
  public Timestamp getDbValue(ResultSet resultSet, String columnLabel) throws SQLException {

    return resultSet.getTimestamp(columnLabel);
  }

}