/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.statement.create;

import java.util.function.Consumer;

import io.github.mmm.db.statement.AbstractDbClause;
import io.github.mmm.db.statement.AbstractEntityClause;
import io.github.mmm.db.statement.AliasMap;
import io.github.mmm.db.statement.DbClause;
import io.github.mmm.db.statement.DbStatement;
import io.github.mmm.db.statement.DbStatementType;
import io.github.mmm.db.statement.DbStartClause;
import io.github.mmm.db.statement.impl.DbStatementTypeImpl;
import io.github.mmm.entity.bean.EntityBean;

/**
 * {@link DbStatement} to {@link CreateTableClause create a table}.
 *
 * @param <E> type of the {@link AbstractEntityClause#getEntity() entity}.
 * @since 1.0.0
 */
public class CreateTableStatement<E extends EntityBean> extends CreateStatement<E> {

  private final CreateTableClause<E> createTable;

  private final CreateTableContentsClause<E> contents;

  /**
   * The constructor.
   *
   * @param createTable the {@link #getCreateTable() create table}.
   */
  public CreateTableStatement(CreateTableClause<E> createTable) {

    super();
    this.createTable = createTable;
    this.contents = new CreateTableContentsClause<>(this);
  }

  /**
   * @deprecated use {@link #getCreateTable()} to make it more explicit.
   */
  @Deprecated
  @Override
  public DbStartClause getStart() {

    return this.createTable;
  }

  /**
   * @return the opening {@link CreateTableClause}-{@link DbClause}.
   */
  public CreateTableClause<E> getCreateTable() {

    return this.createTable;
  }

  /**
   * @return columns
   */
  public CreateTableContentsClause<E> getContents() {

    return this.contents;
  }

  @Override
  protected void addClauses(Consumer<AbstractDbClause> consumer) {

    consumer.accept(this.createTable);
    consumer.accept(this.contents);
  }

  @Override
  public DbStatementType getType() {

    return DbStatementTypeImpl.CREATE_TABLE;
  }

  @Override
  protected AliasMap getAliasMap() {

    return this.createTable.getAliasMap();
  }

}
