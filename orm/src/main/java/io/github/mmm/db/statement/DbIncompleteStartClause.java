/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.statement;

/**
 * A {@link DbStartClause} that is not a {@link DbMainClause} and therefore incomplete before the statement is properly
 * build and received via {@link DbMainClause#get()}. However, for generic access it offers the method
 * {@link #get()}.
 *
 * @since 1.0.0
 */
public interface DbIncompleteStartClause extends DbStartClause, DbClauseWithStatement {

}
