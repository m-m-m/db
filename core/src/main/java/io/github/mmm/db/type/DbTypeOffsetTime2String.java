/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.type;

import java.sql.Types;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.format.DateTimeFormatter;

import io.github.mmm.db.name.DbKeyword;

/**
 * {@link DbType} with {@link OffsetDateTime} as {@link #getSourceType() Java source type}.<br>
 * Unfortunately, JDBC has a long legacy history and still does not support {@code java.time} (JSR310). Unless your JDBC
 * driver does not properly support this as {@link #isNativeTypeSupport() native type}, we have to do {@link String}
 * conversion what is wasting performance and could break on unexpected formatting (e.g. if a DB changes its format).
 *
 * @since 1.0.0
 */
public final class DbTypeOffsetTime2String extends DbType2String<OffsetTime, DbTypeOffsetTime2String> {

  static final DbTypeOffsetTime2String INSTANCE = new DbTypeOffsetTime2String(null, null, null, "HH:mm:ss.SSSSS x",
      null, false, DbTypeRegistration.CODE_TYPE);

  private final DateTimeFormatter formatter;

  private DbTypeOffsetTime2String(String name, Integer size, Integer scale, String format, String suffix,
      boolean nativeTypeSupport, DbTypeRegistration registration) {

    super(name, size, scale, null, suffix, nativeTypeSupport, registration);
    if (format == null) {
      this.formatter = DateTimeFormatter.ISO_TIME;
    } else {
      this.formatter = DateTimeFormatter.ofPattern(format);
    }
  }

  @Override
  public int getCode() {

    return Types.TIME_WITH_TIMEZONE;
  }

  @Override
  String getDefaultName() {

    return DbKeyword.TIME;
  }

  @Override
  String getDefaultSuffix() {

    return DbKeyword.WITH_TIME_ZONE;
  }

  @Override
  public Class<OffsetTime> getSourceType() {

    return OffsetTime.class;
  }

  @Override
  public String toTarget(OffsetTime source) {

    return this.formatter.format(source);
  }

  @Override
  public OffsetTime toSource(String target) {

    return OffsetTime.parse(target, this.formatter);
  }

  @Override
  protected DbTypeOffsetTime2String create(String newName, Integer newSize, Integer newScale, String newFormat,
      String newSuffix, boolean newNativeTypeSupport) {

    return new DbTypeOffsetTime2String(newName, newSize, newScale, newFormat, newSuffix, newNativeTypeSupport, null);
  }

}
