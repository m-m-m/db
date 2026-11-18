/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.tx.spi;

import io.github.mmm.db.source.DbSource;
import io.github.mmm.db.source.DbSourceConfig;
import io.github.mmm.db.tx.DbTransactionExecutor;

/// Provider of [DbTransactionExecutor].
///
/// @see DbTransactionExecutor#get(DbSource)
/// @since 1.0.0
public interface DbTransactionExecutorProvider {

  /// @param config the [DbSourceConfig].
  /// @return the corresponding [DbTransactionExecutor].
  DbTransactionExecutor create(DbSourceConfig config);

}
