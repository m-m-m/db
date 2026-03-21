/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.type;

import java.sql.Clob;

/**
 * {@link DbType} for a regular {@link Clob}.
 */
@SuppressWarnings("exports")
public final class DbTypeClob extends DbType2Clob<Clob, DbTypeClob> {

  static final DbTypeClob INSTANCE = new DbTypeClob(null, null, null, null, null, false, DbTypeRegistration.ALL);

  private DbTypeClob(String name, Integer size, Integer scale, String format, String suffix, boolean nativeTypeSupport,
      DbTypeRegistration registration) {

    super(name, size, scale, format, suffix, nativeTypeSupport, registration);
  }

  @Override
  public Class<Clob> getSourceType() {

    return Clob.class;
  }

  @Override
  public Clob toTarget(Clob source) {

    return source;
  }

  @Override
  public Clob toSource(Clob target) {

    return target;
  }

  @Override
  protected DbTypeClob create(String newName, Integer newSize, Integer newScale, String newFormat, String newSuffix,
      boolean newNativeTypeSupport) {

    return new DbTypeClob(newName, newSize, newScale, newFormat, newSuffix, newNativeTypeSupport, null);
  }

}
