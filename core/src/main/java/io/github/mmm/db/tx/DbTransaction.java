/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.tx;

import io.github.mmm.db.ddl.DbMetaData;
import io.github.mmm.db.source.DbSource;
import io.github.mmm.db.tx.spi.AbstractDbTransaction;

/// Interface for a transaction.
///
/// @since 1.0.0
public interface DbTransaction {

  /// @return `true` if this transaction is still open, `false` otherwise (if closed as committed or
  ///         rolled-backe).
  boolean isOpen();

  /// @return the [DbTransactionConfig] that was used to create this transaction.
  /// @see DbTransactionExecutor#doInTx(Runnable, DbTransactionConfig)
  /// @see DbTransactionExecutor#doInTx(java.util.concurrent.Callable, DbTransactionConfig)
  DbTransactionConfig getConfig();

  /// @param <T> type of the associated object.
  /// @param type the [Class] reflecting the associated object. E.g. `DbDialect`.
  /// @return the object associated with the given [Class] or `null` if not set.
  <T> T getValue(Class<T> type);

  /// @return the [DbMetaData].
  DbMetaData getMetaData();

  /// @return the current transaction or `null` if no transaction is active for the current [Thread].
  static DbTransaction get() {

    return get(null);
  }

  /// @param source the [DbSource] for which the active [DbTransaction] is requested. May be `null` to
  ///        get the current top-level [DbTransaction].
  /// @return the active transaction matching the given [DbSource] or `null` if no such transaction is active
  ///         for the current [Thread].
  static DbTransaction get(DbSource source) {

    return AbstractDbTransaction.get(source);
  }

  /// @param source the [DbSource] for which the active [DbTransaction] is requested. May be `null` to
  ///        get the current top-level [DbTransaction].
  /// @return the active transaction for the given [DbSource].
  /// @see DbTransaction#get(DbSource)
  public static DbTransaction getRequired(DbSource source) {

    return AbstractDbTransaction.getRequired(source);
  }

}
