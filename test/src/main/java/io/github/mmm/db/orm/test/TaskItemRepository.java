package io.github.mmm.db.orm.test;

import java.util.Objects;

import io.github.mmm.db.repository.DbRepository;
import io.github.mmm.db.repository.operation.DbDdlOperations;
import io.github.mmm.db.statement.DbStatement;
import io.github.mmm.db.statement.select.SelectStatement;
import io.github.mmm.entity.id.Id;

/**
 * {@link DbRepository Repository} for {@link TaskItem}.
 */
public interface TaskItemRepository extends DbRepository<TaskItem>, DbDdlOperations<TaskItem> {

  /**
   * @param taskListId the {@link Id} of the {@link TaskList}.
   * @return an {@link Iterable} of the {@link TaskItem}s.
   */
  default Iterable<TaskItem> findByTaskList(Id<TaskList> taskListId) {

    Objects.requireNonNull(taskListId);
    TaskItem item = TaskItem.of();
    SelectStatement<TaskItem> select = DbStatement.select(item).as("i").where(item.TaskList().eq(taskListId)).get();
    return findByQuery(select);
  }

  @Override
  default String getSequenceName() {

    return "TASK_ITEM_SEQ";
  }

}
