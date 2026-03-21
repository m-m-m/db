/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.typemapping;

import io.github.mmm.db.type.DbType;
import io.github.mmm.entity.bean.typemapping.ComposedTypeMapping;
import io.github.mmm.entity.id.FkMapper;
import io.github.mmm.entity.id.PkId;
import io.github.mmm.entity.id.PkIdLong;
import io.github.mmm.entity.id.PkIdString;
import io.github.mmm.entity.id.PkIdUuid;
import io.github.mmm.entity.id.RevisionedIdInstant;
import io.github.mmm.entity.id.RevisionedIdVersion;
import io.github.mmm.entity.link.Link;
import io.github.mmm.entity.link.LinkMapper;
import io.github.mmm.property.ReadableProperty;
import io.github.mmm.value.converter.TypeMapper;

/**
 * Extends {@link ComposedTypeMapping} for database dialect specific type mappings.
 *
 * @since 1.0.0
 */
public class DbTypeMapping extends ComposedTypeMapping {

  /**
   * The constructor.
   */
  public DbTypeMapping() {

    super();
    addPkId(PkIdLong.getEmpty());
    addPkId(PkIdString.getEmpty());
    addPkId(PkIdUuid.getEmpty());
    initTypes();
  }

  private void addPkId(PkId<?, ?, ?> pkId) {

    add(FkMapper.of(pkId));
    add(FkMapper.of(new RevisionedIdVersion<>(pkId, null)));
    add(FkMapper.of(new RevisionedIdInstant<>(pkId, null)));
  }

  private void initTypes() {

    addString();
    addInteger();
    addLong();
    addBoolean();
    addDouble();
    addFloat();
    addBigDecimal();
    addBigInteger();
    addInstant();
    addLocalDateTime();
    addLocalDate();
    addLocalTime();
    addOffsetDateTime();
    addOffsetTime();
    addYear();
    addDuration();
    addShort();
    addByte();
    addUuid();
  }

  /**
   * Add support for {@link String}.
   */
  protected void addString() {

    add(DbType.STRING, 65535, 0);
  }

  /**
   * Add support for {@link Integer}.
   */
  protected void addInteger() {

    add(DbType.INTEGER, 0, 0);
  }

  /**
   * Add support for {@link Long}.
   */
  protected void addLong() {

    add(DbType.LONG, 0, 0);
  }

  /**
   * Add support for {@link Boolean}.
   */
  protected void addBoolean() {

    add(DbType.BOOLEAN, 0, 0);
  }

  /**
   * Add support for {@link Double}.
   */
  protected void addDouble() {

    add(DbType.DOUBLE, 0, 0);
  }

  /**
   * Add support for {@link Float}.
   */
  protected void addFloat() {

    add(DbType.FLOAT, 0, 0);
  }

  /**
   * Add support for {@link java.math.BigDecimal}.
   */
  protected void addBigDecimal() {

    add(DbType.BIG_DECIMAL, getMaxSizeBigDecimal(), getMaxScaleBigDecimal());
  }

  /**
   * Add support for {@link java.math.BigInteger}.
   */
  protected void addBigInteger() {

    add(DbType.BIG_INTEGER, getMaxSizeBigDecimal(), 0);
  }

  /**
   * Add support for {@link java.time.Instant}.
   */
  protected void addInstant() {

    add(DbType.INSTANT, getMaxSizeTemporal(), 0);
  }

  /**
   * Add support for {@link java.time.LocalDateTime}.
   */
  protected void addLocalDateTime() {

    add(DbType.LOCAL_DATE_TIME, getMaxSizeTemporal(), 0);
  }

  /**
   * Add support for {@link java.time.LocalDate}.
   */
  protected void addLocalDate() {

    add(DbType.LOCAL_DATE, 0, 0);
  }

  /**
   * Add support for {@link java.time.LocalTime}.
   */
  protected void addLocalTime() {

    add(DbType.LOCAL_TIME, getMaxSizeTemporal(), 0);
  }

  /**
   * Add support for {@link java.time.OffsetDateTime}.
   */
  protected void addOffsetDateTime() {

    add(DbType.OFFSET_DATE_TIME, getMaxSizeTemporal(), 0);
  }

  /**
   * Add support for {@link java.time.OffsetTime}.
   */
  protected void addOffsetTime() {

    add(DbType.OFFSET_TIME, getMaxSizeTemporal(), 0);
  }

  /**
   * Add support for {@link java.time.Year}.
   */
  protected void addYear() {

    add(DbType.YEAR, 0, 0);
  }

  /**
   * Add support for {@link java.time.Duration}.
   */
  protected void addDuration() {

    add(DbType.DURATION, 0, 0);
  }

  /**
   * Add support for {@link Short}.
   */
  protected void addShort() {

    add(DbType.SHORT, 0, 0);
  }

  /**
   * Add support for {@link Byte}.
   */
  protected void addByte() {

    add(DbType.BYTE, 0, 0);
  }

  /**
   * Add support for {@link java.util.UUID}.
   */
  protected void addUuid() {

    add(DbType.UUID, 0, 0);
  }

  /**
   * @return the maximum {@link DbType#getSize() size} (digits) for {@link #addBigDecimal()} and
   *         {@link #addBigInteger()}.
   */
  protected int getMaxSizeBigDecimal() {

    return 38;
  }

  /**
   * @return the maximum {@link DbType#getScale() scale} for {@link #addBigDecimal()}.
   */
  protected int getMaxScaleBigDecimal() {

    return 38;
  }

  /**
   * @return the maximum {@link DbType#getSize() size} for temporal types.
   * @see #addInstant()
   * @see #addLocalDateTime()
   * @see #addLocalTime()
   * @see #addOffsetDateTime()
   * @see #addOffsetTime()
   */
  protected int getMaxSizeTemporal() {

    return 6;
  }

  /**
   * @param dbType the {@link DbType} to register.
   * @param maxSize DB specific limit for {@link DbType#getSize() size}.
   * @param maxScale DB specific limit for {@link DbType#getScale() scale}.
   */
  protected void add(DbType<?, ?, ?> dbType, int maxSize, int maxScale) {

    add(new SingleTypeMappingDb<>(dbType, maxSize, maxScale));
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public <V> TypeMapper<V, ?> getTypeMapper(Class<V> valueType, ReadableProperty<?> property) {

    TypeMapper<V, ?> typeMapper = super.getTypeMapper(valueType, property);
    if (typeMapper == null) {
      if (Link.class.isAssignableFrom(valueType)) {
        typeMapper = (TypeMapper) new LinkMapper(null);
      }
    }
    return typeMapper;
  }

}
