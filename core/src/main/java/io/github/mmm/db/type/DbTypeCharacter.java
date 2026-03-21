/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.type;

import java.util.Objects;

/**
 * {@link DbType} for a regular {@link Character}.
 */
public final class DbTypeCharacter extends DbType2Character<Character, DbTypeCharacter> {

  static final DbTypeCharacter INSTANCE = new DbTypeCharacter(null, null, null, null, false, DbTypeRegistration.TYPE);

  private DbTypeCharacter(String name, Integer scale, String format, String suffix, boolean nativeTypeSupport,
      DbTypeRegistration registration) {

    super(name, scale, format, suffix, nativeTypeSupport, registration);
  }

  @Override
  public Class<Character> getSourceType() {

    return Character.class;
  }

  @Override
  public Character toTarget(Character source) {

    return source;
  }

  @Override
  public Character toSource(Character target) {

    return target;
  }

  @Override
  protected DbTypeCharacter create(String newName, Integer newSize, Integer newScale, String newFormat,
      String newSuffix, boolean newNativeTypeSupport) {

    assert (Objects.equals(newSize, 1));
    return new DbTypeCharacter(newName, newScale, newFormat, newSuffix, newNativeTypeSupport, null);
  }

}
