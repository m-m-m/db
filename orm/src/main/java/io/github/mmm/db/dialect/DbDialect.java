/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.dialect;

import io.github.mmm.db.name.DbNamingStrategy;
import io.github.mmm.db.name.DbQualifiedName;
import io.github.mmm.db.statement.BasicDbStatementFormatter;
import io.github.mmm.db.statement.DbStatementFormatter;

/// Interface for an database dialect. It abstracts from the concrete syntax (e.g. specific SQL) of a database.
public interface DbDialect {

  /// @return the name of the [DbDialect] (e.g. "h2", "postgresql", etc.). Should be entirely lower-case to prevent
  ///         case mismatching.
  String getId();

  /// @return the database type. This is very similar to the [ID][#getId()] but for the same database types
  ///         potentially different dialects may exist (e.g. due to different versions of the database product).
  default String getType() {

    return getId();
  }

  /// @return the [DbNamingStrategy].
  DbNamingStrategy getNamingStrategy();

  /// @param qName the [DbQualifiedName] to format.
  /// @return the [DbQualifiedName] formatted as [String].
  default String format(DbQualifiedName qName) {

    return qName.toString();
  }

  /// @return a new [BasicDbStatementFormatter] using this SQL dialect.
  DbStatementFormatter createFormatter();

  /// @return `true` if the underlying database supports sequences, `false` otherwise.
  default boolean isSupportingSequence() {

    return true;
  }

}
