/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.statement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.mmm.base.io.AppendableWriter;
import io.github.mmm.base.sort.SortOrder;
import io.github.mmm.bean.mapping.ClassNameMapper;
import io.github.mmm.db.ddl.column.DbColumn;
import io.github.mmm.db.ddl.column.DbColumnReference;
import io.github.mmm.db.ddl.column.DbColumnReferenceWithSortOrder;
import io.github.mmm.db.ddl.constraint.DbCheckConstraint;
import io.github.mmm.db.ddl.constraint.DbConstraint;
import io.github.mmm.db.ddl.constraint.DbForeignKeyConstraint;
import io.github.mmm.db.ddl.constraint.DbPrimaryKeyConstraint;
import io.github.mmm.db.ddl.constraint.state.DbConstraintDeferrable;
import io.github.mmm.db.ddl.constraint.state.DbConstraintInitially;
import io.github.mmm.db.ddl.constraint.state.DbConstraintRely;
import io.github.mmm.db.ddl.constraint.state.DbConstraintState;
import io.github.mmm.db.ddl.index.DbIndexKindType;
import io.github.mmm.db.ddl.table.DbTableReference;
import io.github.mmm.db.ddl.table.operation.TableColumnOperation;
import io.github.mmm.db.ddl.table.operation.TableConstraintOperation;
import io.github.mmm.db.ddl.table.operation.TableOperation;
import io.github.mmm.db.ddl.table.operation.TableOperationType;
import io.github.mmm.db.ddl.table.operation.TableRenameConstraintOperation;
import io.github.mmm.db.dialect.AbstractDbDialect;
import io.github.mmm.db.dialect.DbContext;
import io.github.mmm.db.mapping.DbBeanMapper;
import io.github.mmm.db.name.DbKeyword;
import io.github.mmm.db.name.DbNamingStrategy;
import io.github.mmm.db.name.DbQualifiedName;
import io.github.mmm.db.orm.Orm;
import io.github.mmm.db.param.CriteriaParametersFactory;
import io.github.mmm.db.result.DbResult;
import io.github.mmm.db.result.DbResultValue;
import io.github.mmm.db.statement.alter.AlterTableClause;
import io.github.mmm.db.statement.alter.AlterTableOperationsClause;
import io.github.mmm.db.statement.create.CreateIndexClause;
import io.github.mmm.db.statement.create.CreateIndexColumnsClause;
import io.github.mmm.db.statement.create.CreateIndexOnClause;
import io.github.mmm.db.statement.create.CreateSequenceClause;
import io.github.mmm.db.statement.create.CreateTableClause;
import io.github.mmm.db.statement.create.CreateTableContentsClause;
import io.github.mmm.db.statement.delete.DeleteClause;
import io.github.mmm.db.statement.impl.CriteriaJqlParametersInline;
import io.github.mmm.db.statement.insert.InsertClause;
import io.github.mmm.db.statement.insert.InsertValuesClause;
import io.github.mmm.db.statement.merge.MergeClause;
import io.github.mmm.db.statement.select.GroupByClause;
import io.github.mmm.db.statement.select.HavingClause;
import io.github.mmm.db.statement.select.OrderByClause;
import io.github.mmm.db.statement.select.SelectClause;
import io.github.mmm.db.statement.select.SelectFromClause;
import io.github.mmm.db.statement.select.SelectSequenceNextValueClause;
import io.github.mmm.db.statement.select.SelectStatement;
import io.github.mmm.db.statement.update.UpdateClause;
import io.github.mmm.db.statement.upsert.UpsertClause;
import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.id.PkMapper;
import io.github.mmm.property.ReadableProperty;
import io.github.mmm.property.criteria.BooleanLiteral;
import io.github.mmm.property.criteria.CriteriaExpression;
import io.github.mmm.property.criteria.CriteriaFormatter;
import io.github.mmm.property.criteria.CriteriaOperator;
import io.github.mmm.property.criteria.CriteriaOrdering;
import io.github.mmm.property.criteria.CriteriaPredicate;
import io.github.mmm.property.criteria.Literal;
import io.github.mmm.property.criteria.PredicateOperator;
import io.github.mmm.property.criteria.ProjectionProperty;
import io.github.mmm.property.criteria.PropertyAssignment;
import io.github.mmm.value.CriteriaObject;
import io.github.mmm.value.PropertyPath;
import io.github.mmm.value.SimplePath;
import io.github.mmm.value.converter.TypeMapper;

/**
 * Formatter to format a {@link DbClause} or {@link DbStatement} to SQL.
 *
 * @since 1.0.0
 */
public class BasicDbStatementFormatter extends CriteriaFormatter implements DbStatementFormatter {

  /** Default value for {@link #getIndentation() indentation}. */
  protected static final String INDENTATION = "  ";

  private static final Logger LOG = LoggerFactory.getLogger(BasicDbStatementFormatter.class);

  private static final CriteriaPredicate PARENT_AND = PredicateOperator.AND.expression(List.of(BooleanLiteral.TRUE));

  /** The {@link AbstractDbDialect} or {@code null} for generic formatter. */
  protected final AbstractDbDialect<?> dialect;

  /** The {@link DbNamingStrategy}. */
  protected final DbNamingStrategy namingStrategy;

  private final StringBuilder sb;

  private CriteriaParametersFactory parametersFactory;

  private DbPlainStatement plainStatement;

  private final String indentation;

  private int indent;

  private int line;

  BasicDbStatementFormatter() {

    this(INDENTATION);
  }

  BasicDbStatementFormatter(String indentation) {

    this(null, CriteriaJqlParametersInline.FACTORY, indentation);
  }

  /**
   * The constructor.
   *
   * @param dialect the owning {@link AbstractDbDialect}.
   * @param parametersFactory the {@link CriteriaParametersFactory}.
   */
  public BasicDbStatementFormatter(AbstractDbDialect<?> dialect, CriteriaParametersFactory parametersFactory) {

    this(dialect, parametersFactory, INDENTATION);
  }

  /**
   * The constructor.
   *
   * @param dialect the owning {@link AbstractDbDialect}.
   * @param parametersFactory the {@link CriteriaParametersFactory}.
   * @param indentation the {@link #getIndentation() indentation}.
   */
  public BasicDbStatementFormatter(AbstractDbDialect<?> dialect, CriteriaParametersFactory parametersFactory,
      String indentation) {

    this(dialect, parametersFactory, indentation, new StringBuilder(32));
  }

  private BasicDbStatementFormatter(AbstractDbDialect<?> dialect, CriteriaParametersFactory parametersFactory,
      String indentation, StringBuilder sb) {

    super(parametersFactory.create((dialect == null) ? null : dialect.getOrm().getTypeMapping()),
        new AppendableWriter(sb));
    this.dialect = dialect;
    if (dialect == null) {
      this.namingStrategy = DbNamingStrategy.of();
    } else {
      this.namingStrategy = dialect.getNamingStrategy();
    }
    this.sb = sb;
    this.parametersFactory = parametersFactory;
    this.indentation = indentation;
  }

  /**
   * @return the indentation (e.g. " " or "") or {@code null} to format as single line.
   */
  protected String getIndentation() {

    return this.indentation;
  }

  /**
   * @return the {@link Orm object/relational mapping}.
   */
  protected Orm getOrm() {

    if (this.dialect == null) {
      return null;
    }
    return this.dialect.getOrm();
  }

  @Override
  public void onPropertyPath(PropertyPath<?> property, int i, CriteriaExpression<?> parent) {

    String columnName = this.namingStrategy.getColumnName(property);
    write(columnName);
  }

  /**
   * Starts a new {@link DbPlainStatement} in case multiple statements are needed.<br>
   * <b>ATTENTION:</b> When producing multiple plain statements, they have to be created in reverse order due to the
   * nature of {@link DbPlainStatement#getNext()}.
   */
  protected void newStatement() {

    this.plainStatement = new DbPlainStatement(this.out.toString(), this.parameters, this.plainStatement);
    this.parameters = this.parametersFactory.create(this.dialect.getOrm().getTypeMapping());
    this.sb.setLength(0);
  }

  /**
   * @param name the unqualified DB name to format.
   */
  protected void formatName(String name) {

    write(name);
  }

  /**
   * @param qName the {@link DbQualifiedName} to format.
   */
  protected void formatQualifiedName(DbQualifiedName qName) {

    String formattedName;
    if (this.dialect == null) {
      formattedName = qName.toString();
    } else {
      formattedName = this.dialect.format(qName);
    }
    write(formattedName);
  }

  @Override
  public DbPlainStatement formatStatement(DbStatement<?> statement, DbContext context) {

    for (DbClause clause : statement.getClauses()) {
      formatClause(clause, context);
    }
    return get();
  }

  /**
   * @param clause the {@link DbClause} to format.
   * @param context the {@link DbContext}.
   */
  public void formatClause(DbClause clause, DbContext context) {

    if (!clause.isOmit()) {
      if (clause instanceof DbStartClause) {
        formatStartClause((DbStartClause) clause, context);
      } else if (clause instanceof DbMainClause) {
        formatMainClause((DbMainClause<?>) clause, context);
      } else {
        formatOtherClause(clause, context);
      }
    }
  }

  /**
   * @param start the {@link DbStartClause} to format.
   * @param context the {@link DbContext}.
   */
  public void formatStartClause(DbStartClause start, DbContext context) {

    if (start instanceof SelectClause) {
      formatSelectClause((SelectClause<?>) start, context);
    } else if (start instanceof UpdateClause update) {
      formatUpdateClause(update, context);
    } else if (start instanceof InsertClause insert) {
      formatInsertClause(insert, context);
    } else if (start instanceof DeleteClause delete) {
      formatDeleteClause(delete, context);
    } else if (start instanceof MergeClause merge) {
      formatMergeClause(merge, context);
    } else if (start instanceof UpsertClause upsert) {
      formatUpsertClause(upsert, context);
    } else if (start instanceof CreateTableClause<?> createTable) {
      formatCreateTableClause(createTable, context);
    } else if (start instanceof CreateIndexClause createIndex) {
      formatCreateIndexClause(createIndex, context);
    } else if (start instanceof CreateSequenceClause seq) {
      formatCreateSequenceClause(seq, context);
    } else if (start instanceof AlterTableClause<?> alterTable) {
      formatAlterTableClause(alterTable, context);
    }
  }

  /**
   * @param clause the {@link DbClause} to format.
   * @param context the {@link DbContext}.
   */
  public void formatMainClause(DbMainClause<?> clause, DbContext context) {

    if (clause instanceof FromClause<?, ?, ?> from) {
      formatFromClause(from, context);
    } else if (clause instanceof WhereClause<?, ?> where) {
      formatWhereClause(where, context);
    } else if (clause instanceof GroupByClause<?> groupBy) {
      formatGroupByClause(groupBy, context);
    } else if (clause instanceof HavingClause<?> having) {
      formatHavingClause(having, context);
    } else if (clause instanceof OrderByClause<?> orderBy) {
      formatOrderByClause(orderBy, context);
    } else if (clause instanceof ValuesClause<?, ?> values) {
      formatValuesClause(values, context);
    } else if (clause instanceof SetClause<?, ?> set) {
      formatSetClause(set, context);
    } else if (clause instanceof CreateTableContentsClause<?> columns) {
      formatCreateTableContentsClause(columns, context);
    } else if (clause instanceof CreateIndexColumnsClause<?> columns) {
      formatCreateIndexColumnsClause(columns, context);
    } else if (clause instanceof AlterTableOperationsClause<?> addColumns) {
      formatAlterTableOperationsClause(addColumns, context);
    }
  }

  /**
   * @param clause the {@link DbClause} that is neither a {@link DbStartClause} nor a {@link DbMainClause}.
   * @param context the {@link DbContext}.
   */
  public void formatOtherClause(DbClause clause, DbContext context) {

    if (clause instanceof IntoClause<?, ?, ?> into) {
      formatIntoClause(into, context);
    } else if (clause instanceof CreateIndexOnClause<?> on) {
      formatCreateIndexOnClause(on, context);
    }
  }

  /**
   * Writes an indentation as needed.
   */
  protected void writeIndent() {

    if (this.indentation == null) {
      return;
    }
    if (this.line > 0) {
      write("\n");
    }
    this.line++;
    if (!this.indentation.isEmpty() && this.indent > 0) {
      for (int i = this.indent; i > 0; i--) {
        write(this.indentation);
      }
    }
  }

  /**
   * @param keyword the {@link DbKeyword database keyword} to {@link #write(String) write}.
   */
  protected void writeDbKeyword(String keyword) {

    write(keyword);
  }

  private void incIndent() {

    this.indent++;
  }

  private void decIndent() {

    assert (this.indent > 0);
    this.indent--;
  }

  /**
   * @param select the {@link SelectClause}-{@link DbClause} to format.
   * @param context the {@link DbContext}.
   */
  public void formatSelectClause(SelectClause<?> select, DbContext context) {

    if (select instanceof SelectSequenceNextValueClause seq) {
      formatSelectSeqNextVal(seq);
      return;
    }
    writeIndent();
    writeDbKeyword(DbKeyword.SELECT);
    write(" ");
    SelectStatement<?> statement = select.get();
    SelectFromClause<?, ?> selectFrom = null;
    if (statement != null) {
      selectFrom = statement.getFrom();
    }
    formatSelections(select, selectFrom);
  }

  /**
   * @param seq the {@link SelectSequenceNextValueClause} to format.
   */
  protected void formatSelectSeqNextVal(SelectSequenceNextValueClause seq) {

    writeIndent();
    writeDbKeyword(DbKeyword.SELECT);
    write(' ');
    writeDbKeyword(DbKeyword.NEXTVAL);
    write("(");
    formatQualifiedName(seq.getSequenceName());
    write(")");
  }

  /**
   * @param delete the {@link DeleteClause} to format.
   * @param context the {@link DbContext}.
   */
  public void formatDeleteClause(DeleteClause delete, DbContext context) {

    writeIndent();
    writeDbKeyword(DbKeyword.DELETE);
    write(' ');
  }

  /**
   * @param insert the {@link InsertClause}-{@link DbClause} to format.
   * @param context the {@link DbContext}.
   */
  public void formatInsertClause(InsertClause insert, DbContext context) {

    writeIndent();
    writeDbKeyword(DbKeyword.INSERT);
    write(' ');
  }

  /**
   * @param update the {@link UpdateClause}-{@link DbClause} to format.
   * @param context the {@link DbContext}.
   */
  public void formatUpdateClause(UpdateClause<?> update, DbContext context) {

    writeIndent();
    writeDbKeyword(DbKeyword.UPDATE);
    write(' ');
    formatEntities(update);
  }

  /**
   * @param merge the {@link MergeClause}-{@link DbClause} to format.
   * @param context the {@link DbContext}.
   */
  public void formatMergeClause(MergeClause merge, DbContext context) {

    writeIndent();
    writeDbKeyword(DbKeyword.MERGE);
    write(' ');
  }

  /**
   * @param upsert the {@link UpsertClause}-{@link DbClause} to format.
   * @param context the {@link DbContext}.
   */
  public void formatUpsertClause(UpsertClause upsert, DbContext context) {

    writeIndent();
    writeDbKeyword(DbKeyword.UPSERT);
    write(' ');
  }

  /**
   * @param createTable the {@link CreateTableClause}-{@link DbClause} to format.
   * @param context the {@link DbContext}.
   */
  public void formatCreateTableClause(CreateTableClause<?> createTable, DbContext context) {

    writeIndent();
    writeDbKeyword(DbKeyword.CREATE_TABLE);
    write(' ');
    formatTableName(createTable);
    write(" (");
  }

  /**
   * @param contents the {@link CreateTableContentsClause}-{@link DbClause} to format.
   * @param context the {@link DbContext}.
   */
  public void formatCreateTableContentsClause(CreateTableContentsClause<?> contents, DbContext context) {

    incIndent();
    List<DbColumnReference> columns = contents.getColumns();
    formatColumns(columns, contents.get().getCreateTable().getEntity(), true, false);
    if (!columns.isEmpty()) {
      write(",");
    }
    String s = "";
    List<DbConstraint> constraints = contents.getConstraints().stream()
        .sorted((p1, p2) -> p1.getName().compareTo(p2.getName())).collect(Collectors.toList());
    for (DbConstraint constraint : constraints) {
      write(s);
      writeIndent();
      formatConstraint(TableOperationType.ADD, constraint);
      s = ",";
    }
    decIndent();
    writeIndent();
    write(')');
  }

  /**
   * @param alterTable the {@link AlterTableClause}-{@link DbClause} to format.
   * @param context the {@link DbContext}.
   */
  public void formatAlterTableClause(AlterTableClause<?> alterTable, DbContext context) {

    writeIndent();
    writeDbKeyword(DbKeyword.ALTER_TABLE);
    write(' ');
    formatEntityName(alterTable);
  }

  /**
   * @param select the {@link SelectClause} with the {@link SelectClause#getSelections() selections}.
   * @param selectFrom the {@link SelectFromClause}.
   */
  protected void formatSelections(SelectClause<?> select, SelectFromClause<?, ?> selectFrom) {

    List<? extends CriteriaObject<?>> selectionCriterias = select.getSelections();
    if (selectionCriterias.isEmpty()) {
      if (!select.isSelectEntity()) {
        LOG.info("Formatting invalid select statement.");
      }
      formatSelectAll(selectFrom);
    } else {
      String s;
      if (select.isSelectResult()) {
        s = "new (";
      } else if (select.isSelectEntity() || select.isSelectSingle()) {
        s = "(";
      } else {
        s = "new " + select.getResultName() + "(";
      }
      int i = 0;
      for (CriteriaObject<?> selection : selectionCriterias) {
        write(s);
        onArg(selection, i++, null);
        s = ", ";
      }
      write(") ");
    }
  }

  /**
   * @return {@code true} if a {@link SelectClause} of all properties should happen via
   *         {@link SelectFromClause#getAlias() alias}, {@code false} otherwise (to simply use {@code *}). The default
   *         is {@code false}. Override to change. E.g. in JPQL you would write "SELECT a FROM Entity a ..." whereas in
   *         plain SQL you would write "SELECT * FROM Entity ..."
   */
  public boolean isSelectAllByAlias() {

    return true;
  }

  /**
   * @return {@code true} to use the {@code AS} keyword before an {@link SelectFromClause#getAlias() alias} (e.g. "FROM
   *         Entity <b>AS</b> e"), {@code false} otherwise.
   */
  public boolean isUseAsBeforeAlias() {

    return false;
  }

  /**
   * @param selectFrom the {@link SelectFromClause} giving access to the {@link SelectFromClause#getAlias() alias}.
   */
  protected void formatSelectAll(SelectFromClause<?, ?> selectFrom) {

    if (selectFrom == null) {
      write("?"); // robustness to avoid toString fails in debugger while object is created.
    } else {
      if (isSelectAllByAlias()) {
        write(selectFrom.getAlias());
        write(" ");
      } else {
        write("* ");
      }
    }
  }

  /**
   * @param from the {@link FromClause}-{@link DbClause} to format.
   * @param context the {@link DbContext}.
   */
  public void formatFromClause(FromClause<?, ?, ?> from, DbContext context) {

    writeDbKeyword(DbKeyword.FROM);
    write(' ');
    if ((from.getName() == null) && (from.getEntity() == null)) {
      assert (from.get() instanceof SelectStatement);
    } else {
      formatEntities(from);
    }
  }

  /**
   * @param entities the {@link AbstractEntitiesClause} to format.
   */
  protected void formatEntities(AbstractEntitiesClause<?, ?, ?> entities) {

    formatEntity(entities);
    for (EntitySubClause<?, ?> entity : entities.getAdditionalEntities()) {
      formatAdditionalEntity(entity);
    }
  }

  /**
   * @param entity the {@link AbstractEntityClause} to format.
   */
  protected void formatEntity(AbstractEntityClause<?, ?, ?> entity) {

    formatEntity(entity, true);
  }

  /**
   * @param entity the {@link AbstractEntityClause} for which to format the {@link AbstractEntityClause#getName() entity
   *        name} or table name and optional {@link #formatAlias(String, DbClause) alias}.
   * @param forceAlias - {@code true} to force an {@link #formatAlias(String, DbClause) alias}, {@code false} otherwise.
   */
  protected void formatEntity(AbstractEntityClause<?, ?, ?> entity, boolean forceAlias) {

    formatEntityName(entity);
    if (forceAlias || entity.hasAlias()) {
      formatAlias(entity.getAlias(), entity);
    }
  }

  /**
   * @param table the {@link DbTableReference} for which to format the {@link DbTableReference#getName() name} name and
   *        optional {@link #formatAlias(String, DbClause) alias}.
   */
  protected void formatTable(AbstractEntityClause<?, ?, ?> table) {

    formatTable(table, true);
  }

  /**
   * @param table the {@link DbTableReference} for which to format the {@link DbTableReference#getName() name} name and
   *        optional {@link #formatAlias(String, DbClause) alias}.
   * @param forceAlias - {@code true} to force an {@link #formatAlias(String, DbClause) alias}, {@code false} otherwise.
   */
  protected void formatTable(AbstractEntityClause<?, ?, ?> table, boolean forceAlias) {

    formatTableName(table);
    if (forceAlias || table.hasAlias()) {
      formatAlias(table.getAlias(), table);
    }
  }

  /**
   * @param table the {@link DbTableReference} for which to format the {@link AbstractEntityClause#getName() entity
   *        name} or table name.
   */
  protected void formatTableName(DbTableReference<?> table) {

    write(this.namingStrategy.getTableName(table));
  }

  /**
   * @param entityClause the {@link AbstractEntityClause} for which to format the {@link AbstractEntityClause#getName()
   *        entity name} or table name.
   */
  protected void formatEntityName(AbstractEntityClause<?, ?, ?> entityClause) {

    write(entityClause.getName());
  }

  /**
   * @param entity the {@link EntitySubClause} to format.
   */
  protected void formatAdditionalEntity(EntitySubClause<?, ?> entity) {

    write(", ");
    formatTable(entity);
  }

  /**
   * @param alias the {@link EntitySubClause#getAlias() alias}.
   * @param clause the owning {@link AbstractEntityClause}.
   */
  protected void formatAlias(String alias, DbClause clause) {

    if (alias != null) {
      write(' ');
      if (isUseAsBeforeAlias()) {
        writeDbKeyword(DbKeyword.AS);
        write(' ');
      }
      write(alias);
    }
  }

  /**
   * @param where the {@link WhereClause}-{@link DbClause} to format.
   * @param context the {@link DbContext}.
   */
  public void formatWhereClause(WhereClause<?, ?> where, DbContext context) {

    write(' ');
    writeDbKeyword(DbKeyword.WHERE);
    write(' ');
    onPredicateClause(where);
  }

  private void onPredicateClause(PredicateClause<?, ?> clause) {

    boolean and = false;
    for (CriteriaPredicate predicate : clause.getPredicates()) {
      if (and) {
        write(' ');
        writeDbKeyword(DbKeyword.AND);
        write(' ');
      } else {
        and = true;
      }
      onExpression(predicate, PARENT_AND);
    }
  }

  /**
   * @param orderBy the {@link OrderByClause}-{@link DbClause} to format.
   * @param context the {@link DbContext}.
   */
  public void formatOrderByClause(OrderByClause<?> orderBy, DbContext context) {

    write(' ');
    writeDbKeyword(DbKeyword.ORDER_BY);
    write(' ');
    String s = "";
    for (CriteriaOrdering ordering : orderBy.getOrderings()) {
      write(s);
      onOrdering(ordering);
      s = ", ";
    }
  }

  /**
   * @param groupBy the {@link GroupByClause}-{@link DbClause} to format.
   * @param context the {@link DbContext}.
   */
  public void formatGroupByClause(GroupByClause<?> groupBy, DbContext context) {

    write(' ');
    writeDbKeyword(DbKeyword.GROUP_BY);
    write(' ');
    String s = "";
    for (PropertyPath<?> property : groupBy.getProperties()) {
      write(s);
      onPropertyPath(property, 0, null);
      s = ", ";
    }
  }

  /**
   * @param having the {@link HavingClause}-{@link DbClause} to format.
   * @param context the {@link DbContext}.
   */
  public void formatHavingClause(HavingClause<?> having, DbContext context) {

    write(' ');
    writeDbKeyword(DbKeyword.HAVING);
    write(' ');
    String s = "";
    for (CriteriaPredicate predicate : having.getPredicates()) {
      write(s);
      onExpression(predicate);
      s = ", ";
    }
  }

  /**
   * @param into the {@link IntoClause} to format.
   * @param context the {@link DbContext}.
   */
  public void formatIntoClause(IntoClause<?, ?, ?> into, DbContext context) {

    writeDbKeyword(DbKeyword.INTO);
    write(' ');
    formatEntity(into, false);
  }

  /**
   * @param values the {@link ValuesClause} to format.
   * @param context the {@link DbContext}.
   */
  public void formatValuesClause(ValuesClause<?, ?> values, DbContext context) {

    boolean isInto = values instanceof InsertValuesClause;
    List<PropertyAssignment<?>> assignments = values.getAssignments();
    List<CriteriaObject<?>> args = null;
    if (isInto) {
      args = new ArrayList<>(assignments.size());
      write('(');
    } else {
      write(' ');
      writeDbKeyword(DbKeyword.VALUES);
      write(" (");
    }
    String s = "";
    int i = 0;
    for (PropertyAssignment<?> assignment : assignments) {
      i = formatAssignmentRecursive(assignment.getProperty(), assignment.getValue(), i, false, args, values);
    }
    write(")");
    if (args != null) { // isInto
      write(' ');
      writeDbKeyword(DbKeyword.VALUES);
      write(" (");
      s = "";
      ;
      for (int argIndex = 0; argIndex < i; argIndex++) {
        write(s);
        onArg(args.get(argIndex), argIndex, null);
        s = ", ";
      }
      write(')');
    }
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  private CriteriaObject<?> mapValue(CriteriaObject<?> value, TypeMapper typeMapper) {

    if (value instanceof Literal<?> literal) {
      Object sourceValue = literal.get();
      if ((sourceValue instanceof Number) && (typeMapper instanceof PkMapper)) {
        return value;
      }
      Object mapped = typeMapper.toTarget(sourceValue);
      return Literal.of(mapped);
    } else if (value instanceof PropertyPath<?> property) {
      return mapProperty(property, typeMapper);
    } else if (value instanceof ProjectionProperty<?> projection) {
      return new ProjectionProperty(mapValue(projection.getSelection(), typeMapper),
          mapProperty(projection.getProperty(), typeMapper));
    } else {
      // currently unsupported...
      return value;
    }
  }

  @SuppressWarnings("rawtypes")
  private PropertyPath<?> mapProperty(PropertyPath<?> property, TypeMapper typeMapper) {

    String name = this.dialect.getNamingStrategy().getColumnName(property, typeMapper);
    return new SimplePath(property.parentPath(), name);
  }

  @SuppressWarnings("rawtypes")
  private int formatAssignmentRecursive(PropertyPath<?> property, CriteriaObject<?> value, int index, boolean assign,
      List<CriteriaObject<?>> args, DbClause clause) {

    if ((this.dialect != null) && (property instanceof ReadableProperty<?> p)) {
      TypeMapper typeMapper = p.getTypeMapper();
      if (typeMapper != null) {
        do {
          String name = this.dialect.getNamingStrategy().getColumnName(property, typeMapper);
          index = formatAssignmentRecursive(new SimplePath(property.parentPath(), name), mapValue(value, typeMapper),
              index, assign, args, clause);
          typeMapper = typeMapper.next();
        } while (typeMapper != null);
        return index;
      }
    }
    formatAssignment(property, value, index, assign, args, clause);
    return index + 1;
  }

  /**
   *
   * @param property the {@link PropertyAssignment#getProperty() property to assign}.
   * @param value the {@link PropertyAssignment#getValue() value to assign}.
   * @param index the index of the assignment in the potential list of assignments (0 for first).
   * @param assign - {@code true} for a regular assignment, {@code false} to only format the {@code value} and ignore
   *        the {@code property}.
   * @param args the optional {@link List} where to {@link List#add(Object) add} the {@code value} or {@code null}.
   * @param clause the owning {@link DbClause}.
   */
  protected void formatAssignment(PropertyPath<?> property, CriteriaObject<?> value, int index, boolean assign,
      List<CriteriaObject<?>> args, DbClause clause) {

    if (index > 0) {
      write(", ");
    }
    if (assign) {
      onPropertyPath(property, index, null);
      write(" = ");
      assert (args == null);
    }
    if (args == null) {
      onArg(value, index, null);
    } else {
      onPropertyPath(property, index, null);
      args.add(value);
    }
  }

  /**
   * @param set the {@link SetClause} to format.
   * @param context the {@link DbContext}.
   */
  public void formatSetClause(SetClause<?, ?> set, DbContext context) {

    write(' ');
    writeDbKeyword(DbKeyword.SET);
    write(' ');
    int i = 0;
    for (PropertyAssignment<?> assignment : set.getAssignments()) {
      i = formatAssignmentRecursive(assignment.getProperty(), assignment.getValue(), i, true, null, set);
    }
  }

  /**
   * @param columns the {@link Iterable} with the {@link DbColumnReference columns} to format.
   * @param entity the {@link EntityBean} containing the columns.
   * @param withDeclaration - {@code true} to write column declarations, {@code false} otherwise (to omit declarations).
   * @param skipDecompose - {@code true} to skip decomposing columns via ORM (e.g. for PK-Constraint), {@code false}
   *        otherwise.
   */
  protected void formatColumns(Iterable<? extends DbColumnReference> columns, EntityBean entity,
      boolean withDeclaration, boolean skipDecompose) {

    String s = "";
    if (skipDecompose || (this.dialect == null)) {
      for (DbColumnReference column : columns) {
        write(s);
        formatColumn(column, withDeclaration);
        s = ",";
      }
    } else {
      List<ReadableProperty<?>> properties = new ArrayList<>();
      for (DbColumnReference column : columns) {
        properties.add(column.getProperty());
      }
      DbBeanMapper<EntityBean> mapping = this.dialect.getOrm().createBeanMapper(entity, properties);
      DbResult dbResult = mapping.java2db(entity);
      for (DbResultValue<?> dbValue : dbResult) {
        String columnName = dbValue.getName();
        write(s);
        if (withDeclaration) {
          writeIndent();
        }
        formatName(columnName);
        if (withDeclaration) {
          String declaration = dbValue.getDeclaration();
          write(" ");
          write(declaration);
        }
        s = ",";
      }
    }
  }

  private void formatColumn(DbColumnReference columnRef, boolean withDeclaration) {

    formatColumn(columnRef, withDeclaration, withDeclaration);
  }

  private void formatColumn(DbColumnReference columnRef, boolean withDeclaration, boolean writeIndent) {

    if (writeIndent) {
      writeIndent();
    }
    String columnName = this.namingStrategy.getColumnName(columnRef);
    formatName(columnName);
    if (withDeclaration) {
      String declaration = null;
      if (columnRef instanceof DbColumn column) {
        declaration = column.getType().getDeclaration();
      }
      formatDeclaration(columnRef, declaration);
    } else if (columnRef instanceof DbColumnReferenceWithSortOrder columnWithSort) {
      formatSortOrder(columnWithSort.getSortOrder());
    }
  }

  /**
   * @param sortOrder the {@link SortOrder} to format.
   */
  protected void formatSortOrder(SortOrder sortOrder) {

    if (sortOrder == null) {
      return;
    }
    write(' ');
    String keyword = switch (sortOrder) {
      case ASC -> DbKeyword.ASC;
      case DESC -> DbKeyword.DESC;
    };
    writeDbKeyword(keyword);
  }

  private void formatDeclaration(DbColumnReference column, String declaration) {

    if (declaration == null) {
      Class<?> valueClass = column.getProperty().getValueClass();
      declaration = ClassNameMapper.get().getNameOrQualified(valueClass);
    }
    write(' ');
    write(declaration);
  }

  /**
   * @param constraint the {@link DbConstraint} to format.
   */
  private void formatConstraint(TableOperationType type, DbConstraint constraint) {

    formatConstraintKeywordWithName(type, constraint);
    write(' ');
    formatConstraintType(constraint);
    write(" (");
    if (constraint instanceof DbCheckConstraint ck) {
      CriteriaPredicate predicate = ck.getPredicate();
      formatCriteriaExpression(predicate);
    } else {
      DbTableReference<?> sourceTable = constraint.getSourceTable();
      List<DbColumnReference> sourceColumns = constraint.getSourceColumns();
      constraint.getTargetColumns();
      EntityBean entity = sourceTable.getEntity();
      boolean skipDecompose = (constraint instanceof DbPrimaryKeyConstraint)
          || (constraint instanceof DbForeignKeyConstraint);
      formatColumns(sourceColumns, entity, false, skipDecompose);
    }
    write(')');
    if (constraint instanceof DbForeignKeyConstraint fk) {
      formatConstraintFkReference(fk);
    }
    formatConstraintState(constraint.getState());
  }

  private void formatConstraintType(DbConstraint constraint) {

    write(constraint.getType());
  }

  /**
   * @param fkConstraint the {@link DbForeignKeyConstraint} for which the references shall be formatted.
   */
  protected void formatConstraintFkReference(DbForeignKeyConstraint fkConstraint) {

    write(' ');
    writeDbKeyword(DbKeyword.REFERENCES);
    write(' ');
    DbTableReference<?> table = fkConstraint.getTargetTable();
    formatTableName(table);
    write('(');
    formatColumns(fkConstraint.getTargetColumns(), table.getEntity(), false, true);
    write(')');
  }

  /**
   * Writes "CONSTRAINT «constraint_name»". May be overridden as needed for specific dialect (e.g. for MySQL flaws).
   *
   * @param type the {@link TableOperationType}.
   * @param constraint the {@link DbConstraint}.
   */
  protected void formatConstraintKeywordWithName(TableOperationType type, DbConstraint constraint) {

    writeDbKeyword(DbKeyword.CONSTRAINT);
    write(' ');
    String constraintName = this.namingStrategy.getConstraintName(constraint);
    formatName(constraintName);
  }

  /**
   * @param state the {@link DbConstraintState}.
   * @see #formatConstraintStateInitially(DbConstraintInitially)
   * @see #formatConstraintStateDeferrable(DbConstraintDeferrable)
   * @see #formatConstraintStateRely(DbConstraintRely)
   */
  protected void formatConstraintState(DbConstraintState state) {

    formatConstraintStateInitially(state.getInitially());
    formatConstraintStateDeferrable(state.getDeferrable());
    formatConstraintStateRely(state.getRely());
  }

  /**
   * @param rely the {@link DbConstraintRely} to format.
   */
  protected void formatConstraintStateRely(DbConstraintRely rely) {

    String keyword = rely.toString();
    if (keyword.isEmpty()) {
      return;
    }
    write(' ');
    writeDbKeyword(keyword);
  }

  /**
   * @param initially the {@link DbConstraintInitially} to format.
   */
  protected void formatConstraintStateInitially(DbConstraintInitially initially) {

    String keyword = initially.toString();
    if (keyword.isEmpty()) {
      return;
    }
    write(' ');
    writeDbKeyword(keyword);
  }

  /**
   * @param deferrable the {@link DbConstraintDeferrable} to format.
   */
  protected void formatConstraintStateDeferrable(DbConstraintDeferrable deferrable) {

    String keyword = deferrable.toString();
    if (keyword.isEmpty()) {
      return;
    }
    write(' ');
    writeDbKeyword(keyword);
  }

  /**
   * @param predicate the {@link CriteriaPredicate} to format (for a check constraint).
   */
  protected void formatCriteriaExpression(CriteriaExpression<?> predicate) {

    CriteriaOperator operator = predicate.getOperator();
    if (PredicateOperator.isNullBased(operator)) {
      assert (predicate.getArgCount() == 1);
      formatCriteriaObject(predicate.getFirstArg());
      write(' ');
      formatOperator(operator);
    } else if (operator.isInfix()) {
      // TODO add parenthesis as needed according to priorities...
      boolean first = true;
      for (CriteriaObject<?> arg : predicate.getArgs()) {
        if (first) {
          first = false;
        } else {
          if (operator.isConjunction()) {
            write(' ');
            formatOperator(operator);
            write(' ');
          } else {
            formatOperator(operator);
          }
        }
        formatCriteriaObject(arg);
      }
    } else if (operator.isUnary()) {
      assert (predicate.getArgCount() == 1);
      formatOperator(operator);
      write('(');
      formatCriteriaObject(predicate.getFirstArg());
      write(')');
    }
  }

  /**
   * @param operator the {@link PredicateOperator} to format.
   */
  protected void formatOperator(CriteriaOperator operator) {

    write(operator.getSyntax());
  }

  /**
   * @param object the {@link CriteriaObject} to format.
   */
  protected void formatCriteriaObject(CriteriaObject<?> object) {

    if (object instanceof ReadableProperty<?> property) {
      formatColumn(property);
    } else if (object instanceof CriteriaExpression<?> expression) {
      formatCriteriaExpression(expression);
    } else if (object instanceof Literal<?> literal) {
      formatLiteral(literal);
    } else if (object instanceof PropertyPath<?> path) {
      formatPath(path);
    }
  }

  /**
   * @param path the {@link PropertyPath} to format.
   */
  private void formatPath(PropertyPath<?> path) {

    write(path.path());
  }

  /**
   * @param literal the {@link Literal} to format.
   */
  private void formatLiteral(Literal<?> literal) {

    write(literal.toString());
  }

  /**
   * @param property the {@link ReadableProperty} to format as column.
   */
  protected void formatColumn(ReadableProperty<?> property) {

    // TODO not entirely correct, may be multiple columns...
    formatColumn(DbColumnReference.of(property), false);
  }

  /**
   * @param createIndex the {@link CreateIndexClause}-{@link DbClause} to format.
   * @param context the {@link DbContext}.
   */
  public void formatCreateIndexClause(CreateIndexClause createIndex, DbContext context) {

    writeIndent();
    writeDbKeyword(DbKeyword.CREATE);
    write(' ');
    DbIndexKindType kind = createIndex.getKind();
    if (kind.isUnique()) {
      writeDbKeyword(DbKeyword.UNIQUE);
      write(' ');
    }
    if (kind.isClustered()) {
      writeDbKeyword(DbKeyword.CLUSTERED);
      write(' ');
    }
    writeDbKeyword(DbKeyword.INDEX);
    write(' ');
    String name = this.namingStrategy.getIndexName(createIndex.get().asIndex());
    formatName(name);
  }

  /**
   * @param on the {@link CreateIndexOnClause} to format.
   * @param context the {@link DbContext}.
   */
  public void formatCreateIndexOnClause(CreateIndexOnClause<?> on, DbContext context) {

    write(' ');
    writeDbKeyword(DbKeyword.ON);
    write(' ');
    formatTable(on, false);
  }

  /**
   * @param columns the {@link CreateIndexColumnsClause} to format.
   * @param context the {@link DbContext}.
   */
  public void formatCreateIndexColumnsClause(CreateIndexColumnsClause<?> columns, DbContext context) {

    write('(');
    formatColumns(columns.getColumns(), columns.get().getOn().getEntity(), false, false);
    write(')');
  }

  /**
   * @param seq the {@link CreateSequenceClause} to format.
   * @param context the {@link DbContext}.
   */
  protected void formatCreateSequenceClause(CreateSequenceClause seq, DbContext context) {

    writeIndent();
    writeDbKeyword(DbKeyword.CREATE_SEQUENCE);
    write(' ');
    formatQualifiedName(seq.getSequenceName());
    formatCreateSequenceAttribute(DbKeyword.INCREMENT_BY, seq.getIncrementBy());
    formatCreateSequenceAttribute(DbKeyword.START_WITH, seq.getStartWith());
    formatCreateSequenceAttribute(DbKeyword.MINVALUE, seq.getMinValue());
    formatCreateSequenceAttribute(DbKeyword.MAXVALUE, seq.getMaxValue());
    Boolean cycle = seq.getCycle();
    if (cycle != null) {
      formatCreateSequenceCycle(cycle.booleanValue());
    }
  }

  /**
   * @param keyword the {@link DbKeyword} to {@link #writeDbKeyword(String) write}.
   * @param value the {@link Number} value to format.
   */
  protected void formatCreateSequenceAttribute(String keyword, Number value) {

    if (value == null) {
      return;
    }
    write(' ');
    writeDbKeyword(keyword);
    write(' ');
    write(value.toString());
  }

  /**
   * Formats the explicit cycle attribute of a sequence.
   *
   * @param cycle {@code true} for cycle, {@code false} otherwise (nocycle).
   */
  protected void formatCreateSequenceCycle(boolean cycle) {

    if (cycle) {
      write(" CYCLE");
    } else {
      write(" NOCYCLE");
    }
  }

  /**
   * @param operations the {@link AlterTableOperationsClause} to format.
   * @param context the {@link DbContext}.
   */
  public void formatAlterTableOperationsClause(AlterTableOperationsClause<?> operations, DbContext context) {

    String s = "\n";
    for (TableOperation operation : operations.getOperations()) {
      write(s);
      formatAlterTableOperation(operation);
      s = ",\n";
    }
  }

  /**
   * @param operation the {@link TableOperation} to format.
   */
  public void formatAlterTableOperation(TableOperation operation) {

    TableOperationType type = operation.getType();
    formatAlterTableOperationType(type);
    write(' ');
    if (operation instanceof TableColumnOperation columnOp) {
      formatAlterTableColumn(type);
      DbColumnReference column = columnOp.getColumn();
      formatColumn(column, (type == TableOperationType.ADD) || (type == TableOperationType.MODIFY), false);
    } else if (operation instanceof TableConstraintOperation constraintOp) {
      DbConstraint constraint = constraintOp.getConstraint();
      if (type == TableOperationType.ADD) {
        formatConstraint(type, constraint);
      } else {
        String name = this.namingStrategy.getConstraintName(constraint);
        formatName(name);
        if (operation instanceof TableRenameConstraintOperation rename) {
          write(' ');
          writeDbKeyword(DbKeyword.TO);
          write(' ');
          formatName(rename.getNewName());
        } else {
          assert (type == TableOperationType.DROP);
        }
      }
    }
  }

  /**
   * @param type the {@link TableOperationType} to format. May be overridden as needed for specific dialect (e.g. for MS
   *        SQL-Server flaws).
   */
  protected void formatAlterTableOperationType(TableOperationType type) {

    write(type.name());
  }

  /**
   * Write "COLUMN" keyword as needed for ALTER TABLE statement of the given {@code type}.
   *
   * @param type the {@link TableOperationType}.
   */
  protected void formatAlterTableColumn(TableOperationType type) {

    if ((type != TableOperationType.ADD) && (type != TableOperationType.MODIFY)) {
      writeDbKeyword(DbKeyword.COLUMN);
      write(' ');
    }
  }

  /**
   * @return the {@link #formatStatement(DbStatement, DbContext) formatted statement} as {@link String} (e.g. SQL).
   */
  public DbPlainStatement get() {

    return new DbPlainStatement(this.out.toString(), this.parameters, this.plainStatement);
  }

  @Override
  public String toString() {

    return get().toString();
  }

}
