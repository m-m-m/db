/*
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
/**
 * Provides DB dialect for SQLite database.
 *
 * @provides io.github.mmm.db.dialect.DbDialect
 */
module io.github.mmm.db.type.sqlite.dialect {

  requires transitive io.github.mmm.db.orm;

  provides io.github.mmm.db.dialect.DbDialect //
      with io.github.mmm.db.type.sqlite.dialect.SqliteDialect;

  exports io.github.mmm.db.type.sqlite.dialect;

}
