/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.statement;

import io.github.mmm.entity.bean.EntityBean;

/// A [DbClause] that is typed by the [EntityBean].
///
/// @param <E> type of this [DbClause] (typically the [entity][AbstractEntityClause#getEntity()]).
/// @since 1.0.0
public interface DbTypedClause<E> extends DbClause {

}
