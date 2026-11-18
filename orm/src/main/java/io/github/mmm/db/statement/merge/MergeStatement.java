/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.statement.merge;

import java.util.function.Consumer;

import io.github.mmm.db.statement.AbstractDbClause;
import io.github.mmm.db.statement.AliasMap;
import io.github.mmm.db.statement.DbClause;
import io.github.mmm.db.statement.DbStatement;
import io.github.mmm.db.statement.DbStatementType;
import io.github.mmm.db.statement.IntoValuesStatement;
import io.github.mmm.db.statement.impl.DbStatementTypeImpl;
import io.github.mmm.db.statement.upsert.UpsertInto;
import io.github.mmm.db.statement.upsert.UpsertValues;
import io.github.mmm.entity.bean.EntityBean;

/// [DbStatement] to [MergeClause] data into the database.
///
/// @param <E> type of the [entity][UpsertInto#getEntity()].
/// @since 1.0.0
public class MergeStatement<E extends EntityBean> extends IntoValuesStatement<E> {

  private final MergeClause merge;

  private final MergeIntoClause<E> into;

  private final MergeValuesClause<E> values;

  /// The constructor.
  ///
  /// @param merge the [MERGE][#getMerge()].
  /// @param into the [INTO][#getInto()].
  public MergeStatement(MergeClause merge, MergeIntoClause<E> into) {

    super();
    this.merge = merge;
    this.into = into;
    this.values = new MergeValuesClause<>(this);
  }

  /// @deprecated use [#getMerge()] to make it more explicit.
  @Deprecated
  @Override
  public MergeClause getStart() {

    return this.merge;
  }

  /// @return the opening [MergeClause]-[clause][DbClause].
  public MergeClause getMerge() {

    return this.merge;
  }

  /// @return the [INTO][MergeIntoClause]-[clause][DbClause].
  @Override
  public MergeIntoClause<E> getInto() {

    return this.into;
  }

  /// @return the [VALUES][UpsertValues]-[clause][DbClause] or `null` if none was added.
  @Override
  public MergeValuesClause<E> getValues() {

    return this.values;
  }

  @Override
  protected void addClauses(Consumer<AbstractDbClause> consumer) {

    consumer.accept(this.merge);
    consumer.accept(this.into);
    consumer.accept(this.values);
  }

  @Override
  public DbStatementType getType() {

    return DbStatementTypeImpl.MERGE;
  }

  @Override
  protected AliasMap getAliasMap() {

    return this.into.getAliasMap();
  }

}
