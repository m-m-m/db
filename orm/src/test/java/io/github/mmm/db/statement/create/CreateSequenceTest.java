/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.statement.create;

import org.junit.jupiter.api.Test;

import io.github.mmm.db.name.DbQualifiedName;
import io.github.mmm.db.statement.DbStatementTest;

/**
 * Test of {@link CreateSequenceClause} and {@link CreateSequenceStatement}.
 */
class CreateSequenceTest extends DbStatementTest {

  /** Test of {@link CreateSequenceClause} with all attributes. */
  @Test
  void testAllAttributes() {

    // arrange
    DbQualifiedName sequenceName = new DbQualifiedName("MY_SEQUENCE", "MYSCHEMA", null);
    // act
    CreateSequenceStatement statement = new CreateSequenceClause(sequenceName).incrementBy(10).startWith(1000000000000L)
        .minValue(1000000000000L).maxValue(9123456789123456789L).nocycle().get();
    // assert
    check(statement,
        "CREATE SEQUENCE MYSCHEMA.MY_SEQUENCE INCREMENT BY 10 START WITH 1000000000000 MINVALUE 1000000000000 MAXVALUE 9123456789123456789 NOCYCLE",
        true);
  }

}
