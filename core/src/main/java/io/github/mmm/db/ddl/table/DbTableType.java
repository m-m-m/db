/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.ddl.table;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/// Name of a database object (schema, table, column, etc.).
public final class DbTableType {

  private static final Map<String, DbTableType> STANDARD_TYPES = new HashMap<>();

  /// [DbTableType] for a regular [database table][DbTable].
  public static final DbTableType TABLE = new DbTableType("TABLE", true);

  /// [DbTableType] for a view that selects from other [DbTable]s.
  public static final DbTableType VIEW = new DbTableType("VIEW", true);

  /// [DbTableType] for a synonym that imports a [DbTable] from a different catalog/schema.
  public static final DbTableType SYNONYM = new DbTableType("SYNONYM", true);

  static {
    STANDARD_TYPES.put("BASE TABLE", TABLE); // workaround for h2
  }

  private final String type;

  private DbTableType(String type, boolean standard) {

    super();
    Objects.requireNonNull(type);
    this.type = type;
  }

  /// @return the type as [String].
  public String get() {

    return this.type;
  }

  /// @return `true` for a regular [#TABLE], `false` otherwise.
  public boolean isTable() {

    return (this == TABLE);
  }

  /// @return `true` for a [#VIEW], `false` otherwise.
  public boolean isView() {

    return (this == VIEW);
  }

  /// @return `true` for a [#SYNONYM], `false` otherwise.
  public boolean isSynonym() {

    return (this == TABLE);
  }

  @Override
  public int hashCode() {

    return this.type.hashCode();
  }

  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    } else if ((obj == null) || (obj.getClass() != DbTableType.class)) {
      return false;
    }
    DbTableType other = (DbTableType) obj;
    return this.type.equals(other.type);
  }

  @Override
  public String toString() {

    return this.type;
  }

  /// @param type the [type][#get()] as [String].
  /// @return the new [DbTableType] or `null` if the given [String] was `null` or [blank][String#isBlank()].
  public static DbTableType of(String type) {

    if (type == null) {
      return null;
    }
    if (type.isEmpty()) {
      return null;
    }
    type = type.toUpperCase(Locale.ROOT);
    DbTableType result = STANDARD_TYPES.get(type);
    if (result == null) {
      result = new DbTableType(type, false);
    }
    return result;
  }

}
