/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.memory.index;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import io.github.mmm.db.memory.repository.MemoryRepository;
import io.github.mmm.db.name.DbQualifiedName;
import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.id.Id;
import io.github.mmm.property.ReadableProperty;

/// Simple in-memory unique index for [MemoryRepository]. Will store [EntityBean]s using a unique key (e.g. a
/// "title" or "name" column if unique). Will ignore `null` values as keys.
///
/// @param <K> type of the index key (e.g. [String]).
/// @param <E> type of the [EntityBean].
public class MemoryNonUniqueIndex<K, E extends EntityBean> extends MemoryIndex<K, E> {

  private final Map<K, Set<Id<E>>> map;

  /// The constructor.
  ///
  /// @param name the [name][#getName()] of this index.
  /// @param repository the owning [MemoryRepository].
  /// @param properties the [properties][ReadableProperty] to index also known as [columns][#getColumns()].
  public MemoryNonUniqueIndex(DbQualifiedName name, MemoryRepository<E> repository, ReadableProperty<?>... properties) {

    this(name, repository, Function.identity(), properties);
  }

  /// The constructor.
  ///
  /// @param name the [name][#getName()] of this index.
  /// @param repository the owning [MemoryRepository].
  /// @param normalizer the [normalizer][#getNormalizer()].
  /// @param properties the [properties][ReadableProperty] to index also known as [columns][#getColumns()].
  public MemoryNonUniqueIndex(DbQualifiedName name, MemoryRepository<E> repository, Function<K, K> normalizer,
      ReadableProperty<?>... properties) {

    super(name, repository, normalizer, properties);
    this.map = new HashMap<>(256);
  }

  @Override
  public boolean isUnique() {

    return false;
  }

  @Override
  protected void addNormalized(K key, E entity) {

    if (key == null) {
      return;
    }
    Set<Id<E>> set = this.map.computeIfAbsent(key, _ -> new HashSet<>());
    set.add(Id.from(entity).withoutRevision());
  }

  @Override
  protected void removeNormalized(K key, E entity) {

    if (key != null) {
      Set<Id<E>> set = this.map.get(key);
      if (set != null) {
        set.remove(Id.from(entity).withoutRevision());
      }
    }
  }

  /// @param key the key of the [EntityBean] to find.
  /// @return the [Iterable] of [EntityBean]s found for the given key.
  public Iterable<E> find(K key) {

    Iterable<E> result = Collections.emptySet();
    if (key != null) {
      key = this.normalizer.apply(key);
      if (key != null) {
        Set<Id<E>> ids = this.map.get(key);
        if (ids != null) {
          result = this.repository.findAllById(ids);
        }
      }
    }
    return result;
  }

  @Override
  public Iterable<E> findAll(K key) {

    return find(key);
  }

}
