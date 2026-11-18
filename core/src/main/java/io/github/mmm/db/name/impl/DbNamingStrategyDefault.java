/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.name.impl;

import io.github.mmm.db.name.AbstractDbNamingStrategy;
import io.github.mmm.db.name.DbNamingStrategy;

/// The default implementation of [DbNamingStrategy].
///
/// @since 1.0.0
public class DbNamingStrategyDefault extends AbstractDbNamingStrategy {

  /// The singleton instance.
  public static final DbNamingStrategy INSTANCE = new DbNamingStrategyDefault();

}
