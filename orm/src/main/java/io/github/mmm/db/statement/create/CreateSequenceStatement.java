/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.statement.create;

import java.util.function.Consumer;

import io.github.mmm.db.statement.AbstractDbClause;
import io.github.mmm.db.statement.AliasMap;
import io.github.mmm.db.statement.DbStartClause;
import io.github.mmm.db.statement.DbStatement;

/// [DbStatement] to [create a sequence][CreateSequenceClause].
///
/// @since 1.0.0
public class CreateSequenceStatement extends CreateStatement<Void> {

  private final CreateSequenceClause createSequence;

  /// The constructor.
  ///
  /// @param createSequence the [create sequence][#getCreateSequence()].
  public CreateSequenceStatement(CreateSequenceClause createSequence) {

    super();
    this.createSequence = createSequence;
  }

  /// @deprecated use [#getCreateSequence()] to make it more explicit.
  @Deprecated
  @Override
  public DbStartClause getStart() {

    return this.createSequence;
  }

  /// @return the opening [CreateSequenceClause].
  public CreateSequenceClause getCreateSequence() {

    return this.createSequence;
  }

  @Override
  protected void addClauses(Consumer<AbstractDbClause> consumer) {

    consumer.accept(this.createSequence);
  }

  @Override
  protected AliasMap getAliasMap() {

    return null;
  }

}
