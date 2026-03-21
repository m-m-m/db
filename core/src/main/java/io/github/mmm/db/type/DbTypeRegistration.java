/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.type;

/**
 * {@link Enum} for auto-registration of {@link DbType}.
 *
 * @see DbType#get(int)
 * @see DbType#get(Class)
 * @see DbType#get(String)
 */
public enum DbTypeRegistration {

  /** No registration. */
  NONE(false, false, false),

  /** Only {@link #isRegisterCode() register code}. */
  CODE(true, false, false),

  /** Only {@link #isRegisterType() register type}. */
  TYPE(false, true, false),

  /** Only {@link #isRegisterName() register name}. */
  NAME(false, false, true),

  /** Only {@link #isRegisterCode() register code} and {@link #isRegisterType() type}. */
  CODE_TYPE(true, true, false),

  /** Only {@link #isRegisterCode() register code} and {@link #isRegisterName() name}. */
  CODE_NAME(true, false, true),

  /** Only {@link #isRegisterType() register type} and {@link #isRegisterName() name}. */
  TYPE_NAME(false, true, true),

  /** All registrations. */
  ALL(true, true, true);

  private final boolean code;

  private final boolean type;

  private final boolean name;

  private DbTypeRegistration(boolean code, boolean type, boolean name) {

    this.code = code;
    this.type = type;
    this.name = name;
  }

  /**
   * @return {@code true} to register {@link DbType#getCode() code} for {@link DbType#get(int)}.
   */
  public boolean isRegisterCode() {

    return this.code;
  }

  /**
   * @return {@code true} to register {@link DbType#getSourceType() Java source type} for {@link DbType#get(Class)}.
   */
  public boolean isRegisterType() {

    return this.type;
  }

  /**
   * @return {@code true} to register {@link DbType#getName() type name} for {@link DbType#get(String)}.
   */
  public boolean isRegisterName() {

    return this.name;
  }

}
