/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.schema.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import io.github.mmm.base.collection.CollectionHelper;
import io.github.mmm.db.schema.DbSchemaManager;

/**
 * Node of a {@link DbSchemaManager changelog} that has {@link #get(String) children}.
 *
 * @param <K> type of the key.
 * @param <C> type of the {@link #get(String) children}.
 */
public abstract class DbChangeLogNodeParent<K extends Comparable<K>, C> {

  /** @see #getKeys() */
  protected final List<K> keys;

  /** @see #get(String) */
  protected final Map<String, C> map;

  /**
   * The constructor.
   */
  public DbChangeLogNodeParent() {

    super();
    this.keys = new LinkedList<>();
    this.map = new HashMap<>();
  }

  /**
   * @return the {@link Collection} with the available keys of {@link #get(String) children}.
   */
  public List<K> getKeys() {

    return this.keys;
  }

  /**
   * @param key the identifier of the requested child object.
   * @return the child object for the given {@code key} or {@code null} if not found.
   */
  public C get(String key) {

    return this.map.get(key);
  }

  /**
   * @param key the identifier of the requested child object.
   * @return the child object for the given {@code key}. Will be created lazily if no such child exists, yet.
   */
  public C getOrCreate(String key) {

    return this.map.computeIfAbsent(key, this::createChildInternal);
  }

  /**
   * @param key the key as {@link String}.
   * @return the key as parsed type.
   */
  protected abstract K toKey(String key);

  private C createChildInternal(String keyString) {

    K key = toKey(keyString);
    CollectionHelper.sortedInsert(key, this.keys);
    return createChild(key);
  }

  /**
   * @param key the key of the new child object.
   * @return the newly created child object.
   */
  protected abstract C createChild(K key);

}
