/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.ddl.table;

import java.util.Objects;

import io.github.mmm.base.io.UncheckedAppendable;
import io.github.mmm.bean.ReadableBean;
import io.github.mmm.db.ddl.table.impl.DbTableReferenceImpl;
import io.github.mmm.db.name.DbNamingStrategy;
import io.github.mmm.db.name.DbObject;
import io.github.mmm.db.name.DbQualifiedName;
import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.property.ReadableProperty;

/**
 * Reference to a {@link DbTable database table} (or view, materialized view, etc.).
 *
 * @param <E> type of the referenced {@link EntityBean}.
 * @since 1.0.0
 */
public interface DbTableReference<E extends EntityBean> extends DbObject {

  /**
   * @return the optional {@link EntityBean} from where to get the data and to operate on.
   */
  E getEntity();

  @Override
  default String getName(DbNamingStrategy namingStrategy) {

    return getQualifiedName(namingStrategy).getName();
  }

  /**
   * @return the {@link DbQualifiedName} referencing the owning {@link DbTable}.
   */
  default DbQualifiedName getQualifiedName() {

    return getQualifiedName(DbNamingStrategy.of());
  }

  /**
   * @param namingStrategy the {@link DbNamingStrategy} to use. May be {@code null} to get the raw
   *        {@link DbQualifiedName}.
   * @return the {@link DbQualifiedName} referencing the owning {@link DbTable} formatted using the given
   *         {@link DbNamingStrategy}.
   */
  DbQualifiedName getQualifiedName(DbNamingStrategy namingStrategy);

  @Override
  default void toString(UncheckedAppendable sb, int mode) {

    sb.append(getQualifiedName());
  }

  /**
   * @param property the {@link ReadableProperty} of an {@link EntityBean}.
   * @return the new {@link DbTableReference}.
   */
  static DbTableReference<EntityBean> of(ReadableProperty<?> property) {

    Objects.requireNonNull(property);
    ReadableBean bean = ReadableBean.from(property);
    if (bean instanceof EntityBean entity) {
      return of(entity);
    }
    throw new IllegalArgumentException("Property " + property.getClass().getSimpleName() + "[" + property.toString()
        + "] is from bean " + bean.getType() + " that is not an EntityBean.");
  }

  /**
   * @param <E> type of the {@link #getEntity() entity}.
   * @param entity the {@link #getEntity() entity}.
   * @return the new {@link DbTableReference}.
   */
  static <E extends EntityBean> DbTableReference<E> of(E entity) {

    return new DbTableReferenceImpl<>(null, entity);
  }

  /**
   * @param <E> type of the {@link #getEntity() entity}.
   * @param name the {@link #getQualifiedName() qualified name}.
   * @param entity the {@link #getEntity() entity}.
   * @return the new {@link DbTableReference}.
   */
  static <E extends EntityBean> DbTableReference<E> of(String name, E entity) {

    return of(DbQualifiedName.of(name), entity);
  }

  /**
   * @param <E> type of the {@link #getEntity() entity}.
   * @param name the {@link #getQualifiedName() qualified name}.
   * @param entity the {@link #getEntity() entity}.
   * @return the new {@link DbTableReference}.
   */
  static <E extends EntityBean> DbTableReference<E> of(DbQualifiedName name, E entity) {

    return new DbTableReferenceImpl<>(name, entity);
  }

}
