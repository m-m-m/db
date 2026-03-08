/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.ddl.column.impl;

import java.util.Objects;

import io.github.mmm.base.lang.AbstractToString;
import io.github.mmm.db.ddl.column.DbColumnReference;
import io.github.mmm.db.name.DbNamingStrategy;
import io.github.mmm.property.ReadableProperty;

/**
 * Implementation of {@link DbColumnReference}.
 *
 * @since 1.0.0
 */
public class DbColumnReferenceImpl extends AbstractToString implements DbColumnReference {

  /** @see #getName() */
  protected final String name;

  /** @see #getProperty() */
  protected final ReadableProperty<?> property;

  /**
   * The constructor.
   *
   * @param name the {@link #getName() column name}.
   */
  public DbColumnReferenceImpl(String name) {

    this(name, null);
  }

  /**
   * The constructor.
   *
   * @param property the {@link #getProperty() property}.
   */
  public DbColumnReferenceImpl(ReadableProperty<?> property) {

    this(null, property);
  }

  /**
   * The constructor.
   *
   * @param reference the {@link DbColumnReference} to copy.
   */
  public DbColumnReferenceImpl(DbColumnReference reference) {

    this(reference.getName(), reference.getProperty());
  }

  /**
   * The constructor.
   *
   * @param name the {@link #getName() column name}.
   * @param property the {@link #getProperty() property}.
   */
  public DbColumnReferenceImpl(String name, ReadableProperty<?> property) {

    super();
    if (property == null) {
      Objects.requireNonNull(name);
    } else if (name == null) {
      name = property.getName();
    }
    this.name = name;
    this.property = property;
  }

  @Override
  public String getName(DbNamingStrategy namingStrategy) {

    if (namingStrategy == null) {
      return this.name;
    }
    return namingStrategy.getColumnName(this);
  }

  @Override
  public ReadableProperty<?> getProperty() {

    return this.property;
  }

}
