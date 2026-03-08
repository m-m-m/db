/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.schema;

import java.util.stream.Stream;

/**
 * Interface for the {@link DbChangeLogScripts}.
 */
public interface DbChangeLogScripts {

  /**
   * @return a {@link Stream} of the contained {@link DbChangeLogScript}s in ascending order.
   */
  Stream<DbChangeLogScript> getScripts();

}
