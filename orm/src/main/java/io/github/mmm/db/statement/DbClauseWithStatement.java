/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.statement;

/**
 * A {@link DbClause} that gives access to {@link #get()}.
 *
 * @since 1.0.0
 */
public abstract interface DbClauseWithStatement extends DbClause {

  /**
   * @return the owning {@link DbStatement}. May be {@code null} or result in an incomplete statement. This method
   *         should therefore not be used by API end-users.
   */
  DbStatement<?> get();

}
