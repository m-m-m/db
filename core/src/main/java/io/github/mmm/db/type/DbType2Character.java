/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import io.github.mmm.db.name.DbKeyword;

/**
 * {@link DbType}with {@link #getTargetType() DB target type} of {@link Character}.
 *
 * @param <J> type of {@link #getSourceType() Java source type}.
 * @param <SELF> type of this class itself for fluent API calls.
 *
 * @since 1.0.0
 */
@SuppressWarnings("exports")
public abstract class DbType2Character<J, SELF extends DbType2Character<J, SELF>> extends DbType<J, Character, SELF> {

  DbType2Character(String name, Integer scale, String format, String suffix, boolean nativeTypeSupport,
      DbTypeRegistration registration) {

    super(name, 1, scale, format, suffix, nativeTypeSupport, registration);
  }

  @Override
  public int getCode() {

    return Types.CHAR;
  }

  @Override
  String getDefaultName() {

    return DbKeyword.CHAR;
  }

  @Override
  public Class<Character> getTargetType() {

    return Character.class;
  }

  @Override
  protected void setDbValueNonNull(PreparedStatement statement, int index, Character value) throws SQLException {

    statement.setString(index, value.toString());
  }

  @Override
  public Character getDbValue(ResultSet resultSet, int columnIndex) throws SQLException {

    return convertDbValue(resultSet.getString(columnIndex));
  }

  @Override
  public Character getDbValue(ResultSet resultSet, String columnLabel) throws SQLException {

    return convertDbValue(resultSet.getString(columnLabel));
  }

  private Character convertDbValue(String value) {

    if (value == null) {
      return null;
    }
    int length = value.length();
    if (length == 0) {
      return null;
    }
    assert (length == 1);
    return value.charAt(0);
  }

}
