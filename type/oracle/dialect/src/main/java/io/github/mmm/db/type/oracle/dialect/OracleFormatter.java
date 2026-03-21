/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.type.oracle.dialect;

import io.github.mmm.db.dialect.DbDialectStatementFormatter;
import io.github.mmm.db.param.CriteriaParametersFactory;

/**
 * {@link DbDialectStatementFormatter} for Oracle database.
 *
 * @since 1.0.0
 */
public class OracleFormatter extends DbDialectStatementFormatter {

  /**
   * The constructor.
   *
   * @param dialect the {@link OracleDialect}.
   */
  public OracleFormatter(OracleDialect dialect) {

    super(dialect);
  }

  /**
   * The constructor.
   *
   * @param dialect the {@link OracleDialect}.
   * @param parametersFactory the {@link CriteriaParametersFactory}.
   * @param indentation the {@link #getIndentation() indentation}.
   */
  public OracleFormatter(OracleDialect dialect, CriteriaParametersFactory parametersFactory, String indentation) {

    super(dialect, parametersFactory, indentation);
  }

}
