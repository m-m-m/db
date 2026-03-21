/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.orm.impl;

import java.util.Properties;

import io.github.mmm.base.exception.ObjectNotFoundException;
import io.github.mmm.db.dialect.AbstractDbDialect;
import io.github.mmm.db.dialect.DbDialect;
import io.github.mmm.db.dialect.DbDialectProvider;
import io.github.mmm.db.source.DbSource;
import io.github.mmm.db.source.spi.AbstractDbSourceConfigurer;

/**
 * Implementation of {@link AbstractDbSourceConfigurer} via available {@link DbDialect}.
 */
public class OrmDbSourceConfigurer extends AbstractDbSourceConfigurer {

  @Override
  public boolean autoConfigure(Properties properties, DbSource source) {

    AbstractDbDialect<?> dialect = getDialect(properties, source);
    dialect.autoConfigure(properties, source);
    return super.autoConfigure(properties, source);
  }

  @Override
  protected String autoConfigureUrl(Properties properties, String kind, String type, String host, DbSource source) {

    AbstractDbDialect<?> dialect = getDialect(properties, source);
    dialect.autoConfigure(properties, source);
    return super.autoConfigureUrl(properties, kind, type, host, source);
  }

  private AbstractDbDialect<?> getDialect(Properties properties, DbSource source) {

    DbDialectProvider provider = DbDialectProvider.get();
    DbDialect dialect = null;
    String dialectId = DbSource.KEY_DIALECT.get(properties);
    if (dialectId == null) {
      String url = DbSource.KEY_URL.get(properties);
      if (url == null) {
        for (DbDialect d : provider) {
          if (dialect != null) {
            dialect = null;
            break; // force ObjectNotFoundException because dialect is ambiguous (multiple dialects present)
          }
          dialect = d;
        }
      } else {
        dialect = provider.getByDbUrl(url);
      }
      if (dialect == null) {
        throw new ObjectNotFoundException("Property", source.getPropertyKey(DbSource.KEY_DIALECT.getName()));
      }
      properties.put(DbSource.KEY_DIALECT, dialect.getId());
    } else {
      dialect = provider.get(dialectId);
    }
    return (AbstractDbDialect<?>) dialect;
  }
}
