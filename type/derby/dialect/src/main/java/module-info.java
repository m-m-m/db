/*
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
/**
 * Provides Apache Derby database dialect.
 *
 * @provides io.github.mmm.db.dialect.DbDialect
 */
module io.github.mmm.db.type.derby.dialect {

  requires io.github.mmm.db.orm;

  provides io.github.mmm.db.dialect.DbDialect //
      with io.github.mmm.db.type.derby.dialect.DerbyDialect;

}
