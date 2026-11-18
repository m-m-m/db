/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.ddl.index.impl;

import java.util.List;

import io.github.mmm.db.ddl.column.DbColumnReferenceWithSortOrder;
import io.github.mmm.db.ddl.index.DbIndexKind;
import io.github.mmm.db.ddl.index.DbIndexMetadata;
import io.github.mmm.db.ddl.table.DbTableReference;
import io.github.mmm.db.name.DbQualifiedName;
import io.github.mmm.entity.bean.EntityBean;

/// Implementation of [DbIndexMetadata].
///
/// @since 1.0.0
public class DbIndexMetadataImpl extends DbIndexImpl implements DbIndexMetadata {

  private final boolean statistics;

  private final long cardinality;

  private final String filterCondition;

  /// The constructor.
  ///
  /// @param name the [name][#getName()] of the index.
  /// @param table the owning [table][#getTable()].
  /// @param kind the [DbIndexKind].
  /// @param statistics the [statistics][#isStatistics()] flag.
  /// @param cardinality the [cardinality][#getCardinality()].
  /// @param filterCondition the [filter condition][#getFilterCondition()].
  /// @param columns the [columns][#getColumns()].
  public DbIndexMetadataImpl(DbQualifiedName name, DbTableReference<EntityBean> table, DbIndexKind kind,
      boolean statistics, long cardinality, String filterCondition, List<DbColumnReferenceWithSortOrder> columns) {

    super(name, table, kind, columns);
    this.statistics = statistics;
    this.cardinality = cardinality;
    this.filterCondition = filterCondition;
  }

  @Override
  public boolean isStatistics() {

    return this.statistics;
  }

  @Override
  public long getCardinality() {

    return this.cardinality;
  }

  @Override
  public String getFilterCondition() {

    return this.filterCondition;
  }

}
