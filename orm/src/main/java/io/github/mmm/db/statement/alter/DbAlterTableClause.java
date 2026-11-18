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

/// [DbClause] to add [TableOperation]s.
///
/// @param <E> type of the [entity][AlterTableClause#getEntity()].
public interface DbAlterTableClause<E extends EntityBean> extends DbClause {

  /// @return the [DbTableReference] from this clause or its statement.
  DbTableReference<E> asTableReference();

  /// @param property the [property][PropertyPath] to add as column.
  /// @return this [AlterTableOperationsClause] for fluent API calls.
  default AlterTableOperationsClause<E> addColumn(ReadableProperty<?> property) {

    return addColumn(DbColumnReference.of(property));
  }

  /// @param column the [column][DbColumnReference] to add.
  /// @return this [AlterTableOperationsClause] for fluent API calls.
  AlterTableOperationsClause<E> addColumn(DbColumnReference column);

  /// @param property the [PropertyPath] to add.
  /// @param autoConstraints - `true` to automatically add constraints, `false` otherwise (to only add the
  ///        property as column).
  /// @return this [AlterTableOperationsClause] for fluent API calls.
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

  /// @param constraint the [DbConstraint] to add.
  /// @return this [AlterTableOperationsClause] for fluent API calls.
  AlterTableOperationsClause<E> addConstraint(DbConstraint constraint);

  /// @param column the [DbColumnReference] to add with [DbNotNullConstraint].
  /// @return this [AlterTableOperationsClause] for fluent API calls.
  default AlterTableOperationsClause<E> addColumnNotNull(DbColumnReference column) {

    addColumn(column);
    DbNotNullConstraint constraint = DbNotNullConstraint.of(asTableReference(), column);
    return addConstraint(constraint);
  }

  /// @param column the [DbColumnReference] to add with [DbUniqueConstraint].
  /// @return this [AlterTableOperationsClause] for fluent API calls.
  default AlterTableOperationsClause<E> addColumnUnique(DbColumnReference column) {

    addColumn(column);
    DbUniqueConstraint constraint = DbUniqueConstraint.of(asTableReference(), column);
    return addConstraint(constraint);
  }

  /// @param property the [IdProperty] to add as column with [DbForeignKeyConstraint].
  /// @return this [AlterTableOperationsClause] for fluent API calls.
  default AlterTableOperationsClause<E> addColumnForeignKey(FkProperty<?> property) {

    DbColumnReference column = DbColumnReference.of(property);
    addColumn(column);
    return addFkConstraint(column, property.get().getEntityClass());
  }

  /// @param property the [LinkProperty] to add as column with [DbForeignKeyConstraint].
  /// @return this [AlterTableOperationsClause] for fluent API calls.
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

  /// @param property the [property][PropertyPath] to drop as column.
  /// @return the [AlterTableOperationsClause] for fluent API calls.
  default AlterTableOperationsClause<E> dropColumn(ReadableProperty<?> property) {

    return dropColumn(DbColumnReference.of(property));
  }

  /// @param column the [column][DbColumnReference] to drop.
  /// @return the [AlterTableOperationsClause] for fluent API calls.
  AlterTableOperationsClause<E> dropColumn(DbColumnReference column);

  /// @param constraint the [DbConstraint] to drop.
  /// @return the [AlterTableOperationsClause] for fluent API calls.
  AlterTableOperationsClause<E> dropConstraint(DbConstraint constraint);

  /// @param constraint the [name of the constraint][DbConstraint#getName()] to drop.
  /// @return the [AlterTableOperationsClause] for fluent API calls.
  AlterTableOperationsClause<E> dropConstraint(String constraint);

  /// @param column the [column][DbColumnReference] to rename.
  /// @param newColumn the new [column][DbColumnReference] to rename to.
  /// @return the [AlterTableOperationsClause] for fluent API calls.
  AlterTableOperationsClause<E> renameColumn(DbColumnReference column, DbColumnReference newColumn);

  /// @param constraint the [name of the constraint][DbConstraint#getName()] to rename.
  /// @param newName the new [constraint name][DbConstraint#getName()].
  /// @return the [AlterTableOperationsClause] for fluent API calls.
  AlterTableOperationsClause<E> renameConstraint(String constraint, String newName);

  /// @param constraint the [DbConstraint] to rename.
  /// @param newName the new [constraint name][DbConstraint#getName()].
  /// @return the [AlterTableOperationsClause] for fluent API calls.
  AlterTableOperationsClause<E> renameConstraint(DbConstraint constraint, String newName);

}
