/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.type.sqlserver.dialect;

import io.github.mmm.db.dialect.AbstractDbDialect;
import io.github.mmm.db.dialect.DbDialect;
import io.github.mmm.db.dialect.DbDialectStatementFormatter;
import io.github.mmm.db.orm.Orm;

/**
 * Implementation of {@link DbDialect} for <a href="https://docs.microsoft.com/en-us/sql/">MS SQL Server</a>.
 *
 * @since 1.0.0
 */
public class SqlServerDialect extends AbstractDbDialect<SqlServerDialect> {

  /**
   * The constructor.
   */
  public SqlServerDialect() {

    super(new SqlServerTypeMapper());
  }

  /**
   * The constructor.
   *
   * @param orm the {@link Orm}.
   */
  protected SqlServerDialect(Orm orm) {

    super(orm);
  }

  @Override
  public String getId() {

    return "sqlserver";
  }

  @Override
  protected SqlServerDialect withOrm(Orm newOrm) {

    return new SqlServerDialect(newOrm);
  }

  @Override
  public DbDialectStatementFormatter createFormatter() {

    return new SqlServerFormatter(this);
  }

}
