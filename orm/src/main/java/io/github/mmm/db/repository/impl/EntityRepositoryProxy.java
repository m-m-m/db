/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.repository.impl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;

import io.github.mmm.bean.BeanFactory;
import io.github.mmm.db.repository.EntityRepository;
import io.github.mmm.db.repository.spi.AbstractEntityRepository;
import io.github.mmm.db.repository.spi.EntityRepositoryFactory;
import io.github.mmm.entity.bean.EntityBean;

/**
 * Support to create an instance of an {@link EntityRepository} interface as dynamic proxy.
 *
 * @param <R> type of the {@link EntityRepository}.
 * @since 1.0.0
 */
public class EntityRepositoryProxy<R extends EntityRepository<?>> implements InvocationHandler {

  /** @see #getProxy() */
  protected final R proxy;

  private final AbstractEntityRepository<?> repository;

  @SuppressWarnings({ "unchecked", "rawtypes" })
  private EntityRepositoryProxy(Class<R> type, ClassLoader classLoader, EntityBean prototype,
      EntityRepositoryFactory factory) {

    super();
    this.proxy = (R) Proxy.newProxyInstance(classLoader, new Class[] { type }, this);
    this.repository = factory.create(prototype, this.proxy);
    this.repository.init();
  }

  @Override
  public Object invoke(Object proxyInstance, Method method, Object[] args) throws Throwable {

    assert (proxyInstance == this.proxy);
    if (method.isDefault()) {
      return InvocationHandler.invokeDefault(this.proxy, method, args);
    }
    Object result = method.invoke(this.repository, args);
    if (result == this.repository) {
      result = this.proxy;
    }
    return result;
  }

  /**
   * @return the {@link EntityRepository} instance.
   */
  public R getProxy() {

    return this.proxy;
  }

  /**
   * @param <R> type of the {@link EntityRepository}.
   * @param type the {@link Class} reflecting the {@link EntityRepository} to instantiate.
   * @param factory the {@link EntityRepositoryFactory#isApplicable(Class) applicable} {@link EntityRepositoryFactory}.
   * @return the {@link EntityRepositoryProxy}.
   */
  @SuppressWarnings("unchecked")
  public static <R extends EntityRepository<?>> EntityRepositoryProxy<R> of(Class<R> type,
      EntityRepositoryFactory<?> factory) {

    for (Type superInterface : type.getGenericInterfaces()) {
      if (superInterface instanceof ParameterizedType parameterizedType) {
        Class<?> rawType = (Class<?>) parameterizedType.getRawType();
        if (EntityRepository.class.isAssignableFrom(rawType)) {
          Type[] typeArguments = parameterizedType.getActualTypeArguments();
          if (typeArguments.length == 1) {
            Type entityType = typeArguments[0];
            if (entityType instanceof Class<?> entityClass) {
              if (EntityBean.class.isAssignableFrom(entityClass)) {
                ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
                EntityBean prototype = BeanFactory.get().create((Class<? extends EntityBean>) entityClass);
                return new EntityRepositoryProxy<>(type, classLoader, prototype, factory);
              }
            }
          }
        }
      }
    }
    return null;
  }

}
