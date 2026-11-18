/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.type.hana.dialect;

import io.github.mmm.db.dialect.DbDialectStatementFormatter;
import io.github.mmm.db.param.CriteriaParametersFactory;

/// [DbDialectStatementFormatter] for SAP Hana Database.
///
/// @since 1.0.0
public class HanaFormatter extends DbDialectStatementFormatter {

  /// The constructor.
  ///
  /// @param dialect the [HanaDialect].
  public HanaFormatter(HanaDialect dialect) {

    super(dialect);
  }

  /// The constructor.
  ///
  /// @param dialect the [HanaDialect].
  /// @param parametersFactory the [CriteriaParametersFactory].
  /// @param indentation the [indentation][#getIndentation()].
  public HanaFormatter(HanaDialect dialect, CriteriaParametersFactory parametersFactory, String indentation) {

    super(dialect, parametersFactory, indentation);
  }

}
