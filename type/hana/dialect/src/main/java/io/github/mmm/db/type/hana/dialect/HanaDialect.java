/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.type.hana.dialect;

import io.github.mmm.db.dialect.AbstractDbDialect;
import io.github.mmm.db.dialect.DbDialect;
import io.github.mmm.db.dialect.DbDialectStatementFormatter;
import io.github.mmm.db.orm.Orm;

/**
 * Implementation of {@link DbDialect} for SAP Hana database.
 */
public final class HanaDialect extends AbstractDbDialect<HanaDialect> {

  /**
   * The constructor.
   */
  public HanaDialect() {

    super(new HanaTypeMapping());
  }

  /**
   * The constructor.
   *
   * @param orm the {@link Orm}.
   */
  protected HanaDialect(Orm orm) {

    super(orm);
  }

  @Override
  public String getId() {

    return "sap";
  }

  @Override
  protected HanaDialect withOrm(Orm newOrm) {

    return new HanaDialect(newOrm);
  }

  @Override
  public DbDialectStatementFormatter createFormatter() {

    return new HanaFormatter(this);
  }

}
