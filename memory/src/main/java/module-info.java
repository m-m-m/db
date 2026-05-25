/*
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
/**
 * Provides in-memory implementation of entity repository.
 *
 * @see io.github.mmm.db.memory.repository.SequenceMemoryRepository
 * @provides io.github.mmm.db.repository.spi.EntityRepositoryFactory
 */
@SuppressWarnings("rawtypes") //
module io.github.mmm.db.memory {

  requires transitive io.github.mmm.db.orm;

  exports io.github.mmm.db.memory.repository;

  exports io.github.mmm.db.memory.index;

  provides io.github.mmm.db.repository.spi.EntityRepositoryFactory
      with io.github.mmm.db.memory.repository.impl.MemoryRepositoryFactory;

}
