/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.statement.create;

import java.util.Objects;

import io.github.mmm.bean.BeanFactory;
import io.github.mmm.db.ddl.column.DbColumnReference;
import io.github.mmm.db.ddl.constraint.DbConstraint;
import io.github.mmm.db.ddl.constraint.DbForeignKeyConstraint;
import io.github.mmm.db.ddl.constraint.DbNotNullConstraint;
import io.github.mmm.db.ddl.constraint.DbPrimaryKeyConstraint;
import io.github.mmm.db.ddl.constraint.DbUniqueConstraint;
import io.github.mmm.db.ddl.table.DbTableReference;
import io.github.mmm.db.statement.DbClause;
import io.github.mmm.db.statement.alter.AlterTableClause;
import io.github.mmm.db.statement.alter.AlterTableOperationsClause;
import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.property.id.FkProperty;
import io.github.mmm.entity.property.id.IdProperty;
import io.github.mmm.entity.property.id.PkProperty;
import io.github.mmm.entity.property.link.LinkProperty;
import io.github.mmm.property.ReadableProperty;
import io.github.mmm.value.PropertyPath;

/**
 * Interface for a fragment or clause to add {@link DbColumnReference columns}.
 *
 * @param <E> type of the {@link AlterTableClause#getEntity() entity}.
 */
public interface DbCreateTableClause<E extends EntityBean> extends DbClause {

  /**
   * @return the {@link DbTableReference} from this clause or its statement.
   */
  DbTableReference<E> asTableReference();

  /**
   * @param property the {@link PropertyPath property} to add as column.
   * @return this {@link AlterTableOperationsClause} for fluent API calls.
   */
  default CreateTableContentsClause<E> column(ReadableProperty<?> property) {

    return column(DbColumnReference.of(property));
  }

  /**
   * @param column the {@link DbColumnReference column} to add.
   * @return this {@link AlterTableOperationsClause} for fluent API calls.
   */
  CreateTableContentsClause<E> column(DbColumnReference column);

  /**
   * @param property the {@link PropertyPath} to add.
   * @param autoConstraints - {@code true} to automatically add constraints, {@code false} otherwise (to only add the
   *        property as column).
   * @return this {@link AlterTableOperationsClause} for fluent API calls.
   */
  default CreateTableContentsClause<E> column(ReadableProperty<?> property, boolean autoConstraints) {

    Objects.requireNonNull(property, "properety");
    DbColumnReference column = DbColumnReference.of(property);
    if (autoConstraints) {
      if (property.getMetadata().getValidator().isMandatory()) {
        return columnNotNull(column);
      } else if (property instanceof PkProperty) {
        column(column);
        DbPrimaryKeyConstraint constraint = DbPrimaryKeyConstraint.of(asTableReference(), column);
        return constraint(constraint);
      } else if (property instanceof FkProperty<?> fk) {
        return columnForeignKey(fk);
      } else if (property instanceof LinkProperty<?> link) {
        return columnForeignKey(link);
      }
    }
    return column(column);
  }

  /**
   * @param constraint the {@link DbConstraint} to add.
   * @return this {@link AlterTableOperationsClause} for fluent API calls.
   */
  CreateTableContentsClause<E> constraint(DbConstraint constraint);

  /**
   * @param column the {@link DbColumnReference column} to add with {@link DbNotNullConstraint}.
   * @return this {@link AlterTableOperationsClause} for fluent API calls.
   */
  default CreateTableContentsClause<E> columnNotNull(DbColumnReference column) {

    column(column);
    DbNotNullConstraint constraint = DbNotNullConstraint.of(asTableReference(), column);
    return constraint(constraint);
  }

  /**
   * @param column the {@link DbColumnReference column} to add with {@link DbUniqueConstraint}.
   * @return this {@link AlterTableOperationsClause} for fluent API calls.
   */
  default CreateTableContentsClause<E> columnUnique(DbColumnReference column) {

    column(column);
    DbUniqueConstraint constraint = DbUniqueConstraint.of(asTableReference(), column);
    return constraint(constraint);
  }

  /**
   * @param property the {@link IdProperty} to add as column with {@link DbForeignKeyConstraint}.
   * @return this {@link AlterTableOperationsClause} for fluent API calls.
   */
  default CreateTableContentsClause<E> columnForeignKey(FkProperty<?> property) {

    DbColumnReference column = DbColumnReference.of(property);
    column(column);
    return constraintFk(column, property.get().getEntityClass());
  }

  /**
   * @param property the {@link LinkProperty} to add as column with {@link DbForeignKeyConstraint}.
   * @return this {@link AlterTableOperationsClause} for fluent API calls.
   */
  default CreateTableContentsClause<E> columnForeignKey(LinkProperty<?> property) {

    DbColumnReference column = DbColumnReference.of(property);
    column(column);
    return constraintFk(column, property.getEntityClass());
  }

  private CreateTableContentsClause<E> constraintFk(DbColumnReference column, Class<? extends EntityBean> entityClass) {

    EntityBean targetEntity = BeanFactory.get().create(entityClass);
    String targetEntityName = null;
    DbTableReference<?> targetTable = DbTableReference.of(targetEntityName, targetEntity);
    return constraintFk(column, targetTable);
  }

  private CreateTableContentsClause<E> constraintFk(DbColumnReference column, DbTableReference<?> targetTable) {

    EntityBean targetEntity = targetTable.getEntity();
    DbColumnReference targetColumn = DbColumnReference.of(targetEntity.Id());
    DbForeignKeyConstraint constraint = DbForeignKeyConstraint.of(asTableReference(), column, targetTable,
        targetColumn);
    return constraint(constraint);
  }

}
