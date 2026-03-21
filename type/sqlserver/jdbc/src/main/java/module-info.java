/*
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
/**
 * Provides DB support for Microsoft SQL-Server database via JDBC.
 */
module io.github.mmm.db.type.sqlserver.jdbc {

  requires io.github.mmm.db.type.sqlserver.dialect;

  requires com.microsoft.sqlserver.jdbc;
}
