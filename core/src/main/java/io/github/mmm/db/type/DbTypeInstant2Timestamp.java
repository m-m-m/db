/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.type;

import java.sql.Timestamp;
import java.time.Instant;

/**
 * Implementation of {@link DbType} for a regular {@link Instant}.
 */
@SuppressWarnings("exports")
public final class DbTypeInstant2Timestamp extends DbType2Timestamp<Instant, DbTypeInstant2Timestamp> {

  static final DbTypeInstant2Timestamp INSTANCE = new DbTypeInstant2Timestamp(null, null, null, null, null, false,
      DbTypeRegistration.ALL);

  private DbTypeInstant2Timestamp(String name, Integer size, Integer scale, String format, String suffix,
      boolean nativeTypeSupport, DbTypeRegistration registration) {

    super(name, size, scale, format, suffix, nativeTypeSupport, registration);
  }

  @Override
  public Class<Instant> getSourceType() {

    return Instant.class;
  }

  @Override
  public Timestamp toTarget(Instant source) {

    return Timestamp.from(source);
  }

  @Override
  public Instant toSource(Timestamp target) {

    return target.toInstant();
  }

  @Override
  protected DbTypeInstant2Timestamp create(String newName, Integer newSize, Integer newScale, String newFormat,
      String newSuffix, boolean newNativeTypeSupport) {

    return new DbTypeInstant2Timestamp(newName, newSize, newScale, newFormat, newSuffix, newNativeTypeSupport, null);
  }

}
