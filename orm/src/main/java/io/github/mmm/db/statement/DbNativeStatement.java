package io.github.mmm.db.statement;

import io.github.mmm.property.criteria.AttributeReadParameters;
import io.github.mmm.property.criteria.CriteriaParameters;

/// Simple container for a plain native database statement (SQL).
public class DbNativeStatement implements Statement, AttributeReadParameters {

  private final DbStatementType type;

  private final String statement;

  private final CriteriaParameters<?> parameters;

  private final DbNativeStatement next;

  /// The constructor.
  ///
  /// @param type the [type][#getType()].
  /// @param statement the [raw statement][#getStatement()].
  /// @param parameters the [parameters][#getParameters()].
  /// @param next the [next][#getNext()].
  public DbNativeStatement(DbStatementType type, String statement, CriteriaParameters<?> parameters,
      DbNativeStatement next) {

    super();
    this.type = type;
    this.statement = statement;
    this.parameters = parameters;
    this.next = next;
  }

  /// @return the SQL of this statement.
  public String getStatement() {

    return this.statement;
  }

  @Override
  public CriteriaParameters<?> getParameters() {

    return this.parameters;
  }

  @Override
  public DbStatementType getType() {

    return this.type;
  }

  /// @return the next [DbNativeStatement] or `null` if this is the last one.
  public DbNativeStatement getNext() {

    return this.next;
  }

  @Override
  public String toString() {

    if (this.next == null) {
      return this.statement;
    }
    StringBuilder sb = new StringBuilder(this.statement.length() + this.next.statement.length() + 2);
    toString(sb);
    return sb.toString();
  }

  private void toString(StringBuilder sb) {

    sb.append(this.statement);
    if (this.next != null) {
      sb.append(";\n");
      this.next.toString(sb);
    }
  }

}
