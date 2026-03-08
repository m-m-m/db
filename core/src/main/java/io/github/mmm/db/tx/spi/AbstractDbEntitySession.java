/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.tx.spi;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import io.github.mmm.base.exception.DuplicateObjectException;
import io.github.mmm.entity.Entity;
import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.id.Id;

/**
 * Abstract base implementation of {@link DbEntitySession}.
 *
 * @param <E> type of the managed {@link EntityBean}.
 * @since 1.0.0
 */
public abstract class AbstractDbEntitySession<E extends EntityBean> implements DbEntitySession<E> {

  /** @see #getEntityClass() */
  protected final Class<E> entityClass;

  private final Map<Object, DbEntityHolder<E>> entityMap; // first-level cache

  /**
   * The constructor.
   *
   * @param entity the prototype of managed the {@link EntityBean}.
   */
  public AbstractDbEntitySession(E entity) {

    super();
    this.entityClass = Entity.getJavaClass(entity);
    this.entityMap = new ConcurrentHashMap<>();
  }

  @Override
  public Class<E> getEntityClass() {

    return this.entityClass;
  }

  @Override
  public DbEntityHolder<E> getOrLoad(Id<E> id, Function<Id<E>, E> loader) {

    if (id == null) {
      return null;
    }
    Object pk = id.getPk();
    if (pk == null) {
      return null;
    }
    DbEntityHolder<E> holder;
    if (loader == null) {
      holder = this.entityMap.get(pk);
    } else {
      holder = this.entityMap.computeIfAbsent(pk, _ -> createHolder(loader.apply(id)));
    }
    return holder;
  }

  @Override
  public DbEntityHolder<E> put(E managedEntity, Id<E> id) {

    Objects.requireNonNull(managedEntity);
    Objects.requireNonNull(id);
    Object pk = id.getPk();
    if (pk == null) {
      throw new IllegalArgumentException("Missing primary key for given ID " + id);
    }
    DbEntityHolder<E> holder = createHolder(managedEntity);
    DbEntityHolder<E> duplicate = this.entityMap.putIfAbsent(pk, holder);
    if (duplicate != null) {
      throw new DuplicateObjectException(managedEntity, id, duplicate); // should never happen
    }
    return holder;
  }

  /**
   * @param managed the {@link DbEntityHolder#getInternal() managed entity}.
   * @return the new {@link DbEntityHolder} instance.
   */
  protected abstract DbEntityHolder<E> createHolder(E managed);

  /**
   * Closes this session and releases allocated resources.
   */
  protected void close() {

    this.entityMap.clear();
  }

}
