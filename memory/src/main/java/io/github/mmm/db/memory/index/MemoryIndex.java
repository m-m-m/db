/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.memory.index;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

import io.github.mmm.base.lang.AbstractToString;
import io.github.mmm.bean.ReadableBean;
import io.github.mmm.db.ddl.column.DbColumnReference;
import io.github.mmm.db.ddl.column.DbColumnReferenceWithSortOrder;
import io.github.mmm.db.ddl.index.DbIndex;
import io.github.mmm.db.ddl.index.DbIndexType;
import io.github.mmm.db.ddl.table.DbTableReference;
import io.github.mmm.db.memory.repository.MemoryRepository;
import io.github.mmm.db.memory.repository.impl.MemoryRepositoryImpl;
import io.github.mmm.db.name.DbNamingStrategy;
import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.property.ReadableProperty;

/**
 * Simple in-memory index for {@link MemoryRepositoryImpl}. Will store {@link EntityBean}s using a key (e.g. a "title"
 * or "name" column if unique). Will ignore {@code null} values as keys.
 *
 * @param <K> type of the index key (e.g. {@link String}).
 * @param <E> type of the {@link EntityBean}.
 */
public abstract class MemoryIndex<K, E extends EntityBean> extends AbstractToString implements DbIndex {

  private final String name;

  private final DbTableReference<E> table;

  /** The {@link MemoryRepository}. */
  protected final MemoryRepository<E> repository;

  /** @see #getNormalizer() */
  protected final Function<K, K> normalizer;

  private final List<DbColumnReferenceWithSortOrder> columns;

  /**
   * The constructor.
   *
   * @param name the {@link #getName() name} of this index.
   * @param repository the owning {@link MemoryRepositoryImpl}.
   * @param normalizer the {@link #getNormalizer() normalizer}.
   * @param properties the {@link ReadableProperty properties} to index also known as {@link #getColumns() columns}.
   */
  public MemoryIndex(String name, MemoryRepository<E> repository, Function<K, K> normalizer,
      ReadableProperty<?>... properties) {

    super();
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

    this.name = name;
    E entity = ReadableBean.from(properties[0]);
    assert (entity != null);
    this.table = DbTableReference.of(entity);
  }

  @Override
  public String getName(DbNamingStrategy namingStrategy) {

    if (namingStrategy == null) {
      return this.name;
    }
    return namingStrategy.getIndexName(this);
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

  /**
   * @return the {@link Function} to normalize the values in this index. E.g. if you create an index on {@link String}
   *         properties, you can use {@link String#toLowerCase()} as normalizer to make the searches case-insensitive.
   *         The given {@link Function} will not be called with {@code null} values as keys but may return {@code null}
   *         to filter a given key.
   */
  public Function<K, K> getNormalizer() {

    return this.normalizer;
  }

  //
  // /**
  // * @return the number of properties to index.
  // */
  // public int getPropertyCount() {
  //
  // return this.properties.length;
  // }
  //
  // /**
  // * @param i the index of the property in the range from {@code 0} to <code>{@link #getPropertyCount()}-1</code>.
  // * @return the {@link io.github.mmm.property.WritableProperty#getName() property name} of the property to index or
  // * {@code null} if the given index is out of range.
  // */
  // public String getProperty(int i) {
  //
  // if ((i < 0) || (i >= this.properties.length)) {
  // return null;
  // }
  // return this.properties[i];
  // }

  /**
   * @param key the key to add to this index.
   * @param entity the {@link EntityBean} to add.
   */
  public void add(K key, E entity) {

    if (key == null) {
      return;
    }
    addNormalized(this.normalizer.apply(key), entity);
  }

  /**
   * @param key the {@link #getNormalizer() normalized} key to add to this index.
   * @param entity the {@link EntityBean} to add.
   */
  protected abstract void addNormalized(K key, E entity);

  /**
   * @param keys the {@link Iterable} of the keys to add to this index.
   * @param entity the {@link EntityBean} to associate.
   */
  public void addAll(Iterable<K> keys, E entity) {

    if (keys == null) {
      return;
    }
    for (K key : keys) {
      add(key, entity);
    }
  }

  /**
   * @param key the key to remove from this index.
   */
  public void remove(K key) {

    remove(key, null);
  }

  /**
   * @param key the key to remove from this index.
   * @param entity the optional {@link EntityBean} to assert the removal was consistent.
   */
  public void remove(K key, E entity) {

    if (key == null) {
      return;
    }
    removeNormalized(this.normalizer.apply(key), entity);
  }

  /**
   * @param key the {@link #getNormalizer() normalized} key to remove from this index.
   * @param entity the optional {@link EntityBean} to assert the removal was consistent.
   */
  protected abstract void removeNormalized(K key, E entity);

  /**
   * @param keys the {@link Iterable} of the keys to remove from this index.
   * @param entity the optional {@link EntityBean} to assert the removal was consistent.
   */
  public void removeAll(Iterable<K> keys, E entity) {

    if (keys == null) {
      return;
    }
    for (K key : keys) {
      remove(key, entity);
    }
  }

  /**
   * Updates the given {@link EntityBean} from {@code oldKey} to {@code newKey}.
   *
   * @param newKey the new to update to.
   * @param entity the {@link EntityBean} to update.
   * @param oldKey the old key the {@code entity} was associated before.
   */
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

  /**
   * Updates an entire {@link Set} of keys (e.g. for aliases or synonyms to index).
   *
   * @param newKeys the new {@link Set} of keys.
   * @param entity the {@link EntityBean} to update.
   * @param oldKeys the old {@link Set} of keys.
   */
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

  /**
   * Generic access method to find by key. If you know the specific index type, prefer {@code find} methods like
   * {@link MemoryUniqueIndex#find(Object)} or {@link MemoryNonUniqueIndex#find(Object)}.
   *
   * @param key the key of the {@link EntityBean} to find.
   * @return the {@link Iterable} of {@link EntityBean}s found for the given key.
   */
  public abstract Iterable<E> findAll(K key);

}
