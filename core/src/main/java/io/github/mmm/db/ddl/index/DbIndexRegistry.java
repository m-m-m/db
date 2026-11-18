/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.ddl.index;

/// A registry where [DbIndex]es can be [added][#add(DbIndex)] (registered).
///
/// @since 1.0.0
public interface DbIndexRegistry {

  /// @param index the [DbIndex] to add to this registry.
  void add(DbIndex index);

}
