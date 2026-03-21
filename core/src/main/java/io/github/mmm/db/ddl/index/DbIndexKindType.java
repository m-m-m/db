/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.ddl.index;

import java.util.Objects;

import io.github.mmm.base.lang.AbstractToString;

/**
 * Implementation of {@link DbIndexKind} as immutable type.
 */
public class DbIndexKindType extends AbstractToString implements DbIndexKind {

  /** The default instance. */
  public static final DbIndexKindType DEFAULT = new DbIndexKindType(false, false, DbIndexType.NONE);

  /** An instance with defaults but {@link #isUnique() unique}. */
  public static final DbIndexKindType DEFAULT_UNIQUE = new DbIndexKindType(true, false, DbIndexType.NONE);

  /** An instance with defaults but {@link #isClustered() clustered}. */
  public static final DbIndexKindType DEFAULT_CLUSTERED = new DbIndexKindType(false, true, DbIndexType.NONE);

  private final boolean unique;

  private final boolean clustered;

  private final DbIndexType type;

  /**
   * The constructor.
   *
   * @param unique the {@link #isUnique() unique} flag.
   * @param clustered the {@link #isClustered() clustered} flag.
   * @param type the {@link #getType() type}.
   */
  public DbIndexKindType(boolean unique, boolean clustered, DbIndexType type) {

    super();
    Objects.requireNonNull(type);
    this.unique = unique;
    this.clustered = clustered;
    this.type = type;
  }

  @Override
  public boolean isUnique() {

    return this.unique;
  }

  @Override
  public boolean isClustered() {

    return this.clustered;
  }

  @Override
  public DbIndexType getType() {

    return this.type;
  }

}
