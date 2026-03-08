/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.tx;

import io.github.mmm.db.ddl.DbMetaData;
import io.github.mmm.db.source.DbSource;
import io.github.mmm.db.tx.spi.AbstractDbTransaction;

/**
 * Interface for a transaction.
 *
 * @since 1.0.0
 */
public interface DbTransaction {

  /**
   * @return {@code true} if this transaction is still open, {@code false} otherwise (if closed as committed or
   *         rolled-backe).
   */
  boolean isOpen();

  /**
   * @return the {@link DbTransactionConfig} that was used to create this transaction.
   * @see DbTransactionExecutor#doInTx(Runnable, DbTransactionConfig)
   * @see DbTransactionExecutor#doInTx(java.util.concurrent.Callable, DbTransactionConfig)
   */
  DbTransactionConfig getConfig();

  /**
   * @param <T> type of the associated object.
   * @param type the {@link Class} reflecting the associated object. E.g. {@code DbDialect}.
   * @return the object associated with the given {@link Class} or {@code null} if not set.
   */
  <T> T getValue(Class<T> type);

  /**
   * @return the {@link DbMetaData}.
   */
  DbMetaData getMetaData();

  /**
   * @return the current transaction or {@code null} if no transaction is active for the current {@link Thread}.
   */
  static DbTransaction get() {

    return get(null);
  }

  /**
   * @param source the {@link DbSource} for which the active {@link DbTransaction} is requested. May be {@code null} to
   *        get the current top-level {@link DbTransaction}.
   * @return the active transaction matching the given {@link DbSource} or {@code null} if no such transaction is active
   *         for the current {@link Thread}.
   */
  static DbTransaction get(DbSource source) {

    return AbstractDbTransaction.get(source);
  }

  /**
   * @param source the {@link DbSource} for which the active {@link DbTransaction} is requested. May be {@code null} to
   *        get the current top-level {@link DbTransaction}.
   * @return the active transaction for the given {@link DbSource}.
   * @see DbTransaction#get(DbSource)
   */
  public static DbTransaction getRequired(DbSource source) {

    return AbstractDbTransaction.getRequired(source);
  }

}
