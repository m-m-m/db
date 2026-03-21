
/*
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
/**
 * Provides core database support.
 *
 * @uses io.github.mmm.db.source.spi.DbSourceConfigurer
 * @provides io.github.mmm.db.source.spi.DbSourceConfigurer
 * @uses io.github.mmm.db.pool.DbConnectionPoolProvider
 * @uses io.github.mmm.db.tx.spi.DbTransactionExecutorProvider
 * @provides io.github.mmm.db.tx.spi.DbTransactionExecutorProvider
 */
@SuppressWarnings("rawtypes") //
module io.github.mmm.db {

  requires transitive io.github.mmm.entity.bean;

  requires transitive io.github.mmm.binary;

  requires java.sql;

  uses io.github.mmm.db.pool.DbConnectionPoolProvider;

  uses io.github.mmm.db.tx.spi.DbTransactionExecutorProvider;

  provides io.github.mmm.db.tx.spi.DbTransactionExecutorProvider //
      with io.github.mmm.db.tx.impl.JdbcTransactionExecutorProvider;

  uses io.github.mmm.db.source.spi.DbSourceConfigurer;

  provides io.github.mmm.db.source.spi.DbSourceConfigurer //
      with io.github.mmm.db.source.impl.DbSourceConfigurerFallback;

  exports io.github.mmm.db.pool;

  exports io.github.mmm.db.pool.spi;

  exports io.github.mmm.db.ddl;

  exports io.github.mmm.db.ddl.column;

  exports io.github.mmm.db.ddl.constraint;

  exports io.github.mmm.db.ddl.constraint.state;

  exports io.github.mmm.db.ddl.index;

  exports io.github.mmm.db.ddl.table.operation;

  exports io.github.mmm.db.ddl.table;

  exports io.github.mmm.db.name;

  exports io.github.mmm.db.sequence;

  exports io.github.mmm.db.source;

  exports io.github.mmm.db.source.spi;

  exports io.github.mmm.db.tx;

  exports io.github.mmm.db.tx.spi;

  exports io.github.mmm.db.type;

}
