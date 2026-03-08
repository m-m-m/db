package io.github.mmm.db.result.impl;

import io.github.mmm.db.result.DbResult;
import io.github.mmm.db.result.DbResultValue;

/**
 * Test of {@link DbResultObject}.
 */
class DbResultObjectTest extends AbstractDbResultTest {

  @Override
  protected DbResult create(DbResultValue<?>... cells) {

    return new DbResultObject(cells);
  }

}
