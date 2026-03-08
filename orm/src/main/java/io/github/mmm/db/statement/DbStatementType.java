/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.statement;

/**
 * Interface for the type of a {@link DbStatement}.
 *
 * @since 1.0.0
 * @see DbStatement#getType()
 */
public interface DbStatementType {

  /**
   * @return {@code true} for a data-definition-language statement such as e.g.
   *         {@link io.github.mmm.db.statement.create.CreateTableStatement},
   *         {@link io.github.mmm.db.statement.create.CreateIndexStatement},
   *         {@link io.github.mmm.db.statement.alter.AlterTableStatement} or
   *         {@link io.github.mmm.db.statement.drop.DropTableStatement}, {@code false} otherwise.
   */
  boolean isDdl();

  /**
   *
   * @return {@code true} for a data-manipulation-language statement such as e.g.
   *         {@link io.github.mmm.db.statement.insert.InsertStatement},
   *         {@link io.github.mmm.db.statement.update.UpdateStatement},
   *         {@link io.github.mmm.db.statement.delete.DeleteStatement}, or even a
   *         {@link io.github.mmm.db.statement.select.SelectStatement}, {@code false} otherwise.
   */
  boolean isDml();

  /**
   * @return {@code true} for a {@link io.github.mmm.db.statement.select.SelectStatement}, {@code false} otherwise.
   */
  boolean isQuery();

}
