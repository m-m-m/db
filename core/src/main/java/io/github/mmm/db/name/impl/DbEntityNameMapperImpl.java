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
import io.github.mmm.db.name.AbstractDbObject;
import io.github.mmm.db.name.DbEntityNameInfo;
import io.github.mmm.db.name.DbEntityNameMapper;
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
  public static class DbEntityNameInfoImpl extends AbstractDbObject implements DbEntityNameInfo {

    private final EntityBean entity;

    private final Map<String, DbColumnReference> propertyNameMap;

    private DbEntityNameInfoImpl(EntityBean entity) {

      BeanType type = entity.getType();
      String name = getDbName(type.getMetaInfo(), EntityBean.META_KEY_TABLE, "EntityBean", type.getQualifiedName());
      boolean java = false;
      if (name == null) {
        name = type.getStableName();
        java = type.getSimpleName().equals(name);
      }
      super(name, java);
      this.entity = entity;
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
    public DbColumnReference getColumnInfo(ReadableProperty<?> property) {

      assert (ReadableBean.from(property).getType() == this.entity.getType());
      return this.propertyNameMap.computeIfAbsent(property.getName(),
          _ -> new PropertyColumnReference(property, this.entity));
    }
  }

  /**
   * Implementation of {@link DbColumnReference}.
   */
  public static class PropertyColumnReference extends AbstractDbObject implements DbColumnReference {

    private final ReadableProperty<?> property;

    private PropertyColumnReference(ReadableProperty<?> property, ReadableBean bean) {

      String qName;
      if (bean != null) {
        qName = bean.getType().getQualifiedName();
      } else {
        qName = property.getClass().getName();
      }
      String columnName = getDbName(property.getMetadata().getMetaInfo(), EntityBean.META_KEY_COLUMN,
          "ReadableProperty", qName + "." + property.getName());
      boolean java = false;
      if (columnName == null) {
        columnName = property.getName();
        java = true;
      }
      super(columnName, java);
      this.property = property;
    }

    @Override
    public ReadableProperty<?> getProperty() {

      return this.property;
    }

  }
}
