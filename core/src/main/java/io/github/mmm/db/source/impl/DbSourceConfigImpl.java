/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.source.impl;

import io.github.mmm.base.metainfo.MetaInfo;
import io.github.mmm.db.name.DbQualifiedName;
import io.github.mmm.db.pool.DbConnectionPool;
import io.github.mmm.db.source.DbSource;
import io.github.mmm.db.source.DbSourceConfig;

/**
 * Implementation of {@link DbSourceConfig}.
 */
public class DbSourceConfigImpl implements DbSourceConfig {

  private final DbSource source;

  private final MetaInfo metaInfo;

  private final DbQualifiedName qualifiedNameTemplate;

  private DbConnectionPool<?> pool;

  /**
   * The constructor.
   *
   * @param source the {@link #getSource() source}.
   * @param metaInfo the {@link #getMetaInfo() metaInfo}.
   */
  public DbSourceConfigImpl(DbSource source, MetaInfo metaInfo) {

    super();
    this.source = source;
    this.metaInfo = metaInfo;
    this.qualifiedNameTemplate = new DbQualifiedName("template", getSchema(), getCatalog());
  }

  @Override
  public DbSource getSource() {

    return this.source;
  }

  @Override
  public MetaInfo getMetaInfo() {

    return this.metaInfo;
  }

  /**
   * @return the {@link DbConnectionPool}.
   */
  public DbConnectionPool<?> getConnectionPool() {

    return this.pool;
  }

  /**
   * @param pool new value of {@link #getPoolId()}.
   */
  public void setConnectionPool(DbConnectionPool<?> pool) {

    if (this.pool != null) {
      throw new IllegalStateException();
    }
    this.pool = pool;
  }

  @Override
  public DbQualifiedName getQualifiedNameTemplate() {

    return this.qualifiedNameTemplate;
  }

  @Override
  public String toString() {

    return this.source + "@" + "[" + this.metaInfo.get(DbSource.KEY_URL) + "]";
  }

}
