/*
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
/**
 * Provides tests of {@code mmm-orm} to be reused for various databases.
 */
module io.github.mmm.db.orm.test {

  requires io.github.mmm.db.orm;

  requires java.sql;

  requires org.assertj.core;

  requires org.junit.jupiter.api;

  exports io.github.mmm.db.orm.test;

}
