/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.schema;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.mmm.db.source.DbSource;

/**
 * Test of {@link DbSchemaManager}.
 */
class DbSchemaManagerTest extends Assertions {

  @Test
  void testChangelog() {

    // arrange
    DbSchemaManager manager = DbSchemaManager.get();

    // act
    DbChangeLog changeLog = manager.getChangeLog();

    // assert
    assertThat(changeLog).isNotNull();
    DbChangeLogSource defaultSourceNode = changeLog.get();
    assertThat(defaultSourceNode).isNotNull();
    assertThat(defaultSourceNode.getSource()).isSameAs(DbSource.get());
    assertThat(defaultSourceNode.getVersions()).isEmpty();
  }
}
