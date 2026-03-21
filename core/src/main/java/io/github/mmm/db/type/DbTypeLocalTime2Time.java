/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.type;

import java.sql.Time;
import java.time.LocalTime;

/**
 * Implementation of {@link DbType} for a regular {@link LocalTime}.
 */
@SuppressWarnings("exports")
public final class DbTypeLocalTime2Time extends DbType2Time<LocalTime, DbTypeLocalTime2Time> {

  static final DbTypeLocalTime2Time INSTANCE = new DbTypeLocalTime2Time(null, null, null, null, null, false,
      DbTypeRegistration.ALL);

  private DbTypeLocalTime2Time(String name, Integer size, Integer scale, String format, String suffix,
      boolean nativeTypeSupport, DbTypeRegistration registration) {

    super(name, size, scale, format, suffix, nativeTypeSupport, registration);
  }

  @Override
  public Class<LocalTime> getSourceType() {

    return LocalTime.class;
  }

  @Override
  public Time toTarget(LocalTime source) {

    return Time.valueOf(source);
  }

  @Override
  public LocalTime toSource(Time target) {

    return target.toLocalTime();
  }

  @Override
  protected DbTypeLocalTime2Time create(String newName, Integer newSize, Integer newScale, String newFormat,
      String newSuffix, boolean newNativeTypeSupport) {

    return new DbTypeLocalTime2Time(newName, newSize, newScale, newFormat, newSuffix, newNativeTypeSupport, null);
  }

}
