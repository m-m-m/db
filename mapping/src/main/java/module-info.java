
/*
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
/**
 * Provides mapping for types and result from/to database.
 */
module io.github.mmm.db.mapping {

  requires transitive io.github.mmm.db;

  requires transitive io.github.mmm.entity.bean;

  requires transitive io.github.mmm.binary;

  requires java.sql;

  exports io.github.mmm.db.criteria;

  exports io.github.mmm.db.mapping;

  exports io.github.mmm.db.mapping.impl to io.github.mmm.db.orm;

  exports io.github.mmm.db.param;

  exports io.github.mmm.db.result;

  exports io.github.mmm.db.result.impl to io.github.mmm.db.orm;

  exports io.github.mmm.db.typemapping;

}
