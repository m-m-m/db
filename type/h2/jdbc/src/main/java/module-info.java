/*
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
/**
 * Provides DB support for H2 database via JDBC.
 *
 * @provides io.github.mmm.db.pool.DbConnectionPoolProvider
 */
@SuppressWarnings("rawtypes") //
module io.github.mmm.db.type.h2.jdbc {

  requires transitive io.github.mmm.db.type.h2.dialect;

  requires java.sql;

  requires java.naming;

  requires com.h2database;

  provides io.github.mmm.db.pool.DbConnectionPoolProvider //
      with io.github.mmm.db.type.h2.jdbc.connection.H2ConnectionPoolProvider;

}
