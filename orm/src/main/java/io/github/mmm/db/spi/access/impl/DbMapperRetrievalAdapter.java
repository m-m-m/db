package io.github.mmm.db.spi.access.impl;

import io.github.mmm.db.mapping.DbMapper2Java;
import io.github.mmm.db.result.DbResult;
import io.github.mmm.db.tx.spi.DbEntityHolder;
import io.github.mmm.db.tx.spi.DbEntitySession;
import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.id.PkId;
import io.github.mmm.entity.property.id.PkProperty;

/**
 * Adapter on {@link DbMapper2Java} that retrieves entities from first level cache.
 *
 * @param <E> type of the managed {@link EntityBean}.
 */
public class DbMapperRetrievalAdapter<E extends EntityBean> implements DbMapper2Java<E> {

  private final DbMapper2Java<E> delegate;

  private final DbEntitySession<E> entitySession;

  private int idIndex;

  /**
   * The constructor.
   *
   * @param delegate the {@link DbMapper2Java} to delegate to.
   * @param entitySession the {@link DbEntitySession}.
   */
  public DbMapperRetrievalAdapter(DbMapper2Java<E> delegate, DbEntitySession<E> entitySession) {

    super();
    this.delegate = delegate;
    this.entitySession = entitySession;
    this.idIndex = -1;
  }

  @Override
  public E db2java(DbResult dbValue) {

    if (this.idIndex == -1) {
      this.idIndex = dbValue.indexOfRequired(PkProperty.NAME);
    }
    assert dbValue.getName(this.idIndex).equalsIgnoreCase(PkProperty.NAME);
    Object pk = dbValue.getValue(this.idIndex);
    PkId<E, ?, ?> id = PkId.of(this.entitySession.getEntityClass(), pk);
    DbEntityHolder<E> holder = this.entitySession.get(id);
    if (holder == null) {
      E entity = this.delegate.db2java(dbValue);
      holder = this.entitySession.put(entity);
    } else {
      // TODO sync data to internal entity? or assert values are equal?
    }
    return holder.getExternal();
  }

}
