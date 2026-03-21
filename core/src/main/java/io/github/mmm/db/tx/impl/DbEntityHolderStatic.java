package io.github.mmm.db.tx.impl;

import io.github.mmm.bean.BeanHelper;
import io.github.mmm.bean.WritableBean;
import io.github.mmm.db.tx.spi.AbstractDbEntityHolder;
import io.github.mmm.db.tx.spi.DbEntityHolder;
import io.github.mmm.entity.bean.EntityBean;

/**
 * Implementation of {@link DbEntityHolder} with static {@link #getExternal() external entity}.
 *
 * @param <E> type of the managed {@link EntityBean}.
 * @since 1.0.0
 */
public class DbEntityHolderStatic<E extends EntityBean> extends AbstractDbEntityHolder<E> {

  private final E external;

  /**
   * The constructor.
   *
   * @param internal the {@link #getInternal() internal entity}.
   */
  public DbEntityHolderStatic(E internal) {

    this(internal, WritableBean.getReadOnly(internal));
  }

  /**
   * The constructor.
   *
   * @param internal the {@link #getInternal() internal entity}.
   * @param external the {@link #getExternal() external entity}.
   */
  public DbEntityHolderStatic(E internal, E external) {

    super(internal);
    this.external = external;
  }

  @Override
  public E getExternal() {

    return this.external;
  }

  @Override
  public void update(E entity) {

    super.update(entity);
    if (!this.external.isReadOnly()) {
      BeanHelper.copy(entity, this.external, true);
    }
  }

}
