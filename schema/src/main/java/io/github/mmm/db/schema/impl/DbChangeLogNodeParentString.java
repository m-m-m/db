/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.schema.impl;

/// [DbChangeLogNodeParent] using [String] as key.
///
/// @param <C> type of the [children][#get(String)].
public abstract class DbChangeLogNodeParentString<C> extends DbChangeLogNodeParent<String, C> {

  @Override
  protected String toKey(String key) {

    return key;
  }

}
