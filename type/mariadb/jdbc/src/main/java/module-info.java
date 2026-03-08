/*
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
/**
 * Provides DB support for MariaDB database via JDBC.
 */
module io.github.mmm.db.type.mariadb.jdbc {

  requires io.github.mmm.db.type.mariadb.dialect;

  requires org.mariadb.jdbc;

}
