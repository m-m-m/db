/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.type;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.SQLXML;
import java.sql.Types;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Year;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.mmm.db.name.DbKeyword;

/**
 * Test of {@link DbType}.
 */
class DbTypeTest extends Assertions {

  @Test
  void testGetCode() {

    assertThat(DbType.get(Types.ARRAY)).isSameAs(DbType.ARRAY);
    assertThat(DbType.get(Types.BIGINT)).isSameAs(DbType.LONG);
    // assertThat(DbType.get(Types.BINARY)).isSameAs(DbType.BINARY);
    // assertThat(DbType.get(Types.BIT)).isSameAs(DbType.BIT);
    assertThat(DbType.get(Types.BLOB)).isSameAs(DbType.BLOB);
    assertThat(DbType.get(Types.BOOLEAN)).isSameAs(DbType.BOOLEAN);
    // assertThat(DbType.get(Types.CHAR)).isSameAs(DbType.CHAR);
    assertThat(DbType.get(Types.CLOB)).isSameAs(DbType.CLOB);
    assertThat(DbType.get(Types.DATE)).isSameAs(DbType.LOCAL_DATE);
    assertThat(DbType.get(Types.DECIMAL)).isSameAs(DbType.BIG_DECIMAL);
    assertThat(DbType.get(Types.DOUBLE)).isSameAs(DbType.DOUBLE);
    assertThat(DbType.get(Types.FLOAT)).isSameAs(DbType.FLOAT);
    assertThat(DbType.get(Types.INTEGER)).isSameAs(DbType.INTEGER);
    assertThat(DbType.get(Types.NUMERIC)).isSameAs(DbType.BIG_INTEGER);
    assertThat(DbType.get(Types.REAL)).isSameAs(DbType.REAL);
    assertThat(DbType.get(Types.ROWID)).isSameAs(DbType.ROWID);
    assertThat(DbType.get(Types.SMALLINT)).isSameAs(DbType.SHORT);
    assertThat(DbType.get(Types.TIME)).isSameAs(DbType.LOCAL_TIME);
    assertThat(DbType.get(Types.TIME_WITH_TIMEZONE)).isSameAs(DbType.OFFSET_TIME);
    assertThat(DbType.get(Types.TIMESTAMP)).isSameAs(DbType.INSTANT);
    assertThat(DbType.get(Types.TIMESTAMP_WITH_TIMEZONE)).isSameAs(DbType.OFFSET_DATE_TIME);
    assertThat(DbType.get(Types.TINYINT)).isSameAs(DbType.BYTE);
    // assertThat(DbType.get(Types.SQLXML)).isSameAs(DbType.SQL_XML);
    // assertThat(DbType.get(Types.VARBINARY)).isSameAs(DbType.VARBINARY);
    assertThat(DbType.get(Types.VARCHAR)).isSameAs(DbType.STRING);
  }

  @Test
  void testGetType() {

    assertThat(DbType.get(Array.class)).isSameAs(DbType.ARRAY);
    assertThat(DbType.get(BigDecimal.class)).isSameAs(DbType.BIG_DECIMAL);
    assertThat(DbType.get(BigInteger.class)).isSameAs(DbType.BIG_INTEGER);
    // assertThat(DbType.get(BitSet.class)).isSameAs(DbType.BIT);
    assertThat(DbType.get(Blob.class)).isSameAs(DbType.BLOB);
    assertThat(DbType.get(Boolean.class)).isSameAs(DbType.BOOLEAN);
    assertThat(DbType.get(Byte.class)).isSameAs(DbType.BYTE);
    // assertThat(DbType.get(byte[].class)).isSameAs(DbType.BINARY);
    // assertThat(DbType.get(char[].class)).isSameAs(DbType.CHAR);
    assertThat(DbType.get(Clob.class)).isSameAs(DbType.CLOB);
    // assertThat(DbType.get(Document.class)).isSameAs(DbType.XML);
    assertThat(DbType.get(Double.class)).isSameAs(DbType.DOUBLE);
    assertThat(DbType.get(Float.class)).isSameAs(DbType.FLOAT);
    assertThat(DbType.get(Instant.class)).isSameAs(DbType.INSTANT);
    assertThat(DbType.get(Integer.class)).isSameAs(DbType.INTEGER);
    assertThat(DbType.get(LocalDate.class)).isSameAs(DbType.LOCAL_DATE);
    assertThat(DbType.get(LocalDateTime.class)).isSameAs(DbType.LOCAL_DATE_TIME);
    assertThat(DbType.get(LocalTime.class)).isSameAs(DbType.LOCAL_TIME);
    assertThat(DbType.get(Long.class)).isSameAs(DbType.LONG);
    assertThat(DbType.get(OffsetDateTime.class)).isSameAs(DbType.OFFSET_DATE_TIME);
    assertThat(DbType.get(OffsetTime.class)).isSameAs(DbType.OFFSET_TIME);
    assertThat(DbType.get(Short.class)).isSameAs(DbType.SHORT);
    assertThat(DbType.get(String.class)).isSameAs(DbType.STRING);
    assertThat(DbType.get(SQLXML.class)).isSameAs(DbType.SQL_XML);
    assertThat(DbType.get(UUID.class)).isSameAs(DbType.UUID);
    assertThat(DbType.get(Year.class)).isSameAs(DbType.YEAR);
    // assertThat(DbType.get(ZonedDateTime.class)).isSameAs(DbType.ZONED_DATE_TIME);
    // edge-cases
    assertThat(DbType.get(Throwable.class)).isNull();
    assertThat(DbType.get(boolean.class)).isSameAs(DbType.BOOLEAN);
    assertThat(DbType.get(byte.class)).isSameAs(DbType.BYTE);
    assertThat(DbType.get(char.class)).isNull();
    assertThat(DbType.get(double.class)).isSameAs(DbType.DOUBLE);
    assertThat(DbType.get(float.class)).isSameAs(DbType.FLOAT);
    assertThat(DbType.get(int.class)).isSameAs(DbType.INTEGER);
    assertThat(DbType.get(long.class)).isSameAs(DbType.LONG);
    assertThat(DbType.get(short.class)).isSameAs(DbType.SHORT);
  }

  @Test
  void testDbSpecific() {

    assertThat(DbType.BIG_INTEGER.withSize(64).getDeclaration()).isEqualTo("NUMERIC(64)");
    assertThat(DbType.BIG_DECIMAL.withName(DbKeyword.NUMBER).withSize(64).withScale(16).getDeclaration())
        .isEqualTo("NUMBER(64,16)");
    assertThat(DbType.STRING.withName(DbKeyword.VARCHAR2).withSize(255).getDeclaration()).isEqualTo("VARCHAR2(255)");
  }

  @Test
  void testConstants() {

    verify(DbType.ARRAY, Types.ARRAY, Array.class, DbKeyword.ARRAY);
    verify(DbType.BIG_DECIMAL, Types.DECIMAL, BigDecimal.class, DbKeyword.DECIMAL);
    verify(DbType.BIG_INTEGER, Types.NUMERIC, BigInteger.class, DbKeyword.NUMERIC);
    // verify(DbType.BINARY, Types.BINARY, byte[].class, DbKeyword.BINARY);
    // verify(DbType.BIT, Types.BIT, BitSet.class, DbKeyword.BIT);
    verify(DbType.BLOB, Types.BLOB, Blob.class, DbKeyword.BLOB);
    verify(DbType.BOOLEAN, Types.BOOLEAN, Boolean.class, DbKeyword.BOOLEAN);
    verify(DbType.BYTE, Types.TINYINT, Byte.class, DbKeyword.TINYINT);
    // verify(DbType.CHAR, Types.CHAR, char[].class, DbKeyword.CHAR);
    verify(DbType.CLOB, Types.CLOB, Clob.class, DbKeyword.CLOB);
    verify(DbType.DOUBLE, Types.DOUBLE, Double.class, DbKeyword.DOUBLE_PRECISION);
    verify(DbType.FLOAT, Types.FLOAT, Float.class, DbKeyword.FLOAT);
    verify(DbType.INSTANT, Types.TIMESTAMP, Instant.class, DbKeyword.TIMESTAMP);
    verify(DbType.INTEGER, Types.INTEGER, Integer.class, DbKeyword.INTEGER);
    verify(DbType.LOCAL_DATE, Types.DATE, LocalDate.class, DbKeyword.DATE);
    verify(DbType.LOCAL_DATE_TIME, Types.TIMESTAMP, LocalDateTime.class, DbKeyword.TIMESTAMP);
    verify(DbType.LOCAL_TIME, Types.TIME, LocalTime.class, DbKeyword.TIME);
    verify(DbType.LONG, Types.BIGINT, Long.class, DbKeyword.BIGINT);
    verify(DbType.OFFSET_DATE_TIME, Types.TIMESTAMP_WITH_TIMEZONE, OffsetDateTime.class, DbKeyword.TIMESTAMP,
        DbKeyword.WITH_TIME_ZONE);
    verify(DbType.OFFSET_TIME, Types.TIME_WITH_TIMEZONE, OffsetTime.class, DbKeyword.TIME, DbKeyword.WITH_TIME_ZONE);
    verify(DbType.REAL, Types.REAL, Double.class, DbKeyword.REAL);
    verify(DbType.ROWID, Types.ROWID, Long.class, DbKeyword.ROWID);
    verify(DbType.SHORT, Types.SMALLINT, Short.class, DbKeyword.SMALLINT);
    verify(DbType.STRING, Types.VARCHAR, String.class, DbKeyword.VARCHAR);
    // verify(DbType.VARBINARY, Types.VARBINARY, Blob.class, DbKeyword.VARBINARY);
    verify(DbType.SQL_XML, Types.SQLXML, SQLXML.class, DbKeyword.XML);
    verify(DbType.UUID, Types.OTHER, UUID.class, DbKeyword.UUID);
    verify(DbType.YEAR, Types.INTEGER, Year.class, DbKeyword.INTEGER);
    // verify(DbType.ZONED_DATE_TIME, 0, ZonedDateTime.class, DbKeyword.TIMESTAMP, DbKeyword.WITH_TIME_ZONE);
  }

  private void verify(DbType<?, ?, ?> columnType, int code, Class<?> type, String name) {

    verify(columnType, code, type, name, null);
  }

  private void verify(DbType<?, ?, ?> columnType, int code, Class<?> type, String name, String suffix) {

    assertThat(columnType.getCode()).isEqualTo(code);
    assertThat(columnType.getSourceType()).isSameAs(type);
    assertThat(columnType.getName()).isSameAs(name);
    assertThat(columnType.getSuffix()).isSameAs(suffix);
    assertThat(columnType.getSize()).isNull();
    assertThat(columnType.getScale()).isNull();
    assertThat(columnType.getFormat()).isNull();
    if (suffix == null) {
      assertThat(columnType.getDeclaration()).isEqualTo(name);
    } else {
      assertThat(columnType.getDeclaration()).isEqualTo(name + " " + suffix);
    }
    assertThat(columnType).hasToString(columnType.getDeclaration());
  }
}
