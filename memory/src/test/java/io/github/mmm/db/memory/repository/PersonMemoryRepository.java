/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.memory.repository;

import io.github.mmm.db.ddl.index.DbIndexRegistry;
import io.github.mmm.db.memory.index.MemoryNonUniqueIndex;
import io.github.mmm.db.memory.index.MemoryUniqueIndex;
import io.github.mmm.db.name.DbQualifiedName;

/**
 * Implementation of {@link PersonRepository} using {@link MemoryRepository}.
 */
public interface PersonMemoryRepository extends MemoryRepository<Person>, PersonRepository {

  /** @see #findByAge(int) */
  DbQualifiedName INDEX_AGE = DbQualifiedName.ofName("IX_AGE");

  /** @see #findByNameOrAlias(String) */
  DbQualifiedName INDEX_NAME_OR_ALIAS = DbQualifiedName.ofName("UX_NAME_OR_ALIAS");

  @Override
  default void createIndexes(DbIndexRegistry registry) {

    Person person = getPrototype();
    registry.add(new MemoryNonUniqueIndex<>(INDEX_AGE, this, person.Age()));
    registry.add(
        new MemoryUniqueIndex<>(INDEX_NAME_OR_ALIAS, this, StringNormalizer.get(), person.Name(), person.Aliases()));
    MemoryRepository.super.createIndexes(registry);
  }

  @Override
  default Iterable<Person> findByAge(int age) {

    MemoryNonUniqueIndex<Integer, Person> ageIndex = getIndex(INDEX_AGE);
    return ageIndex.find(Integer.valueOf(age));
  }

  @Override
  default Person findByNameOrAlias(String key) {

    MemoryUniqueIndex<String, Person> nameOrAliasIndex = getIndex(INDEX_NAME_OR_ALIAS);
    return nameOrAliasIndex.find(key);
  }

}
