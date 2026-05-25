package io.github.mmm.db.orm.test;

import io.github.mmm.db.repository.DbRepository;
import io.github.mmm.db.repository.operation.DbDdlOperations;

/**
 * {@link DbRepository Repository} for {@link TaskList}.
 */
public interface TaskListRepository extends DbRepository<TaskList>, DbDdlOperations<TaskList> {

  @Override
  default String getSequenceName() {

    return "TASK_LIST_SEQ";
  }

}
