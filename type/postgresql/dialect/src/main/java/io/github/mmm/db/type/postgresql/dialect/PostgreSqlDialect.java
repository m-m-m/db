/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.type.postgresql.dialect;

import io.github.mmm.db.dialect.AbstractDbDialect;
import io.github.mmm.db.dialect.DbDialect;
import io.github.mmm.db.dialect.DbDialectStatementFormatter;
import io.github.mmm.db.orm.Orm;

/**
 * Implementation of {@link DbDialect} for PostGreSQL database.
 */
public class PostgreSqlDialect extends AbstractDbDialect<PostgreSqlDialect> {

  /**
   * The constructor.
   */
  public PostgreSqlDialect() {

    super(new PostgreSqlTypeMapper());
  }

  /**
   * The constructor.
   *
   * @param orm the {@link Orm}.
   */
  protected PostgreSqlDialect(Orm orm) {

    super(orm);
  }

  @Override
  public String getId() {

    return "postgresql";
  }

  @Override
  protected PostgreSqlDialect withOrm(Orm newOrm) {

    return new PostgreSqlDialect(newOrm);
  }

  @Override
  public DbDialectStatementFormatter createFormatter() {

    return new PostgreSqlFormatter(this);
  }

}
