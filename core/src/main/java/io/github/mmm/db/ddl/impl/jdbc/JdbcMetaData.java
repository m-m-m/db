/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.ddl.impl.jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.mmm.base.collection.ListBuilder;
import io.github.mmm.base.exception.DuplicateObjectException;
import io.github.mmm.base.exception.ObjectMismatchException;
import io.github.mmm.base.sort.SortOrder;
import io.github.mmm.db.ddl.DbMetaData;
import io.github.mmm.db.ddl.column.DbColumn;
import io.github.mmm.db.ddl.column.DbColumnReference;
import io.github.mmm.db.ddl.column.impl.DbColumnImpl;
import io.github.mmm.db.ddl.column.impl.DbColumnReferenceImpl;
import io.github.mmm.db.ddl.column.impl.DbColumnReferenceWithSortOrderImpl;
import io.github.mmm.db.ddl.constraint.DbForeignKeyConstraint;
import io.github.mmm.db.ddl.constraint.DbPrimaryKeyConstraint;
import io.github.mmm.db.ddl.constraint.impl.DbForeignKeyConstraintBuilder;
import io.github.mmm.db.ddl.index.DbIndexMetadata;
import io.github.mmm.db.ddl.index.impl.DbIndexMetadataBuilder;
import io.github.mmm.db.ddl.table.DbTable;
import io.github.mmm.db.ddl.table.DbTableData;
import io.github.mmm.db.ddl.table.DbTableReference;
import io.github.mmm.db.ddl.table.DbTableType;
import io.github.mmm.db.ddl.table.impl.DbTableDataImpl;
import io.github.mmm.db.name.DbQualifiedName;
import io.github.mmm.db.type.DbType;

/**
 * Implementation of {@link DbMetaData} for JDBC.
 *
 * @since 1.0.0
 */
public class JdbcMetaData implements DbMetaData {

  private static final Logger LOG = LoggerFactory.getLogger(JdbcMetaData.class);

  private static final String RS_TABLE_CATALOG = "TABLE_CAT";

  private static final String RS_TABLE_SCHEMA = "TABLE_SCHEM";

  private static final String RS_TABLE_NAME = "TABLE_NAME";

  private static final String RS_TABLE_TYPE = "TABLE_TYPE";

  private static final String RS_REMARKS = "REMARKS";

  private static final String RS_COLUMN_NAME = "COLUMN_NAME";

  private static final String RS_COLUMN_DATA_TYPE = "DATA_TYPE"; // SQL type (int)

  private static final String RS_COLUMN_TYPE_NAME = "TYPE_NAME";

  private static final String RS_COLUMN_SIZE = "COLUMN_SIZE";

  private static final String RS_COLUMN_DECIMAL_DIGITS = "DECIMAL_DIGITS";

  private static final String RS_COLUMN_NULLABLE = "IS_NULLABLE";

  private static final String RS_INDEX_TYPE = "TYPE";

  private static final String RS_INDEX_NAME = "INDEX_NAME";

  private static final String RS_ORDINAL_POSITION = "ORDINAL_POSITION";

  private static final String RS_NON_UNIQUE = "NON_UNIQUE";

  private static final String RS_CARDINALITY = "CARDINALITY";

  private static final String RS_FILTER_CONDITION = "FILTER_CONDITION";

  private static final String RS_ASC_OR_DESC = "ASC_OR_DESC";

  private static final String RS_FK_NAME = "FK_NAME";

  private static final String RS_FK_COLUMN_NAME = "FKCOLUMN_NAME";

  private static final String RS_PK_NAME = "PK_NAME";

  private final Connection connection;

  private final DatabaseMetaData metaData;

  private String currentCatalog;

  private String currentSchema;

  /**
   * The constructor.
   *
   * @param connection the JDBC {@link Connection}.
   */
  public JdbcMetaData(Connection connection) {

    super();
    try {
      this.connection = connection;
      this.metaData = connection.getMetaData();
    } catch (SQLException e) {
      throw new IllegalStateException(e);
    }
  }

  @Override
  public String getCurrentCatalog() {

    if (this.currentCatalog == null) {
      try {
        String catalog = this.connection.getCatalog();
        if (catalog == null) {
          this.currentCatalog = "";
        } else {
          this.currentCatalog = catalog;
        }
      } catch (SQLException e) {
        throw new IllegalStateException(e);
      }
    }
    if (this.currentCatalog.isEmpty()) {
      return null;
    }
    return this.currentCatalog;
  }

  @Override
  public String getCurrentSchema() {

    if (this.currentSchema == null) {
      try {
        String schema = this.connection.getSchema();
        if (schema == null) {
          this.currentSchema = "";
        } else {
          this.currentSchema = schema;
        }
      } catch (SQLException e) {
        throw new IllegalStateException(e);
      }
    }
    if (this.currentSchema.isEmpty()) {
      return null;
    }
    return this.currentSchema;
  }

  @Override
  public Iterable<DbTableData> getTables(String name) {

    return getTables(getCurrentCatalog(), getCurrentSchema(), name);
  }

  @Override
  public List<DbTableData> getTables(String catalog, String schema, String name) {

    LOG.debug("Retrieving table data via JDBC.");
    try {
      List<DbTableData> tables = new ArrayList<>();
      try (ResultSet resultSet = this.metaData.getTables(catalog, schema, name, null)) {
        while (resultSet.next()) {
          DbTableDataImpl tableData = extractTableData(resultSet);
          LOG.trace("Retieved table data: {}", tableData);
          tables.add(tableData);
        }
      }
      LOG.debug("Retrieved {} tables via JDBC.", tables.size());
      return tables;
    } catch (SQLException e) {
      throw new IllegalStateException(e);
    }
  }

  @Override
  public DbTable getTable(DbQualifiedName qName) {

    try {
      Objects.requireNonNull(qName);
      String catalog = qName.getCatalog();
      String schema = qName.getSchema();
      String name = qName.getName();
      if ((catalog == null) && (schema == null)) {
        catalog = getCurrentCatalog();
        schema = getCurrentSchema();
      }
      return getTable(catalog, schema, name);
    } catch (SQLException e) {
      throw new IllegalStateException(e);
    }
  }

  @Override
  public DbTable getTable(String name) {

    try {
      return getTable(getCurrentCatalog(), getCurrentSchema(), name);
    } catch (SQLException e) {
      throw new IllegalStateException(e);
    }
  }

  private DbTable getTable(String catalog, String schema, String name) throws SQLException {

    LOG.debug("Retrieving table {} (catalog={}, schema={}).", name, catalog, schema);
    DbTableData tableData = null;
    try (ResultSet resultSet = this.metaData.getTables(catalog, schema, name, null)) {
      while (resultSet.next()) {
        if (tableData == null) {
          tableData = extractTableData(resultSet);
        } else {
          DbTableData duplicate = extractTableData(resultSet);
          throw new DuplicateObjectException(duplicate, name, tableData);
        }
      }
    }
    if (tableData == null) {
      return null;
    }
    return getTable(tableData);
  }

  @Override
  public DbTable getTable(DbTableData tableData) {

    DbQualifiedName tableName = tableData.getQualifiedName();
    LOG.trace("Retrieving columns table {}.", tableName);
    try {
      Objects.requireNonNull(tableData);
      assert (tableData instanceof DbTableDataImpl);
      List<DbColumn> columns;
      String catalog = tableName.getCatalog();
      String schema = tableName.getSchema();
      String name = tableName.getName();
      try (ResultSet resultSet = this.metaData.getColumns(catalog, schema, name, "%")) {
        columns = extractColumns(resultSet);
      }
      LOG.trace("Retrieved {} columns.", columns.size());
      return new JdbcTableImpl(tableData, columns, this.metaData);
    } catch (SQLException e) {
      throw new IllegalStateException(e);
    }
  }

  private static List<DbColumn> extractColumns(ResultSet resultSet) throws SQLException {

    ListBuilder<DbColumn> builder = new ListBuilder<>();
    while (resultSet.next()) {
      DbColumnImpl column = extractColumn(resultSet);
      builder.add(column);
    }
    return builder.build();
  }

  private static DbColumnImpl extractColumn(ResultSet resultSet) throws SQLException {

    String name = resultSet.getString(RS_COLUMN_NAME);
    String comment = resultSet.getString(RS_REMARKS);
    Boolean nullable = asBoolean(resultSet.getString(RS_COLUMN_NULLABLE));
    int typeCode = resultSet.getInt(RS_COLUMN_DATA_TYPE);
    String typeName = resultSet.getString(RS_COLUMN_TYPE_NAME);
    int size = resultSet.getInt(RS_COLUMN_SIZE);
    int decimalDigits = resultSet.getInt(RS_COLUMN_DECIMAL_DIGITS);
    DbType<?, ?, ?> type = DbType.get(typeCode);
    if (type == null) {
      type = DbType.get(typeName);
    } else {
      assert Objects.equals(typeName, type.getName()) : typeName + "!=" + type;
    }
    if (size > 0) {
      type = type.withSize(size);
    }
    if (decimalDigits > 0) {
      type = type.withScale(decimalDigits);
    }
    return new DbColumnImpl(name, comment, nullable, type);
  }

  private static Boolean asBoolean(String value) {

    if (value == null) {
      return null;
    }
    if ("TRUE".equalsIgnoreCase(value) || "YES".equalsIgnoreCase(value)) {
      return Boolean.TRUE;
    } else if ("FALSE".equalsIgnoreCase(value) || "NO".equalsIgnoreCase(value)) {
      return Boolean.FALSE;
    } else {
      throw new IllegalArgumentException(value);
    }
  }

  private static DbTableDataImpl extractTableData(ResultSet resultSet) throws SQLException {

    DbQualifiedName qName = extractTableName(resultSet);
    String type = resultSet.getString(RS_TABLE_TYPE);
    String comment = resultSet.getString(RS_REMARKS);
    return new DbTableDataImpl(qName, comment, DbTableType.of(type));
  }

  private static DbQualifiedName extractTableName(ResultSet resultSet) throws SQLException {

    String catalog = resultSet.getString(RS_TABLE_CATALOG);
    String schema = resultSet.getString(RS_TABLE_SCHEMA);
    String name = resultSet.getString(RS_TABLE_NAME);
    DbQualifiedName qName = new DbQualifiedName(name, schema, catalog);
    return qName;
  }

  static DbPrimaryKeyConstraint extractPrimaryKey(DbTable table, DatabaseMetaData metaData) {

    DbQualifiedName qualifiedName = table.getQualifiedName();
    String catalog = qualifiedName.getCatalog();
    String schema = qualifiedName.getSchema();
    String name = qualifiedName.getName();
    try (ResultSet resultSet = metaData.getPrimaryKeys(catalog, schema, name)) {
      return extractPrimaryKey(resultSet, table);
    } catch (SQLException e) {
      throw new IllegalStateException(e);
    }
  }

  private static DbPrimaryKeyConstraint extractPrimaryKey(ResultSet resultSet, DbTableReference<?> tableReference)
      throws SQLException {

    ListBuilder<DbColumnReference> columnBuilder = new ListBuilder<>();
    String name = null;
    while (resultSet.next()) {
      String currentName = resultSet.getString(RS_PK_NAME);
      if (name == null) {
        name = currentName;
      } else if (!name.equals(currentName)) {
        throw new ObjectMismatchException(currentName, name);
      }
      String columnName = resultSet.getString(RS_COLUMN_NAME);
      columnBuilder.add(DbColumnReference.of(columnName));
    }
    if (columnBuilder.isEmpty()) {
      return null;
    }
    return DbPrimaryKeyConstraint.of(name, tableReference, columnBuilder.build());
  }

  /**
   * @param qualifiedName the {@link DbQualifiedName} of the {@link DbTable table}.
   * @param metaData the {@link DatabaseMetaData}.
   * @return the {@link DbObjectContainer} with the {@link DbForeignKey}s for the specified {@link DbTable table}.
   */
  static List<DbForeignKeyConstraint> extractForeignKeys(DbTable table, DatabaseMetaData metaData) {

    DbQualifiedName tableName = table.getQualifiedName();
    String catalog = tableName.getCatalog();
    String schema = tableName.getSchema();
    String name = tableName.getName();
    try (ResultSet resultSet = metaData.getImportedKeys(catalog, schema, name)) {
      Map<String, DbForeignKeyConstraintBuilder> fkBuilderMap = new HashMap<>();
      while (resultSet.next()) {
        String fkName = resultSet.getString(RS_FK_NAME);
        DbQualifiedName targetTableName = extractTableName(resultSet);
        DbForeignKeyConstraintBuilder builder = fkBuilderMap.computeIfAbsent(fkName,
            _ -> new DbForeignKeyConstraintBuilder(fkName, table));
        DbColumnReferenceImpl sourceColumn = new DbColumnReferenceImpl(resultSet.getString(RS_COLUMN_NAME));
        builder.getSourceColumnBuilder().add(sourceColumn);
        builder.setTargetTable(targetTableName);
        DbColumnReferenceImpl targetColumn = new DbColumnReferenceImpl(resultSet.getString(RS_FK_COLUMN_NAME));
        builder.getTargetColumnBuilder().add(targetColumn);
      }
      int size = fkBuilderMap.size();
      if (size == 0) {
        return List.of();
      } else if (size == 1) {
        return List.of(fkBuilderMap.values().iterator().next().build());
      } else {
        DbForeignKeyConstraint[] array = new DbForeignKeyConstraint[fkBuilderMap.size()];
        int i = 0;
        for (DbForeignKeyConstraintBuilder builder : fkBuilderMap.values()) {
          array[i++] = builder.build();
        }
        return List.of(array);
      }
    } catch (SQLException e) {
      throw new IllegalStateException(e);
    }
  }

  static List<DbIndexMetadata> extractIndexes(DbTable table, DatabaseMetaData metaData) {

    try {
      ListBuilder<DbIndexMetadata> builder = new ListBuilder<>();
      DbQualifiedName tableName = table.getQualifiedName();
      String catalog = tableName.getCatalog();
      String schema = tableName.getSchema();
      String name = tableName.getName();
      try (ResultSet resultSet = metaData.getIndexInfo(catalog, schema, name, false, true)) {
        Map<String, DbIndexMetadataBuilder> indexBuilderMap = new HashMap<>();
        while (resultSet.next()) {
          String indexName = resultSet.getString(RS_INDEX_NAME);
          DbIndexMetadataBuilder indexBuilder = indexBuilderMap.computeIfAbsent(indexName,
              _ -> new DbIndexMetadataBuilder(indexName, table));
          extractIndex(resultSet, indexBuilder);
        }
        for (DbIndexMetadataBuilder indexBuilder : indexBuilderMap.values()) {
          builder.add(indexBuilder.build());
        }
        return builder.build();
      }
    } catch (SQLException e) {
      throw new IllegalStateException(e);
    }
  }

  private static void extractIndex(ResultSet resultSet, DbIndexMetadataBuilder indexBuilder) throws SQLException {

    int typeCode = resultSet.getInt(RS_INDEX_TYPE);
    indexBuilder.setTypeCode(typeCode);
    String column = resultSet.getString(RS_COLUMN_NAME);
    SortOrder order = extractSortOrder(resultSet);
    int ordinalPosition = resultSet.getInt(RS_ORDINAL_POSITION);
    indexBuilder.getColumnsBuilder().add(new DbColumnReferenceWithSortOrderImpl(column, order), ordinalPosition);
    boolean nonUnique = resultSet.getBoolean(RS_NON_UNIQUE);
    indexBuilder.setUnique(!nonUnique);
    long cardinality = resultSet.getLong(RS_CARDINALITY);
    indexBuilder.setCardinality(cardinality);
    String filterCondition = resultSet.getString(RS_FILTER_CONDITION);
    indexBuilder.setFilterCondition(filterCondition);
  }

  private static SortOrder extractSortOrder(ResultSet resultSet) throws SQLException {

    String orderCode = resultSet.getString(RS_ASC_OR_DESC);
    SortOrder order = null;
    if ("A".equalsIgnoreCase(orderCode)) {
      order = SortOrder.ASC;
    } else if ("D".equalsIgnoreCase(orderCode)) {
      order = SortOrder.DESC;
    }
    return order;
  }

}
