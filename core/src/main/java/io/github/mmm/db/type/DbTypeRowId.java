/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.type;

import java.sql.Types;

import io.github.mmm.db.name.DbKeyword;

/**
 * {@link DbType} for a regular {@link Long}.
 */
public final class DbTypeRowId extends DbType2Long<Long, DbTypeRowId> {

  static final DbTypeRowId INSTANCE = new DbTypeRowId(null, null, null, null, null, false,
      DbTypeRegistration.CODE_NAME);

  private DbTypeRowId(String name, Integer size, Integer scale, String format, String suffix, boolean nativeTypeSupport,
      DbTypeRegistration registration) {

    super(name, size, scale, format, suffix, nativeTypeSupport, registration);
  }

  @Override
  public int getCode() {

    return Types.ROWID;
  }

  @Override
  String getDefaultName() {

    return DbKeyword.ROWID;
  }

  @Override
  public Class<Long> getSourceType() {

    return Long.class;
  }

  @Override
  public Long toTarget(Long source) {

    return source;
  }

  @Override
  public Long toSource(Long target) {

    return target;
  }

  @Override
  protected DbTypeRowId create(String newName, Integer newSize, Integer newScale, String newFormat, String newSuffix,
      boolean newNativeTypeSupport) {

    return new DbTypeRowId(newName, newSize, newScale, newFormat, newSuffix, newNativeTypeSupport, null);
  }

}
