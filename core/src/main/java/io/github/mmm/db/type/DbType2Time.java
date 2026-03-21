/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Types;

import io.github.mmm.db.name.DbKeyword;

/**
 * {@link DbType} with {@link #getTargetType() DB target type} of {@link Time}.
 *
 * @param <J> type of {@link #getSourceType() Java source type}.
 * @param <SELF> type of this class itself for fluent API calls.
 *
 * @since 1.0.0
 */
@SuppressWarnings("exports")
public abstract class DbType2Time<J, SELF extends DbType2Time<J, SELF>> extends DbType<J, Time, SELF> {

  DbType2Time(String name, Integer size, Integer scale, String format, String suffix, boolean nativeTypeSupport,
      DbTypeRegistration registration) {

    super(name, size, scale, format, suffix, nativeTypeSupport, registration);
  }

  @Override
  public int getCode() {

    return Types.TIME;
  }

  @Override
  String getDefaultName() {

    return DbKeyword.TIME;
  }

  @Override
  public Class<Time> getTargetType() {

    return Time.class;
  }

  @Override
  protected void setDbValueNonNull(PreparedStatement statement, int index, Time value) throws SQLException {

    statement.setTime(index, value);
  }

  @Override
  public Time getDbValue(ResultSet resultSet, int columnIndex) throws SQLException {

    return resultSet.getTime(columnIndex);
  }

  @Override
  public Time getDbValue(ResultSet resultSet, String columnLabel) throws SQLException {

    return resultSet.getTime(columnLabel);
  }

}