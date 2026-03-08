/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.type.oracle.dialect;

import io.github.mmm.db.name.DbKeyword;
import io.github.mmm.db.type.DbType;
import io.github.mmm.db.typemapping.DbTypeMapping;

/**
 * {@link DbTypeMapping} for Oracle database.
 *
 * @since 1.0.0
 */
public class OracleTypeMapping extends DbTypeMapping {

  @Override
  protected void addLong() {

    add(DbType.LONG.withName(DbKeyword.NUMBER).withSize(19), 19, 0);
  }

  @Override
  protected void addInteger() {

    add(DbType.INTEGER.withName(DbKeyword.NUMBER).withSize(10), 10, 0);
  }

  @Override
  protected void addShort() {

    add(DbType.SHORT.withName(DbKeyword.NUMBER).withSize(5), 5, 0);
  }

  @Override
  protected void addByte() {

    add(DbType.BYTE.withName(DbKeyword.NUMBER).withSize(3), 3, 0);
  }

  @Override
  protected void addDouble() {

    add(DbType.DOUBLE.withName(DbKeyword.NUMBER).withSize(19).withScale(4), 19, 4);
  }

  @Override
  protected void addFloat() {

    add(DbType.FLOAT.withName(DbKeyword.NUMBER).withSize(19).withScale(4), 19, 4);
  }

  @Override
  protected void addBigDecimal() {

    add(DbType.BIG_DECIMAL.withName(DbKeyword.NUMBER).withSize(38).withScale(5), 38, 5);
  }

  @Override
  protected void addBigInteger() {

    add(DbType.BIG_INTEGER.withName(DbKeyword.NUMBER).withSize(38), 38, 0);
  }

}
