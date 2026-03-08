/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.schema;

import java.util.stream.Stream;

import io.github.mmm.base.version.VersionNumber;
import io.github.mmm.db.source.DbSource;

/**
 * Changelog node for a specific {@link DbSource}. Contains the {@link #getVersions() release versions} as well as
 * {@link #getBeforeScripts() before} and {@link #getAfterScripts() after} scripts.
 *
 * @see #getSource()
 */
public interface DbChangeLogSource {

  /**
   * @return the {@link DbSource}.
   */
  DbSource getSource();

  /**
   * @return the {@link Stream} of release {@link VersionNumber}s in ascending order.
   * @see #get(VersionNumber)
   */
  Stream<VersionNumber> getVersions();

  /**
   * @return the latest registered release {@link VersionNumber}.
   */
  VersionNumber getLastVersion();

  /**
   * @param version the {@link VersionNumber} {@link String}.
   * @return the {@link DbChangeLogVersion} or {@code null} if not found.
   * @see #getVersions()
   * @see #get(VersionNumber)
   */
  DbChangeLogVersion get(String version);

  /**
   * @param version the {@link VersionNumber}.
   * @return the {@link DbChangeLogVersion} or {@code null} if not found.
   * @see #getVersions()
   */
  default DbChangeLogVersion get(VersionNumber version) {

    return get(version.toString());
  }

  /**
   * @return the {@link DbChangeLogScripts} to execute always before the regular scripts.
   */
  DbChangeLogScripts getBeforeScripts();

  /**
   * @return the {@link DbChangeLogScripts} to execute always after the regular scripts.
   */
  DbChangeLogScripts getAfterScripts();
}
