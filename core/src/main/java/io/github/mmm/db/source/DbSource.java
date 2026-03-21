/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.source;

import java.time.Duration;
import java.util.Objects;
import java.util.Set;

import io.github.mmm.base.variable.VariableDefinition;
import io.github.mmm.db.name.DbQualifiedName;

/**
 * A {@link DbSource} identifies a database to connect to as a tenant. It is just a wrapper for a {@link String} used as
 * {@link #getId() identifier}. Most applications only need to talk to a single database and schema. There is always a
 * {@link DbSource#get() default} {@link DbSource}. However, to support multi-tenancy and connecting to multiple
 * different databases the {@link DbSource} is used to identify the tenant. <br>
 * From a logical point-of-view a {@link DbSource} is similar to a {@link javax.sql.DataSource}. However, it is a much
 * higher abstraction. Only in case a {@link DbSource} points to an RDBMS and you are using JDBC to connect then there
 * will be a {@link javax.sql.DataSource} associated under the hood to talk to that database.<br>
 * When you configure database settings, you will always include the {@link DbSource} {@link #getId() identifier} in the
 * property names:
 *
 * <pre>
 * db.default.url=jdbc:postgresql://db.company.com:5432/db
 * db.default.dialect=postgresql
 * db.default.user=admin
 * db.default.password=top$ecret
 * db.default.pool=hikari
 * </pre>
 *
 * So in order to define a secondary database you can simply use a {@link #getId() identifier} other than "default":
 *
 * <pre>
 * db.h2.url=jdbc:h2:mem:db
 * db.h2.dialect=h2
 * db.h2.user=sa
 * db.h2.password=
 * db.h2.pool=hikari
 * </pre>
 *
 * Now, use {@link DbSource#of(String) DbSource.of}("h2") to work on the secondary database.
 */
public final class DbSource implements Comparable<DbSource> {

  private static final DbSource DEFAULT = new DbSource("default");

  private final String id;

  /** The static part of the {@link #getPropertyPrefix() property prefix}. */
  public static final String PROPERTY_PREFIX_DB = "db.";

  /** Value of {@link #KEY_KIND kind} for Java DataBase Connectivity (JDBC). */
  public static final String VALUE_KIND_JDBC = "jdbc";

  /** Default value of {@link #KEY_SEQUENCE_INCREMENT sequence_increment}. */
  public static final int VALUE_SEQUENCE_INCREMENT_DEFAULT = 10;

  /**
   * {@link VariableDefinition} for the {@link DbSourceConfig#getUrl() database connection URL}.
   *
   * @see DbSourceConfig#getUrl()
   */
  public static final VariableDefinition<String> KEY_URL = VariableDefinition.ofString("url", null);

  /** {@link VariableDefinition} for the database host name. */
  public static final VariableDefinition<String> KEY_HOST = VariableDefinition.ofString("host", null);

  /** {@link VariableDefinition} for the database port number. */
  public static final VariableDefinition<Integer> KEY_PORT = VariableDefinition.ofInteger("port", null);

  /**
   * {@link VariableDefinition} for the {@link DbSourceConfig#getUser() user login of the database connection}.
   *
   * @see DbSourceConfig#getUser()
   */
  public static final VariableDefinition<String> KEY_USER = VariableDefinition.ofString("user", null);

  /**
   * {@link VariableDefinition} for the {@link DbSourceConfig#getPassword() password of the database connection}.
   *
   * @see DbSourceConfig#getPassword()
   */
  public static final VariableDefinition<String> KEY_PASSWORD = VariableDefinition.ofString("password", null);

  /**
   * {@link VariableDefinition} for the {@link DbSourceConfig#getDatabase() database name}.
   *
   * @see DbSourceConfig#getDatabase()
   */
  public static final VariableDefinition<String> KEY_DATABASE = VariableDefinition.ofString("database", null);

  /**
   * {@link VariableDefinition} for the {@link DbSourceConfig#getDialect() database dialect}.
   *
   * @see DbSourceConfig#getDialect()
   */
  public static final VariableDefinition<String> KEY_DIALECT = VariableDefinition.ofString("dialect", null);

  /**
   * {@link VariableDefinition} for the {@link DbSourceConfig#getType() type of database}.
   *
   * @see DbSourceConfig#getType()
   */
  public static final VariableDefinition<String> KEY_TYPE = VariableDefinition.ofString("type", null);

  /**
   * {@link VariableDefinition} for the {@link Class#getName() class-name} of the database driver. Typically not needed
   * and derived from {@link #KEY_URL connection URL}, but can be configured for specific cases when meta-drivers (e.g.
   * aurora-jdbc-wrapper) shall be used.
   */
  public static final VariableDefinition<String> KEY_DRIVER_CLASS_NAME = VariableDefinition
      .ofString("driver.class.name", null);

  /**
   * {@link VariableDefinition} for the ID of the connection pool to use (e.g. "hikari", "c3po", or "dbcp"). Will be
   * auto-configured if undefined.
   */
  public static final VariableDefinition<String> KEY_POOL_ID = VariableDefinition.ofString("pool.id", null);

  /**
   * {@link VariableDefinition} for the minimum size of connections kept in the pool.
   */
  public static final VariableDefinition<Integer> KEY_POOL_SIZE_MIN = VariableDefinition.ofInteger("pool.size.min",
      null);

  /**
   * {@link VariableDefinition} for the maximum size of connections kept in the pool.
   */
  public static final VariableDefinition<Integer> KEY_POOL_SIZE_MAX = VariableDefinition.ofInteger("pool.size.max",
      null);

  /**
   * {@link VariableDefinition} for the {@link DbSourceConfig#getPoolConnectionTimeout() connection timeout} of the
   * pool.
   *
   * @see DbSourceConfig#getPoolConnectionTimeout()
   */
  public static final VariableDefinition<Duration> KEY_POOL_CONNECTION_TIMEOUT = VariableDefinition
      .ofDuration("pool.connection.timeout", null);

  /**
   * {@link VariableDefinition} for the {@link DbSourceConfig#getKind() kind} of database connection.
   *
   * @see #VALUE_KIND_JDBC
   * @see DbSourceConfig#getKind()
   */
  public static final VariableDefinition<String> KEY_KIND = VariableDefinition.ofString("kind", VALUE_KIND_JDBC);

  /**
   * {@link VariableDefinition} for the explicit {@link DbQualifiedName#getSchema() schema} to use.
   */
  public static final VariableDefinition<String> KEY_SCHEMA = VariableDefinition.ofString("schema", null);

  /**
   * {@link VariableDefinition} for the explicit {@link DbQualifiedName#getCatalog() catalog} to use.
   */
  public static final VariableDefinition<String> KEY_CATALOG = VariableDefinition.ofString("catalog", null);

  /** {@link VariableDefinition} for the sequence increment. */
  public static final VariableDefinition<Integer> KEY_SEQUENCE_INCREMENT = VariableDefinition
      .ofInteger("sequence_increment", VALUE_SEQUENCE_INCREMENT_DEFAULT);

  /**
   * The standard keys for the database connection. Other keys will be specific for particular implementations (e.g.
   * connection pools).
   */
  public static final Set<VariableDefinition<?>> VARIABLES = Set.of(KEY_URL, KEY_USER, KEY_PASSWORD, KEY_DIALECT,
      KEY_TYPE, KEY_KIND, KEY_SCHEMA, KEY_HOST, KEY_PORT, KEY_DATABASE, KEY_DRIVER_CLASS_NAME, KEY_SEQUENCE_INCREMENT,
      KEY_POOL_ID, KEY_POOL_SIZE_MIN, KEY_POOL_SIZE_MAX, KEY_POOL_CONNECTION_TIMEOUT);

  private DbSource(String id) {

    super();
    Objects.requireNonNull(id);
    this.id = id;
  }

  /**
   * @return the identifier of the database as tenant.
   */
  public String getId() {

    return this.id;
  }

  /**
   * @return the property prefix as "db.«id»." (e.g. "db.default." for the {@link #get() default source}).
   * @see #PROPERTY_PREFIX_DB
   * @see io.github.mmm.base.metainfo.MetaInfo#with(String)
   */
  public String getPropertyPrefix() {

    return PROPERTY_PREFIX_DB + this.id + ".";
  }

  /**
   * @param key the unqualified property key.
   * @return the {@link #getPropertyPrefix() qualified} property key.
   */
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

  /**
   * @return the default {@link DbSource} for the "primary" database. It has the {@link #getId() identifier} "default".
   */
  public static DbSource get() {

    return DEFAULT;
  }

  /**
   * @param id the {@link #getId() identifier} to wrap as {@link DbSource}.
   * @return a {@link DbSource} with the given {@code id}. Will be the {@link #get() default} {@link DbSource} if
   *         {@code id} is {@code null} or "default".
   */
  public static DbSource of(String id) {

    if ((id == null) || id.equals(DEFAULT.id)) {
      return DEFAULT;
    }
    return new DbSource(id);
  }

}
