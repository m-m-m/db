
/*
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
/**
 * Provides core database support.
 */
module io.github.mmm.db.schema {

  requires transitive io.github.mmm.db;

  requires transitive io.github.mmm.base.resource;

  requires org.slf4j;

  requires java.sql;

  exports io.github.mmm.db.schema;

}
