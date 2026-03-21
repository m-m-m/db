/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.type;

import java.sql.Date;
import java.time.LocalDate;

/**
 * {@link DbType} for a regular {@link LocalDate}.
 */
@SuppressWarnings("exports")
public final class DbTypeLocalDate2Date extends DbType2Date<LocalDate, DbTypeLocalDate2Date> {

  static final DbTypeLocalDate2Date INSTANCE = new DbTypeLocalDate2Date(null, null, null, null, null, false,
      DbTypeRegistration.ALL);

  private DbTypeLocalDate2Date(String name, Integer size, Integer scale, String format, String suffix,
      boolean nativeTypeSupport, DbTypeRegistration registration) {

    super(name, size, scale, format, suffix, nativeTypeSupport, registration);
  }

  @Override
  public Class<LocalDate> getSourceType() {

    return LocalDate.class;
  }

  @Override
  public Date toTarget(LocalDate source) {

    return Date.valueOf(source);
  }

  @Override
  public LocalDate toSource(Date target) {

    return target.toLocalDate();
  }

  @Override
  protected DbTypeLocalDate2Date create(String newName, Integer newSize, Integer newScale, String newFormat,
      String newSuffix, boolean newNativeTypeSupport) {

    return new DbTypeLocalDate2Date(newName, newSize, newScale, newFormat, newSuffix, newNativeTypeSupport, null);
  }

}
