/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.source.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.ServiceLoader;

import io.github.mmm.db.source.DbSource;
import io.github.mmm.db.source.spi.DbSourceConfigurer;

/**
 * Adapter implementation of {@link DbSourceConfigurer} that holds the {@link DbSourceConfigurer}s.
 *
 * @since 1.0.0
 */
public final class DbSourceConfigurerAdapter implements DbSourceConfigurer {

  /** The singleton instance. */
  public static final DbSourceConfigurerAdapter INSTANCE = new DbSourceConfigurerAdapter();

  private final DbSourceConfigurerFallback fallback;

  private final List<DbSourceConfigurer> configurers;

  /**
   * The constructor.
   */
  public DbSourceConfigurerAdapter() {

    super();
    this.configurers = new ArrayList<>();
    DbSourceConfigurerFallback myFallback = null;
    ServiceLoader<DbSourceConfigurer> serviceLoader = ServiceLoader.load(DbSourceConfigurer.class);
    for (DbSourceConfigurer service : serviceLoader) {
      if (service instanceof DbSourceConfigurerFallback f) {
        myFallback = f;
      } else {
        this.configurers.add(service);
      }
    }
    if (myFallback == null) {
      this.fallback = new DbSourceConfigurerFallback();
    } else {
      this.fallback = myFallback;
    }
  }

  @Override
  public boolean autoConfigure(Properties properties, DbSource source) {

    for (DbSourceConfigurer configurer : this.configurers) {
      if (configurer.autoConfigure(properties, source)) {
        return true;
      }
    }
    return this.fallback.autoConfigure(properties, source);
  }
}
