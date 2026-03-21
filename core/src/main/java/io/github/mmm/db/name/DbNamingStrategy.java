/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.name;

import java.util.List;

import io.github.mmm.base.text.CaseSyntax;
import io.github.mmm.db.ddl.column.DbColumnReference;
import io.github.mmm.db.ddl.column.DbColumnReferenceWithSortOrder;
import io.github.mmm.db.ddl.constraint.DbConstraint;
import io.github.mmm.db.ddl.constraint.DbPrimaryKeyConstraint;
import io.github.mmm.db.ddl.index.DbIndex;
import io.github.mmm.db.ddl.index.DbIndexKind;
import io.github.mmm.db.ddl.table.DbTableReference;
import io.github.mmm.db.name.impl.DbNamingStrategyCaseSyntax;
import io.github.mmm.db.name.impl.DbNamingStrategyDefault;
import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.property.ReadableProperty;
import io.github.mmm.value.PropertyPath;
import io.github.mmm.value.ReadablePath;
import io.github.mmm.value.ReadablePath.PathBuilder;
import io.github.mmm.value.converter.TypeMapper;

/**
 * Interface to define the naming strategy to map {@link EntityBean}s to a database.
 *
 * @see #getTableName(EntityBean)
 * @see #getTableName(DbTableReference)
 * @see #getColumnName(ReadableProperty)
 * @see #getColumnName(DbColumnReference)
 * @see #getConstraintName(DbConstraint)
 * @see #getIndexName(DbIndex)
 * @since 1.0.0
 */
public interface DbNamingStrategy {

  /**
   * @return the maximum length for names of DDL elements (tables, columns, constraints, indexes, etc.) supported by the
   *         underlying database. To avoid trouble you should name your entities and properties short but precise.
   *         However, if you somehow exceed the limit, your names will be truncated automatically.
   */
  default int getMaximumNameLength() {

    return 128;
  }

  /**
   * @param name the suggested name of a DDL element.
   * @return the given {@code name} clipped to ensure {@link #getMaximumNameLength()} is not exceeded.
   */
  default String clipName(String name) {

    int max = getMaximumNameLength();
    if (name.length() > max) {
      return name.substring(0, max);
    }
    return name;
  }

  /**
   * This method should typically not be called directly from outside.
   *
   * @param name the original name.
   * @param dbObject the optional {@link DbObject}. May be used to determine the type of database object or
   *        {@link DbObject#hasJavaName()}.
   * @return the given name converted to the desired syntax and conventions for the target database or application
   *         design preferences. E.g. "CustomerAddress" may be converted to "CUSTOMER_ADDRESS".
   */
  default String convertName(String name, DbObject dbObject) {

    return name;
  }

  /**
   * @param name the original name to format for database usage.
   * @param dbObject the optional {@link DbObject}. May be used to determine the type of database object or
   *        {@link DbObject#hasJavaName()}. Never call {@link DbObject#getName()} or
   *        {@link DbObject#getName(DbNamingStrategy)} from this method to prevent infinity loops.
   * @return the given {@code name} mapped to the database according to this naming convention.
   */
  default String getName(String name, DbObject dbObject) {

    return clipName(convertName(name, dbObject));
  }

  /**
   * @param qualifiedName the {@link DbQualifiedName}.
   * @param table the {@link DbTableReference}.
   * @return the {@link DbQualifiedName} mapped to the database according to this naming convention.
   */
  default DbQualifiedName getQualifiedName(DbQualifiedName qualifiedName, DbTableReference<?> table) {

    String name = getName(qualifiedName.getName(), table);
    return qualifiedName.withName(name);
  }

  /**
   * @param column the {@link DbColumnReference} to derive the column name from.
   * @return the column name for the given {@link DbColumnReference}.
   */
  default String getColumnName(DbColumnReference column) {

    ReadableProperty<?> property = column.getProperty();
    if (property != null) {
      return getColumnName(property);
    }
    return getName(column.getName(null), column);
  }

  /**
   * @param property the {@link ReadableProperty} to derive the column name from.
   * @return the column name for the given {@link ReadableProperty property}.
   */
  default String getColumnName(ReadableProperty<?> property) {

    DbColumnReference column = DbEntityNameMapper.get().getColumn(property);
    String columnName = column.getName(null);
    return getColumnName(property, columnName, column);
  }

  private String getColumnName(PropertyPath<?> property, String name, DbColumnReference column) {

    String columnName = getName(name, column);
    ReadablePath parent = property.parentPath();
    if (parent != null) {
      PathBuilder builder = PathBuilder.of();
      parent.path(builder);
      builder.add(columnName);
      name = builder.toString();
    }
    return name;
  }

  /**
   * @param property the {@link PropertyPath} to derive the column name from.
   * @return the column name for the given {@link PropertyPath property}.
   */
  default String getColumnName(PropertyPath<?> property) {

    if (property instanceof ReadableProperty<?> rp) {
      return getColumnName(rp);
    } else {
      return getColumnName(property, property.getName(), null); // TODO avoid null
    }
  }

  /**
   * @param property the {@link PropertyPath} to derive the column name from.
   * @param typeMapper the explicit {@link TypeMapper}.
   * @return the column name for the given {@link ReadableProperty property}.
   */
  default String getColumnName(PropertyPath<?> property, TypeMapper<?, ?> typeMapper) {

    return getColumnName(getColumnName(property), typeMapper);
  }

  /**
   * @param rawColumnName the raw column name to map.
   * @param typeMapper the explicit {@link TypeMapper}.
   * @return the column name for the given {@link ReadableProperty property}.
   */
  default String getColumnName(String rawColumnName, TypeMapper<?, ?> typeMapper) {

    if (typeMapper == null) {
      return rawColumnName;
    }
    String suffix = typeMapper.getSuffix();
    if ((suffix != null) && !suffix.isEmpty()) {
      suffix = getName(suffix, null); // TODO avoid null
    }
    String columnName = typeMapper.getNameMode().format(rawColumnName, suffix);
    return clipName(columnName);
  }

  /**
   * @param entity the {@link EntityBean} to map to a database table.
   * @return the physical table name.
   */
  default DbQualifiedName getTableName(EntityBean entity) {

    DbEntityNameInfo nameInfo = DbEntityNameMapper.get().getTable(entity);
    return getTableName(nameInfo);
  }

  /**
   * @param table the {@link DbTableReference} to map.
   * @return the physical table name.
   */
  default DbQualifiedName getTableName(DbTableReference<?> table) {

    DbQualifiedName name = table.getQualifiedName(null);
    if (name == null) {
      return getTableName(table.getEntity());
    }
    return getQualifiedName(name, table);
  }

  /**
   * @param constraint the {@link DbConstraint}.
   * @return the {@link DbConstraint#getName() constraint name}. Will be {@link #generateConstraintName(DbConstraint)
   *         generated} if not present.
   */
  default String getConstraintName(DbConstraint constraint) {

    String name = constraint.getName(null);
    if (name == null) {
      return generateConstraintName(constraint);
    }
    return getName(name, constraint);
  }

  /**
   * @param constraint the {@link DbConstraint}.
   * @return the auto-generated {@link DbConstraint#getName() constraint name}.
   */
  default String generateConstraintName(DbConstraint constraint) {

    StringBuilder sb = new StringBuilder();
    sb.append(constraint.getNamePrefix());
    DbColumnReference column = constraint.getSourceColumns().getFirst();
    sb.append(getTableName(constraint.getSourceTable()));
    if (!(constraint instanceof DbPrimaryKeyConstraint)) {
      sb.append('_');
      sb.append(getColumnName(column.getProperty()));
    }
    // PK_TABLE

    // FK_TABLE_CUSTOMER_ID_CUSTOMER_ID ???
    // .........*********** source column
    // .....................******** target table
    // ..............................** target column
    return getName(sb.toString(), constraint);
  }

  /**
   * @param index the {@link DbIndex}.
   * @return the {@link DbIndex#getName() constraint name}. Will be {@link #generateIndexName(DbIndex) generated} if not
   *         present.
   */
  default String getIndexName(DbIndex index) {

    String name = index.getName(null);
    if ((name == null) || (name.isEmpty())) {
      return generateIndexName(index);
    }
    return getName(name, index);
  }

  /**
   * @param index the {@link DbIndex}.
   * @return the auto-generated index name.
   */
  default String generateIndexName(DbIndex index) {

    List<DbColumnReferenceWithSortOrder> columns = index.getColumns();
    DbQualifiedName tableName = getTableName(index.getTable());
    int capacity = 3 + tableName.toString().length() + (columns.size() * 5);
    int max = getMaximumNameLength();
    if (capacity > max) {
      capacity = max;
    }
    StringBuilder sb = new StringBuilder(capacity);
    String infix = getIndexNameInfix();
    sb.append(getIndexNamePrefix(index));
    sb.append(infix);
    sb.append(tableName);
    for (DbColumnReference column : columns) {
      String columnName = getColumnName(column);
      sb.append(infix);
      sb.append(columnName);
      if (sb.length() > max) {
        break;
      }
    }
    String indexName = sb.toString();
    return clipName(indexName);
  }

  /**
   * @param indexKind the {@link DbIndexKind}.
   * @return the static prefix for an {@link #generateIndexName(DbIndex) index name}.
   */
  default String getIndexNamePrefix(DbIndexKind indexKind) {

    if (indexKind.isUnique()) {
      return "UX";
    } else if (indexKind.isClustered()) {
      return "CX";
    } else {
      return "IX";
    }
  }

  /**
   * @return the static infix for an {@link #generateIndexName(DbIndex) index name} used to separate the columns.
   */
  default String getIndexNameInfix() {

    return "_";
  }

  /**
   * @return the default {@link DbNamingStrategy}.
   */
  static DbNamingStrategy of() {

    return DbNamingStrategyDefault.INSTANCE;
  }

  /**
   * @param caseSyntax the {@link CaseSyntax} to use.
   * @return the {@link DbNamingStrategy} converting to the given {@link CaseSyntax}.
   */
  static DbNamingStrategy of(CaseSyntax caseSyntax) {

    return DbNamingStrategyCaseSyntax.of(caseSyntax);
  }

  /**
   * @return the {@link DbNamingStrategy} for legacy
   *         <a href="https://en.wikipedia.org/wiki/Relational_database">RDBMS</a>.
   */
  static DbNamingStrategy ofRdbms() {

    return of(CaseSyntax.UPPER_SNAKE_CASE);
  }
}
