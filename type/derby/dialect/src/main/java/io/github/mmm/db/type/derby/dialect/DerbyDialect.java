/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.type.derby.dialect;

import io.github.mmm.db.dialect.AbstractDbDialect;
import io.github.mmm.db.dialect.DbDialect;
import io.github.mmm.db.dialect.DbDialectStatementFormatter;
import io.github.mmm.db.orm.Orm;

/**
 * Implementation of {@link DbDialect} for Apache Derby database.
 */
public final class DerbyDialect extends AbstractDbDialect<DerbyDialect> {

  /**
   * The constructor.
   */
  public DerbyDialect() {

    super(new DerbyTypeMapping());
  }

  /**
   * The constructor.
   *
   * @param orm the {@link Orm}.
   */
  protected DerbyDialect(Orm orm) {

    super(orm);
  }

  @Override
  public String getId() {

    return "derby";
  }

  @Override
  protected DerbyDialect withOrm(Orm newOrm) {

    return new DerbyDialect(newOrm);
  }

  @Override
  public DbDialectStatementFormatter createFormatter() {

    return new DerbyFormatter(this);
  }

}
