/*
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
/**
 * Provides DB dialect for MariaDB.
 *
 * @provides io.github.mmm.db.dialect.DbDialect
 */
module io.github.mmm.db.type.mariadb.dialect {

  requires transitive io.github.mmm.db.orm;

  provides io.github.mmm.db.dialect.DbDialect //
      with io.github.mmm.db.type.mariadb.dialect.MariaDbDialect;

}
