/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.statement.drop;

import java.util.function.Consumer;

import io.github.mmm.db.statement.AbstractDbClause;
import io.github.mmm.db.statement.AbstractEntityClause;
import io.github.mmm.db.statement.AliasMap;
import io.github.mmm.db.statement.DbStartClause;
import io.github.mmm.db.statement.DbStatement;
import io.github.mmm.entity.bean.EntityBean;

/// [DbStatement] to [drop a table][DropTableClause].
///
/// @param <E> type of the [entity][AbstractEntityClause#getEntity()].
/// @since 1.0.0
public class DropTableStatement<E extends EntityBean> extends DropStatement<E> {

  private final DropTableClause<E> dropTable;

  /// The constructor.
  ///
  /// @param dropTable the [drop table][#getDropTable()].
  public DropTableStatement(DropTableClause<E> dropTable) {

    super();
    this.dropTable = dropTable;
  }

  /// @deprecated use [#getDropTable()] to make it more explicit.
  @Deprecated
  @Override
  public DbStartClause getStart() {

    return this.dropTable;
  }

  /// @return the opening [DropTableClause].
  public DropTableClause<E> getDropTable() {

    return this.dropTable;
  }

  @Override
  protected void addClauses(Consumer<AbstractDbClause> consumer) {

    consumer.accept(this.dropTable);
  }

  @Override
  protected AliasMap getAliasMap() {

    return this.dropTable.getAliasMap();
  }

}
