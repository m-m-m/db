/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.statement.create;

import java.util.List;
import java.util.function.Consumer;

import io.github.mmm.db.ddl.column.DbColumnReferenceWithSortOrder;
import io.github.mmm.db.ddl.index.DbIndex;
import io.github.mmm.db.ddl.index.DbIndexType;
import io.github.mmm.db.ddl.table.DbTableReference;
import io.github.mmm.db.name.AbstractDbObject;
import io.github.mmm.db.name.DbQualifiedName;
import io.github.mmm.db.statement.AbstractDbClause;
import io.github.mmm.db.statement.AbstractEntityClause;
import io.github.mmm.db.statement.AliasMap;
import io.github.mmm.db.statement.DbClause;
import io.github.mmm.db.statement.DbStartClause;
import io.github.mmm.entity.bean.EntityBean;

/// [CreateStatement] to [create an index][CreateIndexClause]
///
/// @param <E> type of the [entity][AbstractEntityClause#getEntity()].
/// @since 1.0.0
public class CreateIndexStatement<E extends EntityBean> extends CreateStatement<E> {

  private final CreateIndexClause createIndex;

  private final CreateIndexOnClause<E> on;

  private final CreateIndexColumnsClause<E> column;

  private final DbIndex index;

  /// The constructor.
  ///
  /// @param createIndex the [create index clause][#getCreateIndex()].
  /// @param on the [on clause][#getOn()].
  public CreateIndexStatement(CreateIndexClause createIndex, CreateIndexOnClause<E> on) {

    super();
    this.createIndex = createIndex;
    this.on = on;
    this.column = new CreateIndexColumnsClause<>(this);
    this.index = new DbIndexAdapter();
  }

  /// @deprecated use [#getCreateIndex()] to make it more explicit.
  @Deprecated
  @Override
  public DbStartClause getStart() {

    return this.createIndex;
  }

  /// @return the opening [CreateIndexClause]-[DbClause].
  public CreateIndexClause getCreateIndex() {

    return this.createIndex;
  }

  /// @return the [CreateIndexOnClause]-[DbClause].
  public CreateIndexOnClause<E> getOn() {

    return this.on;
  }

  /// @return the [CreateIndexColumnsClause]-[DbClause].
  public CreateIndexColumnsClause<E> getColumn() {

    return this.column;
  }

  @Override
  protected void addClauses(Consumer<AbstractDbClause> consumer) {

    consumer.accept(this.createIndex);
    consumer.accept(this.on);
    consumer.accept(this.column);
  }

  @Override
  protected AliasMap getAliasMap() {

    return this.on.getAliasMap();
  }

  /// @return this [CreateIndexStatement] as a [DbIndex] that would be created by executing it.
  public DbIndex asIndex() {

    return this.index;
  }

  private class DbIndexAdapter extends AbstractDbObject implements DbIndex {

    private DbIndexAdapter() {

      super((DbQualifiedName) null);
    }

    @Override
    public boolean isUnique() {

      return CreateIndexStatement.this.createIndex.getKind().isUnique();
    }

    @Override
    public boolean isClustered() {

      return CreateIndexStatement.this.createIndex.getKind().isClustered();
    }

    @Override
    public DbIndexType getType() {

      return CreateIndexStatement.this.createIndex.getKind().getType();
    }

    @Override
    public DbQualifiedName getRawName() {

      return CreateIndexStatement.this.createIndex.getName();
    }

    @Override
    public DbTableReference<? extends EntityBean> getTable() {

      return CreateIndexStatement.this.on;
    }

    @Override
    public List<DbColumnReferenceWithSortOrder> getColumns() {

      return CreateIndexStatement.this.column.getColumns();
    }

  }

}
