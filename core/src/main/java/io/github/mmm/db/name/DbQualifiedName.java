/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.name;

import java.util.Objects;

import io.github.mmm.base.filter.CharFilter;
import io.github.mmm.base.filter.ListCharFilter;

/// A potentially qualified identifier consisting of [catalog][#getCatalog()], [schema][#getSchema()], and
/// [name][#getName()]. The [name][#getName()] is always required, while the other attributes can be
/// `null`. In such case the default values of the database connection will apply.
///
/// @since 1.0.0
public final class DbQualifiedName {

  private static final CharFilter QUOTED_NAME = CharFilter.LATIN_LETTER_OR_DIGIT.compose(new ListCharFilter("_$ ."));

  private final String catalog;

  private final String schema;

  private final String name;

  private final String qName;

  private DbQualifiedName(String name) {

    this(name, null, null);
  }

  /// The constructor.
  ///
  /// @param name the [name][#getName()].
  /// @param schema the [schema][#getSchema()].
  public DbQualifiedName(String name, String schema) {

    this(name, schema, null);
  }

  /// The constructor.
  ///
  /// @param name the [name][#getName()].
  /// @param schema the [schema][#getSchema()].
  /// @param catalog the [catalog][#getCatalog()].
  public DbQualifiedName(String name, String schema, String catalog) {

    this(name, schema, catalog, null);
  }

  private DbQualifiedName(String name, String schema, String catalog, String qualifiedName) {

    super();
    Objects.requireNonNull(name);
    this.catalog = catalog;
    this.schema = schema;
    this.name = name;
    if (qualifiedName == null) {
      checkNameSegment(catalog);
      checkNameSegment(schema);
      checkNameSegment(name);
      this.qName = buildQualifiedName();
    } else {
      this.qName = qualifiedName;
    }
  }

  private String checkNameSegment(String segment) {

    boolean valid = true;
    if ((segment == null) || segment.isBlank()) {
      valid = (segment == null);
    } else {
      int len = segment.length();
      char quote = 0;
      for (int i = 0; i < len; i++) {
        char c = segment.charAt(i); // no surrogate support intended
        if (c == '"') {
          if (i == 0) {
            quote = c;
          } else {
            if (((i + 1) != len) || (c != quote) || i == 1) {
              valid = false;
              break;
            }
          }
        } else {
          // developers should follow best practices and avoid anti-patterns so we do not allow space or dot here
          CharFilter filter = CharFilter.SEGMENT;
          if ((i == 0) || ((i == 1) && (quote != 0))) {
            filter = CharFilter.LATIN_LETTER;
          }
          if (!filter.accept(c)) {
            valid = false;
            break;
          }
        }
      }
    }
    if (valid) {
      return segment;
    }
    throw new IllegalArgumentException(segment);
  }

  private String buildQualifiedName() {

    int len = this.name.length();
    if (this.catalog != null) {
      len += this.catalog.length() + 1;
    }
    if (this.schema != null) {
      len += this.schema.length() + 1;
    }
    StringBuilder sb = new StringBuilder(len);
    if (this.catalog != null) {
      sb.append(this.catalog);
      sb.append('.');
    }
    if (this.schema != null) {
      sb.append(this.schema);
      sb.append('.');
    }
    sb.append(this.name);
    return sb.toString();
  }

  /// @return the name of the catalog (a database in the DB server aka cluster) or `null` for no catalog.
  public String getCatalog() {

    return this.catalog;
  }

  /// @return the name of the schema (namespace containing tables) or `null` for no schema.
  public String getSchema() {

    return this.schema;
  }

  /// @return the local name of the database object. May not be `null`.
  public String getName() {

    return this.name;
  }

  /// @param newName the new [name][#getName()].
  /// @return the new [DbQualifiedName] with the given [name][#getName()].
  public DbQualifiedName withName(String newName) {

    if (Objects.equals(newName, this.name)) {
      return this;
    }
    return new DbQualifiedName(newName, this.schema, this.catalog);
  }

  /// @param newSchema the new [schema][#getSchema()].
  /// @return the new [DbQualifiedName] with the given [schema][#getSchema()].
  public DbQualifiedName withSchema(String newSchema) {

    if (Objects.equals(newSchema, this.schema)) {
      return this;
    }
    return new DbQualifiedName(this.name, newSchema, this.catalog);
  }

  /// @param newCatalog the new [catalog][#getCatalog()].
  /// @return the new [DbQualifiedName] with the given [catalog][#getCatalog()].
  public DbQualifiedName withCatalog(String newCatalog) {

    if (Objects.equals(newCatalog, this.catalog)) {
      return this;
    }
    return new DbQualifiedName(this.name, this.schema, newCatalog);
  }

  /// @return `true` if this [DbQualifiedName] has a qualifier ([catalog][#getCatalog()] or
  ///         [schema][#getSchema()]), `false` otherwise (actually unqualified).
  public boolean hasQualifier() {

    return (this.catalog != null) || (this.schema != null);
  }

  @Override
  public int hashCode() {

    return Objects.hash(this.catalog, this.schema, this.name);
  }

  @Override
  public boolean equals(Object obj) {

    if (obj == this) {
      return true;
    } else if ((obj == null) || !(obj instanceof DbQualifiedName)) {
      return false;
    }
    DbQualifiedName other = (DbQualifiedName) obj;
    return Objects.equals(this.catalog, other.catalog) && Objects.equals(this.schema, other.schema)
        && Objects.equals(this.name, other.name);
  }

  @Override
  public String toString() {

    return this.qName;
  }

  /// @param string the [unqualified name][#getName()]. May be `null`.
  /// @return the new [DbQualifiedName]. Will be `null` if `string` was `null`.
  public static DbQualifiedName ofName(String string) {

    if (string == null) {
      return null;
    }
    return new DbQualifiedName(string);
  }

  /// @param string the [string representation][#toString()] of the [DbQualifiedName].
  /// @return the parsed [DbQualifiedName]. Will be `null` if `string` was `null`.
  public static DbQualifiedName ofParsed(String string) {

    if (string == null) {
      return null;
    }
    string = string.replace('`', '"'); // normalise quotes
    String[] segments = new String[3];
    int segmentIndex = 0;
    int len = string.length();
    boolean valid = true;
    if (len == 0) {
      valid = false;
    }
    char quote = 0;
    int start = 0;
    for (int i = 0; i < len; i++) {
      char c = string.charAt(i);
      if (c == '"') {
        if (i == start) {
          quote = c;
        } else if (quote == c) {
          quote = 0;
          if ((i + 1) < len) {
            valid = string.charAt(i + 1) == '.'; // lookahead, if not at end, next character after quote end must be dot
          } else if (i == (start + 1)) {
            valid = false; // empty quoted segment: ""
          }
        } else {
          valid = false;
        }
      } else if ((c == '.') && (quote == 0)) {
        if (segmentIndex < 2) {
          segments[segmentIndex++] = string.substring(start, i);
          start = i + 1;
          valid = (start < len);
        } else {
          valid = false; // too many segments
        }
      } else {
        CharFilter filter = CharFilter.IDENTIFIER;
        if (quote != 0) {
          filter = QUOTED_NAME;
        }
        if ((i == start) || ((i == (start + 1)) && (quote != 0))) {
          filter = CharFilter.LATIN_LETTER;
        }
        valid = filter.accept(c);
      }
      if (!valid) {
        break;
      }
    }
    segments[segmentIndex++] = string.substring(start);
    if (!valid) {
      segmentIndex = 0;
    }
    return switch (segmentIndex) {
      case 1 -> new DbQualifiedName(segments[0], null, null, string);
      case 2 -> new DbQualifiedName(segments[1], segments[0], null, string);
      case 3 -> new DbQualifiedName(segments[2], segments[1], segments[0], string);
      default -> throw new IllegalArgumentException(string);
    };
  }

}
