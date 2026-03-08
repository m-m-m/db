/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.spi.access.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import io.github.mmm.db.dialect.DbDialect;
import io.github.mmm.db.dialect.DbDialectProvider;
import io.github.mmm.db.name.DbQualifiedName;
import io.github.mmm.db.source.DbSourceConfig;
import io.github.mmm.db.statement.DbPlainStatement;
import io.github.mmm.db.statement.DbStatement;
import io.github.mmm.db.statement.select.SelectStatement;
import io.github.mmm.db.tx.spi.AbstractDbTransaction;
import io.github.mmm.entity.id.Id;
import io.github.mmm.entity.id.sequence.IdSequence;

/**
 * Implementation of {@link IdSequence} backed by a database sequence using JDBC.
 */
public class JdbcSequence implements IdSequence {

  private final DbQualifiedName sequenceName;

  private final SelectStatement<Long> statement;

  private final DbSourceConfig sourceConfig;

  /**
   * The constructor.
   *
   * @param sequenceName the name of the sequence.
   * @param sourceConfig the {@link DbSourceConfig}.
   */
  public JdbcSequence(String sequenceName, DbSourceConfig sourceConfig) {

    this(new DbQualifiedName(sequenceName), sourceConfig);
  }

  /**
   * The constructor.
   *
   * @param sequenceName the name of the sequence.
   * @param sourceConfig the {@link DbSourceConfig}.
   */
  public JdbcSequence(DbQualifiedName sequenceName, DbSourceConfig sourceConfig) {

    super();
    this.sequenceName = sequenceName;
    this.statement = DbStatement.selectSeqNextVal(this.sequenceName);
    this.sourceConfig = sourceConfig;
  }

  @Override
  public long next(Id<?> template) {

    DbDialect dialect = DbDialectProvider.get().get(this.sourceConfig.getDialect());
    Connection connection = AbstractDbTransaction.getRequired(this.sourceConfig.getSource()).getConnection();
    DbPlainStatement plainStatement = dialect.createFormatter().formatStatement(this.statement);
    long id = -1;
    while (plainStatement != null) {
      String sql = plainStatement.getStatement();
      try {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        if (id == -1) {
          ResultSet rs = preparedStatement.executeQuery();
          if (rs.next()) {
            id = rs.getLong(1);
            assert (id != -1);
          } else {
            throw new SQLException("ResultSet empty");
          }
        } else {
          preparedStatement.execute();
        }
      } catch (SQLException e) {
        throw new IllegalStateException("Failed to execute " + sql, e);
      }
      plainStatement = plainStatement.getNext();
    }
    assert (id != -1);
    return id;
  }

}