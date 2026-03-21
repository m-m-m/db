/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.statement.create;

import java.util.List;
import java.util.function.Consumer;

import io.github.mmm.base.lang.AbstractToString;
import io.github.mmm.db.ddl.column.DbColumnReferenceWithSortOrder;
import io.github.mmm.db.ddl.index.DbIndex;
import io.github.mmm.db.ddl.index.DbIndexType;
import io.github.mmm.db.ddl.table.DbTableReference;
import io.github.mmm.db.name.DbNamingStrategy;
import io.github.mmm.db.statement.AbstractDbClause;
import io.github.mmm.db.statement.AbstractEntityClause;
import io.github.mmm.db.statement.AliasMap;
import io.github.mmm.db.statement.DbClause;
import io.github.mmm.db.statement.DbStatementType;
import io.github.mmm.db.statement.DbStartClause;
import io.github.mmm.db.statement.impl.DbStatementTypeImpl;
import io.github.mmm.entity.bean.EntityBean;

/**
 * {@link CreateStatement} to {@link CreateIndexClause create an index}
 *
 * @param <E> type of the {@link AbstractEntityClause#getEntity() entity}.
 * @since 1.0.0
 */
public class CreateIndexStatement<E extends EntityBean> extends CreateStatement<E> {

  private final CreateIndexClause createIndex;

  private final CreateIndexOnClause<E> on;

  private final CreateIndexColumnsClause<E> column;

  private final DbIndex index;

  /**
   * The constructor.
   *
   * @param createIndex the {@link #getCreateIndex() create index clause}.
   * @param on the {@link #getOn() on clause}.
   */
  public CreateIndexStatement(CreateIndexClause createIndex, CreateIndexOnClause<E> on) {

    super();
    this.createIndex = createIndex;
    this.on = on;
    this.column = new CreateIndexColumnsClause<>(this);
    this.index = new DbIndexAdapter();
  }

  /**
   * @deprecated use {@link #getCreateIndex()} to make it more explicit.
   */
  @Deprecated
  @Override
  public DbStartClause getStart() {

    return this.createIndex;
  }

  /**
   * @return the opening {@link CreateIndexClause}-{@link DbClause}.
   */
  public CreateIndexClause getCreateIndex() {

    return this.createIndex;
  }

  /**
   * @return the {@link CreateIndexOnClause}-{@link DbClause}.
   */
  public CreateIndexOnClause<E> getOn() {

    return this.on;
  }

  /**
   * @return the {@link CreateIndexColumnsClause}-{@link DbClause}.
   */
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
  public DbStatementType getType() {

    return DbStatementTypeImpl.CREATE_INDEX;
  }

  @Override
  protected AliasMap getAliasMap() {

    return this.on.getAliasMap();
  }

  /**
   * @return this {@link CreateIndexStatement} as a {@link DbIndex} that would be created by executing it.
   */
  public DbIndex asIndex() {

    return this.index;
  }

  private class DbIndexAdapter extends AbstractToString implements DbIndex {

    private DbIndexAdapter() {

      super();
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
    public String getName(DbNamingStrategy namingStrategy) {

      if (namingStrategy == null) {
        return CreateIndexStatement.this.createIndex.getName();
      }
      return namingStrategy.getIndexName(this);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public DbTableReference<EntityBean> getTable() {

      return (DbTableReference) CreateIndexStatement.this.on;
    }

    @Override
    public List<DbColumnReferenceWithSortOrder> getColumns() {

      return CreateIndexStatement.this.column.getColumns();
    }

  }

}
