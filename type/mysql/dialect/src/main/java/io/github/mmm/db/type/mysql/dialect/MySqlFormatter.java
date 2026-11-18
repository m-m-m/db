/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.type.mysql.dialect;

import io.github.mmm.db.ddl.constraint.DbConstraint;
import io.github.mmm.db.ddl.table.operation.TableOperationType;
import io.github.mmm.db.dialect.DbDialectStatementFormatter;
import io.github.mmm.db.param.CriteriaParametersFactory;

/// [DbDialectStatementFormatter] for MySQL Database.
///
/// @since 1.0.0
public class MySqlFormatter extends DbDialectStatementFormatter {

  /// The constructor.
  ///
  /// @param dialect the [MySqlDialect].
  public MySqlFormatter(MySqlDialect dialect) {

    super(dialect);
  }

  /// The constructor.
  ///
  /// @param dialect the [MySqlDialect].
  /// @param parametersFactory the [CriteriaParametersFactory].
  /// @param indentation the [indentation][#getIndentation()].
  public MySqlFormatter(MySqlDialect dialect, CriteriaParametersFactory parametersFactory, String indentation) {

    super(dialect, parametersFactory, indentation);
  }

  @Override
  protected void formatConstraintKeywordWithName(TableOperationType type, DbConstraint constraint) {

    if (type == TableOperationType.DROP) {
      write("INDEX ");
    } else {
      super.formatConstraintKeywordWithName(type, constraint);
    }
  }

  @Override
  protected void formatAlterTableColumn(TableOperationType type) {

    if (type == TableOperationType.MODIFY) {
      write("COLUMN ");
    } else {
      super.formatAlterTableColumn(type);
    }
  }

}
