/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.type.postgresql.dialect;

import io.github.mmm.db.dialect.DbDialectStatementFormatter;
import io.github.mmm.db.param.CriteriaParametersFactory;

/// [DbDialectStatementFormatter] for PostgreSQL Database.
///
/// @since 1.0.0
public class PostgreSqlFormatter extends DbDialectStatementFormatter {

  /// The constructor.
  ///
  /// @param dialect the [PostgreSqlDialect].
  public PostgreSqlFormatter(PostgreSqlDialect dialect) {

    super(dialect);
  }

  /// The constructor.
  ///
  /// @param dialect the [PostgreSqlDialect].
  /// @param parametersFactory the [CriteriaParametersFactory].
  /// @param indentation the [indentation][#getIndentation()].
  public PostgreSqlFormatter(PostgreSqlDialect dialect, CriteriaParametersFactory parametersFactory,
      String indentation) {

    super(dialect, parametersFactory, indentation);
  }

}
