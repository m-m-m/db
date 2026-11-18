/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.statement.alter;

import java.util.function.Consumer;

import io.github.mmm.db.statement.AbstractDbClause;
import io.github.mmm.db.statement.AbstractEntityClause;
import io.github.mmm.db.statement.AliasMap;
import io.github.mmm.db.statement.DbClause;
import io.github.mmm.db.statement.DbStartClause;
import io.github.mmm.db.statement.DbStatement;
import io.github.mmm.entity.bean.EntityBean;

/// [DbStatement] to [alter a table][AlterTableClause].
///
/// @param <E> type of the [entity][AbstractEntityClause#getEntity()].
/// @since 1.0.0
public class AlterTableStatement<E extends EntityBean> extends AlterStatement<E> {

  private final AlterTableClause<E> alterTable;

  private final AlterTableOperationsClause<E> operations;

  /// The constructor.
  ///
  /// @param createTable the [alter table][#getAlterTable()].
  public AlterTableStatement(AlterTableClause<E> createTable) {

    super();
    this.alterTable = createTable;
    this.operations = new AlterTableOperationsClause<>(this);
  }

  /// @deprecated use [#getAlterTable()] to make it more explicit.
  @Deprecated
  @Override
  public DbStartClause getStart() {

    return this.alterTable;
  }

  /// @return the opening [AlterTableClause]-[DbClause].
  public AlterTableClause<E> getAlterTable() {

    return this.alterTable;
  }

  /// @return the [AlterTableOperationsClause] clause.
  public AlterTableOperationsClause<E> getOperations() {

    return this.operations;
  }

  @Override
  protected void addClauses(Consumer<AbstractDbClause> consumer) {

    consumer.accept(this.alterTable);
    consumer.accept(this.operations);
  }

  @Override
  protected AliasMap getAliasMap() {

    return this.alterTable.getAliasMap();
  }

}
