/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.statement.delete;

import io.github.mmm.db.statement.AbstractDbClause;
import io.github.mmm.db.statement.DbIncompleteStartClause;
import io.github.mmm.db.statement.DbStartClause;
import io.github.mmm.db.statement.DbStatement;
import io.github.mmm.entity.bean.EntityBean;

/// [DbStartClause] to delete data from the database.
///
/// @since 1.0.0
public final class DeleteClause extends AbstractDbClause implements DbIncompleteStartClause {

  private DeleteStatement<?> statement;

  /// The constructor.
  public DeleteClause() {

    super();
  }

  /// @param <E> type of the [EntityBean].
  /// @param entity the [entity][EntityBean] to delete from.
  /// @return the [DeleteFromClause] for fluent API calls.
  public <E extends EntityBean> DeleteFromClause<E> from(E entity) {

    DeleteFromClause<E> deleteFrom = new DeleteFromClause<>(this, entity);
    this.statement = deleteFrom.get();
    return deleteFrom;
  }

  @Override
  public DbStatement<?> get() {

    return this.statement;
  }

}
