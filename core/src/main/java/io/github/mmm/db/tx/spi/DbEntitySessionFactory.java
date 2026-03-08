/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.tx.spi;

import io.github.mmm.db.tx.impl.DbEntitySessionDefault;
import io.github.mmm.entity.bean.EntityBean;

/**
 * Factory to {@link #create(EntityBean) create} instances of {@link DbEntitySession}.
 *
 * @since 1.0.0
 */
@FunctionalInterface
public interface DbEntitySessionFactory {

  /**
   * @param <E> type of the {@link EntityBean}.
   * @param entity the {@link EntityBean} prototype.
   * @return the new {@link DbEntitySession} instance.
   */
  <E extends EntityBean> DbEntitySession<E> create(E entity);

  /**
   * @return the default instance of {@link DbEntitySessionFactory}.
   */
  static DbEntitySessionFactory get() {

    return DbEntitySessionDefault::new;
  }

}
