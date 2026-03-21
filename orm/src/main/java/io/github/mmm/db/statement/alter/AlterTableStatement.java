/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.statement.alter;

import java.util.function.Consumer;

import io.github.mmm.db.statement.AbstractDbClause;
import io.github.mmm.db.statement.AbstractDbStatement;
import io.github.mmm.db.statement.AbstractEntityClause;
import io.github.mmm.db.statement.AliasMap;
import io.github.mmm.db.statement.DbClause;
import io.github.mmm.db.statement.DbStatement;
import io.github.mmm.db.statement.DbStatementType;
import io.github.mmm.db.statement.DbStartClause;
import io.github.mmm.db.statement.impl.DbStatementTypeImpl;
import io.github.mmm.entity.bean.EntityBean;

/**
 * {@link DbStatement} to {@link AlterTableClause alter a table}.
 *
 * @param <E> type of the {@link AbstractEntityClause#getEntity() entity}.
 * @since 1.0.0
 */
public class AlterTableStatement<E extends EntityBean> extends AbstractDbStatement<E> {

  private final AlterTableClause<E> alterTable;

  private final AlterTableOperationsClause<E> operations;

  /**
   * The constructor.
   *
   * @param createTable the {@link #getAlterTable() alter table}.
   */
  public AlterTableStatement(AlterTableClause<E> createTable) {

    super();
    this.alterTable = createTable;
    this.operations = new AlterTableOperationsClause<>(this);
  }

  /**
   * @deprecated use {@link #getAlterTable()} to make it more explicit.
   */
  @Deprecated
  @Override
  public DbStartClause getStart() {

    return this.alterTable;
  }

  /**
   * @return the opening {@link AlterTableClause}-{@link DbClause}.
   */
  public AlterTableClause<E> getAlterTable() {

    return this.alterTable;
  }

  /**
   * @return the {@link AlterTableOperationsClause} clause.
   */
  public AlterTableOperationsClause<E> getOperations() {

    return this.operations;
  }

  @Override
  protected void addClauses(Consumer<AbstractDbClause> consumer) {

    consumer.accept(this.alterTable);
    consumer.accept(this.operations);
  }

  @Override
  public DbStatementType getType() {

    return DbStatementTypeImpl.ALTER_TABLE;
  }

  @Override
  protected AliasMap getAliasMap() {

    return this.alterTable.getAliasMap();
  }

}
