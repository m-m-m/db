/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.type;

import java.sql.Blob;

import io.github.mmm.binary.BinaryStream;
import io.github.mmm.binary.BlobAsBinaryStream;
import io.github.mmm.binary.BlobFromBinaryStream;

/**
 * {@link DbType} with {@link BinaryStream} as {@link #getSourceType() Java source type}.
 *
 * @since 1.0.0
 */
@SuppressWarnings("exports")
public final class DbTypeBinaryStream extends DbType2Blob<BinaryStream, DbTypeBinaryStream> {

  static final DbTypeBinaryStream INSTANCE = new DbTypeBinaryStream(null, null, null, null, null, false,
      DbTypeRegistration.TYPE);

  private DbTypeBinaryStream(String name, Integer size, Integer scale, String format, String suffix,
      boolean nativeTypeSupport, DbTypeRegistration registration) {

    super(name, size, scale, format, suffix, nativeTypeSupport, registration);
  }

  @Override
  public Class<BinaryStream> getSourceType() {

    return BinaryStream.class;
  }

  @Override
  public Blob toTarget(BinaryStream source) {

    return BlobFromBinaryStream.of(source);
  }

  @Override
  public BinaryStream toSource(Blob target) {

    if (target instanceof BlobFromBinaryStream wrapper) {
      return wrapper.getStream();
    }
    return BlobAsBinaryStream.of(target);
  }

  @Override
  protected DbTypeBinaryStream create(String newName, Integer newSize, Integer newScale, String newFormat,
      String newSuffix, boolean newNativeTypeSupport) {

    return new DbTypeBinaryStream(newName, newSize, newScale, newFormat, newSuffix, newNativeTypeSupport, null);
  }

}
