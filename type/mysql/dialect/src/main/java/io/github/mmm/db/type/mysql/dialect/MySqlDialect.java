/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.type.mysql.dialect;

import io.github.mmm.db.dialect.AbstractDbDialect;
import io.github.mmm.db.dialect.DbDialect;
import io.github.mmm.db.dialect.DbDialectStatementFormatter;
import io.github.mmm.db.orm.Orm;

/**
 * Implementation of {@link DbDialect} for MySQL database.
 */
public final class MySqlDialect extends AbstractDbDialect<MySqlDialect> {

  /**
   * The constructor.
   */
  public MySqlDialect() {

    super(new MySqlTypeMapping());
  }

  /**
   * The constructor.
   *
   * @param orm the {@link Orm}.
   */
  protected MySqlDialect(Orm orm) {

    super(orm);
  }

  @Override
  public String getId() {

    return "mysql";
  }

  @Override
  protected MySqlDialect withOrm(Orm newOrm) {

    return new MySqlDialect(newOrm);
  }

  @Override
  public DbDialectStatementFormatter createFormatter() {

    return new MySqlFormatter(this);
  }

}
