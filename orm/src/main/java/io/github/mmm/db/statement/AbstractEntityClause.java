/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.statement;

import java.util.Map;
import java.util.Objects;

import io.github.mmm.db.ddl.table.DbTableReference;
import io.github.mmm.db.name.DbEntityNameMapper;
import io.github.mmm.db.name.DbQualifiedName;
import io.github.mmm.entity.bean.EntityBean;

/// A [AbstractEntityClause] is a [DbClause] of an SQL [DbStatement] that specifies the
/// [entity][#getEntity()] and/or [entity name][#getName()] (table) to operate on.
///
/// @param <R> type of the result. Only different from `<E>` for complex selects.
/// @param <E> type of the [entity][#getEntity()].
/// @param <SELF> type of this class itself.
/// @since 1.0.0
public abstract class AbstractEntityClause<R, E extends EntityBean, SELF extends AbstractEntityClause<R, E, SELF>>
    extends AbstractTypedClause<R, SELF> implements DbTableReference<E> {

  /// Name of property [#getName()] for marshalling.
  public static final String NAME_ENTITY = "entity";

  /// Name of property [alias][#getAlias()] for marshaling.
  public static final String NAME_ALIAS = "as";

  private final AliasMap aliasMap;

  /// @see #getName()
  protected transient E entity;

  private DbQualifiedName qualifiedName;

  private String alias;

  /// The constructor.
  ///
  /// @param aliasMap the [AliasMap].
  /// @param entity the [entity][#getEntity()] to operate on.
  /// @param entityName the [entity name][#getName()].
  protected AbstractEntityClause(AliasMap aliasMap, E entity, String entityName) {

    super();
    Objects.requireNonNull(aliasMap);
    this.aliasMap = aliasMap;
    setEntityPlain(entity, entityName);
  }

  /// @return the [Map] to [map][Map#get(Object)] from [alias][#getAlias()] to [entity][#getEntity()]. A single [Map] instance is used per [DbStatement] to ensure unique [aliases][#getAlias()].
  protected AliasMap getAliasMap() {

    return this.aliasMap;
  }

  @Override
  public E getEntity() {

    return this.entity;
  }

  void setEntity(E entity) {

    setEntity(entity, null);
  }

  void setEntityPlain(E entity, String entityName) {

    DbQualifiedName qName = null;
    if (entityName != null) {
      qName = DbQualifiedName.ofName(entityName);
    }
    setEntity(entity, qName);
  }

  void setEntity(E entity, DbQualifiedName entityName) {

    if (entityName != null) {
      this.qualifiedName = entityName;
    } else if ((entityName == null) && (entity != null)) {
      this.qualifiedName = DbEntityNameMapper.get().getTable(entity).getName();
    }
    this.entity = entity;
  }

  /// @param entityName new value of [#getName()].
  void setName(String entityName) {

    setEntityPlain(null, entityName);
  }

  /// @return alias the alias (variable name) for the [EntityBean] to query. Will be created lazily if not
  ///         [already created][#hasAlias()].
  /// @see #as(String)
  public String getAlias() {

    if (this.alias == null) {
      setAlias(this.aliasMap.createAlias(this));
    }
    return this.alias;
  }

  /// @return `true` if an [alias][#getAlias()] has already been assigned or created, `false` otherwise.
  public boolean hasAlias() {

    return (this.alias != null);
  }

  @Override
  public DbQualifiedName getRawName() {

    return this.qualifiedName;
  }

  /// @param tableName new value of [#getRawName()].
  public void setTableName(DbQualifiedName tableName) {

    this.qualifiedName = tableName;
  }

  /// **ATTENTION:** Only define an explicit alias when explicitly required for uniqueness (e.g. joining the same
  /// table). E.g. sqlite database does not support aliases in UPDATE statements. Your statement may therefore not be
  /// portable if you add pointless aliases in such situations.
  ///
  /// @param entityAlias the alias (variable name) for the [EntityBean] to query.
  /// @return this [DbClause] itself for fluent API calls.
  /// @see #getAlias()
  public SELF as(String entityAlias) {

    setAlias(entityAlias);
    return self();
  }

  private void setAlias(String entityAlias) {

    if (this.alias != null) {
      EntityBean old = this.aliasMap.remove(this.alias);
      assert (old == this.entity);
    }
    this.alias = entityAlias;
    if (entityAlias != null) {
      this.aliasMap.put(entityAlias, this.entity);
    }
    if (this.entity != null) {
      this.entity.pathSegment(entityAlias);
    }
  }

}
