/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.source;

import java.time.Duration;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import io.github.mmm.base.variable.VariableDefinition;
import io.github.mmm.db.name.DbQualifiedName;

/// A [DbSource] identifies a database to connect to as a tenant. It is just a wrapper for a [String] used as [identifier][#getId()]. Most applications only
/// need to talk to a single database and schema. There is always a [default][DbSource#get()] [DbSource]. However, to support multi-tenancy and connecting to
/// multiple different databases the [DbSource] is used to identify the tenant. From a logical point-of-view a [DbSource] is similar to a
/// [javax.sql.DataSource]. However, it is a much higher abstraction. Only in case a [DbSource] points to an RDBMS and you are using JDBC to connect then there
/// will be a [javax.sql.DataSource] associated under the hood to talk to that database. When you configure database settings, you will always include the
/// [DbSource] [identifier][#getId()] in the property names:
///
/// ```
/// db.default.url=jdbc:postgresql://db.company.com:5432/db
/// db.default.dialect=postgresql
/// db.default.user=admin
/// db.default.password=top$ecret
/// db.default.pool=hikari
/// ```
///
/// So in order to define a secondary database you can simply use a [identifier][#getId()] other than "default":
///
/// ```
/// db.h2.url=jdbc:h2:mem:db
/// db.h2.dialect=h2
/// db.h2.user=sa
/// db.h2.password=
/// db.h2.pool=hikari
/// ```
///
/// Now, use [DbSource.of("h2")][DbSource#of(String)] to work on the secondary database.
public final class DbSource implements Comparable<DbSource> {

  private static final DbSource DEFAULT = new DbSource("default");

  private final String id;

  /// The static part of the [property prefix][#getPropertyPrefix()].
  public static final String PROPERTY_PREFIX_DB = "db.";

  /// Value of [kind][#KEY_KIND] for Java DataBase Connectivity (JDBC).
  public static final String VALUE_KIND_JDBC = "jdbc";

  /// Default value of [sequence_increment][#KEY_SEQUENCE_INCREMENT].
  public static final int VALUE_SEQUENCE_INCREMENT_DEFAULT = 10;

  /// [VariableDefinition] for the [database connection URL][DbSourceConfig#getUrl()].
  ///
  /// @see DbSourceConfig#getUrl()
  public static final VariableDefinition<String> KEY_URL = VariableDefinition.ofString("url", null);

  /// [VariableDefinition] for the database host name.
  public static final VariableDefinition<String> KEY_HOST = VariableDefinition.ofString("host", null);

  /// [VariableDefinition] for the database port number.
  public static final VariableDefinition<Integer> KEY_PORT = VariableDefinition.ofInteger("port", null);

  /// [VariableDefinition] for the [user login of the database connection][DbSourceConfig#getUser()].
  ///
  /// @see DbSourceConfig#getUser()
  public static final VariableDefinition<String> KEY_USER = VariableDefinition.ofString("user", null);

  /// [VariableDefinition] for the [password of the database connection][DbSourceConfig#getPassword()].
  ///
  /// @see DbSourceConfig#getPassword()
  public static final VariableDefinition<String> KEY_PASSWORD = VariableDefinition.ofString("password", null);

  /// [VariableDefinition] for the [database name][DbSourceConfig#getDatabase()].
  ///
  /// @see DbSourceConfig#getDatabase()
  public static final VariableDefinition<String> KEY_DATABASE = VariableDefinition.ofString("database", null);

  /// [VariableDefinition] for the [database dialect][DbSourceConfig#getDialect()].
  ///
  /// @see DbSourceConfig#getDialect()
  public static final VariableDefinition<String> KEY_DIALECT = VariableDefinition.ofString("dialect", null);

  /// [VariableDefinition] for the [type of database][DbSourceConfig#getType()].
  ///
  /// @see DbSourceConfig#getType()
  public static final VariableDefinition<String> KEY_TYPE = VariableDefinition.ofString("type", null);

  /// [VariableDefinition] for the [class-name][Class#getName()] of the database driver. Typically not needed and derived from [connection URL][#KEY_URL], but
  /// can be configured for specific cases when meta-drivers (e.g. aurora-jdbc-wrapper) shall be used.
  public static final VariableDefinition<String> KEY_DRIVER_CLASS_NAME = VariableDefinition.ofString("driver.class.name", null);

  /// [VariableDefinition] for the ID of the connection pool to use (e.g. "hikari", "c3po", or "dbcp"). Will be auto-configured if undefined.
  public static final VariableDefinition<String> KEY_POOL_ID = VariableDefinition.ofString("pool.id", null);

  /// [VariableDefinition] for the minimum size of connections kept in the pool.
  public static final VariableDefinition<Integer> KEY_POOL_SIZE_MIN = VariableDefinition.ofInteger("pool.size.min", null);

  /// [VariableDefinition] for the maximum size of connections kept in the pool.
  public static final VariableDefinition<Integer> KEY_POOL_SIZE_MAX = VariableDefinition.ofInteger("pool.size.max", null);

  /// [VariableDefinition] for the [connection timeout][DbSourceConfig#getPoolConnectionTimeout()] of the pool.
  ///
  /// @see DbSourceConfig#getPoolConnectionTimeout()
  public static final VariableDefinition<Duration> KEY_POOL_CONNECTION_TIMEOUT = VariableDefinition.ofDuration("pool.connection.timeout", null);

  /// [VariableDefinition] for the [kind][DbSourceConfig#getKind()] of database connection.
  ///
  /// @see #VALUE_KIND_JDBC
  /// @see DbSourceConfig#getKind()
  public static final VariableDefinition<String> KEY_KIND = VariableDefinition.ofString("kind", VALUE_KIND_JDBC);

  /// [VariableDefinition] for the explicit [schema][DbQualifiedName#getSchema()] to use.
  public static final VariableDefinition<String> KEY_SCHEMA = VariableDefinition.ofString("schema", null);

  /// [VariableDefinition] for the explicit [catalog][DbQualifiedName#getCatalog()] to use.
  public static final VariableDefinition<String> KEY_CATALOG = VariableDefinition.ofString("catalog", null);

  /// [VariableDefinition] for the sequence increment.
  public static final VariableDefinition<Integer> KEY_SEQUENCE_INCREMENT = VariableDefinition.ofInteger("sequence_increment", VALUE_SEQUENCE_INCREMENT_DEFAULT);

  /// The standard keys for the database connection. Other keys will be specific for particular implementations (e.g. connection pools).
  public static final Set<VariableDefinition<?>> VARIABLES = Set.of(KEY_URL, KEY_USER, KEY_PASSWORD, KEY_DIALECT, KEY_TYPE, KEY_KIND, KEY_SCHEMA, KEY_HOST,
      KEY_PORT, KEY_DATABASE, KEY_DRIVER_CLASS_NAME, KEY_SEQUENCE_INCREMENT, KEY_POOL_ID, KEY_POOL_SIZE_MIN, KEY_POOL_SIZE_MAX, KEY_POOL_CONNECTION_TIMEOUT);

  /// The names of [#VARIABLES].
  public static final Set<String> VARIABLE_NAMES = Set.copyOf(VARIABLES.stream().map(VariableDefinition::getName).collect(Collectors.toSet()));

  private DbSource(String id) {

    super();
    Objects.requireNonNull(id);
    this.id = id;
  }

  /// @return the identifier of the database as tenant.
  public String getId() {

    return this.id;
  }

  /// @return the property prefix as "db.«id»." (e.g. "db.default." for the [default source][#get()]).
  /// @see #PROPERTY_PREFIX_DB
  /// @see io.github.mmm.base.metainfo.MetaInfo#withPrefix(String)
  public String getPropertyPrefix() {

    return PROPERTY_PREFIX_DB + this.id + ".";
  }

  /// @param key the unqualified property key.
  /// @return the [qualified][#getPropertyPrefix()] property key.
  public String getPropertyKey(String key) {

    return PROPERTY_PREFIX_DB + this.id + "." + key;
  }

  @Override
  public int hashCode() {

    return this.id.hashCode();
  }

  @Override
  public boolean equals(Object other) {

    if (other == this) {
      return true;
    } else if (other instanceof DbSource source) {
      return this.id.equals(source.id);
    }
    return false;
  }

  @Override
  public int compareTo(DbSource other) {

    if (other == null) {
      return 1;
    }
    if (this.id.equals(DEFAULT.id)) {
      return -1;
    } else if (other.id.equals(DEFAULT.id)) {
      return 1;
    }
    return this.id.compareTo(other.id);
  }

  @Override
  public String toString() {

    return "db." + this.id;
  }

  /// @return the default [DbSource] for the "primary" database. It has the [identifier][#getId()] "default".
  public static DbSource get() {

    return DEFAULT;
  }

  /// @param id the [identifier][#getId()] to wrap as [DbSource].
  /// @return a [DbSource] with the given `id`. Will be the [default][#get()] [DbSource] if `id` is `null` or "default".
  public static DbSource of(String id) {

    if ((id == null) || id.equals(DEFAULT.id)) {
      return DEFAULT;
    }
    return new DbSource(id);
  }

}
