/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.type;

/// [Enum] for auto-registration of [DbType].
///
/// @see DbType#get(int)
/// @see DbType#get(Class)
/// @see DbType#get(String)
public enum DbTypeRegistration {

  /// No registration.
  NONE(false, false, false),

  /// Only [register code][#isRegisterCode()].
  CODE(true, false, false),

  /// Only [register type][#isRegisterType()].
  TYPE(false, true, false),

  /// Only [register name][#isRegisterName()].
  NAME(false, false, true),

  /// Only [register code][#isRegisterCode()] and [type][#isRegisterType()].
  CODE_TYPE(true, true, false),

  /// Only [register code][#isRegisterCode()] and [name][#isRegisterName()].
  CODE_NAME(true, false, true),

  /// Only [register type][#isRegisterType()] and [name][#isRegisterName()].
  TYPE_NAME(false, true, true),

  /// All registrations.
  ALL(true, true, true);

  private final boolean code;

  private final boolean type;

  private final boolean name;

  private DbTypeRegistration(boolean code, boolean type, boolean name) {

    this.code = code;
    this.type = type;
    this.name = name;
  }

  /// @return `true` to register [code][DbType#getCode()] for [DbType#get(int)].
  public boolean isRegisterCode() {

    return this.code;
  }

  /// @return `true` to register [Java source type][DbType#getSourceType()] for [DbType#get(Class)].
  public boolean isRegisterType() {

    return this.type;
  }

  /// @return `true` to register [type name][DbType#getName()] for [DbType#get(String)].
  public boolean isRegisterName() {

    return this.name;
  }

}
