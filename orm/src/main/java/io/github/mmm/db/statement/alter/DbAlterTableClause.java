package io.github.mmm.db.statement.alter;

import java.util.Objects;

import io.github.mmm.bean.BeanFactory;
import io.github.mmm.db.ddl.column.DbColumnReference;
import io.github.mmm.db.ddl.constraint.DbConstraint;
import io.github.mmm.db.ddl.constraint.DbForeignKeyConstraint;
import io.github.mmm.db.ddl.constraint.DbNotNullConstraint;
import io.github.mmm.db.ddl.constraint.DbPrimaryKeyConstraint;
import io.github.mmm.db.ddl.constraint.DbUniqueConstraint;
import io.github.mmm.db.ddl.table.DbTableReference;
import io.github.mmm.db.ddl.table.operation.TableOperation;
import io.github.mmm.db.statement.DbClause;
import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.property.id.FkProperty;
import io.github.mmm.entity.property.id.IdProperty;
import io.github.mmm.entity.property.id.PkProperty;
import io.github.mmm.entity.property.link.LinkProperty;
import io.github.mmm.property.ReadableProperty;
import io.github.mmm.value.PropertyPath;

/**
 * {@link DbClause} to add {@link TableOperation}s.
 *
 * @param <E> type of the {@link AlterTableClause#getEntity() entity}.
 */
public interface DbAlterTableClause<E extends EntityBean> extends DbClause {

  /**
   * @return the {@link DbTableReference} from this clause or its statement.
   */
  DbTableReference<E> asTableReference();

  /**
   * @param property the {@link PropertyPath property} to add as column.
   * @return this {@link AlterTableOperationsClause} for fluent API calls.
   */
  default AlterTableOperationsClause<E> addColumn(ReadableProperty<?> property) {

    return addColumn(DbColumnReference.of(property));
  }

  /**
   * @param column the {@link DbColumnReference column} to add.
   * @return this {@link AlterTableOperationsClause} for fluent API calls.
   */
  AlterTableOperationsClause<E> addColumn(DbColumnReference column);

  /**
   * @param property the {@link PropertyPath} to add.
   * @param autoConstraints - {@code true} to automatically add constraints, {@code false} otherwise (to only add the
   *        property as column).
   * @return this {@link AlterTableOperationsClause} for fluent API calls.
   */
  default AlterTableOperationsClause<E> addColumn(ReadableProperty<?> property, boolean autoConstraints) {

    Objects.requireNonNull(property, "properety");
    DbColumnReference column = DbColumnReference.of(property);
    if (autoConstraints) {
      if (property.getMetadata().getValidator().isMandatory()) {
        return addColumnNotNull(column);
      } else if (property instanceof PkProperty) {
        addColumn(column);
        DbPrimaryKeyConstraint constraint = DbPrimaryKeyConstraint.of(asTableReference(), column);
        return addConstraint(constraint);
      } else if (property instanceof FkProperty<?> fk) {
        return addColumnForeignKey(fk);
      } else if (property instanceof LinkProperty<?> link) {
        return addColumnForeignKey(link);
      }
    }
    return addColumn(column);
  }

  /**
   * @param constraint the {@link DbConstraint} to add.
   * @return this {@link AlterTableOperationsClause} for fluent API calls.
   */
  AlterTableOperationsClause<E> addConstraint(DbConstraint constraint);

  /**
   * @param column the {@link DbColumnReference} to add with {@link DbNotNullConstraint}.
   * @return this {@link AlterTableOperationsClause} for fluent API calls.
   */
  default AlterTableOperationsClause<E> addColumnNotNull(DbColumnReference column) {

    addColumn(column);
    DbNotNullConstraint constraint = DbNotNullConstraint.of(asTableReference(), column);
    return addConstraint(constraint);
  }

  /**
   * @param column the {@link DbColumnReference} to add with {@link DbUniqueConstraint}.
   * @return this {@link AlterTableOperationsClause} for fluent API calls.
   */
  default AlterTableOperationsClause<E> addColumnUnique(DbColumnReference column) {

    addColumn(column);
    DbUniqueConstraint constraint = DbUniqueConstraint.of(asTableReference(), column);
    return addConstraint(constraint);
  }

  /**
   * @param property the {@link IdProperty} to add as column with {@link DbForeignKeyConstraint}.
   * @return this {@link AlterTableOperationsClause} for fluent API calls.
   */
  default AlterTableOperationsClause<E> addColumnForeignKey(FkProperty<?> property) {

    DbColumnReference column = DbColumnReference.of(property);
    addColumn(column);
    return addFkConstraint(column, property.get().getEntityClass());
  }

  /**
   * @param property the {@link LinkProperty} to add as column with {@link DbForeignKeyConstraint}.
   * @return this {@link AlterTableOperationsClause} for fluent API calls.
   */
  default AlterTableOperationsClause<E> addColumnForeignKey(LinkProperty<?> property) {

    DbColumnReference column = DbColumnReference.of(property);
    addColumn(column);
    return addFkConstraint(column, property.getEntityClass());
  }

  private AlterTableOperationsClause<E> addFkConstraint(DbColumnReference column,
      Class<? extends EntityBean> entityClass) {

    EntityBean targetEntity = BeanFactory.get().create(entityClass);
    String targetEntityName = null;
    DbTableReference<?> targetTable = DbTableReference.of(targetEntityName, targetEntity);
    return addFkConstraint(column, targetTable);
  }

  private AlterTableOperationsClause<E> addFkConstraint(DbColumnReference column, DbTableReference<?> targetTable) {

    DbColumnReference targetColumn = DbColumnReference.of(targetTable.getEntity().Id());
    DbForeignKeyConstraint constraint = DbForeignKeyConstraint.of(asTableReference(), column, targetTable,
        targetColumn);
    return addConstraint(constraint);
  }

  /**
   * @param property the {@link PropertyPath property} to drop as column.
   * @return the {@link AlterTableOperationsClause} for fluent API calls.
   */
  default AlterTableOperationsClause<E> dropColumn(ReadableProperty<?> property) {

    return dropColumn(DbColumnReference.of(property));
  }

  /**
   * @param column the {@link DbColumnReference column} to drop.
   * @return the {@link AlterTableOperationsClause} for fluent API calls.
   */
  AlterTableOperationsClause<E> dropColumn(DbColumnReference column);

  /**
   * @param constraint the {@link DbConstraint} to drop.
   * @return the {@link AlterTableOperationsClause} for fluent API calls.
   */
  AlterTableOperationsClause<E> dropConstraint(DbConstraint constraint);

  /**
   * @param constraint the {@link DbConstraint#getName() name of the constraint} to drop.
   * @return the {@link AlterTableOperationsClause} for fluent API calls.
   */
  AlterTableOperationsClause<E> dropConstraint(String constraint);

  /**
   * @param column the {@link DbColumnReference column} to rename.
   * @param newColumn the new {@link DbColumnReference column} to rename to.
   * @return the {@link AlterTableOperationsClause} for fluent API calls.
   */
  AlterTableOperationsClause<E> renameColumn(DbColumnReference column, DbColumnReference newColumn);

  /**
   * @param constraint the {@link DbConstraint#getName() name of the constraint} to rename.
   * @param newName the new {@link DbConstraint#getName() constraint name}.
   * @return the {@link AlterTableOperationsClause} for fluent API calls.
   */
  AlterTableOperationsClause<E> renameConstraint(String constraint, String newName);

  /**
   * @param constraint the {@link DbConstraint} to rename.
   * @param newName the new {@link DbConstraint#getName() constraint name}.
   * @return the {@link AlterTableOperationsClause} for fluent API calls.
   */
  AlterTableOperationsClause<E> renameConstraint(DbConstraint constraint, String newName);

}
