package io.github.mmm.db.result.impl;

import io.github.mmm.db.result.DbResult;
import io.github.mmm.db.result.DbResultValue;

/**
 * Test of {@link DbResultPojo}.
 */
class DbResultPojoTest extends AbstractDbResultTest {

  @Override
  protected DbResult create(DbResultValue<?>... cells) {

    DbResultPojo result = new DbResultPojo();
    for (DbResultValue<?> cell : cells) {
      result.add(cell);
    }
    return result;
  }

}
