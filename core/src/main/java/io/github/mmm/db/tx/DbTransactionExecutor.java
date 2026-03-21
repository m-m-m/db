/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.tx;

import java.util.concurrent.Callable;

import io.github.mmm.db.source.DbSource;
import io.github.mmm.db.source.DbSourceConfig;
import io.github.mmm.db.tx.impl.DbTransactionExceutorProviderAdapter;

/**
 * Interface to execute a {@link Runnable} or {@link Callable} within a transaction. This design guarantees safe coding:
 * <ul>
 * <li>No need to call {@code begin()} as you begin the transaction automatically when calling
 * {@link #doInTx(Runnable)}.</li>
 * <li>No need to call {@code commit()} as you commit the transaction automatically when your task ({@link Runnable} or
 * {@link Callable}) returned successfully.</li>
 * <li>Non need to call {@code rollback()} or {@code setRollbackOnly()} as you will rollback the transaction
 * automatically when your task ({@link Runnable} or {@link Callable}) failed by throwing any {@link Throwable}.</li>
 * <li>No manual fiddling with save-points for KISS and to avoid mistakes.</li>
 * <li>Also, nested transactions (sub-transactions) are typically an indicator of over-complicated or even flawed
 * design. However, if really needed this API can be used again within the transaction task to create a nested
 * transaction.</li>
 * <li></li>
 * </ul>
 *
 * @since 1.0.0
 */
public interface DbTransactionExecutor {

  /**
   * Most simple variant to execute a task in a transaction when no result is needed. Otherwise use
   * {@link #doInTx(Callable)} instead.
   *
   * @param task the {@link Runnable} to execute in a transaction.
   */
  default void doInTx(Runnable task) {

    doInTx(task, DbTransactionConfig.get());
  }

  /**
   * Most simple variant to execute a task in a transaction when no result is needed. Otherwise use
   * {@link #doInTx(Callable)} instead.
   *
   * @param task the {@link Runnable} to execute in a transaction.
   * @param config the {@link DbTransactionConfig}.
   */
  default void doInTx(Runnable task, DbTransactionConfig config) {

    Callable<Void> callable = new Callable<>() {

      @Override
      public Void call() {

        task.run();
        return null;
      }
    };
    doInTx(callable, config);
  }

  /**
   * Executes a {@link Callable} task in a transaction and returns the result of it.
   *
   * @param <R> type of the result.
   * @param task the {@link Callable} to execute in a transaction.
   * @return the result of {@link Callable#call()}.
   */
  default <R> R doInTx(Callable<R> task) {

    return doInTx(task, DbTransactionConfig.get());
  }

  /**
   * Executes a {@link Callable} task in a transaction and returns the result of it.
   *
   * @param <R> type of the result.
   * @param task the {@link Callable} to execute in a transaction.
   * @param config the {@link DbTransactionConfig}.
   * @return the result of {@link Callable#call()}.
   */
  <R> R doInTx(Callable<R> task, DbTransactionConfig config);

  /**
   * @return the {@link DbTransactionExecutor} for the {@link DbSource#get() default} {@link DbSource}.
   */
  static DbTransactionExecutor get() {

    return get(DbSource.get());
  }

  /**
   * @param source the {@link DbSource}.
   * @return the corresponding {@link DbTransactionExecutor}.
   */
  static DbTransactionExecutor get(DbSource source) {

    DbSourceConfig config = DbSourceConfig.of(source);
    return DbTransactionExceutorProviderAdapter.INSTANCE.create(config);
  }

}
