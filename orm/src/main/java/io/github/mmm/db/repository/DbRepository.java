/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.repository;

import io.github.mmm.db.repository.operation.DbDeleteOperations;
import io.github.mmm.db.repository.operation.DbFindOperations;
import io.github.mmm.db.repository.operation.DbUpdateOperations;
import io.github.mmm.db.source.DbSource;
import io.github.mmm.db.statement.select.SelectStatement;
import io.github.mmm.entity.bean.EntityBean;

/**
 * {@link EntityRepository} with DB operations like {@link #findByQuery(SelectStatement)}
 *
 * @param <E> type of the managed {@link EntityBean}.
 */
public interface DbRepository<E extends EntityBean>
    extends EntityRepository<E>, DbFindOperations<E>, DbDeleteOperations<E>, DbUpdateOperations<E> {

  /** The default {@link #getSequenceName() sequence name}. */
  String DEFAULT_SEQUENCE = "ENTITY_SEQUENCE";

  /**
   * @return the {@link DbSource} of this repository. Typically this is the {@link DbSource#get() default}
   *         {@link DbSource}. May be overridden to connect your repository to a different {@link DbSource database
   *         source}.
   */
  default DbSource getSource() {

    return DbSource.get();
  }

  /**
   * @return the (unqualified) name of the database sequence for the managed entity. May be {@code null} for non
   *         sequence based IDs (e.g. UUID).
   */
  default String getSequenceName() {

    return DEFAULT_SEQUENCE;
  }

}
