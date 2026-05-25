/*
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
/**
 * Provides database support for {@code mmm-entity-bean}.
 *
 * @uses io.github.mmm.db.dialect.DbDialect
 * @uses io.github.mmm.db.repository.spi.EntityRepositoryFactory
 * @uses io.github.mmm.db.spi.access.DbAccessProvider
 * @provides io.github.mmm.db.source.spi.DbSourceConfigurer
 * @provides io.github.mmm.db.spi.access.DbAccessProvider
 * @provides io.github.mmm.base.resource.ResourceScannerService
 * @provides io.github.mmm.db.repository.spi.EntityRepositoryFactory
 */
@SuppressWarnings("rawtypes") //
module io.github.mmm.db.orm {

  requires transitive io.github.mmm.db.mapping;

  requires io.github.mmm.base.resource;

  requires java.sql;

  uses io.github.mmm.db.dialect.DbDialect;

  uses io.github.mmm.db.spi.access.DbAccessProvider;

  uses io.github.mmm.db.repository.spi.EntityRepositoryFactory;

  provides io.github.mmm.db.source.spi.DbSourceConfigurer //
      with io.github.mmm.db.orm.impl.OrmDbSourceConfigurer;

  provides io.github.mmm.db.spi.access.DbAccessProvider //
      with io.github.mmm.db.spi.access.impl.JdbcAccessProvider;

  provides io.github.mmm.base.resource.ResourceScannerService //
      with io.github.mmm.db.repository.impl.EntityRepositoryScanner;

  provides io.github.mmm.db.repository.spi.EntityRepositoryFactory
      with io.github.mmm.db.repository.impl.DbRepositoryFactory;

  exports io.github.mmm.db.dialect;

  exports io.github.mmm.db.orm;

  exports io.github.mmm.db.repository;

  exports io.github.mmm.db.repository.operation;

  exports io.github.mmm.db.repository.spi;

  exports io.github.mmm.db.spi.access;

  exports io.github.mmm.db.statement;

  exports io.github.mmm.db.statement.create;

  exports io.github.mmm.db.statement.delete;

  exports io.github.mmm.db.statement.insert;

  exports io.github.mmm.db.statement.merge;

  exports io.github.mmm.db.statement.select;

  exports io.github.mmm.db.statement.update;

  exports io.github.mmm.db.statement.upsert;

}
