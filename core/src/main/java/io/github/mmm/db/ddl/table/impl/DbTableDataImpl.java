package io.github.mmm.db.ddl.table.impl;

import java.util.Objects;

import io.github.mmm.db.ddl.table.DbTableData;
import io.github.mmm.db.ddl.table.DbTableType;
import io.github.mmm.db.name.DbQualifiedName;
import io.github.mmm.entity.bean.EntityBean;

/**
 * Implementation of {@link DbTableData}.
 *
 * @since 1.0.0
 */
public class DbTableDataImpl extends DbTableReferenceImpl<EntityBean> implements DbTableData {

  private final String comment;

  /** @see #getType() */
  protected final DbTableType type;

  /**
   * The constructor.
   *
   * @param name the {@link DbTableImpl#getQualifiedName() qualified name}.
   * @param comment the {@link DbTableImpl#getComment() comment}
   * @param type the {@link DbTableImpl#getType() table type}.
   */
  public DbTableDataImpl(DbQualifiedName name, String comment, DbTableType type) {

    super(name);
    Objects.requireNonNull(type);
    if (comment == null) {
      comment = "";
    }
    this.comment = comment;
    this.type = type;
  }

  @Override
  public String getComment() {

    return this.comment;
  }

  @Override
  public DbTableType getType() {

    return this.type;
  }

}
