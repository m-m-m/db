/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.typemapping;

import java.math.BigInteger;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.mmm.base.metainfo.MetaInfo;
import io.github.mmm.base.range.Range;
import io.github.mmm.db.type.DbType;
import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.bean.typemapping.SingleTypeMapping;
import io.github.mmm.property.ReadableProperty;
import io.github.mmm.value.converter.TypeMapper;

/**
 * Abstract base implementation of {@link SingleTypeMapping} based on {@link DbType}.
 *
 * @param <V> type of the {@link TypeMapper#getSourceType() source type}.
 * @since 1.0.0
 */
public class SingleTypeMappingDb<V> extends SingleTypeMapping<V> {

  private static final Logger LOG = LoggerFactory.getLogger(SingleTypeMappingDb.class);

  private final DbType<V, ?, ?> dbType;

  private final int maxSize;

  private final int maxScale;

  /**
   * The constructor.
   *
   * @param dbType the {@link DbType}.
   * @param maxSize DB specific limit for {@link DbType#getSize() size}.
   * @param maxScale DB specific limit for {@link DbType#getScale() scale}.
   */
  public SingleTypeMappingDb(DbType<V, ?, ?> dbType, int maxSize, int maxScale) {

    super();
    Objects.requireNonNull(dbType);
    requireNonNegative(maxSize, "size");
    requireNonNegative(maxScale, "scale");
    if (maxScale > maxSize) {
      throw new IllegalArgumentException(
          "Max scale (" + maxScale + ") must not be greater than max size (" + maxSize + ").");
    }
    this.dbType = dbType;
    this.maxSize = maxSize;
    this.maxScale = maxScale;
  }

  private static void requireNonNegative(int value, String string) {

    if (value < 0) {
      throw new IllegalArgumentException("Max " + string + " must not be negative: " + value);
    }
  }

  @Override
  public DbType<V, ?, ?> getTypeMapper() {

    return this.dbType;
  }

  @Override
  public DbType<V, ?, ?> getTypeMapper(ReadableProperty<?> property) {

    if ((this.maxSize == 0) || (property == null)) {
      return this.dbType;
    }
    MetaInfo metaInfo = property.getMetadata().getMetaInfo();
    Integer size = metaInfo.getAsInteger(EntityBean.META_KEY_COLUMN_SIZE);
    Integer scale = metaInfo.getAsInteger(EntityBean.META_KEY_COLUMN_SCALE);
    if (size == null) {
      size = determineColumnSize(property);
    }
    if (size == null) {
      assert (scale == null);
      return this.dbType;
    }
    DbType<V, ?, ?> result = this.dbType.withSize(clip(size.intValue(), this.maxSize, "size"));
    if (scale != null) {
      result = this.dbType.withScale(clip(scale.intValue(), this.maxScale, "scale"));
    }
    return result;
  }

  private Integer clip(int value, int max, String name) {

    if (value > max) {
      LOG.warn("For DB type {} ({}) the {} is {} what exceeds the limit {} and will be clipped.", this.dbType.getName(),
          this.dbType.getSourceType().getName(), name, value, max);
      value = max;
    }
    if (value == 0) {
      return null;
    }
    return Integer.valueOf(value);
  }

  private Integer determineColumnSize(ReadableProperty<?> property) {

    if (this.dbType.isTemporal() || this.dbType.isDecimal()) {
      return null; // temporal max has nothing to do with DB column size and for decimals we cannot guess scale
    }
    Range<?> range = getRange(property);
    Object max = range.getMax();
    if (max instanceof Number n) {
      if (max instanceof BigInteger bi) {
        return log10(bi);
      }
      long l = n.longValue();
      if (this.dbType.isInteger()) {
        return log10(l);
      }
      int result = n.intValue();
      if (result == l) {
        return result; // for a string we assume we found the max size
      }
    }
    return null;
  }

  private Integer log10(BigInteger bi) {

    return 3 + (bi.bitCount() * 40) / 132;
  }

  private Integer log10(long l) {

    if (l < 0) {
      return null;
    }
    if (l < 10L) {
      return 1;
    } else if (l < 100L) {
      return 2;
    } else if (l < 1_000L) {
      return 3;
    } else if (l < 10_000L) {
      return 4;
    } else if (l < 100_000L) {
      return 5;
    } else if (l < 1_000_000L) {
      return 6;
    } else if (l < 10_000_000L) {
      return 7;
    } else if (l < 100_000_000L) {
      return 8;
    } else if (l < 1_000_000_000L) {
      return 9;
    } else if (l < 10_000_000_000L) {
      return 10;
    } else if (l < 100_000_000_000L) {
      return 11;
    } else if (l < 1_000_000_000_000L) {
      return 12;
    } else if (l < 10_000_000_000_000L) {
      return 13;
    } else if (l < 100_000_000_000_000L) {
      return 14;
    } else if (l < 1_000_000_000_000_000L) {
      return 15;
    } else if (l < 10_000_000_000_000_000L) {
      return 16;
    } else if (l < 100_000_000_000_000_000L) {
      return 17;
    }
    return 18;
  }

}
