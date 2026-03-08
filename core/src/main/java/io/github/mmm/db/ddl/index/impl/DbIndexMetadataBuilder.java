/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.ddl.index.impl;

import io.github.mmm.base.collection.ListBuilder;
import io.github.mmm.base.container.ContainerMapBuilder;
import io.github.mmm.base.lang.Builder;
import io.github.mmm.db.ddl.column.DbColumnReferenceWithSortOrder;
import io.github.mmm.db.ddl.index.DbIndexKindType;
import io.github.mmm.db.ddl.index.DbIndexMetadata;
import io.github.mmm.db.ddl.index.DbIndexType;
import io.github.mmm.db.ddl.table.DbTableReference;
import io.github.mmm.entity.bean.EntityBean;

/**
 * {@link Builder} of {@link DbIndexMetadata}.
 */
public class DbIndexMetadataBuilder implements Builder<DbIndexMetadata> {

  private final String name;

  private final DbTableReference<EntityBean> table;

  private final ListBuilder<DbColumnReferenceWithSortOrder> columnsBuilder;

  private int typeCode;

  private boolean unique;

  private String filterCondition;

  private long cardinality;

  private boolean populated;

  /**
   * JDBC type code that identifies table statistics that are returned in conjunction with a table's index descriptions.
   */
  public static final int TYPE_STATISTIC = 0;

  /** JDBC type code that identifies a clustered index. */
  public static final int TYPE_CLUSTERED = 1;

  /** JDBC type code that identifies a hashed index. */
  public static final int TYPE_HASHED = 2;

  /** JDBC type code that identifies some other style of index. */
  public static final int TYPE_OTHER = 3;

  /**
   * The constructor.
   *
   * @param name the name of the {@link DbIndexMetadata index} to build.
   * @param table the owning {@link DbIndexMetadata#getTable() table}.
   */
  public DbIndexMetadataBuilder(String name, DbTableReference<EntityBean> table) {

    super();
    this.name = name;
    this.table = table;
    this.columnsBuilder = new ListBuilder<>();
  }

  /**
   * @return the {@link ContainerMapBuilder} for {@link DbIndexMetadata#getColumns()}.
   */
  public ListBuilder<DbColumnReferenceWithSortOrder> getColumnsBuilder() {

    return this.columnsBuilder;
  }

  /**
   * @param typeCode the JDBC type code. See e.g. {@link #TYPE_CLUSTERED}.
   */
  public void setTypeCode(int typeCode) {

    this.typeCode = typeCode;
  }

  /**
   * @param unique new value of {@link DbIndexMetadata#isUnique()}.
   */
  public void setUnique(boolean unique) {

    if (this.populated) {
      assert (this.unique == unique);
    }
    this.unique = unique;
  }

  /**
   * @param cardinality new value of {@link DbIndexMetadata#getCardinality()}.
   */
  public void setCardinality(long cardinality) {

    if (this.populated) {
      assert (this.cardinality == cardinality);
    }
    this.cardinality = cardinality;
  }

  /**
   * @param filterCondition new value of {@link DbIndexMetadata#getFilterCondition()}.
   */
  public void setFilterCondition(String filterCondition) {

    this.filterCondition = filterCondition;
  }

  /**
   * To be called if the regular values are populated from the first result.
   */
  public void setPopulated() {

    this.populated = true;
  }

  @Override
  public DbIndexMetadata build() {

    boolean statistics = this.typeCode == TYPE_STATISTIC;
    boolean clustered = this.typeCode == TYPE_CLUSTERED;
    DbIndexType type = DbIndexType.NONE;
    if (this.typeCode == TYPE_HASHED) {
      type = DbIndexType.HASH;
    }
    DbIndexKindType kind = new DbIndexKindType(this.unique, clustered, type);
    return new DbIndexMetadataImpl(this.name, this.table, kind, statistics, this.cardinality, this.filterCondition,
        this.columnsBuilder.build());
  }

}
