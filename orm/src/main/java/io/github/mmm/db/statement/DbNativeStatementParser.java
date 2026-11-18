/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.statement;

import static io.github.mmm.base.filter.CharFilter.NEWLINE_OR_SPACE;

import java.util.ArrayList;
import java.util.List;

import io.github.mmm.base.filter.CharFilter;
import io.github.mmm.db.name.DbKeyword;
import io.github.mmm.scanner.CharScannerParser;
import io.github.mmm.scanner.CharStreamScanner;

/**
 * {@link CharScannerParser} for {@link DbNativeStatement}s.
 */
public class DbNativeStatementParser extends AbstractDbStatementParser<DbNativeStatement> {

  private static final DbNativeStatementParser INSTANCE = new DbNativeStatementParser();

  @Override
  protected DbNativeStatement doParse(CharStreamScanner scanner) {

    return doParse(scanner, new StatementBuilder());
  }

  /**
   * @param scanner the {@link CharStreamScanner} to read from.
   * @return the {@link List} of {@link DbNativeStatement}s.
   */
  public List<DbNativeStatement> parseAll(CharStreamScanner scanner) {

    List<DbNativeStatement> list = new ArrayList<>();
    StatementBuilder builder = new StatementBuilder();
    while (scanner.hasNext()) {
      DbNativeStatement statement = doParse(scanner, builder);
      if (statement == null) {
        break;
      }
      list.add(statement);
    }
    return list;
  }

  private DbNativeStatement doParse(CharStreamScanner scanner, StatementBuilder builder) {

    while (scanner.hasNext()) {
      scanner.skipWhile(NEWLINE_OR_SPACE);
      String token = readToken(scanner);
      if (token.startsWith("--") || token.startsWith("//")) {
        scanner.skipUntil('\n');
      } else if (token.startsWith("/*")) {
        if (!token.endsWith("*/")) {
          scanner.skipOver("*/");
        }
      } else if (token.isEmpty()) {
        break;
      } else {
        boolean completed = builder.add(token);
        if (completed) {
          break;
        }
      }
    }
    return builder.build();
  }

  private String readToken(CharStreamScanner scanner) {

    return scanner.readUntil(CharFilter.WHITESPACE, true);
  }

  private void skipComments(CharStreamScanner scanner) {

    boolean found;
    do {
      found = false;
      if (scanner.expect("--")) {
        scanner.skipUntil('\n');
        found = true;
      } else if (scanner.expect("/*")) {
        scanner.skipOver("*/");
        found = true;
      }
      if (found) {
        scanner.skipWhile(NEWLINE_OR_SPACE);
      }
    } while (found);
  }

  /**
   * @return the singleton instance of this {@link DbNativeStatementParser}.
   */
  public static DbNativeStatementParser get() {

    return INSTANCE;
  }

  private static class StatementBuilder {

    private final StringBuilder buffer;

    private int tokenCount;

    private DbStatementType type;

    private boolean requireSlash;

    private StatementBuilder() {

      super();
      this.buffer = new StringBuilder(128);
      this.requireSlash = false;
    }

    private void clear() {

      this.buffer.setLength(0);
      this.tokenCount = 0;
      this.type = null;
      this.requireSlash = false;
    }

    private boolean add(String token) {

      if ((token == null) || token.isEmpty()) {
        assert false : "Empty token";
        return false;
      }
      boolean slash = token.equals("/");
      boolean done = slash;
      if (!slash) {
        if (DbKeyword.BEGIN.equalsIgnoreCase(token) || DbKeyword.DECLARE.equalsIgnoreCase(token)) {
          this.requireSlash = true; // Oracle PL/SQL syntax
        }
        if (!this.requireSlash && token.endsWith(";")) {
          if (token.length() == 1) {
            return true;
          }
          token = token.substring(0, token.length() - 1);
          done = true;
        }
        if (this.tokenCount > 0) {
          this.buffer.append(' ');
        }
        this.buffer.append(token);
        this.tokenCount++;
        if ((this.type == null) && (this.tokenCount == 1)) {
          this.type = DbStatementType.of(token);
        }
      }
      return done;
    }

    private DbNativeStatement build() {

      DbNativeStatement statement = null;
      if (this.tokenCount > 0) {
        statement = new DbNativeStatement(this.type, this.buffer.toString(), null, null);
      }
      clear();
      return statement;
    }
  }

}
