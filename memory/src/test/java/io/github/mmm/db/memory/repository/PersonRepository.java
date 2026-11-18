/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.memory.repository;

import io.github.mmm.db.repository.EntityRepository;

/// [EntityRepository] for [Person].
@SuppressWarnings("javadoc")
public interface PersonRepository extends EntityRepository<Person> {

  Person findByNameOrAlias(String key);

  Iterable<Person> findByAge(int age);

}
