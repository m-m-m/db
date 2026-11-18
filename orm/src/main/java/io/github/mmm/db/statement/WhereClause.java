/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.statement;

import io.github.mmm.entity.bean.EntityBean;

/// A [WhereClause]-[DbClause] of an SQL [DbStartClause].
///
/// @param <E> type of the [EntityBean] to query.
/// @param <SELF> type of this class itself.
/// @since 1.0.0
public abstract class WhereClause<E, SELF extends WhereClause<E, SELF>> extends PredicateClause<E, SELF>
    implements DbMainClause<E> {

  /// The constructor.
  public WhereClause() {

    super();
  }

}
