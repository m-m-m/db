/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.tx.spi;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

import io.github.mmm.base.exception.ObjectNotFoundException;
import io.github.mmm.db.source.DbSource;
import io.github.mmm.db.tx.DbTransaction;
import io.github.mmm.db.tx.DbTransactionConfig;
import io.github.mmm.db.tx.DbTransactionExecutor;
import io.github.mmm.entity.bean.EntityBean;

/**
 * Abstract base implementation of {@link DbTransaction}.
 *
 * @since 1.0.0
 */
public abstract class AbstractDbTransaction implements DbTransaction {

  private static final ScopedValue<AbstractDbTransaction> TX = ScopedValue.newInstance();

  private final DbTransactionConfig config;

  /** @see #getParent() */
  protected final AbstractDbTransaction parent;

  private final Map<String, Object> values;

  private boolean open;

  /**
   * The constructor.
   *
   * @param config the {@link DbTransactionConfig}.
   * @param parent the {@link #getParent() parent}.
   */
  public AbstractDbTransaction(DbTransactionConfig config, AbstractDbTransaction parent) {

    super();
    this.config = config;
    this.parent = parent;
    this.values = new ConcurrentHashMap<>();
    this.open = true;
  }

  @Override
  public DbTransactionConfig getConfig() {

    return this.config;
  }

  @Override
  public <T> T getValue(Class<T> type) {

    return getValue(type, true);
  }

  /**
   * @param <T> type of the associated object.
   * @param type the {@link Class} reflecting the associated object. E.g. {@code DbDialect}.
   * @param inherit - {@code true} to inherit the value from {@link #getParent() parents}, {@code false} otherwise.
   * @return the object associated with the given {@link Class} or {@code null} if not set.
   */
  public <T> T getValue(Class<T> type, boolean inherit) {

    assert (!EntityBean.class.isAssignableFrom(type));
    Object value = this.values.get(type.getName());
    if ((value == null) && inherit && (this.parent != null)) {
      value = this.parent.getValue(type, true);
    }
    return type.cast(value);
  }

  /**
   * @param <T> type of the associated object.
   * @param type the {@link Class} reflecting the associated object.
   * @param value the value to associate with the given {@link Class}.
   * @throws RuntimeException if the given {@link Class} is unsupported or already associated.
   */
  public <T> void setValue(Class<T> type, T value) {

    String name = type.getName();
    if (name.startsWith("java.")) {
      throw new IllegalArgumentException(name);
    }
    if (EntityBean.class.isAssignableFrom(type)) {
      throw new IllegalArgumentException(name);
    }
    Object old = this.values.putIfAbsent(name, value);
    if (old != null) {
      throw new IllegalStateException("Type already associated: " + name);
    }
  }

  /**
   * @param <E> type of the {@link EntityBean}.
   * @param entity the {@link EntityBean}.
   * @param factory the {@link DbEntitySessionFactory} or {@code null} to only get existing {@link DbEntitySession}
   *        instances and return {@code null} otherwise.
   * @return the {@link DbEntitySession} will be created via {@link DbEntitySessionFactory factory} if not yet exist.
   *         Only {@code null} if not present and factory is {@code null}.
   */
  @SuppressWarnings("unchecked")
  public <E extends EntityBean> DbEntitySession<E> getEntitySession(E entity, DbEntitySessionFactory factory) {

    Objects.requireNonNull(entity, "entity");
    DbEntitySession<E> session;
    String key = entity.getType().getQualifiedName();
    if (factory == null) {
      session = (DbEntitySession<E>) this.values.get(key);
    } else {
      session = (DbEntitySession<E>) this.values.computeIfAbsent(key, _ -> factory.create(entity));
    }
    return session;

  }

  @Override
  public boolean isOpen() {

    return this.open;
  }

  /**
   * Performs an intermediate commit in case of chunk processing for batches. Assuming you have to process large data
   * (e.g. million of records or entities). Obviously you do not want to process everything in a single transaction.
   * Instead you want to process your data in chunks of e.g. one thousand and call this method after each such chunk was
   * successfully completed. This current transaction remains active and is still committed automatically at the end so
   * you do not need to commit the last chunk manually via this method.<br>
   * Example:
   *
   * <pre>
   * private void processInTx(Iterator<Data> dataProvider) {
   *   {@link DbTransaction} tx = {@link DbTransaction#get()};
   *   int chunkCount = 0;
   *   while (dataProvider.hasNext()) {
   *     chunkCount++;
   *     Data data = dataProvider.next();
   *     processData(data);
   *     if (chunkCount == 1000) {
   *       tx.commit();
   *       chunkCount = 0;
   *     }
   *   }
   * }
   * </pre>
   */
  public abstract void commit();

  /**
   * Performs a rollback of this current transaction. Typically you should not explicitly call this method. Simply throw
   * an {@link Exception} and the transaction will be rolled back automatically. However, if you use
   * {@link DbTransactionExecutor#doInTx(java.util.concurrent.Callable)} and want to return a regular value in case of
   * an error but rollback all changes in the database, you can use this method.
   */
  public abstract void rollback();

  /**
   * Ensures that this transaction is still {@link #isOpen() open}.
   */
  protected void requireOpen() {

    if (!this.open) {
      throw new IllegalStateException("Transaction already closed!");
    }
  }

  /**
   * @return the parent {@link DbTransaction}.
   */
  public AbstractDbTransaction getParent() {

    return this.parent;
  }

  /**
   * @return a new sub-transaction for the same {@link #getConfig() config}.
   */
  public final AbstractDbTransaction createChild() {

    requireOpen();
    return doCreateChild();
  }

  /**
   * @return a new sub-transaction for the same {@link #getConfig() config}.
   */
  protected abstract AbstractDbTransaction doCreateChild();

  /**
   * Closes this transaction and releases allocated resources.
   */
  protected void close() {

    this.open = false;
  }

  <R> R doInTx(Callable<R> task) {

    try {
      R result = ScopedValue.where(TX, this).call(() -> task.call());
      commit();
      return result;
    } catch (Throwable t) {
      try {
        rollback();
      } catch (Exception e) {
        t.addSuppressed(e);
      }
      throw throwException(t);
    } finally {
      close();
    }
  }

  static RuntimeException throwException(Throwable t) {

    if (t instanceof RuntimeException re) {
      throw re;
    } else if (t instanceof Error e) {
      throw e;
    }
    throw new IllegalStateException(t);
  }

  /**
   * @param source the {@link DbSource}.
   * @return the first {@link AbstractDbTransaction} having the given {@link DbSource} along the {@link #getParent()
   *         parent hierarchy} or {@code null} if not found.
   */
  AbstractDbTransaction getParent(DbSource source) {

    AbstractDbTransaction tx = this;
    String id = source.getId();
    while (tx != null) {
      if (tx.getConfig().getSource().getId().equals(id)) {
        return tx;
      }
      tx = tx.getParent();
    }
    return null;
  }

  /**
   * @param <T> type of the specific {@link AbstractDbTransaction transaction} implementation.
   * @return this transaction casted to the desired type.
   */
  @SuppressWarnings("unchecked")
  public <T extends AbstractDbTransaction> T cast() {

    return (T) this;
  }

  /**
   * This is an internal method. API or behaviour may change.
   *
   * @param <T> type of the underlying connection.
   * @return the raw underlying connection.
   */
  public abstract <T> T getConnection();

  /**
   * @param source the {@link DbSource} for which the active {@link DbTransaction} is requested. May be {@code null} to
   *        get the current top-level {@link DbTransaction}.
   * @return the active transaction for the given {@link DbSource} or {@code null} if no such transaction is active for
   *         the current {@link Thread}.
   * @see DbTransaction#get(DbSource)
   */
  public static AbstractDbTransaction get(DbSource source) {

    if (!TX.isBound()) {
      return null;
    }
    AbstractDbTransaction tx = TX.get();
    if (source == null) {
      return tx;
    }
    return tx.getParent(source);
  }

  /**
   * @param source the {@link DbSource} for which the active {@link DbTransaction} is requested. May be {@code null} to
   *        get the current top-level {@link DbTransaction}.
   * @return the active transaction for the given {@link DbSource} or {@code null} if no such transaction is active for
   *         the current {@link Thread}.
   * @see DbTransaction#get(DbSource)
   */
  public static AbstractDbTransaction getRequired(DbSource source) {

    try {
      AbstractDbTransaction tx = TX.get();
      if (source == null) {
        return tx;
      }
      return tx.getParent(source);
    } catch (NoSuchElementException e) {
      if ((source != null) || (AbstractDbTransaction.get(null) == null)) {
        source = null;
      }
      throw new ObjectNotFoundException("DbTransaction", source, e);
    }
  }

}
