package io.github.mmm.db.impl;

import io.github.mmm.db.dialect.DbContext;

/**
 * Implementation of {@link DbContext} to be used as fallback when no database is actually available.
 */
public class DbContextNone implements DbContext {

  /** The singleton instance. */
  public static final DbContextNone INSTANCE = new DbContextNone();

}
