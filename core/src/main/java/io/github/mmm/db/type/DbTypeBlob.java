/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.type;

import java.sql.Blob;

/**
 * {@link DbType} for a regular {@link Blob}.
 */
@SuppressWarnings("exports")
public final class DbTypeBlob extends DbType2Blob<Blob, DbTypeBlob> {

  static final DbTypeBlob INSTANCE = new DbTypeBlob(null, null, null, null, null, false, DbTypeRegistration.ALL);

  private DbTypeBlob(String name, Integer size, Integer scale, String format, String suffix, boolean nativeTypeSupport,
      DbTypeRegistration registration) {

    super(name, size, scale, format, suffix, nativeTypeSupport, registration);
  }

  @Override
  public Class<Blob> getSourceType() {

    return Blob.class;
  }

  @Override
  public Blob toTarget(Blob source) {

    return source;
  }

  @Override
  public Blob toSource(Blob target) {

    return target;
  }

  @Override
  protected DbTypeBlob create(String newName, Integer newSize, Integer newScale, String newFormat, String newSuffix,
      boolean newNativeTypeSupport) {

    return new DbTypeBlob(newName, newSize, newScale, newFormat, newSuffix, newNativeTypeSupport, null);
  }

}
