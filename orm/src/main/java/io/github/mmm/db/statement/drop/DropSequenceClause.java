/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.statement.drop;

import io.github.mmm.db.name.DbQualifiedName;
import io.github.mmm.db.statement.AbstractDbClause;
import io.github.mmm.db.statement.DbMainClause;
import io.github.mmm.db.statement.DbStartClause;
import io.github.mmm.db.statement.DbStatement;

/**
 * A {@link DbStartClause} of a {@link DropTableStatement}.
 *
 * @since 1.0.0
 */
public class DropSequenceClause extends AbstractDbClause implements DbStartClause, DbMainClause<Void> {

  private final DropSequenceStatement statement;

  private final DbQualifiedName sequenceName;

  /**
   * The constructor.
   *
   * @param sequenceName the {@link #getSequenceName()}.
   */
  public DropSequenceClause(String sequenceName) {

    this(new DbQualifiedName(sequenceName, null, null));
  }

  /**
   * The constructor.
   *
   * @param sequenceName the {@link #getSequenceName()}.
   */
  public DropSequenceClause(DbQualifiedName sequenceName) {

    super();
    this.sequenceName = sequenceName;
    this.statement = new DropSequenceStatement(this);
  }

  @Override
  public DbStatement<Void> get() {

    return this.statement;
  }

  /**
   * @return the {@link DbQualifiedName} of the sequence to drop.
   */
  public DbQualifiedName getSequenceName() {

    return this.sequenceName;
  }

}
