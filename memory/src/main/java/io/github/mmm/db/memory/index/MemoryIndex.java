/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.memory.index;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

import io.github.mmm.bean.ReadableBean;
import io.github.mmm.db.ddl.column.DbColumnReference;
import io.github.mmm.db.ddl.column.DbColumnReferenceWithSortOrder;
import io.github.mmm.db.ddl.index.DbIndex;
import io.github.mmm.db.ddl.index.DbIndexType;
import io.github.mmm.db.ddl.table.DbTableReference;
import io.github.mmm.db.memory.repository.MemoryRepository;
import io.github.mmm.db.memory.repository.impl.MemoryRepositoryImpl;
import io.github.mmm.db.name.AbstractDbObject;
import io.github.mmm.db.name.DbQualifiedName;
import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.property.ReadableProperty;

/// Simple in-memory index for [MemoryRepositoryImpl]. Will store [EntityBean]s using a key (e.g. a "title"
/// or "name" column if unique). Will ignore `null` values as keys.
///
/// @param <K> type of the index key (e.g. [String]).
/// @param <E> type of the [EntityBean].
public abstract class MemoryIndex<K, E extends EntityBean> extends AbstractDbObject implements DbIndex {

  private final DbTableReference<E> table;

  /// The [MemoryRepository].
  protected final MemoryRepository<E> repository;

  /// @see #getNormalizer()
  protected final Function<K, K> normalizer;

  private final List<DbColumnReferenceWithSortOrder> columns;

  /// The constructor.
  ///
  /// @param name the [name][#getName()] of this index.
  /// @param repository the owning [MemoryRepositoryImpl].
  /// @param normalizer the [normalizer][#getNormalizer()].
  /// @param properties the [properties][ReadableProperty] to index also known as [columns][#getColumns()].
  public MemoryIndex(DbQualifiedName name, MemoryRepository<E> repository, Function<K, K> normalizer,
      ReadableProperty<?>... properties) {

    super(name);
    assert (properties != null);
    assert (properties.length > 0);
    this.repository = repository;
    this.normalizer = normalizer;

    DbColumnReferenceWithSortOrder[] cols = new DbColumnReferenceWithSortOrder[properties.length];
    for (int i = 0; i < properties.length; i++) {
      DbColumnReference columnRef = DbColumnReference.of(properties[i]);
      cols[i] = DbColumnReferenceWithSortOrder.of(columnRef);
    }
    this.columns = List.of(cols);

    E entity = ReadableBean.from(properties[0]);
    assert (entity != null);
    this.table = DbTableReference.of(entity);
  }

  @Override
  public DbTableReference<E> getTable() {

    return this.table;
  }

  @Override
  public DbIndexType getType() {

    return DbIndexType.HASH;
  }

  @Override
  public boolean isClustered() {

    return false;
  }

  @Override
  public List<DbColumnReferenceWithSortOrder> getColumns() {

    return this.columns;
  }

  /// @return the [Function] to normalize the values in this index. E.g. if you create an index on [String]
  ///         properties, you can use [String#toLowerCase()] as normalizer to make the searches case-insensitive.
  ///         The given [Function] will not be called with `null` values as keys but may return `null`
  ///         to filter a given key.
  public Function<K, K> getNormalizer() {

    return this.normalizer;
  }

  /// @param key the key to add to this index.
  /// @param entity the [EntityBean] to add.
  public void add(K key, E entity) {

    if (key == null) {
      return;
    }
    addNormalized(this.normalizer.apply(key), entity);
  }

  /// @param key the [normalized][#getNormalizer()] key to add to this index.
  /// @param entity the [EntityBean] to add.
  protected abstract void addNormalized(K key, E entity);

  /// @param keys the [Iterable] of the keys to add to this index.
  /// @param entity the [EntityBean] to associate.
  public void addAll(Iterable<K> keys, E entity) {

    if (keys == null) {
      return;
    }
    for (K key : keys) {
      add(key, entity);
    }
  }

  /// @param key the key to remove from this index.
  public void remove(K key) {

    remove(key, null);
  }

  /// @param key the key to remove from this index.
  /// @param entity the optional [EntityBean] to assert the removal was consistent.
  public void remove(K key, E entity) {

    if (key == null) {
      return;
    }
    removeNormalized(this.normalizer.apply(key), entity);
  }

  /// @param key the [normalized][#getNormalizer()] key to remove from this index.
  /// @param entity the optional [EntityBean] to assert the removal was consistent.
  protected abstract void removeNormalized(K key, E entity);

  /// @param keys the [Iterable] of the keys to remove from this index.
  /// @param entity the optional [EntityBean] to assert the removal was consistent.
  public void removeAll(Iterable<K> keys, E entity) {

    if (keys == null) {
      return;
    }
    for (K key : keys) {
      remove(key, entity);
    }
  }

  /// Updates the given [EntityBean] from `oldKey` to `newKey`.
  ///
  /// @param newKey the new to update to.
  /// @param entity the [EntityBean] to update.
  /// @param oldKey the old key the `entity` was associated before.
  public void update(K newKey, E entity, K oldKey) {

    if (newKey != null) {
      newKey = this.normalizer.apply(newKey);
    }
    if (oldKey != null) {
      oldKey = this.normalizer.apply(oldKey);
    }
    if (Objects.equals(newKey, oldKey)) {
      return;
    }
    removeNormalized(oldKey, entity);
    addNormalized(newKey, entity);
  }

  /// Updates an entire [Set] of keys (e.g. for aliases or synonyms to index).
  ///
  /// @param newKeys the new [Set] of keys.
  /// @param entity the [EntityBean] to update.
  /// @param oldKeys the old [Set] of keys.
  public void updateAll(Iterable<K> newKeys, E entity, Iterable<K> oldKeys) {

    if (isEmpty(oldKeys)) {
      for (K key : newKeys) {
        add(key, entity);
      }
      return;
    }
    if (isEmpty(newKeys)) {
      for (K key : oldKeys) {
        remove(key);
      }
      return;
    }
    if (newKeys.equals(oldKeys)) {
      return;
    }
    Set<K> removeSet = new HashSet<>();
    for (K key : oldKeys) {
      if (key != null) {
        key = this.normalizer.apply(key);
        if (key != null) {
          removeSet.add(key);
        }
      }
    }
    for (K key : newKeys) {
      if (key != null) {
        key = this.normalizer.apply(key);
        if (key != null) {
          if (!removeSet.remove(key)) {
            addNormalized(key, entity);
          }
        }
      }
    }
    for (K key : removeSet) {
      removeNormalized(key, entity);
    }
  }

  private boolean isEmpty(Iterable<?> iterable) {

    if (iterable instanceof Collection<?> c) {
      return c.isEmpty();
    }
    return iterable.iterator().hasNext();
  }

  /// Generic access method to find by key. If you know the specific index type, prefer `find` methods like
  /// [MemoryUniqueIndex#find(Object)] or [MemoryNonUniqueIndex#find(Object)].
  ///
  /// @param key the key of the [EntityBean] to find.
  /// @return the [Iterable] of [EntityBean]s found for the given key.
  public abstract Iterable<E> findAll(K key);

}
