/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.sync;

import io.github.mmm.entity.bean.EntityBean;

/// Interface to synchronize the Java [EntityBean]-model with the DDL of your database.
/// It will build the diff of your [EntityBean]-model and your DDL-models. All columns missing in one of the models
/// will be created in the other. So unlike hibernate hbm2dd auto feature it will synchronize bidirectional and also
/// creates properties in case of [dynamic][EntityBean#isDynamic()] and [virtual][io.github.mmm.bean.VirtualBean] [EntityBean]s.
/// **ATTENTION:**
/// This feature is designed only for development and should not be used in production systems.
public interface ModelSynchronizer {

  /// Synchronizes all [EntityBean]s managed by a [repository][io.github.mmm.db.repository.EntityRepository] that currently have an open transaction.
  void sync();

  /// @param entityClass the [Class] reflection the [EntityBean] to synchronize with the database (DDL).
  /// @throws RuntimeException if no transaction is open for the according [database source][io.github.mmm.db.source.DbSource].
  void sync(Class<? extends EntityBean> entityClass);

  /// @param entity the [EntityBean] to synchronize with the database (DDL).
  /// @throws RuntimeException if no transaction is open for the according [database source][io.github.mmm.db.source.DbSource].
  void sync(EntityBean entity);

}
