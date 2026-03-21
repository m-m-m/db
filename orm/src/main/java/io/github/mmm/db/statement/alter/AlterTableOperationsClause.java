package io.github.mmm.db.statement.alter;

import java.util.ArrayList;
import java.util.List;

import io.github.mmm.db.ddl.column.DbColumnReference;
import io.github.mmm.db.ddl.constraint.DbConstraint;
import io.github.mmm.db.ddl.table.DbTableReference;
import io.github.mmm.db.ddl.table.operation.TableAddColumnOperation;
import io.github.mmm.db.ddl.table.operation.TableAddConstraintOperation;
import io.github.mmm.db.ddl.table.operation.TableDropColumnOperation;
import io.github.mmm.db.ddl.table.operation.TableDropConstraintOperation;
import io.github.mmm.db.ddl.table.operation.TableOperation;
import io.github.mmm.db.ddl.table.operation.TableRenameColumnOperation;
import io.github.mmm.db.ddl.table.operation.TableRenameConstraintOperation;
import io.github.mmm.db.statement.AbstractTypedClause;
import io.github.mmm.db.statement.DbClause;
import io.github.mmm.db.statement.DbMainClause;
import io.github.mmm.entity.bean.EntityBean;

/**
 * A {@link AlterTableOperationsClause}-{@link DbClause} of an SQL {@link AlterTableStatement}.
 *
 * @param <E> type of the {@link io.github.mmm.db.statement.AbstractEntityClause#getEntity() entity}.
 * @since 1.0.0
 */
public class AlterTableOperationsClause<E extends EntityBean>
    extends AbstractTypedClause<E, AlterTableOperationsClause<E>> implements DbMainClause<E>, DbAlterTableClause<E> {

  private final AlterTableStatement<E> statement;

  private final List<TableOperation> operations;

  /**
   * The constructor.
   *
   * @param statement the {@link AlterTableStatement}.
   */
  public AlterTableOperationsClause(AlterTableStatement<E> statement) {

    super();
    this.statement = statement;
    this.operations = new ArrayList<>();
  }

  @Override
  public AlterTableStatement<E> get() {

    return this.statement;
  }

  @Override
  public DbTableReference<E> asTableReference() {

    return this.statement.getAlterTable();
  }

  /**
   * @return the {@link List} of {@link TableOperation}s.
   */
  public List<TableOperation> getOperations() {

    return this.operations;
  }

  @Override
  public AlterTableOperationsClause<E> addColumn(DbColumnReference column) {

    this.operations.add(new TableAddColumnOperation(column));
    return this;
  }

  @Override
  public AlterTableOperationsClause<E> addConstraint(DbConstraint constraint) {

    this.operations.add(new TableAddConstraintOperation(constraint));
    return this;
  }

  @Override
  public AlterTableOperationsClause<E> dropColumn(DbColumnReference column) {

    this.operations.add(new TableDropColumnOperation(column));
    return this;
  }

  @Override
  public AlterTableOperationsClause<E> dropConstraint(DbConstraint constraint) {

    this.operations.add(new TableDropConstraintOperation(constraint));
    return this;
  }

  @Override
  public AlterTableOperationsClause<E> dropConstraint(String constraint) {

    this.operations.add(new TableDropConstraintOperation(constraint));
    return this;
  }

  @Override
  public AlterTableOperationsClause<E> renameColumn(DbColumnReference column, DbColumnReference newColumn) {

    this.operations.add(new TableRenameColumnOperation(column, newColumn));
    return this;
  }

  @Override
  public AlterTableOperationsClause<E> renameConstraint(String constraint, String newName) {

    this.operations.add(new TableRenameConstraintOperation(constraint, newName));
    return this;
  }

  @Override
  public AlterTableOperationsClause<E> renameConstraint(DbConstraint constraint, String newName) {

    this.operations.add(new TableRenameConstraintOperation(constraint, newName));
    return this;
  }

}
