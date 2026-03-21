/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.source.spi;

import java.util.Locale;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.github.mmm.base.variable.VariableDefinition;
import io.github.mmm.db.source.DbSource;
import io.github.mmm.db.source.DbSourceConfig;

/**
 * Abstract base implementation for {@link DbSourceConfigurer}.
 */
public abstract class AbstractDbSourceConfigurer implements DbSourceConfigurer {

  /** The filename of the default database. */
  public static final String DEFAULT_DB = "db";

  private static final Pattern HOST_PATTERN = Pattern
      // ........1.........23................4.5...........6.........7.8.........9.10
      .compile(":(//|@//|@)(([a-zA-Z0-9_.-]+)(:([^@]+))?@)?([^/?:;]+)(:([0-9]+))?(/([a-zA-Z0-9_.-]+))?");

  private static final int HOST_GROUP_USER = 3;

  private static final int HOST_GROUP_PASSWORD = 5;

  private static final int HOST_GROUP_HOST = 6;

  private static final int HOST_GROUP_PORT = 8;

  private static final int HOST_GROUP_DATABASE = 10;

  private static final Set<String> NO_KIND = Set.of("http", "https", "ftp", "ftps", "scp", "git");

  private static final Set<String> MAIN_KIND = Set.of("jdbc", "r2dbc");

  @Override
  public boolean autoConfigure(Properties properties, DbSource source) {

    String url = DbSource.KEY_URL.get(properties);

    String urlLower = null;
    if (url != null) {
      autoConfigureFromUrl(properties, url);
    }

    String kind = DbSource.KEY_KIND.get(properties);
    String type = DbSource.KEY_TYPE.get(properties);
    String user = DbSource.KEY_USER.get(properties);
    String password = DbSource.KEY_PASSWORD.get(properties);
    String host = DbSource.KEY_HOST.get(properties);

    if (user == null) {
      user = autoConfigureUser(properties, type, urlLower);
      DbSource.KEY_USER.set(properties, user);
    }

    DbSource.KEY_PASSWORD.setIfAbsent(properties, user); // convention over configuration for local development
    DbSource.KEY_DIALECT.setIfAbsent(properties, type);

    if (url == null) {
      url = autoConfigureUrl(properties, kind, type, host, source);
      DbSource.KEY_URL.set(properties, url);
    }
    DbSource.KEY_KIND.setIfAbsent(properties, kind);
    return (url != null) && (user != null) && (password != null);
  }

  /**
   * @param properties the {@link Properties}.
   * @param url the {@link DbSource#KEY_URL URL}. Not {@code null}.
   */
  protected void autoConfigureFromUrl(Properties properties, String url) {

    String kind = null;
    String type = null;
    int colon = url.indexOf(':');
    if (colon > 0) {
      int plus = url.indexOf('+'); // mongodb+srv:// --> mongodb
      if ((plus < 0) || (plus >= colon)) {
        plus = colon;
      }
      kind = url.substring(0, plus).toLowerCase(Locale.ROOT);
      if (!NO_KIND.contains(kind)) {
        String existingKind = DbSource.KEY_KIND.setIfAbsent(properties, kind);
        if (MAIN_KIND.contains(kind)) {
          type = autoConfigureType(properties, url, colon + 1);
        }
        if (existingKind != null) {
          kind = existingKind;
        }
      }
    }
    autoConfigureFromUrlAtHost(properties, url, type);
    int queryStart = url.lastIndexOf('?');
    if (queryStart > 0) {
      String query = url.substring(queryStart);
      autoConfigureFromQuery(properties, query);
    } else {
      autoConfigureCsvProperty(properties, url, ";user=", DbSource.KEY_USER);
      autoConfigureCsvProperty(properties, url, ";password=", DbSource.KEY_PASSWORD);
      autoConfigureCsvProperty(properties, url, ";databaseName=", DbSource.KEY_DATABASE);
    }
  }

  private void autoConfigureCsvProperty(Properties properties, String url, String prefix,
      VariableDefinition<String> key) {

    int userStart = indexAfter(url, prefix);
    if (userStart > 0) {
      int userEnd = url.indexOf(';', userStart);
      if (userEnd < 0) {
        userEnd = url.length();
      }
      String user = url.substring(userStart, userEnd);
      key.setIfAbsent(properties, user);
    }
  }

  /**
   * @param properties the {@link Properties}.
   * @param query the query part of the {@link DbSource#KEY_URL URL}. Not {@code null}.
   */
  protected void autoConfigureFromQuery(Properties properties, String query) {

    int schemaStart = indexAfter(query, "currentSchema=", "search_path=");
    if (schemaStart > 0) {
      int schemaEnd = query.indexOf('&', schemaStart);
      if (schemaEnd < schemaStart) {
        schemaEnd = query.indexOf(',', schemaStart);
        if (schemaEnd < schemaStart) {
          schemaEnd = query.length();
        }
      }
      String schema = query.substring(schemaStart, schemaEnd);
      DbSource.KEY_SCHEMA.setIfAbsent(properties, schema);
    }
    int databaseStart = indexAfter(query, "databaseName=");
    if (databaseStart > 0) {
      int databaseEnd = query.indexOf('&', databaseStart);
      if (databaseEnd < databaseStart) {
        databaseEnd = query.length();
      }
      String database = query.substring(databaseStart, databaseEnd);
      DbSource.KEY_DATABASE.setIfAbsent(properties, database);
    }

  }

  /**
   * Retrieves host, port, user, password, and database if present in URL.
   *
   * @param properties the {@link Properties}.
   * @param url the {@link DbSource#KEY_URL URL}. Not {@code null}.
   * @param type the {@link DbSource#KEY_TYPE type}.
   */
  protected void autoConfigureFromUrlAtHost(Properties properties, String url, String type) {

    Matcher matcher = HOST_PATTERN.matcher(url);
    if (matcher.find()) {
      String user = matcher.group(HOST_GROUP_USER);
      String password = matcher.group(HOST_GROUP_PASSWORD);
      String host = matcher.group(HOST_GROUP_HOST);
      String portString = matcher.group(HOST_GROUP_PORT);
      Integer port = null;
      if (portString == null) {
        port = autoConfigurePort(properties, type);
      } else {
        port = Integer.valueOf(portString);
      }
      String database = matcher.group(HOST_GROUP_DATABASE);
      DbSource.KEY_USER.setIfAbsent(properties, user);
      DbSource.KEY_PASSWORD.setIfAbsent(properties, password);
      DbSource.KEY_HOST.setIfAbsent(properties, host);
      DbSource.KEY_PORT.setIfAbsent(properties, port);
      DbSource.KEY_DATABASE.setIfAbsent(properties, database);
    }
  }

  /**
   * @param properties the {@link Properties}.
   * @param type the {@link DbSource#KEY_TYPE database type}. Not {@code null}.
   * @return the default port for the given database {@code type}.
   */
  protected Integer autoConfigurePort(Properties properties, String type) {

    return switch (type) {
      case null -> null;
      case "derby" -> 1527;
      case "postgresql" -> 5432;
      case "mysql" -> 3306;
      case "mariadb" -> 3306;
      case "sap" -> 30015; // 3xx15 where xx is instance number
      case "oracle" -> 1521;
      case "db2" -> 50000;
      case "sqlserver" -> 1433;
      case "informix" -> 9088;
      case "sybase" -> 5000;
      case "snowflake" -> 443;
      default -> null;
    };
  }

  private static int indexAfter(String string, String... substrings) {

    for (String substring : substrings) {
      int index = string.indexOf(substring);
      if (index >= 0) {
        return index + substring.length();
      }
    }
    return -1;
  }

  private String autoConfigureType(Properties properties, String url, int start) {

    String type = null;
    if (start > 0) {
      int end = url.indexOf(':', start);
      if (end > start) {
        type = url.substring(start, end).toLowerCase(Locale.ROOT);
        String existingType = DbSource.KEY_TYPE.setIfAbsent(properties, type);
        if (existingType != null) {
          type = existingType;
        }
        String database = DbSource.KEY_DATABASE.get(properties);
        if (database == null) {
          database = autoConfigureDatabase(properties, url, type, end + 1);
          if (database != null) {
            DbSource.KEY_DATABASE.set(properties, database);
          }
        }
      }
    }
    return type;
  }

  /**
   * @param properties the {@link Properties}.
   * @param url the {@link DbSource#KEY_URL URL}. Not {@code null}.
   * @param type the {@link DbSource#KEY_TYPE database type}. Not {@code null}.
   * @param start the start index in {@code url} pointing to the first character after the {@code type} and colon. E.g.
   *        for "jdbc:sqlite:/data/db" this will be 12 and pointing to the first slash.
   * @return the auto-configured {@link DbSource#KEY_DATABASE database name} or {@code null} if not found.
   */
  protected String autoConfigureDatabase(Properties properties, String url, String type, int start) {

    String database = null;
    if ("sqlite".equals(type)) {
      database = url.substring(start);
    } else if ("derby".equals(type)) {
      String rest = url.substring(start);
      if (rest.startsWith("//")) {
        int slash = rest.indexOf('/', 2);
        if (slash > 0) {
          rest = rest.substring(slash + 1);
        } else {
          return null;
        }
      }
      if (rest.startsWith("memory:")) {
        rest = rest.substring(7);
      }
      int semicolon = rest.indexOf(';');
      if (semicolon >= 0) {
        rest = rest.substring(0, semicolon);
      }
      if (!rest.isEmpty()) {
        database = rest;
      }
    }
    return database;
  }

  /**
   * @param properties the {@link Properties}.
   * @param type the {@link DbSource#KEY_TYPE type of database connection}.
   * @param url the {@link DbSource#KEY_URL URL}. May be {@code null}.
   * @return the auto-configured {@link DbSource#KEY_USER database user} or {@code null} if there is no meaningful
   *         default.
   */
  protected String autoConfigureUser(Properties properties, String type, String url) {

    return null;
  }

  /**
   * @param properties the {@link Properties} from {@link DbSource} to auto-configure for {@link DbSourceConfig}.
   * @param kind the {@link DbSource#KEY_KIND kind of database connection}.
   * @param type the {@link DbSource#KEY_TYPE type of database connection}.
   * @param host the {@link DbSource#KEY_HOST host of the database connection}.
   * @param source the {@link DbSource}.
   * @return the auto-configured {@link DbSource#KEY_URL database connection URL} or {@code null} if there is no
   *         meaningful default.
   */
  protected String autoConfigureUrl(Properties properties, String kind, String type, String host, DbSource source) {

    return null;
  }
}
