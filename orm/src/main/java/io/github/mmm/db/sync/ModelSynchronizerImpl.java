/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.sync;

import io.github.mmm.bean.BeanFactory;
import io.github.mmm.db.ddl.DbMetaData;
import io.github.mmm.db.ddl.table.DbTable;
import io.github.mmm.db.dialect.DbDialect;
import io.github.mmm.db.name.DbQualifiedName;
import io.github.mmm.db.repository.EntityRepository;
import io.github.mmm.db.repository.EntityRepositoryManager;
import io.github.mmm.db.repository.impl.DbRepositoryImpl;
import io.github.mmm.db.source.DbSource;
import io.github.mmm.db.source.DbSourceConfig;
import io.github.mmm.db.spi.access.DbAccess;
import io.github.mmm.db.tx.DbTransaction;
import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.property.WritableProperty;

/**
 * Implementation of {@link ModelSynchronizer}.
 */
public class ModelSynchronizerImpl implements ModelSynchronizer {

  private DbSourceConfig sourceConfig;

  private DbDialect dialect;

  private DbAccess dbAccess;

  @Override
  public void sync() {

    EntityRepositoryManager erm = EntityRepositoryManager.get();
    for (EntityRepository<?> repository : erm) {
      if (repository instanceof DbRepositoryImpl<?> dbRepository) {
        DbSource source = dbRepository.getSource();
        EntityBean entity = dbRepository.getPrototype();
        sync(entity);
      }
    }
  }

  @Override
  public void sync(Class<? extends EntityBean> entityClass) {

    BeanFactory factory = BeanFactory.get();
    EntityBean entity = factory.create(entityClass);
    sync(entity);
  }

  @Override
  public void sync(EntityBean entity) {

    DbMetaData metaData = DbTransaction.getRequired(this.sourceConfig.getSource()).getMetaData();
    DbQualifiedName tableName = this.dialect.getNamingStrategy().getTableName(entity);
    tableName = metaData.qualify(tableName);
    DbTable table = metaData.getTable(tableName);
    if (table == null) {
      this.dbAccess.createTable(entity);
      return;
    }
    for (WritableProperty<?> property : entity.getProperties()) {
      if (!property.isTransient()) {
        String name = property.getName();
      }
    }
  }

}
