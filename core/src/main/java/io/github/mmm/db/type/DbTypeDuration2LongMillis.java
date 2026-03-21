/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.type;

import java.time.Duration;

/**
 * {@link DbType} mapping from {@link Duration} to {@link Long}.<br>
 * Unfortunately, JDBC has a long legacy history and still does not support {@code java.time} (JSR310). Unless your JDBC
 * driver does not properly support this as {@link #isNativeTypeSupport() native type}, we have to do conversion to
 * {@link Long}. As a best balance we store the {@link Duration} in milliseconds loosing nano-precision but still giving
 * enough room to store up to 292 million years. If we would store nanos the maximum would already be 292 years and that
 * would cause problems for durations e.g. from BC.
 *
 * @since 1.0.0
 */
public final class DbTypeDuration2LongMillis extends DbType2Long<Duration, DbTypeDuration2LongMillis> {

  static final DbTypeDuration2LongMillis INSTANCE = new DbTypeDuration2LongMillis(null, null, null, null, null, false,
      DbTypeRegistration.TYPE);

  private DbTypeDuration2LongMillis(String name, Integer size, Integer scale, String format, String suffix,
      boolean nativeTypeSupport, DbTypeRegistration registration) {

    super(name, size, scale, format, suffix, nativeTypeSupport, registration);
  }

  @Override
  public Class<Duration> getSourceType() {

    return Duration.class;
  }

  @Override
  public Long toTarget(Duration source) {

    return source.toMillis();
  }

  @Override
  public Duration toSource(Long target) {

    return Duration.ofMillis(target.longValue());
  }

  @Override
  protected DbTypeDuration2LongMillis create(String newName, Integer newSize, Integer newScale, String newFormat,
      String newSuffix, boolean newNativeTypeSupport) {

    return new DbTypeDuration2LongMillis(newName, newSize, newScale, newFormat, newSuffix, newNativeTypeSupport, null);
  }

}
