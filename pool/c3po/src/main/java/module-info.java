/*
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
/**
 * Provides connection pooling via C3Po for {@code mmm-db}.
 *
 * @provides io.github.mmm.db.pool.DbConnectionPoolProvider
 */
@SuppressWarnings("rawtypes") //
module io.github.mmm.db.pool.c3p0 {

  requires transitive io.github.mmm.db;

  requires java.sql;

  requires java.desktop; // actually we do not require it but c3p0 lacks JPMS support and has no module descriptor

  requires c3p0;

  requires java.naming;

  provides io.github.mmm.db.pool.DbConnectionPoolProvider //
      with io.github.mmm.db.pool.c3po.C3poConnectionPoolProvider;

}
