/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.type.sqlite.dialect;

import java.util.Properties;

import io.github.mmm.db.dialect.AbstractDbDialect;
import io.github.mmm.db.dialect.DbDialect;
import io.github.mmm.db.dialect.DbDialectStatementFormatter;
import io.github.mmm.db.orm.Orm;
import io.github.mmm.db.source.DbSource;

/**
 * Implementation of {@link DbDialect} for SQLite database.
 */
public class SqliteDialect extends AbstractDbDialect<SqliteDialect> {

  /** The filename of the default database. */
  public static final String DEFAULT_DB = "test.db";

  /**
   * The constructor.
   */
  public SqliteDialect() {

    super(new SqliteTypeMapper());
  }

  /**
   * The constructor.
   *
   * @param orm the {@link Orm}.
   */
  protected SqliteDialect(Orm orm) {

    super(orm);
  }

  @Override
  public String getId() {

    return "sqlite";
  }

  @Override
  protected SqliteDialect withOrm(Orm newOrm) {

    return new SqliteDialect(newOrm);
  }

  @Override
  public DbDialectStatementFormatter createFormatter() {

    return new SqliteFormatter(this);
  }

  @Override
  public void autoConfigure(Properties properties, DbSource source) {

    DbSource.KEY_USER.setIfAbsent(properties, "test");
    super.autoConfigure(properties, source);
  }

  @Override
  protected String autoConfigureUrl(Properties properties, DbSource source, String kind) {

    return kind + ":sqlite:target/" + DEFAULT_DB;
  }

  @Override
  public boolean isSupportingSequence() {

    return true; // this is a lie but we better emulate sequence as table
  }

}
