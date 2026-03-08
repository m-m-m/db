/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.type;

import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import io.github.mmm.db.name.DbKeyword;

/**
 * {@link DbType} with {@link #getTargetType() DB target type} of {@link Blob}.
 *
 * @param <J> type of {@link #getSourceType() Java source type}.
 * @param <SELF> type of this class itself for fluent API calls.
 *
 * @since 1.0.0
 */
@SuppressWarnings("exports")
public abstract class DbType2Blob<J, SELF extends DbType2Blob<J, SELF>> extends DbType<J, Blob, SELF> {

  DbType2Blob(String name, Integer size, Integer scale, String format, String suffix, boolean nativeTypeSupport,
      DbTypeRegistration registration) {

    super(name, size, scale, format, suffix, nativeTypeSupport, registration);
  }

  @Override
  public int getCode() {

    return Types.BLOB;
  }

  @Override
  String getDefaultName() {

    return DbKeyword.BLOB;
  }

  @Override
  public Class<Blob> getTargetType() {

    return Blob.class;
  }

  @Override
  protected void setDbValueNonNull(PreparedStatement statement, int index, Blob value) throws SQLException {

    statement.setBlob(index, value);
  }

  @Override
  public Blob getDbValue(ResultSet resultSet, int columnIndex) throws SQLException {

    return resultSet.getBlob(columnIndex);
  }

  @Override
  public Blob getDbValue(ResultSet resultSet, String columnLabel) throws SQLException {

    return resultSet.getBlob(columnLabel);
  }

}