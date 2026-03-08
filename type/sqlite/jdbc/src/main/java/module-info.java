/*
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
/**
 * Provides DB support for SQLite database via JDBC.
 *
 * @provides io.github.mmm.db.pool.DbConnectionPoolProvider
 */
@SuppressWarnings("rawtypes") //
module io.github.mmm.db.type.sqlite.jdbc {

  requires io.github.mmm.db.type.sqlite.dialect;

  requires java.sql;

  requires org.xerial.sqlitejdbc;

  provides io.github.mmm.db.pool.DbConnectionPoolProvider //
      with io.github.mmm.db.type.sqlite.jdbc.pool.SqliteConnectionPoolProvider;

}
