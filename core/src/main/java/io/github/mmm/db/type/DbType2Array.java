/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.type;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import io.github.mmm.db.name.DbKeyword;

/**
 * {@link DbType} with {@link #getTargetType() DB target type} of {@link Array}.
 *
 * @param <J> type of {@link #getSourceType() Java source type}.
 * @param <SELF> type of this class itself for fluent API calls.
 *
 * @since 1.0.0
 */
@SuppressWarnings("exports")
public abstract class DbType2Array<J, SELF extends DbType2Array<J, SELF>> extends DbType<J, Array, SELF> {

  DbType2Array(String name, Integer size, Integer scale, String format, String suffix, boolean nativeTypeSupport,
      DbTypeRegistration registration) {

    super(name, size, scale, format, suffix, nativeTypeSupport, registration);
  }

  @Override
  public int getCode() {

    return Types.ARRAY;
  }

  @Override
  String getDefaultName() {

    return DbKeyword.ARRAY;
  }

  @Override
  public Class<Array> getTargetType() {

    return Array.class;
  }

  @Override
  protected void setDbValueNonNull(PreparedStatement statement, int index, Array value) throws SQLException {

    statement.setArray(index, value);
  }

  @Override
  public Array getDbValue(ResultSet resultSet, int columnIndex) throws SQLException {

    return resultSet.getArray(columnIndex);
  }

  @Override
  public Array getDbValue(ResultSet resultSet, String columnLabel) throws SQLException {

    return resultSet.getArray(columnLabel);
  }

}