package io.github.mmm.db.tx.spi;

import io.github.mmm.entity.bean.EntityBean;

/// Interface for a holder of the [internal entity][#getInternal()] from the first-level cache together with its
/// [external reference][#getExternal()].
///
/// @param <E> type of the managed [EntityBean].
/// @since 1.0.0
public interface DbEntityHolder<E extends EntityBean> {

  /// @return the internal [EntityBean] from the first-level cache. It is mutable and must strictly be kept
  ///         internal and never passed to the outside world (via public API). Implementors have to protected it with
  ///         their life.
  E getInternal();

  /// @return the external [read-only view][EntityBean#getReadOnly()] of the [internal][#getInternal()] entity.
  E getExternal();

  /// Updates the entity/entities in this holder.
  ///
  /// @param entity the new entity that has been updated.
  void update(E entity);

}
