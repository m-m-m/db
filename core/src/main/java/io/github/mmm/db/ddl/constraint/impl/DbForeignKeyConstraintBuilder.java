/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.ddl.constraint.impl;

import io.github.mmm.base.collection.ListBuilder;
import io.github.mmm.base.lang.Builder;
import io.github.mmm.db.ddl.column.DbColumnReference;
import io.github.mmm.db.ddl.constraint.DbForeignKeyConstraint;
import io.github.mmm.db.ddl.table.DbTableReference;
import io.github.mmm.db.ddl.table.impl.DbTableReferenceImpl;
import io.github.mmm.db.name.DbQualifiedName;

/**
 * {@link Builder} of {@link DbForeignKeyConstraint}.
 */
public class DbForeignKeyConstraintBuilder implements Builder<DbForeignKeyConstraint> {

  private final String name;

  private final DbTableReference<?> sourceTable;

  private final ListBuilder<DbColumnReference> sourceColumnBuilder;

  private final ListBuilder<DbColumnReference> targetColumnBuilder;

  private DbTableReference<?> targetTable;

  /**
   * The constructor.
   *
   * @param name the name of the foreign key.
   * @param sourceTable the {@link DbForeignKeyConstraint#getSourceTable() source table}.
   */
  public DbForeignKeyConstraintBuilder(String name, DbTableReference<?> sourceTable) {

    super();
    this.name = name;
    this.sourceTable = sourceTable;
    this.sourceColumnBuilder = new ListBuilder<>();
    this.targetColumnBuilder = new ListBuilder<>();
  }

  /**
   * @return the {@link ListBuilder} for {@link DbForeignKeyConstraint#getSourceColumns()}.
   */
  public ListBuilder<DbColumnReference> getSourceColumnBuilder() {

    return this.sourceColumnBuilder;
  }

  /**
   * @return the {@link ListBuilder} for {@link DbForeignKeyConstraint#getTargetColumns()}.
   */
  public ListBuilder<DbColumnReference> getTargetColumnBuilder() {

    return this.targetColumnBuilder;
  }

  /**
   * @param targetTable the {@link DbForeignKeyConstraint#getTargetTable() target table}.
   */
  public void setTargetTable(DbTableReference<?> targetTable) {

    this.targetTable = targetTable;
  }

  /**
   * @param targetTable the {@link DbForeignKeyConstraint#getTargetTable() target table}.
   */
  public void setTargetTable(DbQualifiedName targetTable) {

    if (this.targetTable != null) {
      assert (this.targetTable.getQualifiedName().equals(targetTable));
      return;
    }
    this.targetTable = new DbTableReferenceImpl<>(targetTable);
  }

  @Override
  public DbForeignKeyConstraint build() {

    return DbForeignKeyConstraint.of(this.name, this.sourceTable, this.sourceColumnBuilder.build(), this.targetTable,
        this.targetColumnBuilder.build());
  }

}
