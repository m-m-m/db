/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.schema.orm;

import io.github.mmm.db.repository.DbRepository;
import io.github.mmm.db.repository.operation.DbDdlOperations;

/// {@link DbRepository} for {@link DbSchemaChangeLog}.
public interface DbSchemaChangeLogRepository
    extends DbRepository<DbSchemaChangeLog>, DbDdlOperations<DbSchemaChangeLog> {

}
