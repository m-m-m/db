/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.schema.impl;

import java.util.Objects;

import io.github.mmm.base.resource.ResourceFile;
import io.github.mmm.db.schema.DbChangeLogScript;

/**
 * Implementation of {@link DbChangeLogScript}.
 */
public class DbChangeLogScriptImpl implements DbChangeLogScript {

  private final ResourceFile file;

  private final String dbType;

  private final String scriptType;

  DbChangeLogScriptImpl(ResourceFile file, String dbType, String scriptType) {

    super();
    Objects.requireNonNull(file);
    Objects.requireNonNull(scriptType);
    assert verify(file, dbType, scriptType);
    this.file = file;
    this.dbType = dbType;
    this.scriptType = scriptType;
  }

  private static boolean verify(ResourceFile file, String dbType, String scriptType) {

    String name = file.getPath();
    String ext = "." + scriptType;
    if (dbType != null) {
      ext = "." + dbType + ext;
    }
    assert name.endsWith(ext);
    int lastSlash = name.lastIndexOf('/');
    assert name.indexOf('.', lastSlash) == name.length() - ext.length();
    return true;
  }

  @Override
  public ResourceFile getFile() {

    return this.file;
  }

  @Override
  public String getDbType() {

    return this.dbType;
  }

  @Override
  public String getExtension() {

    return this.scriptType;
  }

  @Override
  public String toString() {

    return this.file.getPath();
  }

}
