/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.ddl.table.impl;

import io.github.mmm.db.ddl.table.DbTableReference;
import io.github.mmm.db.name.AbstractDbObject;
import io.github.mmm.db.name.DbEntityNameMapper;
import io.github.mmm.db.name.DbQualifiedName;
import io.github.mmm.entity.bean.EntityBean;

/**
 * Implementation of {@link DbTableReference}.
 *
 * @param <E> type of the referenced {@link EntityBean}.
 * @since 1.0.0
 */
public class DbTableReferenceImpl<E extends EntityBean> extends AbstractDbObject implements DbTableReference<E> {

  private final E entity;

  /**
   * The constructor.
   *
   * @param tableName the {@link #getName() table name}.
   */
  public DbTableReferenceImpl(DbQualifiedName tableName) {

    this(tableName, null);
  }

  /**
   * The constructor.
   *
   * @param entity the {@link #getEntity() entity}.
   */
  public DbTableReferenceImpl(E entity) {

    this(null, entity);
  }

  /**
   * The constructor.
   *
   * @param tableName the {@link #getName() table name}.
   * @param entity the {@link #getEntity() entity}.
   */
  public DbTableReferenceImpl(DbQualifiedName tableName, E entity) {

    if ((entity == null) && (tableName == null)) {
      throw new IllegalArgumentException("Both entity and tableName are null!");
    }
    if (tableName == null) {
      tableName = DbEntityNameMapper.get().getTable(entity).getName();
    }
    super(tableName);
    this.entity = entity;
  }

  @Override
  public E getEntity() {

    return this.entity;
  }

}
