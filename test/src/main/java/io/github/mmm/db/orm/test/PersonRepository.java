package io.github.mmm.db.orm.test;

import io.github.mmm.db.repository.DbRepository;
import io.github.mmm.db.repository.operation.DbDdlOperations;

/// [Repository][DbRepository] for [Person].
public interface PersonRepository extends DbRepository<Person>, DbDdlOperations<Person> {

  @Override
  default String getSequenceName() {

    return "PERSON_SEQ";
  }

}
