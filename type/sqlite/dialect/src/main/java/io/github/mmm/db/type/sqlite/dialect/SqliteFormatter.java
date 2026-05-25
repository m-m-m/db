/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.type.sqlite.dialect;

import io.github.mmm.db.dialect.DbContext;
import io.github.mmm.db.dialect.DbDialectStatementFormatter;
import io.github.mmm.db.name.DbKeyword;
import io.github.mmm.db.param.CriteriaParametersFactory;
import io.github.mmm.db.statement.AbstractEntityClause;
import io.github.mmm.db.statement.SetClause;
import io.github.mmm.db.statement.create.CreateSequenceClause;
import io.github.mmm.db.statement.select.SelectSequenceNextValueClause;
import io.github.mmm.db.statement.update.UpdateClause;

/**
 * {@link DbDialectStatementFormatter} for SQLite Database.
 *
 * @since 1.0.0
 */
public class SqliteFormatter extends DbDialectStatementFormatter {

  private static final String TABLE_SEQUENCE = "SEQUENCE_TABLE";

  private static final String COLUMN_NAME = "NAME";

  private static final String COLUMN_NEXT_VALUE = "NEXT_VALUE";

  private static final String COLUMN_INCREMENT_VALUE = "INCREMENT_VALUE";

  /**
   * The constructor.
   *
   * @param dialect the {@link SqliteDialect}.
   */
  public SqliteFormatter(SqliteDialect dialect) {

    super(dialect);
  }

  /**
   * The constructor.
   *
   * @param dialect the {@link SqliteDialect}.
   * @param parametersFactory the {@link CriteriaParametersFactory}.
   * @param indentation the {@link #getIndentation() indentation}.
   */
  public SqliteFormatter(SqliteDialect dialect, CriteriaParametersFactory parametersFactory, String indentation) {

    super(dialect, parametersFactory, indentation);
  }

  @Override
  public boolean isUseAsBeforeAlias() {

    return true;
  }

  @Override
  protected void formatEntity(AbstractEntityClause<?, ?, ?> entity) {

    if (entity instanceof UpdateClause<?>) {
      formatEntity(entity, false);
    } else {
      super.formatEntity(entity);
    }
  }

  @Override
  public void formatSetClause(SetClause<?, ?> set, DbContext context) {

    // TODO Auto-generated method stub
    super.formatSetClause(set, context);
  }

  @Override
  protected void formatCreateSequenceClause(CreateSequenceClause seq, DbContext context) {

    writeIndent();
    write(DbKeyword.INSERT_INTO);
    write(" ");
    write(TABLE_SEQUENCE);
    write(" (");
    write(COLUMN_NAME);
    write(", ");
    write(COLUMN_NEXT_VALUE);
    write(", ");
    write(COLUMN_INCREMENT_VALUE);
    write(") ");
    write(DbKeyword.VALUES);
    write(" ('");
    write(seq.getSequenceName());
    write("', ");
    write(seq.getStartWith().toString());
    write(", ");
    write(seq.getIncrementBy().toString());
    write(")");
    write("\n  ");
    write(DbKeyword.ON_CONFLICT_DO_NOTHING);

    newStatement();

    writeIndent();
    write(DbKeyword.CREATE_TABLE);
    write(" ");
    write(DbKeyword.IF_NOT_EXISTS);
    write(" ");
    write(TABLE_SEQUENCE);
    write(" (\n  ");
    write(COLUMN_NAME);
    write(" TEXT ");
    write(DbKeyword.PRIMARY_KEY);
    write(",\n  ");
    write(COLUMN_NEXT_VALUE);
    write(" INTEGER ");
    write(DbKeyword.NOT_NULL);
    write(",\n  ");
    write(COLUMN_INCREMENT_VALUE);
    write(" INTEGER ");
    write(DbKeyword.NOT_NULL);
    write("\n)");
  }

  @Override
  protected void formatSelectSeqNextVal(SelectSequenceNextValueClause seq) {

    writeIndent();
    write(DbKeyword.UPDATE);
    write(" ");
    write(TABLE_SEQUENCE);
    write(" ");
    write(DbKeyword.SET);
    write(" ");
    write(COLUMN_NEXT_VALUE);
    write(" = ");
    write(COLUMN_NEXT_VALUE);
    write(" + ");
    write(COLUMN_INCREMENT_VALUE);
    write(" ");
    write(DbKeyword.WHERE);
    write(" ");
    write(COLUMN_NAME);
    write(" = '");
    write(seq.getSequenceName().getName());
    write("'");

    newStatement();

    writeIndent();
    write(DbKeyword.SELECT);
    write(" ");
    write(COLUMN_NEXT_VALUE);
    write(" ");
    write(DbKeyword.FROM);
    write(" ");
    write(TABLE_SEQUENCE);
    write(" ");
    write(DbKeyword.WHERE);
    write(" ");
    write(COLUMN_NAME);
    write(" = '");
    write(seq.getSequenceName());
    write("'");
  }

}
