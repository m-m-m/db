/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.type;

import java.sql.Timestamp;
import java.sql.Types;

import io.github.mmm.db.name.DbKeyword;

/**
 * {@link DbType} with {@link #getTargetType() DB target type} of {@link Timestamp} with timezone.
 *
 * @param <J> type of {@link #getSourceType() Java source type}.
 * @param <SELF> type of this class itself for fluent API calls.
 *
 * @since 1.0.0
 */
public abstract class DbType2TimestampWithTimezone<J, SELF extends DbType2TimestampWithTimezone<J, SELF>>
    extends DbType2Timestamp<J, SELF> {

  DbType2TimestampWithTimezone(String name, Integer size, Integer scale, String format, String suffix,
      boolean nativeTypeSupport, DbTypeRegistration registration) {

    super(name, size, scale, format, suffix, nativeTypeSupport, registration);
  }

  @Override
  public int getCode() {

    return Types.TIMESTAMP_WITH_TIMEZONE;
  }

  @Override
  String getDefaultSuffix() {

    return DbKeyword.WITH_TIME_ZONE;
  }

}