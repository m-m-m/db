/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.name;

import java.util.List;

import io.github.mmm.db.ddl.column.DbColumnReference;
import io.github.mmm.db.ddl.column.DbColumnReferenceWithSortOrder;
import io.github.mmm.db.ddl.column.impl.DbColumnReferenceImpl;
import io.github.mmm.db.ddl.constraint.DbConstraint;
import io.github.mmm.db.ddl.constraint.DbPrimaryKeyConstraint;
import io.github.mmm.db.ddl.index.DbIndex;
import io.github.mmm.db.ddl.index.DbIndexKind;
import io.github.mmm.db.ddl.table.DbTableReference;
import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.property.ReadableProperty;
import io.github.mmm.value.PropertyPath;
import io.github.mmm.value.ReadablePath;
import io.github.mmm.value.ReadablePath.PathBuilder;
import io.github.mmm.value.converter.TypeMapper;

/// Abstract base implementation of [DbNamingStrategy].
///
/// @since 1.0.0
public abstract class AbstractDbNamingStrategy implements DbNamingStrategy {

  @Override
  public DbQualifiedName getName(DbObject dbObject) {

    DbQualifiedName name = dbObject.getRawName();
    if (name == null) {
      name = generateName(dbObject);
      if (name == null) {
        throw new IllegalArgumentException("Could not generate name for DB object " + dbObject.getClass().getName());
      }
    }
    return getName(name, dbObject);
  }

  /// @param dbObject the [DbObject] to generate the name of.
  /// @return the [DbQualifiedName] auto-generated for the given [DbObject]. It is not yet
  ///         [formatted][#getName(DbQualifiedName, DbObject)] according to this naming strategy.
  protected DbQualifiedName generateName(DbObject dbObject) {

    if (dbObject instanceof DbConstraint constraint) {
      return generateConstraintName(constraint);
    } else if (dbObject instanceof DbTableReference<?> table) {
      return generateTableName(table);
    } else if (dbObject instanceof DbIndex index) {
      return generateIndexName(index);
    } else if (dbObject instanceof DbColumnReference column) {
      return generateColumnName(column);
    }
    return null;
  }

  /// @param column the [DbColumnReference].
  /// @return the [generated][#generateName(DbObject)] [DbQualifiedName].
  protected DbQualifiedName generateColumnName(DbColumnReference column) {

    ReadableProperty<?> property = column.getProperty();
    if (property == null) {
      return null;
    }
    return getColumnName(property, column, true);
  }

  /// @param table the [DbTableReference].
  /// @return the [generated][#generateName(DbObject)] [DbQualifiedName].
  protected DbQualifiedName generateTableName(DbTableReference<?> table) {

    EntityBean entity = table.getEntity();
    if (entity == null) {
      return null;
    }
    DbEntityNameInfo nameInfo = DbEntityNameMapper.get().getTable(entity);
    return nameInfo.getName();
  }

  /// @param property the [ReadableProperty] to derive the column name from.
  /// @param raw `true` to get the raw name, `false` otherwise (get it
  ///        [formatted][#getName(DbQualifiedName, DbObject)] according to this naming strategy).
  /// @return the column name for the given [property][ReadableProperty].
  protected DbQualifiedName getColumnName(ReadableProperty<?> property, boolean raw) {

    DbColumnReference column = DbEntityNameMapper.get().getColumn(property);
    DbQualifiedName columnName = column.getRawName();
    return getColumnName(property, columnName, column, raw);
  }

  private DbQualifiedName getColumnName(PropertyPath<?> property, DbQualifiedName name, DbColumnReference column,
      boolean raw) {

    String localName = name.getName();
    ReadablePath parent = property.parentPath();
    if (parent != null) {
      PathBuilder builder = PathBuilder.of();
      parent.path(builder);
      builder.add(localName);
      localName = builder.toString();
    }
    DbQualifiedName result = DbQualifiedName.ofParsed(localName);
    if (!raw) {
      result = getName(result, column);
    }
    return result;
  }

  /// @param property the [PropertyPath] to derive the column name from.
  /// @param column the [DbColumnReference] [owning][DbColumnReference#getProperty()]the given
  ///        [PropertyPath]. May be `null` if not available.
  /// @param raw `true` to get the raw name, `false` otherwise (get it
  ///        [formatted][#getName(DbQualifiedName, DbObject)] according to this naming strategy).
  /// @return the column name for the given [property][PropertyPath].
  public DbQualifiedName getColumnName(PropertyPath<?> property, DbColumnReference column, boolean raw) {

    if (property instanceof ReadableProperty<?> rp) {
      return getColumnName(rp, raw);
    } else {
      String localName = property.getName();
      DbQualifiedName name = DbQualifiedName.ofName(localName);
      if (column == null) {
        column = new DbColumnReferenceImpl(localName);
      }
      return getColumnName(property, name, column, raw);
    }
  }

  /// @param property the [PropertyPath] to derive the column name from.
  /// @param raw `true` to get the raw name, `false` otherwise (get it
  ///        [formatted][#getName(DbQualifiedName, DbObject)] according to this naming strategy).
  /// @param typeMapper the explicit [TypeMapper].
  /// @return the column name for the given [property][ReadableProperty].
  public DbQualifiedName getColumnName(PropertyPath<?> property, boolean raw, TypeMapper<?, ?> typeMapper) {

    return getColumnName(getColumnName(property, null, raw), typeMapper);
  }

  /// @param rawColumnName the raw column name to map.
  /// @param typeMapper the explicit [TypeMapper].
  /// @return the column name for the given [property][ReadableProperty].
  protected DbQualifiedName getColumnName(DbQualifiedName rawColumnName, TypeMapper<?, ?> typeMapper) {

    if (typeMapper == null) {
      return rawColumnName;
    }
    String suffix = typeMapper.getSuffix();
    if ((suffix != null) && !suffix.isEmpty()) {
      suffix = convertName(suffix, null); // TODO avoid null
    }
    String columnName = typeMapper.getNameMode().format(rawColumnName.getName(), suffix);
    columnName = clipName(columnName);
    return rawColumnName.withName(columnName);
  }

  /// @param constraint the [DbConstraint].
  /// @return the auto-generated [constraint name][DbConstraint#getName()].
  protected DbQualifiedName generateConstraintName(DbConstraint constraint) {

    StringBuilder sb = new StringBuilder();
    sb.append(constraint.getNamePrefix());
    DbColumnReference column = constraint.getSourceColumns().getFirst();
    DbQualifiedName tableName = getName(constraint.getSourceTable());
    sb.append(tableName.getName());
    if (!(constraint instanceof DbPrimaryKeyConstraint)) {
      sb.append('_');
      sb.append(getColumnName(column.getProperty(), true));
    }
    // PK_TABLE

    // FK_TABLE_CUSTOMER_ID_CUSTOMER_ID ???
    // .........*********** source column
    // .....................******** target table
    // ..............................** target column
    return DbQualifiedName.ofName(sb.toString());
  }

  /// @param index the [DbIndex].
  /// @return the auto-generated index name.
  protected DbQualifiedName generateIndexName(DbIndex index) {

    List<DbColumnReferenceWithSortOrder> columns = index.getColumns();
    DbQualifiedName tableName = getName(index.getTable());
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
      DbQualifiedName columnName = getName(column);
      sb.append(infix);
      sb.append(columnName.getName());
      if (sb.length() > max) {
        break;
      }
    }
    String indexName = sb.toString();
    return DbQualifiedName.ofName(clipName(indexName));
  }

  /// @param indexKind the [DbIndexKind].
  /// @return the static prefix for an [index name][#generateIndexName(DbIndex)].
  protected String getIndexNamePrefix(DbIndexKind indexKind) {

    if (indexKind.isUnique()) {
      return "UX";
    } else if (indexKind.isClustered()) {
      return "CX";
    } else {
      return "IX";
    }
  }

  /// @return the static infix for an [index name][#generateIndexName(DbIndex)] used to separate the columns.
  protected String getIndexNameInfix() {

    return "_";
  }

}
