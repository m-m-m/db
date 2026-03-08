/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.tx;

import io.github.mmm.db.source.DbSource;

/**
 * Configuration of a {@link DbTransaction}.<br>
 * There is no {@code rollbackFor} or {@code dontRollbackOn} option since this is rather a design flaw than a feature.
 * Every {@link Exception} or {@link Throwable} should cause a rollback. If you do not want to rollback, then exit the
 * callback without throwing anything. Via {@link java.util.concurrent.Callable} you can return an arbitrary object that
 * may contain warning or error information as needed.
 *
 * @see #get()
 * @see #withReadOnly(boolean)
 * @see #withSource(DbSource)
 */
public final class DbTransactionConfig {

  private static final DbTransactionConfig DEFAULT = new DbTransactionConfig(false, DbSource.get(),
      DbTransactionPropagation.REQUIRED);

  private final boolean readOnly;

  private final DbSource source;

  private final DbTransactionPropagation propagation;

  private DbTransactionConfig(boolean readOnly, DbSource source, DbTransactionPropagation propagation) {

    super();
    this.readOnly = readOnly;
    this.source = source;
    this.propagation = propagation;
  }

  /**
   * @return {@code true} if this transaction is defined as read-only transaction, {@code false} otherwise. When your
   *         transaction is read-only and you perform a write operation to the database, it will fail.
   */
  public boolean isReadOnly() {

    return this.readOnly;
  }

  /**
   * @param newReadOnly the new value for {@link #isReadOnly()}.
   * @return a new {@link DbTransactionConfig} with the given {@link #isReadOnly() read-only} value.
   */
  public DbTransactionConfig withReadOnly(boolean newReadOnly) {

    if (this.readOnly == newReadOnly) {
      return this;
    }
    return new DbTransactionConfig(newReadOnly, this.source, this.propagation);
  }

  /**
   * @return a new {@link DbTransactionConfig} with {@link #isReadOnly() read-only} being {@code true}.
   */
  public DbTransactionConfig withReadOnly() {

    return withReadOnly(true);
  }

  /**
   * @return a new {@link DbTransactionConfig} with {@link #isReadOnly() read-only} being {@code false}.
   */
  public DbTransactionConfig withWritable() {

    return withReadOnly(false);
  }

  /**
   * @return the {@link DbSource} of this transaction.
   */
  public DbSource getSource() {

    return this.source;
  }

  /**
   * @param newSource the new value for {@link #getSource()}.
   * @return a new {@link DbTransactionConfig} with the given {@link DbSource}.
   */
  public DbTransactionConfig withSource(DbSource newSource) {

    if (this.source == newSource) {
      return this;
    }
    return new DbTransactionConfig(this.readOnly, newSource, this.propagation);
  }

  /**
   * @return the {@link DbTransactionPropagation}.
   */
  public DbTransactionPropagation getPropagation() {

    return this.propagation;
  }

  /**
   * @param newPropagation the new value for {@link #getPropagation()}.
   * @return a new {@link DbTransactionConfig} with the given {@link DbTransactionPropagation}.
   */
  public DbTransactionConfig withPropagation(DbTransactionPropagation newPropagation) {

    if (this.propagation == newPropagation) {
      return this;
    }
    return new DbTransactionConfig(this.readOnly, this.source, newPropagation);
  }

  /**
   * @return a new {@link DbTransactionConfig} with {@link DbTransactionPropagation#REQUIRED}.
   */
  public DbTransactionConfig withPropagationRequired() {

    return withPropagation(DbTransactionPropagation.REQUIRED);
  }

  /**
   * @return a new {@link DbTransactionConfig} with {@link DbTransactionPropagation#REQUIRES_NEW}.
   */
  public DbTransactionConfig withPropagationRequiresNew() {

    return withPropagation(DbTransactionPropagation.REQUIRES_NEW);
  }

  /**
   * @return the default instance of {@link DbTransactionConfig}. Will allow write access (not {@link #isReadOnly()
   *         read-only} and use {@link DbSource#get() default} {@link DbSource}.
   */
  public static DbTransactionConfig get() {

    return DEFAULT;
  }

}
