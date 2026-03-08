/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.name.impl;

import java.util.HashMap;
import java.util.Map;

import io.github.mmm.base.text.CaseSyntax;
import io.github.mmm.db.name.DbNamingStrategy;
import io.github.mmm.db.name.DbObject;

/**
 * Implementation of {@link DbNamingStrategy} based on {@link CaseSyntax}.
 *
 * @since 1.0.0
 */
public class DbNamingStrategyCaseSyntax implements DbNamingStrategy {

  private static final Map<CaseSyntax, DbNamingStrategyCaseSyntax> MAP = new HashMap<>();

  private final CaseSyntax syntax;

  DbNamingStrategyCaseSyntax(CaseSyntax syntax) {

    super();
    this.syntax = syntax;
  }

  @Override
  public String convertName(String name, DbObject dbObject) {

    return this.syntax.convert(name);
  }

  /**
   * @param caseSyntax the {@link CaseSyntax}.
   * @return the new {@link DbNamingStrategyCaseSyntax}.
   */
  public static DbNamingStrategyCaseSyntax of(CaseSyntax caseSyntax) {

    return MAP.computeIfAbsent(caseSyntax, s -> new DbNamingStrategyCaseSyntax(s));
  }

}
