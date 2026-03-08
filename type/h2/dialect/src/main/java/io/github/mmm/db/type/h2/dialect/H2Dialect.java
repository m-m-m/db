/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.type.h2.dialect;

import java.util.Properties;

import io.github.mmm.db.dialect.AbstractDbDialect;
import io.github.mmm.db.dialect.DbDialect;
import io.github.mmm.db.dialect.DbDialectStatementFormatter;
import io.github.mmm.db.orm.Orm;
import io.github.mmm.db.source.DbSource;

/**
 * Implementation of {@link DbDialect} for H2 database.
 */
public class H2Dialect extends AbstractDbDialect<H2Dialect> {

  /**
   * The constructor.
   */
  public H2Dialect() {

    super(new H2TypeMapping());
  }

  /**
   * The constructor.
   *
   * @param orm the {@link Orm}.
   */
  protected H2Dialect(Orm orm) {

    super(orm);
  }

  @Override
  public String getId() {

    return "h2";
  }

  @Override
  protected H2Dialect withOrm(Orm newOrm) {

    return new H2Dialect(newOrm);
  }

  @Override
  public DbDialectStatementFormatter createFormatter() {

    return new H2Formatter(this);
  }

  @Override
  public void autoConfigure(Properties properties, DbSource source) {

    DbSource.KEY_USER.setIfAbsent(properties, "sa");
    super.autoConfigure(properties, source);
  }

  @Override
  protected String autoConfigureUrl(Properties properties, DbSource source, String kind) {

    return kind + ":h2:mem:";
  }

}
