/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.schema.impl;

import java.util.stream.Stream;

import io.github.mmm.base.version.VersionNumber;
import io.github.mmm.db.schema.DbChangeLogSource;
import io.github.mmm.db.schema.DbSchemaManager;
import io.github.mmm.db.source.DbSource;

/**
 * {@link DbChangeLogNodeParent} for a specific {@link DbSource}.
 */
public class DbChangeLogSourceImpl extends DbChangeLogNodeParent<VersionNumber, DbChangeLogVersionImpl>
    implements DbChangeLogSource {

  private final DbSource source;

  private final DbChangeLogScriptsImpl beforeScripts;

  private final DbChangeLogScriptsImpl afterScripts;

  DbChangeLogSourceImpl(DbSource source) {

    super();
    this.source = source;
    this.beforeScripts = new DbChangeLogScriptsImpl();
    this.afterScripts = new DbChangeLogScriptsImpl();
  }

  @Override
  public DbSource getSource() {

    return this.source;
  }

  @Override
  public Stream<VersionNumber> getVersions() {

    return this.keys.stream();
  }

  /**
   * @return the latest registered release {@link VersionNumber}.
   */
  @Override
  public VersionNumber getLastVersion() {

    return this.keys.getLast();
  }

  @Override
  protected VersionNumber toKey(String key) {

    return VersionNumber.of(key);
  }

  @Override
  protected DbChangeLogVersionImpl createChild(VersionNumber key) {

    return new DbChangeLogVersionImpl(key);
  }

  /**
   * @return the {@link DbChangeLogScriptsImpl} to execute always before the regular scripts.
   */
  @Override
  public DbChangeLogScriptsImpl getBeforeScripts() {

    return this.beforeScripts;
  }

  /**
   * @return the {@link DbChangeLogScriptsImpl} to execute always after the regular scripts.
   */
  @Override
  public DbChangeLogScriptsImpl getAfterScripts() {

    return this.afterScripts;
  }

  @Override
  public String toString() {

    return DbSchemaManager.PATH_DB_CHANGELOG + this.source;
  }

}
