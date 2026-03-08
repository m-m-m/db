/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.statement;

import io.github.mmm.db.dialect.AbstractDbDialect;
import io.github.mmm.db.dialect.DbDialectStatementFormatter;
import io.github.mmm.db.orm.Orm;
import io.github.mmm.db.typemapping.DbTypeMapping;

/**
 * Implementation of {@link AbstractDbDialect} for testing SQL database emulation.
 */
public class SqlDialect extends AbstractDbDialect<SqlDialect> {

  /**
   * The constructor.
   */
  public SqlDialect() {

    super(new DbTypeMapping());
  }

  @Override
  public String getId() {

    return "sql";
  }

  @Override
  public DbDialectStatementFormatter createFormatter() {

    return new DbDialectStatementFormatter(this);
  }

  @Override
  protected SqlDialect withOrm(Orm newOrm) {

    throw new UnsupportedOperationException();
  }

}
