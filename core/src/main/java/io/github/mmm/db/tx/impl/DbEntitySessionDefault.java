/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.tx.impl;

import io.github.mmm.db.tx.spi.AbstractDbEntitySession;
import io.github.mmm.db.tx.spi.DbEntityHolder;
import io.github.mmm.entity.bean.EntityBean;

/// Default implementation of [io.github.mmm.db.tx.spi.DbEntitySession].
///
/// @param <E> type of the managed [EntityBean].
///
/// @since 1.0.0
public class DbEntitySessionDefault<E extends EntityBean> extends AbstractDbEntitySession<E> {

  /// The constructor.
  ///
  /// @param entity the [EntityBean] prototype.
  public DbEntitySessionDefault(E entity) {

    super(entity);
  }

  @Override
  protected DbEntityHolder<E> createHolder(E managed) {

    return new DbEntityHolderStatic<>(managed);
  }

}
