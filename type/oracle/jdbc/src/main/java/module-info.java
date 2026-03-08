/*
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

/**
 * Provides DB support for Oracle database via JDBC.
 *
 * @provides io.github.mmm.db.pool.DbConnectionPoolProvider
 */
@SuppressWarnings("rawtypes") //
module io.github.mmm.db.type.oracle.jdbc {

  requires io.github.mmm.db.type.oracle.dialect;

  requires java.sql;

  requires ucp;

  requires ojdbc10;

  provides io.github.mmm.db.pool.DbConnectionPoolProvider //
      with io.github.mmm.db.type.oracle.jdbc.connection.OracleConnectionPoolProvider;

}
