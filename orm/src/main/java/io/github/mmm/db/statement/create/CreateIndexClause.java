/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.statement.create;

import io.github.mmm.db.ddl.index.DbIndexKindType;
import io.github.mmm.db.name.DbKeyword;
import io.github.mmm.db.statement.AbstractDbClause;
import io.github.mmm.db.statement.DbIncompleteStartClause;
import io.github.mmm.db.statement.DbStartClause;
import io.github.mmm.entity.bean.EntityBean;

/**
 * A {@link CreateIndexClause}-{@link DbStartClause} of an SQL {@link CreateIndexStatement}.
 *
 * @since 1.0.0
 * @see io.github.mmm.db.statement.DbStatement#createIndex()
 */
public class CreateIndexClause extends AbstractDbClause implements DbIncompleteStartClause {

  /** Name of {@link CreateIndexClause} for marshaling. */
  public static final String NAME_CREATE_INDEX = DbKeyword.CREATE_INDEX;

  private final DbIndexKindType kind;

  private CreateIndexStatement<?> statement;

  private String name;

  /**
   * The constructor.
   *
   * @param kind the {@link DbIndexKindType}.
   * @param name the {@link #getName() name of the index}.
   */
  public CreateIndexClause(DbIndexKindType kind, String name) {

    super();
    this.kind = kind;
    this.name = name;
  }

  /**
   * @return the name of the index. Will be empty if it should be auto-generated.
   */
  public String getName() {

    return this.name;
  }

  /**
   * @param name new value of {@link #getName()}.
   */
  public void setName(String name) {

    this.name = name;
  }

  /**
   * @return the {@link DbIndexKindType}.
   */
  public DbIndexKindType getKind() {

    return this.kind;
  }

  /**
   * @param <E> type of the {@link CreateIndexOnClause#getEntity() entity}.
   * @param entity the {@link CreateIndexOnClause#getEntity() entity} (table) to create an index on.
   * @return the {@link CreateIndexOnClause} for fluent API calls.
   */
  public <E extends EntityBean> CreateIndexOnClause<E> on(E entity) {

    return on(entity, null);
  }

  /**
   * @param <E> type of the {@link CreateIndexOnClause#getEntity() entity}.
   * @param entity the {@link CreateIndexOnClause#getEntity() entity} (table) to create an index on.
   * @param entityName the {@link CreateIndexOnClause#getName() entity name} (table name) to create an index on.
   * @return the {@link CreateIndexOnClause} for fluent API calls.
   */
  public <E extends EntityBean> CreateIndexOnClause<E> on(E entity, String entityName) {

    CreateIndexOnClause<E> onClause = new CreateIndexOnClause<>(this, entity, entityName);
    this.statement = onClause.get();
    return onClause;
  }

  @Override
  public CreateIndexStatement<?> get() {

    return this.statement;
  }

}
