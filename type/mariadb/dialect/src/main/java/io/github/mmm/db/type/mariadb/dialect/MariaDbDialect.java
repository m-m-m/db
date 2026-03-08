/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.type.mariadb.dialect;

import io.github.mmm.db.dialect.AbstractDbDialect;
import io.github.mmm.db.dialect.DbDialect;
import io.github.mmm.db.dialect.DbDialectStatementFormatter;
import io.github.mmm.db.orm.Orm;

/**
 * Implementation of {@link DbDialect} for MariaDB.
 */
public final class MariaDbDialect extends AbstractDbDialect<MariaDbDialect> {

  /**
   * The constructor.
   */
  public MariaDbDialect() {

    super(new MariaDbTypeMapping());
  }

  /**
   * The constructor.
   *
   * @param orm the {@link Orm}.
   */
  protected MariaDbDialect(Orm orm) {

    super(orm);
  }

  @Override
  public String getId() {

    return "mariadb";
  }

  @Override
  protected MariaDbDialect withOrm(Orm newOrm) {

    return new MariaDbDialect(newOrm);
  }

  @Override
  public DbDialectStatementFormatter createFormatter() {

    return new MariaDbFormatter(this);
  }

}
