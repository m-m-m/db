package io.github.mmm.orm.test;

import java.util.Objects;

import io.github.mmm.db.spi.repository.AbstractDbRepository;
import io.github.mmm.db.statement.DbStatement;
import io.github.mmm.db.statement.select.SelectStatement;
import io.github.mmm.entity.id.Id;

/**
 * {@link AbstractDbRepository Repository} for {@link TaskItem}.
 */
public class TaskItemRepository extends AbstractDbRepository<TaskItem> {

  /**
   * The constructor.
   */
  public TaskItemRepository() {

    super(TaskItem.of());
  }

  /**
   * @param taskListId the {@link Id} of the {@link TaskList}.
   * @return an {@link Iterable} of the {@link TaskItem}s.
   */
  public Iterable<TaskItem> findByTaskList(Id<TaskList> taskListId) {

    Objects.requireNonNull(taskListId);
    TaskItem item = TaskItem.of();
    SelectStatement<TaskItem> select = DbStatement.select(item).as("i").where(item.TaskList().eq(taskListId)).get();
    return findByQuery(select);
  }

}
