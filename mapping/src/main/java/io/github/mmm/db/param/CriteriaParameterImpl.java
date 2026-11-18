/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.param;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import io.github.mmm.db.type.DbType;
import io.github.mmm.property.criteria.CriteriaParameter;

/// Implementation of [CriteriaParameter].
///
/// @param <V> type of the [value][#getValue()].
public class CriteriaParameterImpl<V> implements CriteriaParameter<V> {

  private static final String PLACEHOLDER_INDEX = "?";

  private final int index;

  private final V value;

  private final DbType<V, ?, ?> dbType;

  private final String name;

  private CriteriaParameterImpl<?> next;

  /// The constructor.
  ///
  /// @param index the [index][#getIndex()].
  /// @param value the [value][#getValue()].
  /// @param dbType the [database type][#getDbType()].
  public CriteriaParameterImpl(int index, V value, DbType<V, ?, ?> dbType) {

    this(index, value, dbType, null);
  }

  /// The constructor.
  ///
  /// @param index the [index][#getIndex()].
  /// @param value the [value][#getValue()].
  /// @param dbType the [database type][#getDbType()].
  /// @param name the [name][#getName()].
  public CriteriaParameterImpl(int index, V value, DbType<V, ?, ?> dbType, String name) {

    super();
    this.index = index;
    this.value = value;
    this.dbType = dbType;
    this.name = name;
  }

  @Override
  public int getIndex() {

    return this.index;
  }

  @Override
  public String getName() {

    return this.name;
  }

  @Override
  public V getValue() {

    return this.value;
  }

  /// @return the [DbType] of this parameter.
  public DbType<V, ?, ?> getDbType() {

    return this.dbType;
  }

  /// @return the placeholder for this parameter.
  public String getPlaceholder() {

    if (this.name == null) {
      return PLACEHOLDER_INDEX;
    } else {
      return ":" + this.name;
    }
  }

  /// @param statement the [PreparedStatement].
  /// @param connection the JDBC [Connection].
  /// @throws SQLException on error.
  @SuppressWarnings("exports")
  public void apply(PreparedStatement statement, Connection connection) throws SQLException {

    this.dbType.setJavaValue(statement, this.index + 1, this.value);
  }

  /// @return the next [parameter][CriteriaParameterImpl] or `null` if this is the last one.
  CriteriaParameterImpl<?> getNext() {

    return this.next;
  }

  void append(CriteriaParameterImpl<?> newNext) {

    if (this.next == null) {
      this.next = newNext;
    } else {
      throw new IllegalStateException("Can only append to the end!");
    }
  }

}
