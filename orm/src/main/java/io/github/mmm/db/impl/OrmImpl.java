/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

import io.github.mmm.bean.WritableBean;
import io.github.mmm.db.ddl.column.DbColumnReference;
import io.github.mmm.db.mapping.DbBeanMapper;
import io.github.mmm.db.mapping.DbMapper;
import io.github.mmm.db.mapping.UnmappedTypeException;
import io.github.mmm.db.mapping.impl.DbBeanMapperImpl;
import io.github.mmm.db.mapping.impl.DbMapperDbResult;
import io.github.mmm.db.mapping.impl.DbPropertyMapper;
import io.github.mmm.db.mapping.impl.DbPropertyMapperImpl;
import io.github.mmm.db.mapping.impl.DbSegmentMapper;
import io.github.mmm.db.name.DbNamingStrategy;
import io.github.mmm.db.orm.Orm;
import io.github.mmm.db.result.impl.DbResultValueObject;
import io.github.mmm.db.statement.select.SelectClause;
import io.github.mmm.db.statement.select.SelectEntityClause;
import io.github.mmm.db.statement.select.SelectProjectionClause;
import io.github.mmm.db.statement.select.SelectResultClause;
import io.github.mmm.db.statement.select.SelectSingleClause;
import io.github.mmm.entity.bean.typemapping.TypeMapping;
import io.github.mmm.entity.id.FkMapper;
import io.github.mmm.entity.id.Id;
import io.github.mmm.entity.link.Link;
import io.github.mmm.entity.property.id.IdProperty;
import io.github.mmm.entity.property.link.LinkProperty;
import io.github.mmm.property.ReadableProperty;
import io.github.mmm.property.WritableProperty;
import io.github.mmm.property.criteria.ProjectionProperty;
import io.github.mmm.value.CriteriaObject;
import io.github.mmm.value.PropertyPath;
import io.github.mmm.value.ReadablePath;
import io.github.mmm.value.converter.TypeMapper;

/**
 * Implementation of {@link Orm}.
 *
 * @since 1.0.0
 */
public class OrmImpl implements Orm {

  private final TypeMapping typeMapping;

  private final DbNamingStrategy namingStrategy;

  /**
   * The constructor.
   *
   * @param typeMapping the {@link TypeMapping}.
   * @param namingStrategy the {@link DbNamingStrategy}.
   */
  public OrmImpl(TypeMapping typeMapping, DbNamingStrategy namingStrategy) {

    super();
    this.typeMapping = typeMapping;
    this.namingStrategy = namingStrategy;
  }

  @Override
  public TypeMapping getTypeMapping() {

    return this.typeMapping;
  }

  @Override
  public DbNamingStrategy getNamingStrategy() {

    return this.namingStrategy;
  }

  @Override
  public <B extends WritableBean> DbBeanMapper<B> createBeanMapper(B bean) {

    // TODO add thread-safe caching
    return Orm.super.createBeanMapper(bean);
  }

  @Override
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public <B extends WritableBean> DbBeanMapper<B> createBeanMapper(B bean,
      Iterable<? extends ReadableProperty<?>> properties) {

    DbBeanMapperImpl<B> beanMapper = new DbBeanMapperImpl<>(bean);
    for (ReadableProperty<?> property : properties) {
      if (!property.isTransient()) {
        DbSegmentMapper valueMapper = createSegmentMapper(property);
        if (valueMapper != null) {
          DbPropertyMapper propertyMapper = new DbPropertyMapperImpl<>(property.getName(), valueMapper);
          beanMapper.add(propertyMapper);
        }
      }
    }
    return beanMapper;
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public <B extends WritableBean> DbBeanMapper<B> createBeanMapperProjection(B bean,
      Iterable<? extends ProjectionProperty<?>> properties) {

    DbBeanMapperImpl<B> beanMapper = new DbBeanMapperImpl<>(bean);
    for (ProjectionProperty<?> projectionProperty : properties) {
      ReadableProperty property = (ReadableProperty) projectionProperty.getProperty();
      String columnName = property.getName();
      DbColumnReference column = DbColumnReference.of(property);
      DbSegmentMapper valueMapper = createSegmentMapper(projectionProperty.getSelection(), columnName, column,
          property.getValueClass(), property);
      ReadablePath parent = property.parentPath();
      if (parent != null) {
        parent = parent.parentPath();
        if (parent instanceof WritableProperty) {

        }
      }
      DbPropertyMapper propertyMapper = new DbPropertyMapperImpl<>(property.getName(), valueMapper);
      beanMapper.add(propertyMapper);
    }
    return beanMapper;
  }

  @Override
  public <V> DbSegmentMapper<V, ?> createSegmentMapper(ReadableProperty<V> property) {

    DbColumnReference column = DbColumnReference.of(property);
    String columnName = this.namingStrategy.getColumnName(column);
    return createSegmentMapper(property, columnName, column, property.getValueClass(), property);
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  private <V> DbSegmentMapper<V, ?> createSegmentMapper(CriteriaObject<?> selection, String columnName,
      DbColumnReference column, Class<V> valueClass, ReadableProperty<V> property) {

    TypeMapper<V, ?> typeMapper = this.typeMapping.getTypeMapper(valueClass);
    if ((typeMapper == null) && (property != null)) {
      typeMapper = property.getTypeMapper();
    }
    if (typeMapper == null) {
      if (Id.class.equals(valueClass)) {
        Id<?> id = null;
        if (selection instanceof LinkProperty<?> linkProperty) {
          Link<?> link = linkProperty.get();
          if (link != null) {
            id = link.getId();
          }
        } else if (selection instanceof IdProperty<?> fkProperty) {
          id = fkProperty.get();
        }
        typeMapper = (TypeMapper) FkMapper.of(id);
      } else if (Collection.class.isAssignableFrom(valueClass)) {
        return null; // ignore
      } else if (Map.class.isAssignableFrom(valueClass)) {
        return null; // ignore
      } else {
        throw new UnmappedTypeException(valueClass, selection);
      }
    }
    return createSegmentMapper(selection, columnName, column, typeMapper);
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  private <V> DbSegmentMapper<V, ?> createSegmentMapper(CriteriaObject<?> selection, String columnName,
      DbColumnReference column, TypeMapper<V, ?> typeMapper) {

    DbSegmentMapper<V, ?> nextSegment = null;
    TypeMapper<V, ?> next = typeMapper.next();
    if (next != null) {
      nextSegment = createSegmentMapper(selection, columnName, column, next);
    }
    String suffix = typeMapper.getSuffix();
    if ((suffix != null) && !suffix.isEmpty()) {
      suffix = this.namingStrategy.getName(suffix, column);
    }
    String newColumnName = typeMapper.getNameMode().format(columnName, suffix);
    if (typeMapper.hasDeclaration()) {
      DbResultValueObject entry = new DbResultValueObject<>(newColumnName, null, typeMapper.getDeclaration());
      return new DbSegmentMapper<>(typeMapper, entry, nextSegment);
    } else {
      Class<?> targetType = typeMapper.getTargetType();
      if (isTooGeneric(targetType)) {
        throw new IllegalStateException(
            "Illegal TypeMapper " + typeMapper + " mapping to generic type " + targetType.getName());
      }
      try {
        DbSegmentMapper child = createSegmentMapper(selection, newColumnName, column, targetType, null);
        return new DbSegmentMapper<>(typeMapper, child, nextSegment);
      } catch (RuntimeException e) {
        throw new IllegalStateException(
            "Failed to create segment mapper for column '" + columnName + "' using mapper: " + typeMapper, e);
      }
    }
  }

  private static boolean isTooGeneric(Class<?> type) {

    return (type == Object.class) || (type == Comparable.class) || (type == Serializable.class);
  }

  /**
   * @param newNamingStrategy the new {@link DbNamingStrategy} to use.
   * @return a {@link OrmImpl} with the given {@link DbNamingStrategy}.
   */
  public OrmImpl withNamingStrategy(DbNamingStrategy newNamingStrategy) {

    if (this.namingStrategy == newNamingStrategy) {
      return this;
    }
    return new OrmImpl(this.typeMapping, newNamingStrategy);
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public <V> DbMapper<V> createMapper(SelectClause<V> select) {

    if (select instanceof SelectEntityClause<?> selectEntity) {
      return (DbMapper) createBeanMapper(selectEntity.getResultBean());
    } else if (select instanceof SelectResultClause) {
      return (DbMapper) DbMapperDbResult.INSTANCE;
    } else if (select instanceof SelectSingleClause<V> selectSingle) {
      return createMapper(selectSingle.getSelection());
    } else if (select instanceof SelectProjectionClause<?> selectProjection) {
      return (DbMapper) createBeanMapperProjection(selectProjection.getResultBean(), selectProjection.getSelections());
    }
    Objects.requireNonNull(select);
    throw new IllegalStateException("Unknown Select type: " + select.getClass().getName());
  }

  /**
   * @param selection the {@link SelectSingleClause#getSelection() single selection}.
   * @return the according {@link DbMapper}.
   */
  private <V> DbSegmentMapper<V, ?> createMapper(CriteriaObject<V> selection) {

    String columnName = null;
    if (selection instanceof ProjectionProperty<V> projectionProperty) {
      selection = projectionProperty.getSelection();
      columnName = columnName(projectionProperty.getProperty());
    } else if (selection instanceof PropertyPath<V> propertyPath) {
      columnName = columnName(propertyPath);
    }
    return createSegmentMapper(selection, columnName, null, null);
  }

  private String columnName(PropertyPath<?> propertyPath) {

    if (propertyPath instanceof ReadableProperty<?> property) {
      return this.namingStrategy.getColumnName(property);
    }
    return this.namingStrategy.getColumnName(propertyPath);
  }

}
