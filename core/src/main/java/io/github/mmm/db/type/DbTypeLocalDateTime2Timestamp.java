/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.type;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Implementation of {@link DbType} for a regular {@link LocalDateTime}.
 */
@SuppressWarnings("exports")
public final class DbTypeLocalDateTime2Timestamp
    extends DbType2Timestamp<LocalDateTime, DbTypeLocalDateTime2Timestamp> {

  static final DbTypeLocalDateTime2Timestamp INSTANCE = new DbTypeLocalDateTime2Timestamp(null, null, null, null, null,
      false, DbTypeRegistration.TYPE);

  private DbTypeLocalDateTime2Timestamp(String name, Integer size, Integer scale, String format, String suffix,
      boolean nativeTypeSupport, DbTypeRegistration registration) {

    super(name, size, scale, format, suffix, nativeTypeSupport, registration);
  }

  @Override
  public Class<LocalDateTime> getSourceType() {

    return LocalDateTime.class;
  }

  @Override
  public Timestamp toTarget(LocalDateTime source) {

    return Timestamp.valueOf(source);
  }

  @Override
  public LocalDateTime toSource(Timestamp target) {

    return target.toLocalDateTime();
  }

  @Override
  protected DbTypeLocalDateTime2Timestamp create(String newName, Integer newSize, Integer newScale, String newFormat,
      String newSuffix, boolean newNativeTypeSupport) {

    return new DbTypeLocalDateTime2Timestamp(newName, newSize, newScale, newFormat, newSuffix, newNativeTypeSupport,
        null);
  }

}
