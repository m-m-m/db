/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.type.oracle.dialect;

import java.util.Properties;

import io.github.mmm.db.dialect.AbstractDbDialect;
import io.github.mmm.db.dialect.DbDialect;
import io.github.mmm.db.dialect.DbDialectStatementFormatter;
import io.github.mmm.db.orm.Orm;
import io.github.mmm.db.source.DbSource;

/**
 * Implementation of {@link DbDialect} for Oracle database.
 */
public class OracleDialect extends AbstractDbDialect<OracleDialect> {

  /**
   * The constructor.
   */
  public OracleDialect() {

    super(new OracleTypeMapping());
  }

  /**
   * The constructor.
   *
   * @param orm the {@link Orm}.
   */
  protected OracleDialect(Orm orm) {

    super(orm);
  }

  @Override
  public String getId() {

    return "oracle";
  }

  @Override
  public String getType() {

    return "oracle";
  }

  @Override
  protected OracleDialect withOrm(Orm newOrm) {

    return new OracleDialect(newOrm);
  }

  @Override
  public DbDialectStatementFormatter createFormatter() {

    return new OracleFormatter(this);
  }

  @Override
  protected String autoConfigureUrl(Properties properties, DbSource source, String kind) {

    return kind + ":oracle:thin:@//localhost:1521/xepdb1";
  }

}
