/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.statement.drop;

import java.util.function.Consumer;

import io.github.mmm.db.statement.AbstractDbClause;
import io.github.mmm.db.statement.AliasMap;
import io.github.mmm.db.statement.DbStartClause;

/**
 * {@link DropStatement} to {@link DropSequenceClause drop a sequence}.
 *
 * @since 1.0.0
 */
public class DropSequenceStatement extends DropStatement<Void> {

  private final DropSequenceClause dropSequence;

  /**
   * The constructor.
   *
   * @param dropSequence the {@link #getDropSequence() drop sequence}.
   */
  public DropSequenceStatement(DropSequenceClause dropSequence) {

    super();
    this.dropSequence = dropSequence;
  }

  /**
   * @deprecated use {@link #getDropSequence()} to make it more explicit.
   */
  @Deprecated
  @Override
  public DbStartClause getStart() {

    return this.dropSequence;
  }

  /**
   * @return the opening {@link DropSequenceClause}.
   */
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
