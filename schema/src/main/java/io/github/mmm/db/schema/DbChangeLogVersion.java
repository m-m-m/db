/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.schema;

import io.github.mmm.base.version.VersionNumber;

/**
 * {@link DbChangeLogScripts} for a specific {@link #getVersion() version}.
 */
public interface DbChangeLogVersion extends DbChangeLogScripts {

  /**
   * @return the release {@link VersionNumber}.
   */
  VersionNumber getVersion();
}
