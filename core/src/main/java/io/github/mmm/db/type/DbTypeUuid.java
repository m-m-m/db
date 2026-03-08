/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.type;

import java.util.UUID;

/**
 * {@link DbType} for a regular {@link UUID} if directly supported by DB.
 */
public final class DbTypeUuid extends DbType2Uuid<UUID, DbTypeUuid> {

  static final DbTypeUuid INSTANCE = new DbTypeUuid(null, null, null, null, null, true, DbTypeRegistration.TYPE_NAME);

  private DbTypeUuid(String name, Integer size, Integer scale, String format, String suffix, boolean nativeTypeSupport,
      DbTypeRegistration registration) {

    super(name, size, scale, format, suffix, nativeTypeSupport, registration);
  }

  @Override
  public Class<UUID> getSourceType() {

    return UUID.class;
  }

  @Override
  public UUID toTarget(UUID source) {

    return source;
  }

  @Override
  public UUID toSource(UUID target) {

    return target;
  }

  @Override
  protected DbTypeUuid create(String newName, Integer newSize, Integer newScale, String newFormat, String newSuffix,
      boolean newNativeTypeSupport) {

    return new DbTypeUuid(newName, newSize, newScale, newFormat, newSuffix, newNativeTypeSupport, null);
  }

}
