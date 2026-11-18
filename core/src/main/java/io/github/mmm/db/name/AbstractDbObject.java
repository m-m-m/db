/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.name;

import io.github.mmm.base.lang.AbstractToString;

/**
 * Abstract base implementation of {@link DbObject}.
 *
 * @since 1.0.0
 */
public abstract class AbstractDbObject extends AbstractToString implements DbObject {

  /** @see #getRawName() */
  protected final DbQualifiedName rawName;

  private final boolean java;

  /**
   * The constructor.
   *
   * @param rawName the {@link #getRawName() raw name}.
   */
  protected AbstractDbObject(String rawName) {

    this(rawName, false);
  }

  /**
   * The constructor.
   *
   * @param rawName the {@link #getRawName() raw name}.
   * @param java the {@link #hasJavaName() Java name flag}.
   */
  protected AbstractDbObject(String rawName, boolean java) {

    this(java ? DbQualifiedName.ofName(rawName) : DbQualifiedName.ofParsed(rawName), java);
  }

  /**
   * The constructor.
   *
   * @param rawName the {@link #getRawName() raw name}.
   */
  protected AbstractDbObject(DbQualifiedName rawName) {

    this(rawName, false);
  }

  /**
   * The constructor.
   *
   * @param rawName the {@link #getRawName() raw name}.
   * @param java the {@link #hasJavaName() Java name flag}.
   */
  protected AbstractDbObject(DbQualifiedName rawName, boolean java) {

    super();
    this.rawName = rawName;
    this.java = java;
  }

  @Override
  public DbQualifiedName getRawName() {

    return this.rawName;
  }

  @Override
  public boolean hasJavaName() {

    return this.java;
  }
}
