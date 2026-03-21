/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.ddl.impl.jdbc;

import java.sql.DatabaseMetaData;
import java.util.List;

import io.github.mmm.db.ddl.column.DbColumn;
import io.github.mmm.db.ddl.constraint.DbForeignKeyConstraint;
import io.github.mmm.db.ddl.constraint.DbPrimaryKeyConstraint;
import io.github.mmm.db.ddl.index.DbIndexMetadata;
import io.github.mmm.db.ddl.table.DbTableData;
import io.github.mmm.db.ddl.table.impl.DbTableDataImpl;
import io.github.mmm.db.ddl.table.impl.DbTableImpl;

/**
 * JDBC implementation of {@link DbTableImpl}.
 */
public class JdbcTableImpl extends DbTableImpl {

  private DatabaseMetaData metaData;

  /**
   * The constructor.
   *
   * @param data the {@link DbTableDataImpl}.
   * @param columns the {@link #getColumns() columns}.
   * @param metaData the JDBC {@link DatabaseMetaData}.
   */
  public JdbcTableImpl(DbTableData data, List<DbColumn> columns, DatabaseMetaData metaData) {

    super(data, columns, null, null, null);
    this.metaData = metaData;
  }

  @Override
  protected List<DbForeignKeyConstraint> loadForeignKeys() {

    return JdbcMetaData.extractForeignKeys(this, this.metaData);
  }

  @Override
  protected List<DbIndexMetadata> loadIndexes() {

    return JdbcMetaData.extractIndexes(this, this.metaData);
  }

  @Override
  protected DbPrimaryKeyConstraint loadPrimaryKey() {

    return JdbcMetaData.extractPrimaryKey(this, this.metaData);
  }

}
