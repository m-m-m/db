/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.schema.impl;

import java.util.stream.Stream;

import io.github.mmm.db.schema.DbChangeLog;
import io.github.mmm.db.schema.DbSchemaManager;
import io.github.mmm.db.source.DbSource;

/**
 * {@link DbChangeLogNodeParent} for the root of the change-log.
 */
public class DbChangeLogImpl extends DbChangeLogNodeParent<DbSource, DbChangeLogSourceImpl> implements DbChangeLog {

  DbChangeLogImpl() {

    super();
    getOrCreate(DbSource.get().getId()); // default source must always exist
  }

  @Override
  protected DbSource toKey(String key) {

    return DbSource.of(key);
  }

  @Override
  protected DbChangeLogSourceImpl createChild(DbSource key) {

    return new DbChangeLogSourceImpl(key);
  }

  @Override
  public Stream<DbSource> getSources() {

    return this.keys.stream();
  }

  @Override
  public String toString() {

    return DbSchemaManager.PATH_DB_CHANGELOG;
  }

}
