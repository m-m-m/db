/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.type.derby.dialect;

import io.github.mmm.db.dialect.DbDialectStatementFormatter;
import io.github.mmm.db.param.CriteriaParametersFactory;

/// [DbDialectStatementFormatter] for Apache Derby Database.
///
/// @since 1.0.0
public class DerbyFormatter extends DbDialectStatementFormatter {

  /// The constructor.
  ///
  /// @param dialect the [DerbyDialect].
  public DerbyFormatter(DerbyDialect dialect) {

    super(dialect);
  }

  /// The constructor.
  ///
  /// @param dialect the [DerbyDialect].
  /// @param parametersFactory the [CriteriaParametersFactory].
  /// @param indentation the [indentation][#getIndentation()].
  public DerbyFormatter(DerbyDialect dialect, CriteriaParametersFactory parametersFactory, String indentation) {

    super(dialect, parametersFactory, indentation);
  }

}
