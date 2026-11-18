/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.tx;

import io.github.mmm.db.source.DbSource;

/// Configuration of a [DbTransaction].
/// There is no `rollbackFor` or `dontRollbackOn` option since this is rather a design flaw than a feature.
/// Every [Exception] or [Throwable] should cause a rollback. If you do not want to rollback, then exit the
/// callback without throwing anything. Via [java.util.concurrent.Callable] you can return an arbitrary object that
/// may contain warning or error information as needed.
///
/// @see #get()
/// @see #withReadOnly(boolean)
/// @see #withSource(DbSource)
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

  /// @return `true` if this transaction is defined as read-only transaction, `false` otherwise. When your
  ///         transaction is read-only and you perform a write operation to the database, it will fail.
  public boolean isReadOnly() {

    return this.readOnly;
  }

  /// @param newReadOnly the new value for [#isReadOnly()].
  /// @return a new [DbTransactionConfig] with the given [read-only][#isReadOnly()] value.
  public DbTransactionConfig withReadOnly(boolean newReadOnly) {

    if (this.readOnly == newReadOnly) {
      return this;
    }
    return new DbTransactionConfig(newReadOnly, this.source, this.propagation);
  }

  /// @return a new [DbTransactionConfig] with [read-only][#isReadOnly()] being `true`.
  public DbTransactionConfig withReadOnly() {

    return withReadOnly(true);
  }

  /// @return a new [DbTransactionConfig] with [read-only][#isReadOnly()] being `false`.
  public DbTransactionConfig withWritable() {

    return withReadOnly(false);
  }

  /// @return the [DbSource] of this transaction.
  public DbSource getSource() {

    return this.source;
  }

  /// @param newSource the new value for [#getSource()].
  /// @return a new [DbTransactionConfig] with the given [DbSource].
  public DbTransactionConfig withSource(DbSource newSource) {

    if (this.source == newSource) {
      return this;
    }
    return new DbTransactionConfig(this.readOnly, newSource, this.propagation);
  }

  /// @return the [DbTransactionPropagation].
  public DbTransactionPropagation getPropagation() {

    return this.propagation;
  }

  /// @param newPropagation the new value for [#getPropagation()].
  /// @return a new [DbTransactionConfig] with the given [DbTransactionPropagation].
  public DbTransactionConfig withPropagation(DbTransactionPropagation newPropagation) {

    if (this.propagation == newPropagation) {
      return this;
    }
    return new DbTransactionConfig(this.readOnly, this.source, newPropagation);
  }

  /// @return a new [DbTransactionConfig] with [DbTransactionPropagation#REQUIRED].
  public DbTransactionConfig withPropagationRequired() {

    return withPropagation(DbTransactionPropagation.REQUIRED);
  }

  /// @return a new [DbTransactionConfig] with [DbTransactionPropagation#REQUIRES_NEW].
  public DbTransactionConfig withPropagationRequiresNew() {

    return withPropagation(DbTransactionPropagation.REQUIRES_NEW);
  }

  /// @return the default instance of [DbTransactionConfig]. Will allow write access (not [read-only][#isReadOnly()] and use [default][DbSource#get()] [DbSource].
  public static DbTransactionConfig get() {

    return DEFAULT;
  }

}
