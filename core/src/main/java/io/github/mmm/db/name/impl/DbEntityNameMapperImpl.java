/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.name.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.mmm.base.metainfo.MetaInfo;
import io.github.mmm.bean.BeanType;
import io.github.mmm.bean.ReadableBean;
import io.github.mmm.db.ddl.column.DbColumnReference;
import io.github.mmm.db.name.DbEntityNameInfo;
import io.github.mmm.db.name.DbEntityNameMapper;
import io.github.mmm.db.name.DbNamingStrategy;
import io.github.mmm.db.name.DbQualifiedName;
import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.property.ReadableProperty;

/**
 * Implementation of {@link DbEntityNameMapper}.
 *
 * @since 1.0.0
 */
public class DbEntityNameMapperImpl implements DbEntityNameMapper {

  /** The singleton instance. */
  public static final DbEntityNameMapperImpl INSTANCE = new DbEntityNameMapperImpl();

  private static final Logger LOG = LoggerFactory.getLogger(DbEntityNameMapperImpl.class);

  private final Map<BeanType, DbEntityNameInfoImpl> entityNameMap;

  private DbEntityNameMapperImpl() {

    super();
    this.entityNameMap = new ConcurrentHashMap<>();
  }

  /**
   * @param bean the {@link EntityBean}.
   * @return the {@link DbEntityNameInfo} for the given {@link EntityBean}.
   */
  @Override
  public DbEntityNameInfoImpl getTable(EntityBean bean) {

    BeanType type = bean.getType();
    return this.entityNameMap.computeIfAbsent(type, _ -> new DbEntityNameInfoImpl(bean));
  }

  @Override
  public DbColumnReference getColumn(ReadableProperty<?> property) {

    ReadableBean bean = ReadableBean.from(property);
    if (bean instanceof EntityBean entity) {
      return getTable(entity).getColumnInfo(property);
    }
    return new PropertyColumnReference(property, bean);
  }

  private static String getDbName(MetaInfo metaInfo, String key, String type, String javaName) {

    String name = metaInfo.get(key);
    if (name != null) {
      if (isValidDbName(name)) {
        return name;
      }
      LOG.warn("Invalid name '{}' for {} {} from meta-info key {}", name, type, javaName, key);
    }
    return null;
  }

  private static boolean isValidDbName(String name) {

    if ((name == null) || name.isEmpty()) {
      return false;
    }
    int length = name.length();
    int i = 0;
    char quote = 0;
    while (i < length) {
      char c = name.charAt(i++);
      if ((c == '"') || (c == '`')) {
        if (quote == 0) {

        }
      }
    }
    return true;
  }

  /**
   * Implementation of {@link DbEntityNameInfo}.
   */
  public static class DbEntityNameInfoImpl implements DbEntityNameInfo {

    private final EntityBean entity;

    private final DbQualifiedName qualifiedName;

    private final boolean java;

    private final Map<String, DbColumnReference> propertyNameMap;

    private DbEntityNameInfoImpl(EntityBean entity) {

      super();
      this.entity = entity;
      BeanType type = entity.getType();
      String name = getDbName(type.getMetaInfo(), EntityBean.META_KEY_TABLE, "EntityBean", type.getQualifiedName());
      if (name == null) {
        name = type.getStableName();
        this.java = type.getSimpleName().equals(name);
      } else {
        this.java = false;
      }
      if (this.java) {
        this.qualifiedName = new DbQualifiedName(name);
      } else {
        this.qualifiedName = DbQualifiedName.of(name);
      }
      this.propertyNameMap = new ConcurrentHashMap<>();
      addProperties();
    }

    private void addProperties() {

      for (ReadableProperty<?> property : this.entity.getProperties()) {
        if (property.isTransient()) {
          continue;
        }
        this.propertyNameMap.put(property.getName(), new PropertyColumnReference(property, this.entity));
      }
    }

    @Override
    public EntityBean getEntity() {

      return this.entity;
    }

    @Override
    public DbQualifiedName getQualifiedName(DbNamingStrategy namingStrategy) {

      if (namingStrategy == null) {
        return this.qualifiedName;
      }
      return namingStrategy.getTableName(this);
    }

    @Override
    public boolean hasJavaName() {

      return this.java;
    }

    @Override
    public DbColumnReference getColumnInfo(ReadableProperty<?> property) {

      assert (ReadableBean.from(property).getType() == this.entity.getType());
      return this.propertyNameMap.computeIfAbsent(property.getName(),
          _ -> new PropertyColumnReference(property, this.entity));
    }
  }

  /**
   * Implementation of {@link DbColumnReference}.
   */
  public static class PropertyColumnReference implements DbColumnReference {

    private final ReadableProperty<?> property;

    private final String name;

    private final boolean java;

    private PropertyColumnReference(ReadableProperty<?> property, ReadableBean bean) {

      super();
      this.property = property;
      String qName;
      if (bean != null) {
        qName = bean.getType().getQualifiedName();
      } else {
        qName = property.getClass().getName();
      }
      String columnName = getDbName(property.getMetadata().getMetaInfo(), EntityBean.META_KEY_COLUMN,
          "ReadableProperty", qName + "." + property.getName());
      if (columnName == null) {
        this.name = property.getName();
        this.java = true;
      } else {
        this.name = columnName;
        this.java = false;
      }
    }

    @Override
    public ReadableProperty<?> getProperty() {

      return this.property;
    }

    @Override
    public String getName(DbNamingStrategy namingStrategy) {

      if (namingStrategy == null) {
        return this.name;
      }
      return namingStrategy.getColumnName(this);
    }

    @Override
    public boolean hasJavaName() {

      return this.java;
    }

  }
}
