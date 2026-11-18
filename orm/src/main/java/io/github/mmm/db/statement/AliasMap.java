/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.statement;

import java.util.HashMap;
import java.util.Map;

import io.github.mmm.base.exception.DuplicateObjectException;
import io.github.mmm.base.text.CaseHelper;
import io.github.mmm.bean.ReadableBean;
import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.property.ReadableProperty;
import io.github.mmm.property.WritableProperty;
import io.github.mmm.property.criteria.PropertyPathParser;
import io.github.mmm.scanner.CharStreamScanner;
import io.github.mmm.value.PropertyPath;
import io.github.mmm.value.ReadablePath;

/// Class to [map][Map#get(Object)] from [alias][AbstractEntitiesClause#getAlias()] to
/// [entity][AbstractEntitiesClause#getEntity()]. A single [AliasMap] instance is used per [DbStatement]
/// to ensure unique [aliases][AbstractEntitiesClause#getAlias()].
///
/// @since 1.0.0
public class AliasMap implements PropertyPathParser {

  private final Map<String, EntityBean> aliasMap;

  /// The constructor.
  public AliasMap() {

    super();
    this.aliasMap = new HashMap<>();
  }

  /// @param alias the [alias][AbstractEntitiesClause#getAlias()] to remove.
  /// @return the removed [EntityBean] or `null` if none was present or mapped.
  public EntityBean remove(String alias) {

    return this.aliasMap.remove(alias);
  }

  /// @param alias the [alias][AbstractEntitiesClause#getAlias()] to check.
  /// @return `true` if the given `alias` is currently mapped, `false` otherwise.
  public boolean contains(String alias) {

    return this.aliasMap.containsKey(alias);
  }

  /// @param alias the [alias][AbstractEntitiesClause#getAlias()] to get the mapping for.
  /// @return the mapped [EntityBean] that is associated with the given `alias`. May be `null`.
  public EntityBean getEntity(String alias) {

    return this.aliasMap.get(alias);
  }

  /// @param alias the [alias][AbstractEntitiesClause#getAlias()] to get the mapping for.
  /// @return the mapped [EntityBean] that is associated with the given `alias`.
  /// @throws IllegalArgumentException if no such [EntityBean] could be found.
  public EntityBean getRequiredEntity(String alias) {

    EntityBean entity = getEntity(alias);
    if (entity == null) {
      throw new IllegalArgumentException("Could not find entity for alias '" + alias + "'.");
    }
    return entity;
  }

  /// @param alias the [alias][AbstractEntitiesClause#getAlias()] to bind.
  /// @param entity the [EntityBean] to bind to.
  /// @return the previously mapped [EntityBean] or `null`.
  public EntityBean put(String alias, EntityBean entity) {

    if (this.aliasMap.containsKey(alias)) {
      if (entity == null) {
        throw new DuplicateObjectException(alias);
      }
      throw new DuplicateObjectException(entity, alias, this.aliasMap.get(alias));
    }
    return this.aliasMap.put(alias, entity);
  }

  /// @param entityClause the [AbstractEntityClause].
  /// @return a unique [alias][AbstractEntityClause#getAlias()] generated for the given
  ///         [AbstractEntityClause].
  public String createAlias(AbstractEntityClause<?, ?, ?> entityClause) {

    String entityName = CaseHelper.uncapitalize(entityClause.getName().getName());
    int len = 1;
    int length = entityName.length();
    String alias;
    while (len < length) {
      alias = entityName.substring(0, len);
      if (!contains(alias)) {
        return alias;
      }
      len++;
    }
    int i = 0;
    while (i < 100) {
      alias = entityName + i;
      if (!contains(alias)) {
        return alias;
      }
      i++;
    }
    throw new IllegalStateException();
  }

  @Override
  public PropertyPath<?> parse(CharStreamScanner scanner, String segment) {

    if (segment == null) {
      segment = PropertyPathParser.parseSegment(scanner);
      scanner.requireOne('.');
    }
    ReadableBean bean = getRequiredEntity(segment);
    ReadableProperty<?> property = null;
    do {
      segment = PropertyPathParser.parseSegment(scanner);
      if (property == null) {
        property = bean.getRequiredProperty(segment);
      } else {
        property = EntityPathParser.traverseProperty(property, segment);
      }
    } while (scanner.expectOne('.'));
    assert (property != null);
    return property;
  }

  /// @param path the [ReadablePath] to resolve (e.g. "alias.Property.ChildProperty").
  /// @return the [WritableProperty] traversed from the given [path][ReadablePath] from its root segment as
  ///         alias.
  public ReadableProperty<?> resolvePath(ReadablePath path) {

    ReadablePath parent = path.parentPath();
    if (parent == null) {
      return null;
    } else {
      String segment = path.pathSegment();
      ReadableProperty<?> property = resolvePath(parent);
      if (property == null) {
        EntityBean entity = getRequiredEntity(parent.pathSegment());
        return entity.getRequiredProperty(segment);
      } else {
        return EntityPathParser.traverseProperty(property, segment);
      }
    }
  }

}
