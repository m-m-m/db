/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.tx.spi;

import java.util.concurrent.Callable;

import io.github.mmm.db.tx.DbTransactionConfig;
import io.github.mmm.db.tx.DbTransactionExecutor;
import io.github.mmm.db.tx.DbTransactionPropagation;

/**
 * Abstract base implementation of {@link DbTransactionExecutor}.
 */
public abstract class AbstractDbTransactionExecutor implements DbTransactionExecutor {

  @Override
  public <R> R doInTx(Callable<R> task, DbTransactionConfig config) {

    AbstractDbTransaction tx = AbstractDbTransaction.get(null);
    AbstractDbTransaction sourceTx = null;
    AbstractDbTransaction newTx = null;
    if (tx != null) {
      sourceTx = tx.getParent(config.getSource());
      DbTransactionPropagation propagation = config.getPropagation();
      if (propagation == DbTransactionPropagation.REQUIRED) {
        if (sourceTx == tx) {
          try {
            return task.call();
          } catch (Exception e) {
            throw AbstractDbTransaction.throwException(e);
          }
        } else if (sourceTx != null) {
          newTx = sourceTx;
        }
      } else if (propagation == DbTransactionPropagation.REQUIRES_NEW) {
        newTx = tx.createChild();
      }
    }
    if (newTx == null) {
      newTx = createNewTransaction(config, tx);
    }
    return newTx.doInTx(task);
  }

  /**
   * @param txConfig the {@link DbTransactionConfig}.
   * @param parent the {@link AbstractDbTransaction#getParent() parent transaction}.
   * @return the newly created {@link AbstractDbTransaction}.
   */
  protected abstract AbstractDbTransaction createNewTransaction(DbTransactionConfig txConfig,
      AbstractDbTransaction parent);

}
