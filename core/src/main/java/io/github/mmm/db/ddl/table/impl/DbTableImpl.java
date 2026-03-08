/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.ddl.table.impl;

import java.util.List;

import io.github.mmm.db.ddl.column.DbColumn;
import io.github.mmm.db.ddl.constraint.DbForeignKeyConstraint;
import io.github.mmm.db.ddl.constraint.DbPrimaryKeyConstraint;
import io.github.mmm.db.ddl.index.DbIndexMetadata;
import io.github.mmm.db.ddl.table.DbTable;
import io.github.mmm.db.ddl.table.DbTableData;
import io.github.mmm.db.ddl.table.DbTableType;
import io.github.mmm.db.name.DbQualifiedName;

/**
 * Implementation of {@link DbTable}.
 *
 * @since 1.0.0
 */
public class DbTableImpl extends DbTableDataImpl implements DbTable {

  private final List<DbColumn> columns;

  private DbPrimaryKeyConstraint primaryKey;

  private boolean primaryKeyLoaded;

  private List<DbForeignKeyConstraint> foreignKeys;

  private List<DbIndexMetadata> indexes;

  /**
   * The constructor.
   *
   * @param data the {@link DbTableDataImpl}.
   * @param columns the {@link #getColumns() columns}.
   * @param primaryKey the {@link #getPrimaryKey() primary key}.
   * @param foreignKeys the {@link #getForeignKeys() foreign keys}.
   * @param indexes the {@link #getIndexes() indexes}.
   */
  public DbTableImpl(DbTableData data, List<DbColumn> columns, DbPrimaryKeyConstraint primaryKey,
      List<DbForeignKeyConstraint> foreignKeys, List<DbIndexMetadata> indexes) {

    this(data.getQualifiedName(), data.getComment(), data.getType(), columns, primaryKey, foreignKeys, indexes);
  }

  /**
   * The constructor.
   *
   * @param tableName the {@link #getQualifiedName() table name}.
   * @param comment the {@link #getComment() comment}.
   * @param type the {@link #getType() table type}.
   * @param columns the {@link #getColumns() columns}.
   * @param primaryKey the {@link #getPrimaryKey() primary key}.
   * @param foreignKeys the {@link #getForeignKeys() foreign keys}.
   * @param indexes the {@link #getIndexes() indexes}.
   */
  public DbTableImpl(DbQualifiedName tableName, String comment, DbTableType type, List<DbColumn> columns,
      DbPrimaryKeyConstraint primaryKey, List<DbForeignKeyConstraint> foreignKeys, List<DbIndexMetadata> indexes) {

    super(tableName, comment, type);
    this.columns = columns;
    this.primaryKey = primaryKey;
    this.foreignKeys = foreignKeys;
    this.indexes = indexes;
  }

  @Override
  public List<DbColumn> getColumns() {

    return this.columns;
  }

  @Override
  public List<DbForeignKeyConstraint> getForeignKeys() {

    if (this.foreignKeys == null) {
      if (this.type.isSynonym() || this.type.isView()) {
        this.foreignKeys = List.of();
      } else {
        this.foreignKeys = loadForeignKeys();
      }
    }
    return this.foreignKeys;
  }

  /**
   * @return the value of {@link #getForeignKeys()}.
   */
  protected List<DbForeignKeyConstraint> loadForeignKeys() {

    throw lazyLoadingNotImplemented();
  }

  @Override
  public DbPrimaryKeyConstraint getPrimaryKey() {

    if (!this.primaryKeyLoaded) {
      assert (this.primaryKey == null);
      // synonyms and views do not have a primary key so we do not want to waste resources by asking the DB
      if (!this.type.isSynonym() && !this.type.isView()) {
        this.primaryKey = loadPrimaryKey();
      }
      this.primaryKeyLoaded = true;
    }
    return this.primaryKey;
  }

  /**
   * @return the value of {@link #getPrimaryKey()}.
   */
  protected DbPrimaryKeyConstraint loadPrimaryKey() {

    throw lazyLoadingNotImplemented();
  }

  @Override
  public List<DbIndexMetadata> getIndexes() {

    if (this.indexes == null) {
      this.indexes = loadIndexes();
    }
    return this.indexes;
  }

  /**
   * @return the value of {@link #getForeignKeys()}.
   */
  protected List<DbIndexMetadata> loadIndexes() {

    throw lazyLoadingNotImplemented();
  }

  private RuntimeException lazyLoadingNotImplemented() {

    throw new IllegalStateException(
        "Child object was given as null in constructor and lazy loading is not implemented!");
  }

}
