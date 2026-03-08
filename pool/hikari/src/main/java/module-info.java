
/*
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

/**
 * Provides connection pooling via HikariCP for {@code mmm-db}.
 *
 * @provides io.github.mmm.db.pool.DbConnectionPoolProvider
 */
@SuppressWarnings("rawtypes") //
module io.github.mmm.db.pool.hikari {

  requires transitive io.github.mmm.db;

  requires com.zaxxer.hikari;

  requires java.sql;

  provides io.github.mmm.db.pool.DbConnectionPoolProvider
      with io.github.mmm.orm.jdbc.pool.hikari.HikariConnectionPoolProvider;

}
