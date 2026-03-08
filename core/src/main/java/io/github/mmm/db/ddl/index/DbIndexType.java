/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.ddl.index;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import io.github.mmm.base.exception.ObjectNotFoundException;
import io.github.mmm.db.name.DbKeyword;

/**
 * Type of a {@link DbIndexMetadata} modelled as a dynamic enum. In case you need to add support for an {@link DbIndexType} that
 * we are not aware of, you can create it by extending this class and define your custom types as constants in the same
 * class. Be aware that you need to initialise your sub-class early on during bootstrapping.
 *
 * @see DbIndexKindType
 */
public class DbIndexType {

  private static final Map<String, DbIndexType> TYPE_MAP = new HashMap<>();

  /** Type of a <a href="https://en.wikipedia.org/wiki/Bitmap_index">b-tree index</a>. */
  public static final DbIndexType NONE = new DbIndexType("");

  /** Type of a <a href="https://en.wikipedia.org/wiki/Bitmap_index">b-tree index</a>. */
  public static final DbIndexType BTREE = new DbIndexType("BTREE");

  /** Type of a <a href="https://en.wikipedia.org/wiki/Database_index#Hash_index">hash index</a>. */
  public static final DbIndexType HASH = new DbIndexType("HASH");

  /** Type of a <a href="https://en.wikipedia.org/wiki/Database_index#Dense_index">dense index</a>. */
  public static final DbIndexType DENSE = new DbIndexType("DENSE");

  /** Type of a <a href="https://en.wikipedia.org/wiki/Database_index#Sparse_index">sparse index</a>. */
  public static final DbIndexType SPARSE = new DbIndexType("SPARSE");

  /** Type of a <a href="https://en.wikipedia.org/wiki/Reverse_index">reverse index</a>. */
  public static final DbIndexType REVERSE = new DbIndexType("REVERSE");

  /** Type of a <a href="https://en.wikipedia.org/wiki/Inverted_index">inverted index</a>. */
  public static final DbIndexType INVERTED = new DbIndexType("INVERTED");

  /** Type of a <a href="https://en.wikipedia.org/wiki/Spatial_database#Spatial_index">spatial index</a>. */
  public static final DbIndexType SPARTIAL = new DbIndexType("SPARTIAL");

  /** Type of a <a href="https://en.wikipedia.org/wiki/Vector_database">vector index</a>. */
  public static final DbIndexType VECTOR = new DbIndexType("VECTOR");

  /** Type of a <a href="https://en.wikipedia.org/wiki/Search_engine_indexing">fulltext index</a>. */
  public static final DbIndexType FULLTEXT = new DbIndexType("FULLTEXT");

  private final String type;

  /**
   * The constructor.
   *
   * @param type the {@link #toString() actual type string}.
   */
  protected DbIndexType(String type) {

    super();
    Objects.requireNonNull(type);
    assert (type.equals(type.toUpperCase(Locale.US)));
    if (DbKeyword.TABLE.equals(type) || DbKeyword.SEQUENCE.equals(type) || DbKeyword.INDEX.equals(type)
        || DbKeyword.PROCEDURE.equals(type) || DbKeyword.TRIGGER.equals(type) || DbKeyword.PACKAGE.equals(type)) {
      throw new IllegalArgumentException(type);
    }
    this.type = type;
    DbIndexType duplicate = TYPE_MAP.putIfAbsent(type, this);
    assert (duplicate == null) : "duplicate DbIndexType: " + type;
  }

  @Override
  public String toString() {

    return this.type;
  }

  /**
   * @param type the {@link #toString() string representation} of the requested {@link DbIndexType}.
   * @return the requested {@link DbIndexType} or {@code null} if not found.
   */
  public static DbIndexType get(String type) {

    return TYPE_MAP.get(type.toUpperCase(Locale.ROOT));
  }

  /**
   * @param type the {@link #toString() string representation} of the requested {@link DbIndexType}.
   * @return the requested {@link DbIndexType}.
   * @throws ObjectNotFoundException if no {@link DbIndexType} is registered for the given {@code type}.
   */
  public static DbIndexType getRequired(String type) {

    DbIndexType result = get(type);
    if (result == null) {
      throw new ObjectNotFoundException(DbIndexType.class, type);
    }
    return result;
  }

  /**
   * @return a {@link Stream} of all registered {@link DbIndexType}s.
   */
  public static Stream<DbIndexType> getAll() {

    return TYPE_MAP.values().stream();
  }

}
