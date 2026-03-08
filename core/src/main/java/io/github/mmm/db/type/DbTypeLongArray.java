/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.type;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import io.github.mmm.db.name.DbKeyword;

/**
 * {@link DbType} for a regular {@link Array}.
 */
@SuppressWarnings("exports")
public final class DbTypeLongArray extends DbType2Array<Long[], DbTypeLongArray> {

  static final DbTypeLongArray INSTANCE = new DbTypeLongArray(null, null, null, null, null, false,
      DbTypeRegistration.TYPE);

  private DbTypeLongArray(String name, Integer size, Integer scale, String format, String suffix,
      boolean nativeTypeSupport, DbTypeRegistration registration) {

    super(name, size, scale, format, suffix, nativeTypeSupport, registration);
  }

  @Override
  public Class<Long[]> getSourceType() {

    return Long[].class;
  }

  @Override
  public Array toTarget(Long[] source) {

    return null;
  }

  @Override
  public Long[] toSource(Array target) {

    try {
      Object array = target.getArray();
      if (array instanceof Long[] result) {
        return result;
      } else if (array instanceof long[] longArray) {
        Long[] result = new Long[longArray.length];
        for (int i = 0; i < result.length; i++) {
          result[i] = longArray[i];
        }
        return result;
      }
      throw new IllegalArgumentException(target.getClass().getName());
    } catch (SQLException e) {
      throw new IllegalStateException(e);
    }
  }

  @Override
  public void setJavaValue(PreparedStatement statement, int index, Long[] value) throws SQLException {

    if (value != null) {
      Array array = statement.getConnection().createArrayOf(DbKeyword.BIGINT, value);
      setDbValueNonNull(statement, index, array);
    }
    super.setJavaValue(statement, index, value);
  }

  @Override
  protected DbTypeLongArray create(String newName, Integer newSize, Integer newScale, String newFormat,
      String newSuffix, boolean newNativeTypeSupport) {

    return new DbTypeLongArray(newName, newSize, newScale, newFormat, newSuffix, newNativeTypeSupport, null);
  }

}
