/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.statement.drop;

import java.util.function.Consumer;

import io.github.mmm.db.statement.AbstractDbClause;
import io.github.mmm.db.statement.AliasMap;
import io.github.mmm.db.statement.DbClause;
import io.github.mmm.db.statement.DbStartClause;

/// [DropStatement] to [drop an index][DropTableClause].
///
/// @since 1.0.0
public class DropIndexStatement extends DropStatement<Void> {

  private final DropIndexClause dropIndex;

  /// The constructor.
  ///
  /// @param dropIndex the [drop sequence][#getDropIndex()].
  public DropIndexStatement(DropIndexClause dropIndex) {

    super();
    this.dropIndex = dropIndex;
  }

  /// @deprecated use [#getDropIndex()] to make it more explicit.
  @Deprecated
  @Override
  public DbStartClause getStart() {

    return this.dropIndex;
  }

  /// @return the opening [DropTableClause]-[DbClause].
  public DropIndexClause getDropIndex() {

    return this.dropIndex;
  }

  @Override
  protected void addClauses(Consumer<AbstractDbClause> consumer) {

    consumer.accept(this.dropIndex);
  }

  @Override
  protected AliasMap getAliasMap() {

    return null;
  }

}
