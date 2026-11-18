/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.statement.upsert;

import java.util.function.Consumer;

import io.github.mmm.db.statement.AbstractDbClause;
import io.github.mmm.db.statement.AliasMap;
import io.github.mmm.db.statement.DbClause;
import io.github.mmm.db.statement.DbStatement;
import io.github.mmm.db.statement.DbStatementType;
import io.github.mmm.db.statement.IntoValuesStatement;
import io.github.mmm.db.statement.impl.DbStatementTypeImpl;
import io.github.mmm.entity.bean.EntityBean;

/// [DbStatement] to [UpsertClause] data into the database.
///
/// @param <E> type of the [entity][UpsertInto#getEntity()].
/// @since 1.0.0
public class UpsertStatement<E extends EntityBean> extends IntoValuesStatement<E> {

  private final UpsertClause upsert;

  private final UpsertInto<E> into;

  private final UpsertValues<E> values;

  /// The constructor.
  ///
  /// @param upsert the [upsert][#getUpsert()].
  /// @param into the #getInto
  public UpsertStatement(UpsertClause upsert, UpsertInto<E> into) {

    super();
    this.upsert = upsert;
    this.into = into;
    this.values = new UpsertValues<>(this);
  }

  /// @deprecated use [#getUpsert()] to make it more explicit.
  @Deprecated
  @Override
  public UpsertClause getStart() {

    return this.upsert;
  }

  /// @return the opening [UpsertClause]-[clause][DbClause].
  public UpsertClause getUpsert() {

    return this.upsert;
  }

  /// @return the [INTO][UpsertInto]-[clause][DbClause].
  @Override
  public UpsertInto<E> getInto() {

    return this.into;
  }

  /// @return the [VALUES][UpsertValues]-[clause][DbClause] or `null` if none was added.
  @Override
  public UpsertValues<E> getValues() {

    return this.values;
  }

  @Override
  protected void addClauses(Consumer<AbstractDbClause> consumer) {

    consumer.accept(this.upsert);
    consumer.accept(this.into);
    consumer.accept(this.values);
  }

  @Override
  public DbStatementType getType() {

    return DbStatementTypeImpl.UPSERT;
  }

  @Override
  protected AliasMap getAliasMap() {

    return this.into.getAliasMap();
  }

}
