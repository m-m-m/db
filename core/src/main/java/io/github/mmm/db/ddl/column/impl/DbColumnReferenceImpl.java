/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.ddl.column.impl;

import java.util.Objects;

import io.github.mmm.db.ddl.column.DbColumnReference;
import io.github.mmm.db.name.AbstractDbObject;
import io.github.mmm.db.name.DbQualifiedName;
import io.github.mmm.property.ReadableProperty;

/// Implementation of [DbColumnReference].
///
/// @since 1.0.0
public class DbColumnReferenceImpl extends AbstractDbObject implements DbColumnReference {

  /// @see #getProperty()
  protected final ReadableProperty<?> property;

  /// The constructor.
  ///
  /// @param name the [column name][#getName()].
  public DbColumnReferenceImpl(String name) {

    this(name, null);
  }

  /// The constructor.
  ///
  /// @param property the [property][#getProperty()].
  public DbColumnReferenceImpl(ReadableProperty<?> property) {

    this(null, property);
  }

  /// The constructor.
  ///
  /// @param reference the [DbColumnReference] to copy.
  public DbColumnReferenceImpl(DbColumnReference reference) {

    this(reference.getProperty(), reference.getName());
  }

  /// The constructor.
  ///
  /// @param name the [column name][#getName()].
  /// @param property the [property][#getProperty()].
  public DbColumnReferenceImpl(String name, ReadableProperty<?> property) {

    this(property, DbQualifiedName.ofName(name));
  }

  /// The constructor.
  ///
  /// @param property the [property][#getProperty()].
  /// @param name the [column name][#getName()].
  public DbColumnReferenceImpl(ReadableProperty<?> property, DbQualifiedName name) {

    if (property == null) {
      Objects.requireNonNull(name);
    }
    super(name);
    this.property = property;
  }

  @Override
  public ReadableProperty<?> getProperty() {

    return this.property;
  }

}
