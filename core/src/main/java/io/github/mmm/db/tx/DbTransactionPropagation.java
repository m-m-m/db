/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.tx;

/**
 * {@link Enum} with the available types how a {@link DbTransaction} is propagated.
 */
public enum DbTransactionPropagation {

  /**
   * In case there is already a transaction open for the {@link DbTransactionConfig#getSource() configured source}, then
   * this transaction is reused. Otherwise a new transaction is created.
   */
  REQUIRED,

  /**
   * Always create a new transaction. If there is already a transaction open for the
   * {@link DbTransactionConfig#getSource() configured source}, then a new sub-transaction is started. After the
   * sub-transaction has been completed (committed or rolled-back), the parent transaction is resumed.
   */
  REQUIRES_NEW

}
