/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.statement.drop;

import java.util.function.Consumer;

import io.github.mmm.db.statement.AbstractDbClause;
import io.github.mmm.db.statement.AliasMap;
import io.github.mmm.db.statement.DbStartClause;

/// [DropStatement] to [drop a sequence][DropSequenceClause].
///
/// @since 1.0.0
public class DropSequenceStatement extends DropStatement<Void> {

  private final DropSequenceClause dropSequence;

  /// The constructor.
  ///
  /// @param dropSequence the [drop sequence][#getDropSequence()].
  public DropSequenceStatement(DropSequenceClause dropSequence) {

    super();
    this.dropSequence = dropSequence;
  }

  /// @deprecated use [#getDropSequence()] to make it more explicit.
  @Deprecated
  @Override
  public DbStartClause getStart() {

    return this.dropSequence;
  }

  /// @return the opening [DropSequenceClause].
  public DropSequenceClause getDropSequence() {

    return this.dropSequence;
  }

  @Override
  protected void addClauses(Consumer<AbstractDbClause> consumer) {

    consumer.accept(this.dropSequence);
  }

  @Override
  protected AliasMap getAliasMap() {

    return null;
  }

}
