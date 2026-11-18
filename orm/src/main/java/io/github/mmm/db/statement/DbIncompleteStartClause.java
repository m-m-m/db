/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.statement;

/// A [DbStartClause] that is not a [DbMainClause] and therefore incomplete before the statement is properly
/// build and received via [DbMainClause#get()]. However, for generic access it offers the method
/// [#get()].
///
/// @since 1.0.0
public interface DbIncompleteStartClause extends DbStartClause, DbClauseWithStatement {

}
