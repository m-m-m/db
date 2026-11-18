/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.spi.access;

import io.github.mmm.db.source.DbSourceConfig;
import io.github.mmm.db.spi.access.impl.DbAccessProviderAdapter;
import io.github.mmm.db.tx.DbTransactionExecutor;

/// Interface providing generic database access with support for all kind of
/// [statements][io.github.mmm.db.statement.DbStatement].
public interface DbAccess extends DbCreateAccess, DbDeleteAccess, DbInsertAccess, DbMergeAccess, DbSelectAccess,
    DbUpdateAccess, DbUpsertAccess {

  /// @param config the [DbSourceConfig].
  /// @return the corresponding [DbTransactionExecutor].
  static DbAccess get(DbSourceConfig config) {

    return DbAccessProviderAdapter.INSTANCE.create(config);
  }

}
