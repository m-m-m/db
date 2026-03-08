/*
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
/**
 * Provides DB support for derby database via JDBC.
 */
module io.github.mmm.db.type.derby.jdbc {

  requires io.github.mmm.db.type.derby.dialect;

  requires org.apache.derby.engine;

}
