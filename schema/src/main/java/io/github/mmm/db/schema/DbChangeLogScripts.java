/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.schema;

import java.util.stream.Stream;

/// Interface for the [DbChangeLogScripts].
public interface DbChangeLogScripts {

  /// @return a [Stream] of the contained [DbChangeLogScript]s in ascending order.
  Stream<DbChangeLogScript> getScripts();

}
