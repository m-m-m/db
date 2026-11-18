/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.ddl.index.impl;

import java.util.List;

import io.github.mmm.db.ddl.column.DbColumnReferenceWithSortOrder;
import io.github.mmm.db.ddl.index.DbIndex;
import io.github.mmm.db.ddl.index.DbIndexKind;
import io.github.mmm.db.ddl.index.DbIndexMetadata;
import io.github.mmm.db.ddl.index.DbIndexType;
import io.github.mmm.db.ddl.table.DbTableReference;
import io.github.mmm.db.name.AbstractDbObject;
import io.github.mmm.db.name.DbQualifiedName;
import io.github.mmm.entity.bean.EntityBean;

/**
 * Implementation of {@link DbIndexMetadata}.
 *
 * @since 1.0.0
 */
public class DbIndexImpl extends AbstractDbObject implements DbIndex {

  private final DbTableReference<EntityBean> table;

  private final DbIndexKind kind;

  private final List<DbColumnReferenceWithSortOrder> columns;

  /**
   * The constructor.
   *
   * @param name the {@link #getName() name} of the index.
   * @param table the owning {@link #getTable() table}.
   * @param kind the {@link DbIndexKind}.
   * @param columns the {@link #getColumns() columns}.
   */
  public DbIndexImpl(DbQualifiedName name, DbTableReference<EntityBean> table, DbIndexKind kind,
      List<DbColumnReferenceWithSortOrder> columns) {

    super(name);
    this.table = table;
    this.kind = kind;
    this.columns = columns;
  }

  @Override
  public DbTableReference<? extends EntityBean> getTable() {

    return this.table;
  }

  @Override
  public DbIndexType getType() {

    return this.kind.getType();
  }

  @Override
  public boolean isUnique() {

    return this.kind.isUnique();
  }

  @Override
  public boolean isClustered() {

    return this.kind.isClustered();
  }

  @Override
  public List<DbColumnReferenceWithSortOrder> getColumns() {

    return this.columns;
  }

}
