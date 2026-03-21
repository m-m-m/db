/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.schema.impl;

import io.github.mmm.base.version.VersionNumber;
import io.github.mmm.db.schema.DbChangeLogVersion;

/**
 * {@link DbChangeLogScriptsImpl} for a specific {@link #getVersion() release version}.
 */
public class DbChangeLogVersionImpl extends DbChangeLogScriptsImpl implements DbChangeLogVersion {

  private final VersionNumber version;

  DbChangeLogVersionImpl(VersionNumber version) {

    super();
    this.version = version;
  }

  /**
   * @return version
   */
  public VersionNumber getVersion() {

    return this.version;
  }

}
