/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.statement;

import static io.github.mmm.base.filter.CharFilter.NEWLINE_OR_SPACE;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Consumer;

import io.github.mmm.base.filter.CharFilter;
import io.github.mmm.base.sort.SortOrder;
import io.github.mmm.bean.BeanFactory;
import io.github.mmm.bean.ReadableBean;
import io.github.mmm.bean.WritableBean;
import io.github.mmm.bean.mapping.ClassNameMapper;
import io.github.mmm.db.ddl.column.DbColumn;
import io.github.mmm.db.ddl.column.DbColumnReference;
import io.github.mmm.db.ddl.constraint.DbCheckConstraint;
import io.github.mmm.db.ddl.constraint.DbConstraint;
import io.github.mmm.db.ddl.constraint.DbForeignKeyConstraint;
import io.github.mmm.db.ddl.constraint.DbNotNullConstraint;
import io.github.mmm.db.ddl.constraint.DbPrimaryKeyConstraint;
import io.github.mmm.db.ddl.constraint.DbUniqueConstraint;
import io.github.mmm.db.ddl.constraint.state.DbConstraintDeferrable;
import io.github.mmm.db.ddl.constraint.state.DbConstraintInitially;
import io.github.mmm.db.ddl.constraint.state.DbConstraintRely;
import io.github.mmm.db.ddl.constraint.state.DbConstraintState;
import io.github.mmm.db.ddl.index.DbIndexKindType;
import io.github.mmm.db.ddl.index.DbIndexType;
import io.github.mmm.db.ddl.table.DbTableReference;
import io.github.mmm.db.ddl.table.operation.TableOperationKind;
import io.github.mmm.db.ddl.table.operation.TableOperationType;
import io.github.mmm.db.name.DbKeyword;
import io.github.mmm.db.name.DbQualifiedName;
import io.github.mmm.db.statement.alter.AlterTableClause;
import io.github.mmm.db.statement.alter.AlterTableOperationsClause;
import io.github.mmm.db.statement.alter.AlterTableStatement;
import io.github.mmm.db.statement.create.CreateIndexClause;
import io.github.mmm.db.statement.create.CreateIndexColumnsClause;
import io.github.mmm.db.statement.create.CreateIndexOnClause;
import io.github.mmm.db.statement.create.CreateIndexStatement;
import io.github.mmm.db.statement.create.CreateSequenceClause;
import io.github.mmm.db.statement.create.CreateSequenceStatement;
import io.github.mmm.db.statement.create.CreateStatement;
import io.github.mmm.db.statement.create.CreateTableClause;
import io.github.mmm.db.statement.create.CreateTableContentsClause;
import io.github.mmm.db.statement.create.CreateTableStatement;
import io.github.mmm.db.statement.delete.DeleteClause;
import io.github.mmm.db.statement.delete.DeleteFromClause;
import io.github.mmm.db.statement.delete.DeleteStatement;
import io.github.mmm.db.statement.insert.InsertClause;
import io.github.mmm.db.statement.insert.InsertIntoClause;
import io.github.mmm.db.statement.insert.InsertStatement;
import io.github.mmm.db.statement.insert.InsertValuesClause;
import io.github.mmm.db.statement.merge.MergeClause;
import io.github.mmm.db.statement.merge.MergeIntoClause;
import io.github.mmm.db.statement.merge.MergeStatement;
import io.github.mmm.db.statement.select.GroupByClause;
import io.github.mmm.db.statement.select.HavingClause;
import io.github.mmm.db.statement.select.OrderByClause;
import io.github.mmm.db.statement.select.SelectClause;
import io.github.mmm.db.statement.select.SelectEntityClause;
import io.github.mmm.db.statement.select.SelectFromClause;
import io.github.mmm.db.statement.select.SelectProjectionClause;
import io.github.mmm.db.statement.select.SelectResultClause;
import io.github.mmm.db.statement.select.SelectSequenceNextValueClause;
import io.github.mmm.db.statement.select.SelectSingleClause;
import io.github.mmm.db.statement.select.SelectStatement;
import io.github.mmm.db.statement.update.UpdateClause;
import io.github.mmm.db.statement.update.UpdateStatement;
import io.github.mmm.db.statement.upsert.UpsertClause;
import io.github.mmm.db.statement.upsert.UpsertInto;
import io.github.mmm.db.statement.upsert.UpsertStatement;
import io.github.mmm.db.type.DbType;
import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.id.Id;
import io.github.mmm.entity.id.PkId;
import io.github.mmm.entity.link.Link;
import io.github.mmm.entity.property.link.LinkProperty;
import io.github.mmm.property.ReadableProperty;
import io.github.mmm.property.criteria.CriteriaExpression;
import io.github.mmm.property.criteria.CriteriaObjectParser;
import io.github.mmm.property.criteria.CriteriaOperator;
import io.github.mmm.property.criteria.CriteriaOrdering;
import io.github.mmm.property.criteria.CriteriaPredicate;
import io.github.mmm.property.criteria.Literal;
import io.github.mmm.property.criteria.ProjectionProperty;
import io.github.mmm.property.criteria.PropertyAssignment;
import io.github.mmm.property.criteria.PropertyPathParser;
import io.github.mmm.property.criteria.SimplePathParser;
import io.github.mmm.scanner.CharScannerParser;
import io.github.mmm.scanner.CharStreamScanner;
import io.github.mmm.value.CriteriaObject;
import io.github.mmm.value.PropertyPath;
import io.github.mmm.value.ReadablePath;
import io.github.mmm.value.SimplePath;

/**
 * {@link CharScannerParser} for {@link DbStatement}s.<br>
 * <b>ATTENTION:</b> This is NOT a generic SQL parser. It will only support the exact syntax produced by
 * {@link BasicDbStatementFormatter} with the defaults (as used by {@link DbStatement#toString()}).
 *
 * @since 1.0.0
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class DbStatementParser implements CharScannerParser<DbStatement<?>>, DbKeyword {

  private static final DbStatementParser INSTANCE = new DbStatementParser();

  private final ClassNameMapper classNameMapper;

  private final BeanFactory beanFactory;

  private final CriteriaObjectParser criteriaSelectionParser;

  /**
   * The constructor.
   */
  protected DbStatementParser() {

    super();
    this.classNameMapper = ClassNameMapper.get();
    this.beanFactory = BeanFactory.get();
    this.criteriaSelectionParser = CriteriaObjectParser.get();
  }

  @Override
  public DbStatement<?> parse(CharStreamScanner scanner) {

    try {
      DbStatement<?> statement;
      scanner.skipWhile(NEWLINE_OR_SPACE);
      String keyword = readKeyword(scanner);
      if (SELECT.equals(keyword)) {
        statement = parseSelectStatement(scanner);
      } else if (UPDATE.equals(keyword)) {
        statement = parseUpdateStatement(scanner);
      } else if (INSERT.equals(keyword)) {
        statement = parseInsertStatement(scanner);
      } else if (DELETE.equals(keyword)) {
        statement = parseDeleteStatement(scanner);
      } else if (CREATE.equals(keyword)) {
        statement = parseCreateStatement(scanner);
      } else if (UPSERT.equals(keyword)) {
        statement = parseUsertStatement(scanner);
      } else if (MERGE.equals(keyword)) {
        statement = parseMergeStatement(scanner);
      } else if (matchesKeyword(ALTER_TABLE, keyword, scanner)) {
        statement = parseAlterTableStatement(scanner);
      } else {
        throw new IllegalStateException("Unknown statement: " + keyword);
      }
      scanner.skipWhile(NEWLINE_OR_SPACE);
      if (scanner.hasNext()) {
        throw new IllegalStateException("Internal error: Statement not parsed to the end.");
      }
      return statement;
    } catch (Exception e) {
      throw new IllegalArgumentException("Failed to parse DB statement after \n" + scanner.getBufferParsed()
          + "\n and before \n" + scanner.getBufferToParse() + "\nwith error: " + e.getMessage(), e);
    }
  }

  /**
   * @param scanner the {@link CharStreamScanner} pointing after the "CREATE " keyword.
   * @return the parsed {@link CreateStatement}.
   */
  protected CreateStatement<?> parseCreateStatement(CharStreamScanner scanner) {

    String keyword = readKeyword(scanner);
    boolean unique = false;
    if (keyword.equals(UNIQUE)) {
      unique = true;
      keyword = readKeyword(scanner);
    }
    Boolean clustered = parseClustered(keyword);
    if (clustered != null) {
      keyword = readKeyword(scanner);
    }
    DbIndexType type = parseIndexType(keyword);
    if (type != null) {
      keyword = readKeyword(scanner);
    }
    if (keyword.equals(INDEX)) {
      if (type == null) {
        type = DbIndexType.NONE;
      }
      return parseCreateIndexStatement(scanner, unique, clustered, type);
    } else if (unique) {
      throw new IllegalArgumentException(UNIQUE);
    } else if (clustered != null) {
      if (Boolean.TRUE.equals(clustered)) {
        throw new IllegalArgumentException(CLUSTERED);
      } else {
        throw new IllegalArgumentException(NONCLUSTERED);
      }
    } else if (type != null) {
      throw new IllegalArgumentException(type.toString());
    } else if (keyword.equals(TABLE)) {
      return parseCreateTableStatement(scanner);
    } else if (keyword.equals(SEQUENCE)) {
      return parseCreateSequenceStatement(scanner);
    } else {
      throw new IllegalStateException();
    }
  }

  private Boolean parseClustered(String keyword) {

    if (keyword.equals(CLUSTERED)) {
      return Boolean.TRUE;
    } else if (keyword.equals(NONCLUSTERED)) {
      return Boolean.FALSE;
    }
    return null;
  }

  private DbIndexType parseIndexType(String keyword) {

    Iterator<DbIndexType> iterator = DbIndexType.getAll().iterator();
    while (iterator.hasNext()) {
      DbIndexType type = iterator.next();
      String typeString = type.toString();
      if (!typeString.isEmpty()) {
        if (keyword.equals(typeString)) {
          return type;
        }
      }
    }
    return null;
  }

  private boolean matchesKeyword(String keyword, String tokenUpper, CharStreamScanner scanner) {

    if (keyword.equals(tokenUpper)) {
      return true;
    }
    int space = keyword.indexOf(' ');
    if (space > 0) {
      String first = keyword.substring(0, space);
      if (tokenUpper.equals(first)) {
        String second = keyword.substring(space + 1);
        scanner.require(second, true);
        scanner.requireOneOrMore(NEWLINE_OR_SPACE);
        return true;
      }
    }
    return false;
  }

  private CreateIndexStatement<?> parseCreateIndexStatement(CharStreamScanner scanner, boolean unique,
      Boolean clustered, DbIndexType type) {

    DbIndexKindType kind = new DbIndexKindType(unique, (clustered == null) ? false : clustered.booleanValue(), type);
    CreateIndexClause createIndex = parseCreateIndex(scanner, kind);
    CreateIndexOnClause<?> createIndexOn = parseCreateIndexOn(scanner, createIndex);
    CreateIndexColumnsClause<?> createIndexColumns = parseCreateIndexColumns(scanner, createIndexOn);
    return createIndexColumns.get();
  }

  private CreateIndexClause parseCreateIndex(CharStreamScanner scanner, DbIndexKindType kind) {

    String name = parseSegment(scanner);
    scanner.requireOneOrMore(NEWLINE_OR_SPACE);
    return new CreateIndexClause(kind, name);
  }

  private CreateIndexOnClause<?> parseCreateIndexOn(CharStreamScanner scanner, CreateIndexClause createIndex) {

    CreateIndexOnClause<?> createIndexOn = createIndex.on(null);
    scanner.require(CreateIndexOnClause.NAME_ON, true);
    scanner.requireOneOrMore(NEWLINE_OR_SPACE);
    parseEntityClause(scanner, createIndexOn, true);
    return createIndexOn;
  }

  private CreateIndexColumnsClause<?> parseCreateIndexColumns(CharStreamScanner scanner,
      CreateIndexOnClause<?> createIndexOn) {

    DbColumnReference[] columns = parseColumns(scanner, createIndexOn.getEntity(), 1);
    return createIndexOn.columns(columns);
  }

  private CreateSequenceStatement parseCreateSequenceStatement(CharStreamScanner scanner) {

    DbQualifiedName sequenceName = parseQualifiedName(scanner);
    CreateSequenceClause clause = new CreateSequenceClause(sequenceName);
    int skip;
    boolean found;
    do {
      found = false;
      skip = scanner.skipWhile(NEWLINE_OR_SPACE);
      if (skip > 0) {
        found = parseCreateSequenceAttributes(scanner, clause);
      }
    } while ((skip > 0) && found);
    return clause.get();
  }

  private boolean parseCreateSequenceAttributes(CharStreamScanner scanner, CreateSequenceClause clause) {

    if (parseCreateSequenceAttribute("INCREMENT BY", scanner, i -> clause.incrementBy(i.intValue()))) {
      return true;
    } else if (parseCreateSequenceAttribute("START WITH", scanner, clause::startWith)) {
      return true;
    } else if (parseCreateSequenceAttribute("MINVALUE", scanner, clause::minValue)) {
      return true;
    } else if (parseCreateSequenceAttribute("MAXVALUE", scanner, clause::maxValue)) {
      return true;
    } else if (parseCreateSequenceAttribute("CYCLE", scanner, null)) {
      clause.cycle();
      return true;
    } else if (parseCreateSequenceAttribute("NOCYCLE", scanner, null)) {
      clause.nocycle();
      return true;
    }
    return false;
  }

  private boolean parseCreateSequenceAttribute(String attribute, CharStreamScanner scanner,
      Consumer<Long> numberConsumer) {

    if (!scanner.expect(attribute, true)) {
      return false;
    }
    if (numberConsumer != null) {
      scanner.requireOne(NEWLINE_OR_SPACE);
      Long number = scanner.readLong();
      if (number == null) {
        throw new IllegalArgumentException("Expected number after " + attribute);
      }
      numberConsumer.accept(number);
    }
    return true;
  }

  private AlterTableStatement<?> parseAlterTableStatement(CharStreamScanner scanner) {

    AlterTableClause<?> alterTable = parseAlterTable(scanner);
    AlterTableOperationsClause<?> operation = parseAlterTableOperations(scanner, alterTable);
    return operation.get();
  }

  private AlterTableClause<?> parseAlterTable(CharStreamScanner scanner) {

    AlterTableClause<?> alterTable = new AlterTableClause<>(null);
    parseEntityClause(scanner, alterTable, false);
    return alterTable;
  }

  private AlterTableOperationsClause<?> parseAlterTableOperations(CharStreamScanner scanner,
      AlterTableClause<?> alterTable) {

    AlterTableOperationsClause<?> operations = alterTable.addColumns(DbColumnReference.NO_COLUMNS);
    EntityBean entity = alterTable.getEntity();
    do {
      scanner.skipWhile(NEWLINE_OR_SPACE);
      TableOperationType type = parseAlterTableOperationType(scanner);
      scanner.skipWhile(NEWLINE_OR_SPACE);
      TableOperationKind kind = TableOperationKind.COLUMN;
      if (scanner.expect(DbKeyword.CONSTRAINT, true)) {
        kind = TableOperationKind.CONSTRAINT;
        scanner.skipWhile(NEWLINE_OR_SPACE);
      } else if (scanner.expect(DbKeyword.COLUMN, true)) {
        scanner.skipWhile(NEWLINE_OR_SPACE);
      }
      String name = parseSegment(scanner);
      scanner.skipWhile(NEWLINE_OR_SPACE);
      switch (type) {
        case ADD -> {
          if (kind == TableOperationKind.COLUMN) {
            DbColumnReference column = parseColumn(name, entity, true, scanner);
            operations.addColumn(column);
          } else {
            DbConstraint constraint = parseConstraint(name, alterTable, scanner);
            operations.addConstraint(constraint);
          }
        }
        case DROP -> {
          if (kind == TableOperationKind.COLUMN) {
            DbColumnReference column = parseColumn(name, entity, false, scanner);
            operations.dropColumn(column);
          } else {
            operations.dropConstraint(name);
          }
        }
        case MODIFY -> {
          if (kind == TableOperationKind.COLUMN) {

          } else {
            throw new IllegalStateException("ALTER TABLE MODIFY is only allowed on COLUMN");
          }
        }
        case RENAME -> {
          if (kind == TableOperationKind.COLUMN) {
            DbColumnReference column = parseColumn(name, entity, false, scanner);
            scanner.require("TO", true);
            scanner.requireOneOrMore(NEWLINE_OR_SPACE);
            DbColumnReference newColumn = parseColumn(null, entity, false, scanner);
            operations.renameColumn(column, newColumn);
          } else {
            scanner.require("TO", true);
            scanner.requireOneOrMore(NEWLINE_OR_SPACE);
            String newName = scanner.readWhile(CharFilter.IDENTIFIER, 1, 128);
            operations.renameConstraint(name, newName);
          }
        }
      }
    } while (scanner.expectOne(','));
    return operations;
  }

  private DbConstraint parseConstraint(String constraintName, DbTableReference<?> sourceTable,
      CharStreamScanner scanner) {

    if (constraintName == null) {
      constraintName = parseSegment(scanner);
      scanner.requireOneOrMore(NEWLINE_OR_SPACE);
    }
    EntityBean entity = sourceTable.getEntity();
    DbConstraint constraint = null;
    if (scanner.expect(DbForeignKeyConstraint.TYPE, true)) {
      List<DbColumnReference> sourceColumns = List.of(parseColumns(scanner, entity, 1));
      scanner.requireOneOrMore(NEWLINE_OR_SPACE);
      scanner.require(DbKeyword.REFERENCES, true);
      scanner.requireOneOrMore(NEWLINE_OR_SPACE);
      DbTableReference<?> targetTable = parseTable(scanner);
      List<DbColumnReference> targetColumns = List.of(parseColumns(scanner, targetTable.getEntity(), 1));
      constraint = DbForeignKeyConstraint.of(constraintName, sourceTable, sourceColumns, targetTable, targetColumns);
    } else if (scanner.expect(DbPrimaryKeyConstraint.TYPE, true)) {
      List<DbColumnReference> sourceColumns = List.of(parseColumns(scanner, entity, 1));
      constraint = DbPrimaryKeyConstraint.of(constraintName, sourceTable, sourceColumns);
    } else if (scanner.expect(DbNotNullConstraint.TYPE, true)) {
      List<DbColumnReference> sourceColumns = List.of(parseColumns(scanner, entity, 1));
      constraint = DbNotNullConstraint.of(constraintName, sourceTable, sourceColumns);
    } else if (scanner.expect(DbUniqueConstraint.TYPE, true)) {
      List<DbColumnReference> sourceColumns = List.of(parseColumns(scanner, entity, 1));
      constraint = DbUniqueConstraint.of(constraintName, sourceTable, sourceColumns);
    } else if (scanner.expect(DbCheckConstraint.TYPE, true)) {
      PropertyPathParser pathParser = new EntityPathParser(entity);
      CriteriaPredicate predicate = this.criteriaSelectionParser.parsePredicate(scanner, pathParser);
      constraint = DbCheckConstraint.of(constraintName, sourceTable, predicate);
    }
    if (constraint != null) {
      scanner.skipWhile(NEWLINE_OR_SPACE);
      DbConstraintInitially initially = parseConstraintInitially(scanner);
      DbConstraintDeferrable deferrable = parseConstraintDeferrable(scanner);
      if ((initially == DbConstraintInitially.DEFAULT) && (deferrable != DbConstraintDeferrable.DEFAULT)) {
        initially = parseConstraintInitially(scanner);
      }
      DbConstraintRely rely = parseConstraintRely(scanner);
      DbConstraintState state = DbConstraintState.of(deferrable, initially, rely);
      constraint = constraint.withState(state);
    }
    return constraint;
  }

  private DbConstraintDeferrable parseConstraintDeferrable(CharStreamScanner scanner) {

    DbConstraintDeferrable deferrable = DbConstraintDeferrable.DEFAULT;
    if (scanner.expect("DEFERRABLE", true)) {
      deferrable = DbConstraintDeferrable.DEFERRABLE;
      scanner.skipWhile(NEWLINE_OR_SPACE);
    } else if (scanner.expect("NOT DEFERRABLE", true)) {
      deferrable = DbConstraintDeferrable.NOT_DEFERRABLE;
      scanner.skipWhile(NEWLINE_OR_SPACE);
    }
    return deferrable;
  }

  private DbConstraintInitially parseConstraintInitially(CharStreamScanner scanner) {

    DbConstraintInitially initially = DbConstraintInitially.DEFAULT;
    if (scanner.expect("INITIALLY", true)) {
      scanner.requireOneOrMore(NEWLINE_OR_SPACE);
      if (scanner.expect("DEFERRED", true)) {
        initially = DbConstraintInitially.INITIALLY_DEFERRED;
      } else if (scanner.expect("IMMEDIATE", true)) {
        initially = DbConstraintInitially.INITIALLY_IMMEDIATE;
      } else {
        throw new IllegalStateException();
      }
      scanner.skipWhile(NEWLINE_OR_SPACE);
    }
    return initially;
  }

  private DbConstraintRely parseConstraintRely(CharStreamScanner scanner) {

    DbConstraintRely deferrable = DbConstraintRely.DEFAULT;
    if (scanner.expect("RELY", true)) {
      deferrable = DbConstraintRely.RELY;
      scanner.skipWhile(NEWLINE_OR_SPACE);
    } else if (scanner.expect("NORELY", true)) {
      deferrable = DbConstraintRely.NORELY;
      scanner.skipWhile(NEWLINE_OR_SPACE);
    }
    return deferrable;
  }

  private DbColumnReference parseColumn(String name, ReadableBean bean, boolean withDeclaration,
      CharStreamScanner scanner) {

    if (name == null) {
      name = scanner.readWhile(CharFilter.IDENTIFIER, 1, 128);
      scanner.skipWhile(NEWLINE_OR_SPACE);
    }
    ReadableProperty<?> property = bean.getProperty(name);
    if (withDeclaration) {
      DbType<?, ?, ?> type = parseColumnType(property, scanner);
      return DbColumn.of(name, property, null, null, type);
    }
    return DbColumnReference.of(name, property);
  }

  private DbType<?, ?, ?> parseColumnType(ReadableProperty<?> property, CharStreamScanner scanner) {

    // TODO: incomplete...
    String declaration = scanner.readWhile(CharFilter.IDENTIFIER);
    int spaces = scanner.skipWhile(NEWLINE_OR_SPACE);
    if (spaces > 0) {

    }
    if (property != null) {
      return getDbType(property).withName(declaration);
      // String type = ClassNameMapper.get().getNameOrQualified(valueClass);
      // if (declaration.equals(type)) {
      // declaration = null;
      // }
    }
    return null;
  }

  private DbType<?, ?, ?> getDbType(ReadableProperty<?> property) {

    if (property instanceof LinkProperty<?> linkProperty) {
      Link<?> link = linkProperty.get();
      Id<?> id = link.getId();
      Class<?> pkClass = id.getPkClass();
      return DbType.get(pkClass);
    }
    Class<?> valueClass = property.getValueClass();
    return DbType.get(valueClass);
  }

  private TableOperationType parseAlterTableOperationType(CharStreamScanner scanner) {

    String type = scanner.readWhile(CharFilter.LATIN_LETTER).toUpperCase(Locale.ROOT);
    return TableOperationType.valueOf(type);
  }

  private CreateTableStatement<?> parseCreateTableStatement(CharStreamScanner scanner) {

    CreateTableClause<?> createTable = parseCreateTable(scanner);
    CreateTableContentsClause<?> contents = parseCreateTableContents(scanner, createTable);
    return contents.get();
  }

  private CreateTableContentsClause<?> parseCreateTableContents(CharStreamScanner scanner,
      CreateTableClause<?> createTable) {

    CreateTableContentsClause<?> contents = createTable.columns(DbColumnReference.NO_COLUMNS);
    EntityBean entity = createTable.getEntity();
    scanner.skipWhile(NEWLINE_OR_SPACE);
    scanner.requireOne('(');
    do {
      scanner.skipWhile(NEWLINE_OR_SPACE);
      if (scanner.expect(DbKeyword.CONSTRAINT, true)) {
        scanner.requireOneOrMore(NEWLINE_OR_SPACE);
        DbConstraint constraint = parseConstraint(null, createTable, scanner);
        contents.constraint(constraint);
      } else {
        ReadableProperty<?> columnProperty = EntityPathParser.parsePath(scanner, entity);
        // if (columnProperty != EntityPathParser.PROPERTY_REVISION) {
        contents.column(columnProperty);
        scanner.requireOne(NEWLINE_OR_SPACE);
        String columnType = parseSegment(scanner);
        Class<?> columnClass = this.classNameMapper.getClass(columnType);
        assert (columnClass == columnProperty.getValueClass());
        // }
      }
      scanner.skipWhile(NEWLINE_OR_SPACE);
    } while (scanner.expectOne(','));
    scanner.requireOne(')');
    return contents;
  }

  private DbTableReference<?> parseTable(CharStreamScanner scanner) {

    String entityName = PropertyPathParser.readSegment(scanner, null);
    Class entityClass = ClassNameMapper.get().getClass(entityName);
    WritableBean bean = BeanFactory.get().create(entityClass);
    if (bean instanceof EntityBean entity) {
      return DbTableReference.of(entityName, entity);
    }
    throw new IllegalStateException(entityName);
  }

  private DbColumnReference[] parseColumns(CharStreamScanner scanner, ReadableBean entity, int min) {

    List<DbColumnReference> columns = new ArrayList<>();
    scanner.skipWhile(NEWLINE_OR_SPACE);
    scanner.requireOne('(');
    do {
      scanner.skipWhile(NEWLINE_OR_SPACE);
      ReadableProperty<?> column = EntityPathParser.parsePath(scanner, entity);
      columns.add(DbColumnReference.of(column));
      scanner.skipWhile(NEWLINE_OR_SPACE);
    } while (scanner.expectOne(','));
    scanner.requireOne(')');
    if (columns.size() < min) {
      throw new IllegalArgumentException("Requires " + min + " column(s) but found only " + columns.size() + ".");
    }
    return columns.toArray(DbColumnReference[]::new);
  }

  private MergeStatement<?> parseMergeStatement(CharStreamScanner scanner) {

    MergeStatement statement = new MergeIntoClause<>(new MergeClause(), null).values(PropertyAssignment.EMPTY_ARRAY)
        .get();
    // TODO Auto-generated method stub
    return statement;
  }

  private UpsertStatement<?> parseUsertStatement(CharStreamScanner scanner) {

    UpsertStatement upsertStatement = new UpsertInto<>(new UpsertClause(), null).values(PropertyAssignment.EMPTY_ARRAY)
        .get();
    // TODO Auto-generated method stub
    return upsertStatement;
  }

  private DeleteStatement<?> parseDeleteStatement(CharStreamScanner scanner) {

    DeleteFromClause<EntityBean> from = new DeleteFromClause<>(new DeleteClause(), null);
    parseFrom(scanner, from);
    DeleteStatement<EntityBean> statement = from.get();
    parseWhere(scanner, statement.getWhere());
    // TODO
    return statement;
  }

  private InsertStatement<?> parseInsertStatement(CharStreamScanner scanner) {

    InsertIntoClause<?> into = new InsertIntoClause<>(new InsertClause(), null);
    parseInto(scanner, into);
    scanner.skipWhile(' ');
    InsertValuesClause<?> values = into.values(PropertyAssignment.EMPTY_ARRAY);
    parseValues(scanner, values, into.getEntity());
    InsertStatement statement = values.get();
    // TODO Auto-generated method stub
    return statement;
  }

  private UpdateStatement<?> parseUpdateStatement(CharStreamScanner scanner) {

    UpdateClause<?> update = parseUpdate(scanner);
    UpdateStatement<?> statement = update.get();
    AliasMap aliasMap = getAliasMap(update);
    parseSetClause(scanner, statement.getSet(), aliasMap);
    parseWhere(scanner, statement.getWhere());
    return statement;
  }

  private void parseSetClause(CharStreamScanner scanner, SetClause set, PropertyPathParser pathParser) {

    do {
      scanner.skipWhile(NEWLINE_OR_SPACE);
      PropertyPath<?> property = pathParser.parse(scanner);
      scanner.skipWhile(NEWLINE_OR_SPACE);
      scanner.requireOne('=');
      scanner.skipWhile(NEWLINE_OR_SPACE);
      CriteriaObject value = this.criteriaSelectionParser.parseSelection(scanner, pathParser);
      set.set(PropertyAssignment.of(property, value));
      scanner.skipWhile(NEWLINE_OR_SPACE);
    } while (scanner.expectOne(','));
  }

  private UpdateClause<?> parseUpdate(CharStreamScanner scanner) {

    UpdateClause<?> update = new UpdateClause<>(null);
    parseEntitiesClause(scanner, update);
    scanner.require("SET", true);
    scanner.requireOneOrMore(NEWLINE_OR_SPACE);
    return update;
  }

  private SelectStatement<?> parseSelectStatement(CharStreamScanner scanner) {

    SelectClause<?> select = parseSelect(scanner);
    if (select instanceof SelectSequenceNextValueClause) {
      return select.get();
    }
    SelectFromClause from = new SelectFromClause<>(select, null);
    parseFrom(scanner, from);
    if (select instanceof SelectEntityClause) {
      String aliasFrom = from.getAlias();
      String aliasSelect = select.getResultName();
      if (!Objects.equals(aliasFrom, aliasSelect)) {
        throw new IllegalArgumentException(
            "Alias of SELECT (" + aliasSelect + ") and FROM (" + aliasFrom + ") do not match.");
      }
    }
    SelectStatement statement = from.get();
    parseWhere(scanner, statement.getWhere());
    parseGroupBy(scanner, statement.getGroupBy());
    parseHaving(scanner, statement.getHaving());
    parseOrderBy(scanner, statement.getOrderBy());
    // post process selections
    AliasMap aliasMap = ((AbstractDbStatement) statement).getAliasMap();
    List<CriteriaObject<?>> selections = (List<CriteriaObject<?>>) select.getSelections();
    int size = selections.size();
    for (int i = 0; i < size; i++) {
      CriteriaObject<?> selection = selections.get(i);
      CriteriaObject<?> resolved = resolve(selection, aliasMap);
      if (resolved != selection) {
        selections.set(i, resolved);
      }
    }
    return statement;

  }

  private void parseValues(CharStreamScanner scanner, ValuesClause values, EntityBean entity) {

    // TODO consider JQL change: INSERT INTO MyEntity e VALUES (e.Property1=value1, e.Property2=value2)
    scanner.requireOne('(');
    List<ReadableProperty<?>> properties = new ArrayList<>();
    boolean todo = true;
    while (todo) {
      scanner.skipWhile(NEWLINE_OR_SPACE);
      ReadableProperty<?> path = EntityPathParser.parsePath(scanner, entity);
      properties.add(path);
      scanner.skipWhile(NEWLINE_OR_SPACE);
      if (scanner.expectOne(')')) {
        todo = false;
      } else if (!scanner.expectOne(',')) {
        throw new IllegalArgumentException("Expecting ')' or ','.");
      }
    }
    scanner.skipWhile(' ');
    // TODO: make optional (e.g. support sub-query instead)
    scanner.require("VALUES", true);
    scanner.skipWhile(' ');
    scanner.requireOne('(');
    int i = 0;
    todo = true;
    int columnCount = properties.size();
    while (todo) {
      if (i >= columnCount) {
        break;
      }
      ReadableProperty<?> property = properties.get(i);
      scanner.skipWhile(' ');
      Literal literal = this.criteriaSelectionParser.parseLiteral(scanner);
      if (property != EntityPathParser.PROPERTY_REVISION) {
        if (property == entity.Id()) {
          Object pk = literal.get();
          Object rev = null;
          if (scanner.expect("@")) {
            literal = this.criteriaSelectionParser.parseLiteral(scanner);
            rev = literal.get();
          } else if ((i + 1) < columnCount) {
            ReadableProperty<?> ref = properties.get(i + 1);
            if (ref == EntityPathParser.PROPERTY_REVISION) {
              scanner.requireOne(',');
              scanner.skipWhile(' ');
              literal = this.criteriaSelectionParser.parseLiteral(scanner);
              rev = literal.get();
              i++;
            }
          }
          Id<?> id = PkId.of(entity.getJavaClass(), pk).withRevisionGeneric((Comparable<?>) rev);
          literal = Literal.of(id);
        }
        values.value(PropertyAssignment.of(property, literal));
      }
      i++;
      scanner.skipWhile(' ');
      if (scanner.expectOne(')')) {
        todo = false;
      } else if (!scanner.expectOne(',')) {
        throw new IllegalArgumentException("Expecting ')' or ','.");
      }
    }
    if (i != columnCount) {
      throw new IllegalArgumentException("Found " + columnCount + " coumn(s) but " + i + " value(s).");
    }
  }

  private void parseInto(CharStreamScanner scanner, IntoClause into) {

    scanner.skipWhile(NEWLINE_OR_SPACE);
    scanner.require("INTO", true);
    scanner.requireOneOrMore(NEWLINE_OR_SPACE);
    parseEntityClause(scanner, into, false);
  }

  private CreateTableClause<?> parseCreateTable(CharStreamScanner scanner) {

    CreateTableClause<?> createTable = new CreateTableClause<>(null);
    parseEntityClause(scanner, createTable, false);
    return createTable;
  }

  private CriteriaObject<?> resolve(CriteriaObject<?> selection, AliasMap aliasMap) {

    if (selection instanceof SimplePath) {
      return aliasMap.resolvePath((SimplePath) selection);
    } else if (selection instanceof CriteriaExpression) {
      CriteriaExpression<?> expression = (CriteriaExpression<?>) selection;
      CriteriaOperator operator = expression.getOperator();
      List<? extends CriteriaObject<?>> args = expression.getArgs();
      List<CriteriaObject<?>> newArgs = null;
      int i = 0;
      for (CriteriaObject<?> arg : args) {
        CriteriaObject<?> resolved = resolve(arg, aliasMap);
        if (newArgs == null) {
          if (resolved != arg) {
            newArgs = new ArrayList<>(args.size());
            for (int j = 0; j <= i; j++) {
              newArgs.add(args.get(j));
            }
          }
        } else {
          newArgs.add(resolved);
        }
        i++;
      }
      if (newArgs != null) {
        return operator.expression(newArgs);
      }
    }
    // nothing to do (e.g. literal)...
    return selection;
  }

  private void parseOrderBy(CharStreamScanner scanner, OrderByClause orderBy) {

    if (!scanner.expect("ORDER BY ", true)) {
      return;
    }
    AliasMap aliasMap = ((AbstractDbStatement) orderBy.get()).getAliasMap();
    do {
      scanner.skipWhile(' ');
      PropertyPath<?> property = aliasMap.parse(scanner);
      SortOrder order = SortOrder.ASC;
      int spaces = scanner.skipWhile(' ');
      if (spaces > 0) {
        if (scanner.expect("ASC", true)) {
          order = SortOrder.ASC;
        } else if (scanner.expect("DESC", true)) {
          order = SortOrder.DESC;
        }
        scanner.skipWhile(' ');
      }
      CriteriaOrdering ordering = new CriteriaOrdering(property, order);
      orderBy.and(ordering);
    } while (scanner.expectOne(','));

  }

  private SelectClause parseSelect(CharStreamScanner scanner) {

    scanner.skipWhile(NEWLINE_OR_SPACE);
    SelectClause select;
    if (scanner.expect("new", true)) {
      scanner.requireOneOrMore(NEWLINE_OR_SPACE);
      WritableBean projectionBean = null;
      String bean = scanner.readUntil('(', false);
      if (bean == null) {
        throw new IllegalArgumentException("Missing '(' after 'SELECT new '.");
      } else {
        bean = bean.trim();
        if (bean.length() > 0) {
          Class beanClass = this.classNameMapper.getClass(bean);
          projectionBean = this.beanFactory.create(beanClass);
          select = new SelectProjectionClause(projectionBean);
        } else {
          select = new SelectResultClause();
        }
      }
      int cp;
      do {
        scanner.skipWhile(NEWLINE_OR_SPACE);
        CriteriaObject<?> selection = this.criteriaSelectionParser.parseSelection(scanner);
        if (projectionBean != null) {
          PropertyPath path = null;
          int spaces = scanner.skipWhile(NEWLINE_OR_SPACE);
          if ((spaces > 0)) {
            if (scanner.expect("AS", true)) {
              scanner.requireOneOrMore(NEWLINE_OR_SPACE);
            }
            path = EntityPathParser.parsePath(scanner, projectionBean);
          } else if (selection instanceof PropertyPath) {
            path = EntityPathParser.resolvePath(projectionBean, (PropertyPath) selection);
          } else {
            // error?
          }
          selection = new ProjectionProperty(selection, path);
        }
        select.getSelections().add(selection);
        cp = scanner.next();
      } while (cp == ',');
      if (cp != ')') {
        throw new IllegalArgumentException("Missing ')'.");
      }
    } else if (scanner.expectOne('(')) {
      // multiple selections
      select = new SelectEntityClause("");
      int cp;
      do {
        scanner.skipWhile(NEWLINE_OR_SPACE);
        CriteriaObject<?> selection = this.criteriaSelectionParser.parseSelection(scanner);
        select.getSelections().add(selection);
        cp = scanner.next();
      } while (cp == ',');
      if (cp != ')') {
        throw new IllegalArgumentException("Missing ')'.");
      }
    } else if (scanner.expect(DbKeyword.NEXTVAL, true)) {
      scanner.skipWhile(NEWLINE_OR_SPACE); // tolerance
      scanner.requireOne('(');
      scanner.skipWhile(NEWLINE_OR_SPACE); // tolerance
      PropertyPath path = SimplePathParser.INSTANCE.parse(scanner);
      scanner.skipWhile(NEWLINE_OR_SPACE); // tolerance
      scanner.requireOne(')');
      select = new SelectSequenceNextValueClause(toSequenceName(path));
    } else {
      // single selection or entityAlias
      PropertyPath path = SimplePathParser.INSTANCE.parse(scanner);
      ReadablePath parentPath = path.parentPath();
      if (parentPath == null) {
        select = new SelectEntityClause(path.getName());
      } else {
        select = new SelectSingleClause(path);
      }
    }
    return select;
  }

  /**
   * @param path the {@link ReadablePath} holding the sequence name.
   * @return the converted {@link DbQualifiedName}.
   */
  private DbQualifiedName toSequenceName(ReadablePath path) {

    String catalog = null;
    String schema = null;
    String name = path.pathSegment();
    ReadablePath parentPath = path.parentPath();
    if (parentPath != null) {
      schema = parentPath.pathSegment();
      ReadablePath rootPath = parentPath.parentPath();
      if (rootPath != null) {
        assert (rootPath.parentPath() == null);
        catalog = rootPath.path();
      }
    }
    return new DbQualifiedName(name, schema, catalog);
  }

  private void parseFrom(CharStreamScanner scanner, FromClause from) {

    scanner.skipWhile(NEWLINE_OR_SPACE);
    scanner.require(DbKeyword.FROM, true);
    scanner.requireOneOrMore(NEWLINE_OR_SPACE);
    parseEntitiesClause(scanner, from);
  }

  private void parseEntityClause(CharStreamScanner scanner, AbstractEntityClause entityClause, boolean allowAlias) {

    String entityName = parseSegment(scanner);
    Class entityClass = this.classNameMapper.getClass(entityName);
    EntityBean entity = (EntityBean) this.beanFactory.create(entityClass);
    scanner.skipWhile(NEWLINE_OR_SPACE);
    String entityAlias = "";
    if (allowAlias) {
      boolean hasAsKeyword = false;
      if (scanner.expect(DbKeyword.AS, true)) {
        hasAsKeyword = true;
        scanner.requireOneOrMore(NEWLINE_OR_SPACE);
      }
      entityAlias = parseSegment(scanner);
      if (entityAlias.isEmpty()) {
        if (hasAsKeyword) {
          throw new IllegalStateException("Missing alias after AS keyword!");
        }
      } else {
        scanner.skipWhile(NEWLINE_OR_SPACE);
      }
    }
    if (entityClause.getEntity() == null) {
      entityClause.setEntity(entity);
      if (!entityAlias.isEmpty()) {
        entityClause.as(entityAlias);
      }
    } else if (entityClause instanceof AbstractEntitiesClause entitesClause) {
      entitesClause.and(entity, entityAlias);
    }
  }

  private void parseEntitiesClause(CharStreamScanner scanner, AbstractEntitiesClause entitesClause) {

    do {
      scanner.skipWhile(NEWLINE_OR_SPACE);
      parseEntityClause(scanner, entitesClause, true);
    } while (scanner.expectOne(','));
  }

  private void parseWhere(CharStreamScanner scanner, WhereClause where) {

    if (!scanner.expect(DbKeyword.WHERE, true)) {
      return;
    }
    do {
      scanner.requireOneOrMore(NEWLINE_OR_SPACE);
      CriteriaObject<?> expression = this.criteriaSelectionParser.parse(scanner);
      if (expression instanceof CriteriaPredicate) {
        where.and((CriteriaPredicate) expression);
      } else {
        throw new IllegalArgumentException("Expected predicate but found " + expression);
      }
      scanner.skipWhile(NEWLINE_OR_SPACE);
    } while (scanner.expect("AND", true));
  }

  private void parseGroupBy(CharStreamScanner scanner, GroupByClause groupBy) {

    if (!scanner.expect("GROUP BY", true)) {
      return;
    }
    scanner.requireOneOrMore(NEWLINE_OR_SPACE);
    PropertyPathParser pathParser = getAliasMap(groupBy);
    do {
      scanner.skipWhile(NEWLINE_OR_SPACE);
      PropertyPath<?> path = pathParser.parse(scanner);
      groupBy.and(path);
      scanner.skipWhile(NEWLINE_OR_SPACE);
    } while (scanner.expectOne(','));
  }

  private DbQualifiedName parseQualifiedName(CharStreamScanner scanner) {

    String catalog = null;
    String schema = null;
    String name;
    String segment = parseSegment(scanner);
    if (scanner.expectOne('.')) {
      String segment2 = parseSegment(scanner);
      if (scanner.expectOne('.')) {
        String segment3 = parseSegment(scanner);
        catalog = segment;
        schema = segment2;
        name = segment3;
      } else {
        schema = segment;
        name = segment2;
      }
    } else {
      name = segment;
    }
    return new DbQualifiedName(name, schema, catalog);
  }

  private String parseSegment(CharStreamScanner scanner) {

    return scanner.readWhile(CharFilter.SEGMENT, 1, 128);
  }

  private String readKeyword(CharStreamScanner scanner) {

    String keyword = scanner.readWhile(CharFilter.LATIN_LETTER, 1, 128).toUpperCase(Locale.ROOT);
    scanner.requireOneOrMore(NEWLINE_OR_SPACE);
    return keyword;
  }

  private void parseHaving(CharStreamScanner scanner, HavingClause having) {

    if (!scanner.expect("HAVING", true)) {
      return;
    }
    scanner.requireOneOrMore(NEWLINE_OR_SPACE);
    PropertyPathParser pathParser = getAliasMap(having);
    do {
      scanner.skipWhile(NEWLINE_OR_SPACE);
      CriteriaPredicate predicate = this.criteriaSelectionParser.parsePredicate(scanner, pathParser);
      having.and(predicate);
      scanner.skipWhile(NEWLINE_OR_SPACE);
    } while (scanner.expectOne(','));
  }

  private static AliasMap getAliasMap(DbMainClause clause) {

    return ((AbstractDbStatement) clause.get()).getAliasMap();
  }

  /**
   * @return the singleton instance of this {@link DbStatementParser}.
   */
  public static DbStatementParser get() {

    return INSTANCE;
  }

}
