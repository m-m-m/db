/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.statement.create;

import java.util.function.Consumer;

import io.github.mmm.db.statement.AbstractDbClause;
import io.github.mmm.db.statement.AbstractEntityClause;
import io.github.mmm.db.statement.AliasMap;
import io.github.mmm.db.statement.DbClause;
import io.github.mmm.db.statement.DbStartClause;
import io.github.mmm.db.statement.DbStatement;
import io.github.mmm.entity.bean.EntityBean;

/// [DbStatement] to [create a table][CreateTableClause].
///
/// @param <E> type of the [entity][AbstractEntityClause#getEntity()].
/// @since 1.0.0
public class CreateTableStatement<E extends EntityBean> extends CreateStatement<E> {

  private final CreateTableClause<E> createTable;

  private final CreateTableContentsClause<E> contents;

  /// The constructor.
  ///
  /// @param createTable the [create table][#getCreateTable()].
  public CreateTableStatement(CreateTableClause<E> createTable) {

    super();
    this.createTable = createTable;
    this.contents = new CreateTableContentsClause<>(this);
  }

  /// @deprecated use [#getCreateTable()] to make it more explicit.
  @Deprecated
  @Override
  public DbStartClause getStart() {

    return this.createTable;
  }

  /// @return the opening [CreateTableClause]-[DbClause].
  public CreateTableClause<E> getCreateTable() {

    return this.createTable;
  }

  /// @return columns
  public CreateTableContentsClause<E> getContents() {

    return this.contents;
  }

  @Override
  protected void addClauses(Consumer<AbstractDbClause> consumer) {

    consumer.accept(this.createTable);
    consumer.accept(this.contents);
  }

  @Override
  protected AliasMap getAliasMap() {

    return this.createTable.getAliasMap();
  }

}
