/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.type;

import java.sql.SQLXML;

/**
 * {@link DbType} for a regular {@link Byte}.
 */
@SuppressWarnings("exports")
public final class DbTypeSqlXml extends DbType2SqlXml<SQLXML, DbTypeSqlXml> {

  static final DbTypeSqlXml INSTANCE = new DbTypeSqlXml(null, null, null, null, null, false, DbTypeRegistration.TYPE);

  private DbTypeSqlXml(String name, Integer size, Integer scale, String format, String suffix,
      boolean nativeTypeSupport, DbTypeRegistration registration) {

    super(name, size, scale, format, suffix, nativeTypeSupport, registration);
  }

  @Override
  public Class<SQLXML> getSourceType() {

    return SQLXML.class;
  }

  @Override
  public SQLXML toTarget(SQLXML source) {

    return source;
  }

  @Override
  public SQLXML toSource(SQLXML target) {

    return target;
  }

  @Override
  protected DbTypeSqlXml create(String newName, Integer newSize, Integer newScale, String newFormat, String newSuffix,
      boolean newNativeTypeSupport) {

    return new DbTypeSqlXml(newName, newSize, newScale, newFormat, newSuffix, newNativeTypeSupport, null);
  }

}
