/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.source.impl;

import java.util.Properties;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.mmm.base.variable.VariableDefinition;
import io.github.mmm.db.source.DbSource;

/**
 * Test of {@link DbSourceConfigurerFallback}.
 */
class DbSourceConfigurerFallbackTest extends Assertions {

  // mongodb+srv://cluster0.example.com/testdb?authSource=$external&authMechanism=MONGODB-AWS
  // mongodb+srv://myDatabaseUser:D1fficultP%40ssw0rd@cluster0.example.mongodb.net/?retryWrites=true&w=majority
  // jdbc:oracle:thin:@//hostname:1521/service.domain.local
  // jdbc:oracle:thin:@hostname:1521/service.domain.local
  // jdbc:oracle:thin:@hostname/service.domain.local
  // jdbc:mysql://localhost:3306/sakila?profileSQL=true
  // jdbc:db2://HOST:50001/SERVER:variable=30
  // jdbc:postgresql://production:5432/dbname
  // jdbc:postgresql://production/
  // jdbc:postgresql://production
  // jdbc:h2:mem
  // r2dbc:driver[:protocol]://[user:password@]host[:port][/path][?option=value]}

  @Test
  void testFromUrl() {

    verify("jdbc:h2:mem:", properties(null, "sa", "sa", "jdbc", "h2", "h2", null, null, null, null));
    verify("jdbc:derby:memory:dbname",
        properties(null, null, null, "jdbc", "derby", "derby", null, "dbname", null, null));
    verify("jdbc:derby:;databaseName=databaseName",
        properties(null, null, null, "jdbc", "derby", "derby", null, "databaseName", null, null));
    verify("jdbc:derby:/data/db/databaseName",
        properties(null, null, null, "jdbc", "derby", "derby", null, "/data/db/databaseName", null, null));
    verify("jdbc:derby://host.net:4711/databaseName;upgrade=true;create=true;territory=ll_CC",
        properties(null, null, null, "jdbc", "derby", "derby", null, "databaseName", "host.net", "4711"));
    verify("jdbc:derby://host.net:4711/memory:myDB;create=true",
        properties(null, null, null, "jdbc", "derby", "derby", null, "myDB", "host.net", "4711"));
    verify("jdbc:sqlite:/data/db/chinook.db",
        properties(null, null, null, "jdbc", "sqlite", "sqlite", null, "/data/db/chinook.db", null, null));
    verify("jdbc:postgresql://admin:t0p$ecr4t@localhost/dbName?currentSchema=myschema", properties(null, "admin",
        "t0p$ecr4t", "jdbc", "postgresql", "postgresql", "myschema", "dbName", "localhost", "5432"));
    verify("jdbc:postgresql://host.name:5432/dbName?currentSchema=myschema",
        properties(null, null, null, "jdbc", "postgresql", "postgresql", "myschema", "dbName", "host.name", "5432"));
    verify("jdbc:mysql://host.com/somedb?currentSchema=someschema",
        properties(null, null, null, "jdbc", "mysql", "mysql", "someschema", "somedb", "host.com", "3306"));
    verify("jdbc:mariadb://maria.es/martadb?useSsl=true&currentSchema=mariaschema",
        properties(null, null, null, "jdbc", "mariadb", "mariadb", "mariaschema", "martadb", "maria.es", "3306"));
    verify("jdbc:sap://myhana.com/?databaseName=myhanadb&encrypt=true&validateCertificate=true",
        properties(null, null, null, "jdbc", "sap", "sap", null, "myhanadb", "myhana.com", "30015"));
    verify("jdbc:oracle:thin:@//host.name.com/databasename",
        properties(null, null, null, "jdbc", "oracle", "oracle", null, "databasename", "host.name.com", "1521"));
    verify("jdbc:sqlserver://microsoft.com;encrypt=true;user=MyUserName;password=$ecr4t;", properties(null,
        "MyUserName", "$ecr4t", "jdbc", "sqlserver", "sqlserver", null, null, "microsoft.com", "1433"));
  }

  @Test
  void testToUrl() {

    verify(properties(null, "sa", null, null, "h2", null), "jdbc:h2:mem:");
    verify(properties(null, null, null, null, "derby", null), "jdbc:derby:memory:");
    verify(properties(null, null, null, null, "derby", null, null, "db"), "jdbc:derby:memory:db");
    verify(properties(null, null, null, "jdbc", "derby", "derby", null, "databaseName", "host.net", null),
        "jdbc:derby://host.net:1527/databaseName");
    verify(properties(null, null, null, "jdbc", "sqlite", "sqlite", null, "/data/db/chinook.db", null, null),
        "jdbc:sqlite:/data/db/chinook.db");
    verify(properties(null, "pgadmin", "pgpass", null, "postgresql", null, null, "dbName", "db.host.org", null),
        "jdbc:postgresql://db.host.org:5432/dbName");
    verify(properties(null, "pgadmin", "pgpass", null, "postgresql", null, "myschema", "dbName", "db.host.org", null),
        "jdbc:postgresql://db.host.org:5432/dbName?currentSchema=myschema");
    verify(properties(null, "root", "test", null, "mysql", null, null, "dbname", "db.host.com", null),
        "jdbc:mysql://db.host.com:3306/dbname");
    verify(properties(null, "user1", "pwd2", null, "mariadb", null, null, "juliadb", "maria.es", null),
        "jdbc:mariadb://maria.es:3306/juliadb");
    verify(properties(null, "admin", "passwd", null, "oracle", null, null, "databasename", "host.name.com", null),
        "jdbc:oracle:thin:@//host.name.com:1521/databasename");
    verify(properties(null, "master", "servant", null, "db2", null, null, "dbtwo", "db.two", null),
        "jdbc:db2://db.two:50000/dbtwo");

  }

  private void verify(Properties in, String url) {

    assertThat(DbSource.KEY_URL.get(in)).isNull();
    Properties out = new Properties();
    out.putAll(in);
    DbSource.KEY_URL.set(out, url);
    for (VariableDefinition<?> key : DbSource.VARIABLES) {
      applyDefault(key, out);
      applyDefault(key, in);
    }
    DbSource.KEY_DIALECT.setIfAbsent(out, DbSource.KEY_TYPE.get(out));
    DbSource.KEY_PASSWORD.setIfAbsent(out, DbSource.KEY_USER.get(out));
    verify(in, out);
  }

  private <T> void applyDefault(VariableDefinition<T> key, Properties properties) {

    key.setIfAbsent(properties, key.getDefaultValue());
  }

  private void verify(String url, Properties out) {

    Properties in = properties(url);
    DbSource.KEY_URL.set(out, url);

    verify(in, out);
  }

  // arrange
  private void verify(Properties in, Properties out) {

    DbSourceConfigurerFallback configurer = new DbSourceConfigurerFallback();
    assertThat(in).isNotEqualTo(out);

    // act
    configurer.autoConfigure(in, DbSource.get());

    // assert
    assertThat(in).isEqualTo(out);
  }

  private Properties properties(String url) {

    return properties(url, null, null, null, null, null, null, null, null, null);
  }

  private Properties properties(String url, String user, String password, String kind, String type, String dialect) {

    return properties(url, user, password, kind, type, dialect, null, null, null, null);
  }

  private Properties properties(String url, String user, String password, String kind, String type, String dialect,
      String schema, String database) {

    return properties(url, user, password, kind, type, dialect, schema, database, null, null);
  }

  private Properties properties(String url, String user, String password, String kind, String type, String dialect,
      String schema, String database, String host, String port) {

    Properties properties = new Properties();
    DbSource.KEY_URL.set(properties, url);
    DbSource.KEY_USER.set(properties, user);
    DbSource.KEY_PASSWORD.set(properties, password);
    DbSource.KEY_KIND.set(properties, kind);
    DbSource.KEY_TYPE.set(properties, type);
    DbSource.KEY_DIALECT.set(properties, dialect);
    DbSource.KEY_SCHEMA.set(properties, schema);
    DbSource.KEY_DATABASE.setAsString(properties, database);
    DbSource.KEY_HOST.set(properties, host);
    DbSource.KEY_PORT.setAsString(properties, port);
    return properties;
  }

}
