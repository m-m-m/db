/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.schema.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import io.github.mmm.base.collection.CollectionHelper;
import io.github.mmm.db.schema.DbChangeLogScript;
import io.github.mmm.db.schema.DbChangeLogScripts;

/**
 * for a specific release version.
 */
public class DbChangeLogScriptsImpl implements DbChangeLogScripts {

  private final List<DbChangeLogScript> scripts;

  DbChangeLogScriptsImpl() {

    super();
    this.scripts = new LinkedList<>();
  }

  void add(DbChangeLogScriptImpl script) {

    CollectionHelper.sortedInsert(script, this.scripts);
  }

  @Override
  public Stream<DbChangeLogScript> getScripts() {

    return this.scripts.stream();
  }

}
