/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.statement.impl;

import io.github.mmm.db.name.DbKeyword;
import io.github.mmm.db.statement.DbStatementType;

/**
 * Implementation of {@link DbStatementType}.
 *
 * @since 1.0.0
 */
public enum DbStatementTypeImpl implements DbStatementType {

  /** {@link DbStatementType} of {@link io.github.mmm.db.statement.select.SelectStatement}. */
  SELECT(DbKeyword.SELECT, false),

  /** {@link DbStatementType} of {@link io.github.mmm.db.statement.insert.InsertStatement}. */
  INSERT(DbKeyword.INSERT, false),

  /** {@link DbStatementType} of {@link io.github.mmm.db.statement.update.UpdateStatement}. */
  UPDATE(DbKeyword.UPDATE, false),

  /** {@link DbStatementType} of {@link io.github.mmm.db.statement.delete.DeleteStatement}. */
  DELETE(DbKeyword.DELETE, false),

  /** {@link DbStatementType} of {@link io.github.mmm.db.statement.merge.MergeStatement}. */
  MERGE(DbKeyword.MERGE, false),

  /** {@link DbStatementType} of {@link io.github.mmm.db.statement.upsert.UpsertStatement}. */
  UPSERT(DbKeyword.UPSERT, false),

  /** {@link DbStatementType} of {@link io.github.mmm.db.statement.create.CreateStatement}. */
  CREATE(DbKeyword.CREATE_TABLE, true),

  /** {@link DbStatementType} of {@link io.github.mmm.db.statement.alter.AlterStatement}. */
  ALTER(DbKeyword.ALTER, true),

  /** {@link DbStatementType} of {@link io.github.mmm.db.statement.drop.DropStatement}. */
  DROP(DbKeyword.DROP, true),

  /** {@link DbStatementType} of {@link io.github.mmm.db.statement.declare.DeclareStatement}. */
  DECLARE(DbKeyword.DECLARE, true),

  /** {@link DbStatementType} of BeginStatement (PL/SQL like DECLARE). */
  BEGIN(DbKeyword.BEGIN, true),

  ;

  private final boolean ddl;

  private final String keyword;

  private DbStatementTypeImpl(String keyword, boolean ddl) {

    this.keyword = keyword;
    this.ddl = ddl;
  }

  @Override
  public boolean isDdl() {

    return this.ddl;
  }

  @Override
  public boolean isDml() {

    return !this.ddl;
  }

  @Override
  public boolean isQuery() {

    return (this == SELECT);
  }

  @Override
  public String getKeyword() {

    return this.keyword;
  }

  @Override
  public String toString() {

    return this.keyword;
  }

}
