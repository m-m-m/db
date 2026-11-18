/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.repository.spi;

import io.github.mmm.bean.ReadableBean;
import io.github.mmm.db.repository.EntityRepository;
import io.github.mmm.entity.Entity;
import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.bean.listener.EntityListener;
import io.github.mmm.entity.bean.listener.EntityListenerAdapter;
import io.github.mmm.entity.id.Id;
import io.github.mmm.entity.id.generator.IdGenerator;

/// Abstract base implementation of [EntityRepository].
///
/// @param <E> type of the managed [Entity].
/// @since 1.0.0
public abstract class AbstractEntityRepository<E extends EntityBean> implements EntityRepository<E> {

  /// @see #getPrototype()
  protected final E prototype;

  /// @see #getEntityClass()
  protected final Class<E> entityClass;

  private EntityListenerAdapter<E> listenerAdapter;

  /// The constructor.
  ///
  /// @param prototype the [prototype][#getPrototype()].
  public AbstractEntityRepository(E prototype) {

    super();
    this.prototype = prototype;
    this.entityClass = ReadableBean.getJavaClass(this.prototype);
    this.listenerAdapter = EntityListenerAdapter.empty();
  }

  /// Initialises this repository.
  public void init() {

  }

  /// @return the [IdGenerator] to use.
  protected abstract IdGenerator getIdGenerator();

  /// @return an instance of the managed [entity][EntityBean] to be used as template. Please note that this is an internal method of the implementation that
  ///         should only be used by framework code. Mutations on the prototype by callers of this method are strictly forbidden and will lead to severe bugs.
  @Override
  public E getPrototype() {

    return this.prototype;
  }

  /// Verifies that an entity [Class] (e.g. from an [Id]) is valid for this repository.
  ///
  /// @param entityType the [Class] reflecting the [EntityBean] to process.
  protected void verifyEntityClass(Class<?> entityType) {

    // TODO support inheritance via isAssignableFrom
    assert (entityType == this.prototype.getJavaClass());
  }

  /// @return the [Class] reflecting the managed [EntityBean].
  @Override
  public Class<E> getEntityClass() {

    return this.entityClass;
  }

  @Override
  public final E findById(Id<E> id) {

    verifyEntityClass(id.getEntityClass());
    E entity = doFindById(id);
    if (entity != null) {
      this.listenerAdapter.postLoad(entity);
    }
    return entity;
  }

  /// @param id the [Id] of the requested [entity][EntityBean].
  /// @return the requested [entity][EntityBean] or `null` if no such entity exists.
  /// @see #findById(Id)
  protected abstract E doFindById(Id<E> id);

  @Override
  public final E save(E entity) {

    Id<E> id = Id.from(entity);
    boolean insert;
    Object pk = id.getPk();
    if (pk == null) {
      id = getIdGenerator().generate(id);
      entity.setId(id);
      insert = true;
    } else {
      Comparable<?> revision = id.getRevision();
      if (revision == null) {
        insert = true;
      } else {
        insert = false;
      }
    }
    if (insert) {
      this.listenerAdapter.preInsert(entity);
      return doInsert(entity);
    } else {
      this.listenerAdapter.preUpdate(entity);
      return doUpdate(entity);
    }
  }

  /// Internal method for the raw insert of an [EntityBean] to the underlying store.
  ///
  /// @param entity the [EntityBean] to insert.
  /// @return the saved [EntityBean] that has been inserted.
  protected abstract E doInsert(E entity);

  /// Internal method for the raw update of an [EntityBean] in the underlying store.
  ///
  /// @param entity the [EntityBean] to update.
  /// @return the saved [EntityBean] that has been updated.
  protected abstract E doUpdate(E entity);

  @Override
  public final boolean deleteById(Id<E> id) {

    verifyEntityClass(id.getEntityClass());
    this.listenerAdapter.preDelete(id);
    return doDeleteById(id);
  }

  /// @param id the [Id] (primary key) of the [entity][EntityBean] to delete.
  /// @return `true` on success, `false` otherwise.
  /// @see #deleteById(Id)
  protected abstract boolean doDeleteById(Id<E> id);

  @Override
  public final int deleteAllById(Iterable<Id<E>> ids) {

    this.listenerAdapter.preDelete(ids);
    return doDeleteAllById(ids);
  }

  /// @param ids the [Iterable] of [Id]s to [delete][#deleteById(Id)].
  /// @return the number of entities that have been physically deleted. * @param ids
  /// @see #deleteAllById(Iterable)
  protected abstract int doDeleteAllById(Iterable<Id<E>> ids);

  /// @return `true` to verify and update the [revision][io.github.mmm.entity.id.Id#getRevision()] on [save][#save(EntityBean)].
  protected boolean isUseRevision() {

    return true;
  }

  /// @param listener the [EntityListener] to add.
  protected void addListener(EntityListener<? super E> listener) {

    this.listenerAdapter = this.listenerAdapter.add(listener);
  }

  /// @param listener the [EntityListener] to remove.
  /// @return `true` if successfully removed, `false` otherwise (was not [added][#addListener(EntityListener)] before).
  protected boolean removeListener(EntityListener<? super E> listener) {

    EntityListenerAdapter<E> removed = this.listenerAdapter.remove(listener);
    if (removed == null) {
      return false;
    }
    this.listenerAdapter = removed;
    return true;
  }

}
