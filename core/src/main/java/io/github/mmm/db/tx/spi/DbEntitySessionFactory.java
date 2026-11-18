/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.tx.spi;

import io.github.mmm.db.tx.impl.DbEntitySessionDefault;
import io.github.mmm.entity.bean.EntityBean;

/// Factory to [create][#create(EntityBean)] instances of [DbEntitySession].
///
/// @since 1.0.0
@FunctionalInterface
public interface DbEntitySessionFactory {

  /// @param <E> type of the [EntityBean].
  /// @param entity the [EntityBean] prototype.
  /// @return the new [DbEntitySession] instance.
  <E extends EntityBean> DbEntitySession<E> create(E entity);

  /// @return the default instance of [DbEntitySessionFactory].
  static DbEntitySessionFactory get() {

    return DbEntitySessionDefault::new;
  }

}
