package io.github.mmm.db.schema.test;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.mmm.base.version.VersionNumber;
import io.github.mmm.db.schema.DbChangeLog;
import io.github.mmm.db.schema.DbChangeLogSource;
import io.github.mmm.db.schema.DbChangeLogVersion;
import io.github.mmm.db.schema.DbSchemaManager;
import io.github.mmm.db.source.DbSource;

/**
 * Test of {@link DbSchemaManager} with real world example.
 */
public class DbSchemaTest extends Assertions {

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
    assertThat(defaultSourceNode.getVersions().map(VersionNumber::toString)).containsExactly("1.0.0", "1.0.1", "1.1.0",
        "2.0.0", "2.0.1");
    DbChangeLogVersion version100 = defaultSourceNode.get("1.0.0");
    assertThat(version100.getVersion()).isEqualTo(VersionNumber.of("1.0.0"));
    assertThat(version100.getScripts().map(Object::toString)).containsExactly(
        "db/changelog/default/1.0.0/20201217_165602-Create_Table_User.sql",
        "db/changelog/default/1.0.0/20201217_170149-Create_Table_Product.sql",
        "db/changelog/default/1.0.0/20201218_081256-Create_Table_Review.sql");
  }
}
