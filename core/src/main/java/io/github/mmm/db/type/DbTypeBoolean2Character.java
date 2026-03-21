/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.type;

import java.util.Objects;

/**
 * Implementation of {@link DbType} for a {@link Boolean} mapped as {@link Character} like suggested by
 * <a href="https://asktom.oracle.com/pls/apex/asktom.search?tag=boolean-datatype">Tom</a> for older Oracle databases.
 */
public final class DbTypeBoolean2Character extends DbType2Character<Boolean, DbTypeBoolean2Character> {

  static final DbTypeBoolean2Character INSTANCE = new DbTypeBoolean2Character(null, null, null,
      "CHECK (${COLUMN} IN ( 'Y', 'N' ))", false, DbTypeRegistration.NONE);

  private static final Character TRUE = Character.valueOf('Y');

  private static final Character FALSE = Character.valueOf('N');

  private DbTypeBoolean2Character(String name, Integer scale, String format, String suffix, boolean nativeTypeSupport,
      DbTypeRegistration registration) {

    super(name, scale, format, suffix, nativeTypeSupport, registration);
  }

  @Override
  public Class<Boolean> getSourceType() {

    return Boolean.class;
  }

  @Override
  public Character toTarget(Boolean source) {

    if (source.booleanValue()) {
      return TRUE;
    } else {
      return FALSE;
    }
  }

  @Override
  public Boolean toSource(Character target) {

    if (TRUE.equals(target)) {
      return Boolean.TRUE;
    } else if (FALSE.equals(target)) {
      return Boolean.FALSE;
    }
    throw new IllegalArgumentException(target.toString());
  }

  @Override
  protected DbTypeBoolean2Character create(String newName, Integer newSize, Integer newScale, String newFormat,
      String newSuffix, boolean newNativeTypeSupport) {

    assert (Objects.equals(newSize, 1));
    return new DbTypeBoolean2Character(newName, newScale, newFormat, newSuffix, newNativeTypeSupport, null);
  }

}
