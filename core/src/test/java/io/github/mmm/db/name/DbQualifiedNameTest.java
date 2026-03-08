/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.name;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Test of {@link DbQualifiedName}.
 */
class DbQualifiedNameTest extends Assertions {

  @Test
  void testOnlyName() {

    // arrange
    String name = "Name";

    // act
    DbQualifiedName qName = new DbQualifiedName(name, null, null);

    // assert
    assertThat(qName.getName()).isSameAs(name);
    assertThat(qName.getSchema()).isNull();
    assertThat(qName.getCatalog()).isNull();
    assertThat(qName).hasToString("Name");
  }

  @Test
  void testOnlyNameAndSchema() {

    // arrange
    String schema = "Schema";
    String name = "Name";

    // act
    DbQualifiedName qName = new DbQualifiedName(name, schema, null);

    // assert
    assertThat(qName.getName()).isSameAs(name);
    assertThat(qName.getSchema()).isSameAs(schema);
    assertThat(qName.getCatalog()).isNull();
    assertThat(qName).hasToString("Schema.Name");
  }

  @Test
  void testFullyQualified() {

    // arrange
    String catalog = "Catalog";
    String schema = "Schema";
    String name = "Name";

    // act
    DbQualifiedName qName = new DbQualifiedName(name, schema, catalog);

    // assert
    assertThat(qName.getName()).isSameAs(name);
    assertThat(qName.getSchema()).isSameAs(schema);
    assertThat(qName.getCatalog()).isSameAs(catalog);
    assertThat(qName).hasToString("Catalog.Schema.Name");
  }

  @Test
  void testFullyQualifiedWithQuotes() {

    // arrange
    String catalog = "\"CATALOG\"";
    String schema = "\"SCHEMA\"";
    String name = "\"TABLE\""; // keywords must be quoted in SQL

    // act
    DbQualifiedName qName = new DbQualifiedName(name, schema, catalog);

    // assert
    assertThat(qName.getName()).isSameAs(name);
    assertThat(qName.getSchema()).isSameAs(schema);
    assertThat(qName.getCatalog()).isSameAs(catalog);
    assertThat(qName).hasToString("\"CATALOG\".\"SCHEMA\".\"TABLE\"");
  }

  @Test
  void testOf() {

    // arrange
    String catalog = "\"CATALOG.NAME\"";
    String schema = "\"SCHEMA$NAME\"";
    String name = "\"TABLE NAME\"";
    String qualifiedName = "\"CATALOG.NAME\".\"SCHEMA$NAME\".\"TABLE NAME\"";

    // act
    DbQualifiedName qName = DbQualifiedName.of(qualifiedName);

    // assert
    assertThat(qName.getName()).isEqualTo(name);
    assertThat(qName.getSchema()).isEqualTo(schema);
    assertThat(qName.getCatalog()).isEqualTo(catalog);
    assertThat(qName).hasToString(qualifiedName);
  }

  @ParameterizedTest
  @ValueSource(strings = { "", "\"\"", "\"Invalid\"Quote", "SPACES WITHOUT QUOTES", "JUST.TOO.MANY.SEGMENTS",
  "\"FOO\"._TABLE", "FOO.", "FOO.\"\"", "FOO.BAR.\"Table.Name§\"" })
  void testInvalidQNames(String name) {

    assertThrows(IllegalArgumentException.class, () -> DbQualifiedName.of(name));
  }

  @ParameterizedTest
  @ValueSource(strings = { "", "\"\"", "\"Invalid \"Quote", "SPACES WITHOUT QUOTES", ".", "SCHEMA.NAME",
  "\"TABLE.NAME\"", "\"TABLE NAME\"" })
  void testInvalidSegments(String segment) {

    assertThrows(IllegalArgumentException.class, () -> new DbQualifiedName(segment));
  }

  @ParameterizedTest
  @ValueSource(strings = { "\"valid Quote\"", "\"SPACES WITH QUOTES\"", "EXACTLY.THREE.SEGMENTS", "\"FOO\".A_TABLE",
  "FOO.BAR", "FOO.\"N\"", "FOO.BAR.\"Table.Name\"" })
  void testValidQNames(String name) {

    assertThat(DbQualifiedName.of(name)).hasToString(name);
  }

  @ParameterizedTest
  @ValueSource(strings = { "\"validQuote\"", "\"SPACES_NOT_ALLOWED\"", "TRAILING_", "V$SQLAREA" })
  void testValidSegments(String segment) {

    assertThat(new DbQualifiedName(segment).getName()).isEqualTo(segment);
  }
}
