/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.statement.create;

import io.github.mmm.db.statement.AbstractDbStatement;
import io.github.mmm.db.statement.AbstractEntityClause;

/**
 * {@link AbstractDbStatement} to create some object (table, index, sequence, etc.).
 *
 * @param <E> type of the {@link AbstractEntityClause#getEntity() entity}.
 * @since 1.0.0
 */
public abstract class CreateStatement<E> extends AbstractDbStatement<E> {
}
