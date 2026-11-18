package io.github.mmm.db.statement;

import io.github.mmm.db.dialect.AbstractDbDialect;
import io.github.mmm.db.param.CriteriaParametersFactory;
import io.github.mmm.property.criteria.CriteriaFormatterFactory;

/// [BasicDbStatementFormatter] [using AS before alias][#isUseAsBeforeAlias()].
public class BasicDbStatementFormatterUseAsBeforeAlias extends BasicDbStatementFormatter {

  /// The constructor.
  public BasicDbStatementFormatterUseAsBeforeAlias() {

    super();
  }

  /// The constructor.
  ///
  /// @param dialect the [AbstractDbDialect].
  /// @param parametersFactory the [CriteriaFormatterFactory].
  public BasicDbStatementFormatterUseAsBeforeAlias(AbstractDbDialect<?> dialect,
      CriteriaParametersFactory parametersFactory) {

    super(dialect, parametersFactory, INDENTATION);
  }

  @Override
  public boolean isUseAsBeforeAlias() {

    return true;
  }

}
