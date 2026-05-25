package io.github.mmm.db.orm.test;

import java.sql.SQLException;
import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.mmm.db.repository.EntityRepositoryManager;
import io.github.mmm.db.tx.DbTransaction;
import io.github.mmm.db.tx.DbTransactionExecutor;
import io.github.mmm.entity.id.Id;
import io.github.mmm.entity.link.Link;

/**
 * Abstract test for JDBC database support. Database specific modules extend this class to test that basic features of
 * the database are working properly.
 */
public abstract class JdbcTest extends Assertions {

  /** Test that H2 integration works. */
  @Test
  void testInsertAndUpdatePerson() {

    try {
      DbTransactionExecutor executor = DbTransactionExecutor.get();
      DbTransaction tx = executor.doInTx(() -> doInTxInsertAndUpdatePerson(executor));
      assertThat(tx.isOpen()).isFalse();
    } catch (Throwable t) {
      // JUnit is buggy and eats up exceptions
      t.printStackTrace();
      throw t;
    }
  }

  private DbTransaction doInTxInsertAndUpdatePerson(DbTransactionExecutor executor) throws SQLException {

    DbTransaction tx = DbTransaction.get();
    assertThat(tx.isOpen()).isTrue();
    Person person = Person.of();
    PersonRepository repository = EntityRepositoryManager.get().get(PersonRepository.class);
    repository.createTable();
    repository.createSequence();
    person.Name().set("John Doe");
    person.Birthday().set(LocalDate.of(1999, 12, 31));
    repository.save(person);
    assertThat(person.getId().getPk()).isEqualTo(1000000000000L);
    assertThat(person.getId().getRevision()).isEqualTo(1L);

    person.Name().set("Joe Doe");
    // to force OptimisticLockException...
    // LongVersionId<Person> id = (LongVersionId<Person>) Id.from(person);
    // person.Id().set(id.withRevision(0L));
    repository.save(person);
    Person person2 = repository.findById(Id.from(person));
    assertThat(person.isEqual(person2)).isTrue();
    assertThat(person.getId().getPk()).isEqualTo(1000000000000L);
    assertThat(person.getId().getRevision()).isEqualTo(2L);
    return tx;
  }

  /** Test that entity with relation can be persisted (inserted). */
  @Test
  void testInsertEntityWithRelation() {

    try {
      DbTransactionExecutor executor = DbTransactionExecutor.get();
      DbTransaction tx = executor.doInTx(() -> doInTxInsertEntityWithRelation(executor));
      assertThat(tx.isOpen()).isFalse();
    } catch (Throwable t) {
      // JUnit is buggy and eats up exceptions
      t.printStackTrace();
      throw t;
    }
  }

  private DbTransaction doInTxInsertEntityWithRelation(DbTransactionExecutor executor) throws SQLException {

    DbTransaction tx = DbTransaction.get();
    assertThat(tx.isOpen()).isTrue();
    TaskListRepository listRepository = EntityRepositoryManager.get().get(TaskListRepository.class);
    listRepository.createTable();
    listRepository.createSequence();
    TaskItemRepository itemRepository = EntityRepositoryManager.get().get(TaskItemRepository.class);
    itemRepository.createTable();
    itemRepository.createSequence();
    TaskList taskList = TaskList.of();
    taskList.Name().set("Shopping");
    listRepository.save(taskList);
    assertThat(taskList.getId().getPk()).isEqualTo(1000000000000L);
    assertThat(taskList.getId().getRevision()).isEqualTo(1L);

    TaskItem item1 = TaskItem.of();
    item1.Name().set("Milk");
    item1.TaskList().set(Link.of(taskList));
    itemRepository.save(item1);

    TaskItem item2 = TaskItem.of();
    item2.Name().set("Bread");
    item2.TaskList().set(Link.of(taskList));
    itemRepository.save(item2);

    item1 = itemRepository.findById(Id.from(item1));
    item2 = itemRepository.findById(Id.from(item2));

    Iterable<TaskItem> items = itemRepository.findByTaskList(Id.from(taskList));
    assertThat(items).containsExactlyInAnyOrder(item1, item2);
    return tx;
  }

}
