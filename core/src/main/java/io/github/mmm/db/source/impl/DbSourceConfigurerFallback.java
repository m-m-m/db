/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.source.impl;

import java.util.Properties;

import io.github.mmm.db.source.DbSource;
import io.github.mmm.db.source.spi.AbstractDbSourceConfigurer;

/**
 * Fallback implementation for {@link io.github.mmm.db.source.spi.DbSourceConfigurer}.
 */
public class DbSourceConfigurerFallback extends AbstractDbSourceConfigurer {

  @Override
  protected String autoConfigureUser(Properties properties, String type, String url) {

    if ("h2".equals(type)) {
      return "sa";
    }
    return super.autoConfigureUser(properties, type, url);
  }

  @Override
  protected String autoConfigureUrl(Properties properties, String kind, String type, String host, DbSource source) {

    return switch (type) {
      case null -> null;
      case "h2" -> kind + ":h2:mem:";
      case "sqlite" -> kind + ":sqlite:" + autoConfigureDatabase(properties, DEFAULT_DB);
      case "derby" -> {
        if (host == null) {
          yield kind + ":derby:memory:" + autoConfigureDatabase(properties, "");
        } else {
          yield kind + ":derby://" + host + autoConfigurePortSuffix(properties, type) + "/"
              + autoConfigureDatabase(properties, DEFAULT_DB);
        }
      }
      case "postgresql" -> kind + ":postgresql://" + host + autoConfigurePortSuffix(properties, type) + "/"
          + autoConfigureDatabase(properties, "data") + getSchema(properties, "?currentSchema=");
      case "mysql" -> kind + ":mysql://" + host + autoConfigurePortSuffix(properties, type) + "/"
          + autoConfigureDatabase(properties, DEFAULT_DB);
      case "mariadb" -> kind + ":mariadb://" + host + autoConfigurePortSuffix(properties, type) + "/"
          + autoConfigureDatabase(properties, DEFAULT_DB);
      case "oracle" -> kind + ":oracle:thin:@//" + host + autoConfigurePortSuffix(properties, type) + "/"
          + autoConfigureDatabase(properties, "xepdb1");
      case "db2" -> kind + ":db2://" + host + autoConfigurePortSuffix(properties, type) + "/"
          + autoConfigureDatabase(properties, DEFAULT_DB);
      case "sqlserver" -> kind + ":sqlserver://" + host + autoConfigurePortSuffix(properties, type);
      default -> super.autoConfigureUrl(properties, kind, type, host, source);
    };
  }

  private String autoConfigurePortSuffix(Properties properties, String type) {

    String portString;
    Integer port = DbSource.KEY_PORT.get(properties);
    if (port == null) {
      port = autoConfigurePort(properties, type);
    }
    portString = "";
    if (port != null) {
      portString = ":" + port;
    }
    return portString;
  }

  private String getSchema(Properties properties, String prefix) {

    String schema = DbSource.KEY_SCHEMA.get(properties);
    if (schema == null) {
      return "";
    }
    return prefix + schema;
  }

  private String autoConfigureDatabase(Properties properties, String fallback) {

    String database = DbSource.KEY_DATABASE.get(properties);
    if ((database == null) && (fallback != null)) {
      database = fallback;
      if (!database.isEmpty()) {
        DbSource.KEY_DATABASE.set(properties, database);
      }
    }
    return database;
  }

}
