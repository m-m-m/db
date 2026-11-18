/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.schema;

import java.util.stream.Stream;

import io.github.mmm.base.version.VersionNumber;
import io.github.mmm.db.source.DbSource;

/// Changelog node for a specific [DbSource]. Contains the [release versions][#getVersions()] as well as
/// [before][#getBeforeScripts()] and [after][#getAfterScripts()] scripts.
///
/// @see #getSource()
public interface DbChangeLogSource {

  /// @return the [DbSource].
  DbSource getSource();

  /// @return the [Stream] of release [VersionNumber]s in ascending order.
  /// @see #get(VersionNumber)
  Stream<VersionNumber> getVersions();

  /// @return the latest registered release [VersionNumber].
  VersionNumber getLastVersion();

  /// @param version the [VersionNumber] [String].
  /// @return the [DbChangeLogVersion] or `null` if not found.
  /// @see #getVersions()
  /// @see #get(VersionNumber)
  DbChangeLogVersion get(String version);

  /// @param version the [VersionNumber].
  /// @return the [DbChangeLogVersion] or `null` if not found.
  /// @see #getVersions()
  default DbChangeLogVersion get(VersionNumber version) {

    return get(version.toString());
  }

  /// @return the [DbChangeLogScripts] to execute always before the regular scripts.
  DbChangeLogScripts getBeforeScripts();

  /// @return the [DbChangeLogScripts] to execute always after the regular scripts.
  DbChangeLogScripts getAfterScripts();
}
