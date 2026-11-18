/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.statement;

import static io.github.mmm.base.filter.CharFilter.NEWLINE_OR_SPACE;

import java.util.Locale;

import io.github.mmm.base.filter.CharFilter;
import io.github.mmm.db.name.DbKeyword;
import io.github.mmm.scanner.CharScannerParser;
import io.github.mmm.scanner.CharStreamScanner;

/**
 * Abstract base class for {@link CharScannerParser} of a database statement.
 *
 * @param <S> type of {@link Statement}.
 * @since 1.0.0
 */
public abstract class AbstractDbStatementParser<S extends Statement> implements CharScannerParser<S>, DbKeyword {

  /**
   * The constructor.
   */
  protected AbstractDbStatementParser() {

    super();
  }

  @Override
  public final S parse(CharStreamScanner scanner) {

    try {
      return doParse(scanner);
    } catch (Exception e) {
      throw new IllegalArgumentException("Failed to parse DB statement after \n" + scanner.getBufferParsed()
          + "\n and before \n" + scanner.getBufferToParse() + "\nwith error: " + e.getMessage(), e);
    }
  }

  /**
   * Implementation of {@link #parse(CharStreamScanner)} without caring about exception handling.
   *
   * @param scanner the {@link CharStreamScanner}.
   * @return the parsed {@link Statement}.
   */
  protected abstract S doParse(CharStreamScanner scanner);

  /**
   * Consumes and returns the next segment.
   *
   * @param scanner the {@link CharStreamScanner}.
   * @return the next consumed segment.
   */
  protected String parseSegment(CharStreamScanner scanner) {

    return scanner.readWhile(CharFilter.SEGMENT, 1, 128);
  }

  /**
   * Consumes and returns the next keyword, requires and skips following whitespaces.
   *
   * @param scanner the {@link CharStreamScanner}.
   * @return the next consumed keyword in UPPER CASE.
   */
  protected String readKeyword(CharStreamScanner scanner) {

    return readKeyword(scanner, false);
  }

  /**
   * Consumes and returns the next keyword, requires and skips following whitespaces.
   *
   * @param scanner the {@link CharStreamScanner}.
   * @param keepCase {@code true} to keep the keyword in the original case, {@code false} otherwise (UPPER CASE).
   * @return the next consumed keyword. Converted to UPPER CASE if {@code keepCase} is {@code false}.
   */
  protected String readKeyword(CharStreamScanner scanner, boolean keepCase) {

    String keyword = scanner.readWhile(CharFilter.LATIN_LETTER, 1, 128).toUpperCase(Locale.ROOT);
    scanner.requireOneOrMore(NEWLINE_OR_SPACE);
    return keyword;
  }

}
