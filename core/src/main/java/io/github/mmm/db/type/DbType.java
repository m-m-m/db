/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.type;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Year;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import io.github.mmm.base.exception.DuplicateObjectException;
import io.github.mmm.base.lang.ValueType;
import io.github.mmm.value.converter.AtomicTypeMapper;

/**
 * Datatype of the database.
 *
 * @param <J> type of {@link #getSourceType() Java source type}.
 * @param <D> type of the {@link #getTargetType() database target type}.
 * @param <SELF> type of this class itself for fluent API calls.
 *
 * @since 1.0.0
 */
@SuppressWarnings("exports")
public abstract class DbType<J, D, SELF extends DbType<J, D, SELF>> extends AtomicTypeMapper<J, D> {

  private static final Map<Integer, DbType<?, ?, ?>> CODE_MAP = new HashMap<>();

  private static final Map<Class<?>, DbType<?, ?, ?>> TYPE_MAP = new HashMap<>();

  private static final Map<String, DbType<?, ?, ?>> NAME_MAP = new HashMap<>();

  /** Array type. */
  public static final DbTypeArray ARRAY = DbTypeArray.INSTANCE;

  /** {@link BigDecimal} type with {@link #getSize() maximum number of digits}. */
  public static final DbTypeBigDecimal BIG_DECIMAL = DbTypeBigDecimal.INSTANCE;

  /** {@link BigInteger} type with {@link #getSize() maximum number of digits}. */
  public static final DbTypeBigInteger2BigDecimal BIG_INTEGER = DbTypeBigInteger2BigDecimal.INSTANCE;

  // /** Binary type for a fixed length {@code java.sql.Blob} as {@code byte[]}. */
  // public static final DbType<byte[]> BINARY = new DbType<>(Types.BINARY, byte[].class, DbKeyword.BINARY);
  //
  // /** Bit type for a fixed number of bits with {@link #getSize() size} in the range of 1-64. */
  // public static final DbType<BitSet> BIT = new DbType<>(Types.BIT, BitSet.class, DbKeyword.BIT);

  /** {@link java.sql.Blob} type with variable length and a maximum {@link #getSize() size} in the range of 1-65535. */
  public static final DbTypeBlob BLOB = DbTypeBlob.INSTANCE;

  /** {@link Boolean} type. */
  public static final DbTypeBoolean BOOLEAN = DbTypeBoolean.INSTANCE;

  /** {@link Byte} type with {@link #getSize() maximum number of digits} in the range of 1-3. */
  public static final DbTypeByte BYTE = DbTypeByte.INSTANCE;

  // /** Char type for a fixed length {@link String}. */
  // public static final DbType<char[]> CHAR = new DbType<>(Types.CHAR, char[].class, DbKeyword.CHAR);

  /** {@link java.sql.Clob} type with variable length and a maximum {@link #getSize() size}. */
  public static final DbTypeClob CLOB = DbTypeClob.INSTANCE;

  /** {@link Double} type with {@link #getSize() maximum number of digits} in the range of 1-24. */
  public static final DbTypeDouble DOUBLE = DbTypeDouble.INSTANCE;

  /** {@link Duration} type. */
  public static final DbTypeDuration2LongMillis DURATION = DbTypeDuration2LongMillis.INSTANCE;

  /** {@link Float} type with {@link #getSize() maximum number of digits} in the range of 1-24. */
  public static final DbTypeFloat FLOAT = DbTypeFloat.INSTANCE;

  /** {@link Instant} type. */
  public static final DbTypeInstant2Timestamp INSTANT = DbTypeInstant2Timestamp.INSTANCE;

  /** {@link Integer} type with {@link #getSize() maximum number of digits} in the range of 1-10. */
  public static final DbTypeInteger INTEGER = DbTypeInteger.INSTANCE;

  /** {@link LocalDate} type. */
  public static final DbTypeLocalDate2Date LOCAL_DATE = DbTypeLocalDate2Date.INSTANCE;

  /** {@link LocalDateTime} type. */
  public static final DbTypeLocalDateTime2Timestamp LOCAL_DATE_TIME = DbTypeLocalDateTime2Timestamp.INSTANCE;

  /** {@link LocalTime} type. */
  public static final DbTypeLocalTime2Time LOCAL_TIME = DbTypeLocalTime2Time.INSTANCE;

  /** {@link Long} type with {@link #getSize() size} as the maximum number of digits in the range of 1-20. */

  public static final DbTypeLong LONG = DbTypeLong.INSTANCE;

  /** Long[] type. */
  public static final DbTypeLongArray LONG_ARRAY = DbTypeLongArray.INSTANCE;

  // /** {@link BigInteger} type with {@link #getSize() maximum number of digits}. */
  // public static final DbSimpleType<Number> NUMBER = new DbType<>(Types.NUMERIC, Number.class, DbKeyword.NUMBER);

  /** {@link OffsetDateTime} type. */
  public static final DbTypeOffsetDateTime2String OFFSET_DATE_TIME = DbTypeOffsetDateTime2String.INSTANCE;

  /** {@link OffsetTime} type. */
  public static final DbTypeOffsetTime2String OFFSET_TIME = DbTypeOffsetTime2String.INSTANCE;

  /** {@link Double} type based on SQL {@link Types#REAL}. */
  public static final DbTypeReal REAL = DbTypeReal.INSTANCE;

  /** Row-ID type. */
  public static final DbTypeRowId ROWID = DbTypeRowId.INSTANCE;

  /** {@link Short} type with {@link #getSize() maximum number of digits} in the range of 1-5. */
  public static final DbTypeShort SHORT = DbTypeShort.INSTANCE;

  /** {@link String} type with variable length and maximum {@link #getSize() size}. */
  public static final DbTypeString STRING = DbTypeString.INSTANCE;

  // /** {@link java.sql.Blob} type with variable length and a maximum {@link #getSize() size} in the range of 1-65535.
  // */
  // public static final DbType<Blob> VARBINARY = new DbType<>(Types.VARBINARY, Blob.class, DbKeyword.VARBINARY, false);

  /** {@link java.sql.SQLXML} type. */
  public static final DbTypeSqlXml SQL_XML = DbTypeSqlXml.INSTANCE;

  /** {@link String} type with variable length and maximum {@link #getSize() size}. */
  public static final DbTypeText TEXT = DbTypeText.INSTANCE;

  /** {@link java.util.UUID} type based on SQL {@link Types#REAL}. */
  public static final DbTypeUuid UUID = DbTypeUuid.INSTANCE;

  /** {@link Year} type. */
  public static final DbTypeYear2Integer YEAR = DbTypeYear2Integer.INSTANCE;

  // /** {@link ZonedDateTime} type. */
  // public static final DbType<ZonedDateTime> ZONED_DATE_TIME = new DbType<>(0, ZonedDateTime.class,
  // DbKeyword.TIMESTAMP,
  // DbKeyword.WITH_TIME_ZONE);

  private final String name;

  private final Integer size;

  private final Integer scale;

  private final String format;

  private final String suffix;

  private final boolean nativeTypeSupport;

  private String declaration;

  DbType(String name, Integer size, Integer scale, String format, String suffix, boolean nativeTypeSupport,
      DbTypeRegistration registration) {

    super();
    if (name == null) {
      name = getDefaultName();
    }
    Objects.requireNonNull(name);
    this.name = name;
    this.size = size;
    this.scale = scale;
    this.format = format;
    if (suffix == null) {
      suffix = getDefaultSuffix();
    }
    this.suffix = suffix;
    this.nativeTypeSupport = nativeTypeSupport;
    if (registration == null) {
      registration = DbTypeRegistration.NONE;
    }
    if (registration.isRegisterCode()) {
      int code = getCode();
      assert (code != 0);
      register(CODE_MAP, code);
    }
    if (registration.isRegisterType()) {
      register(TYPE_MAP, getSourceType());
    }
    if (registration.isRegisterName()) {
      register(NAME_MAP, name);
    }
  }

  private <K> void register(Map<K, DbType<?, ?, ?>> map, K key) {

    DbType<?, ?, ?> duplicate = map.put(key, this);
    if (duplicate != null) {
      throw new DuplicateObjectException(this, key, duplicate);
    }
  }

  /**
   * @return this object itself for fluent API calls.
   */
  @SuppressWarnings("unchecked")
  protected SELF self() {

    return (SELF) this;
  }

  /**
   * @return the type of this column as {@link java.sql.Types SQL type constant}.
   */
  public abstract int getCode();

  /**
   * @return the type of this column as {@link String}.
   */
  public String getName() {

    return this.name;
  }

  /**
   * @return the default value for {@link #getName() name}.
   */
  abstract String getDefaultName();

  /**
   * @return the default value for {@link #getSuffix suffix}.
   */
  String getDefaultSuffix() {

    return null;
  }

  /**
   * <b>ATTENTION</b>: This is an internal API that should only be used by dialects.
   *
   * @param newName the new {@link #getName() name}.
   * @return the {@link DbType} with the given {@link #getName() name}.
   */
  public SELF withName(String newName) {

    if (this.name.equals(newName)) {
      return self();
    }
    return create(newName, this.size, this.scale, this.format, this.suffix, this.nativeTypeSupport);
  }

  /**
   * @return the size or {@code null} if not defined.
   */
  public Integer getSize() {

    return this.size;
  }

  /**
   * @param newSize the new {@link #getSize() size}.
   * @return the {@link DbType} with the given {@link #getSize() size}.
   */
  public SELF withSize(Integer newSize) {

    if (Objects.equals(this.size, newSize)) {
      return self();
    }
    return create(this.name, newSize, this.scale, this.format, this.suffix, this.nativeTypeSupport);
  }

  /**
   * @return the number of digits after the decimal point or {@code null} if not defined.
   */
  public Integer getScale() {

    return this.scale;
  }

  /**
   * @param newScale the new {@link #getScale() scale}.
   * @return the {@link DbType} with the given {@link #getScale() scale}.
   */
  public SELF withScale(Integer newScale) {

    if (Objects.equals(this.scale, newScale)) {
      return self();
    }
    return create(this.name, this.size, newScale, this.format, this.suffix, this.nativeTypeSupport);
  }

  /**
   * @return the number of digits after the decimal point or {@code null} if not defined.
   */
  public String getFormat() {

    return this.format;
  }

  /**
   * @param newFormat the new {@link #getFormat() format}.
   * @return the {@link DbType} with the given {@link #getFormat() format}.
   */
  public SELF withFormat(String newFormat) {

    if (Objects.equals(this.format, newFormat)) {
      return self();
    }
    return create(this.name, this.size, this.scale, newFormat, this.suffix, this.nativeTypeSupport);
  }

  /**
   * @return the type suffix (e.g. "WITH TIME ZONE").
   */
  @Override
  public String getSuffix() {

    return this.suffix;
  }

  /**
   * <b>ATTENTION</b>: This is an internal API that should only be used by dialects.
   *
   * @param newSuffix the new {@link #getSuffix() suffix}.
   * @return the {@link DbType} with the given {@link #getSuffix() suffix}..
   */
  public SELF withSuffix(String newSuffix) {

    if (Objects.equals(this.suffix, newSuffix)) {
      return self();
    }
    return create(this.name, this.size, this.scale, this.format, newSuffix, this.nativeTypeSupport);
  }

  /**
   * @return {@code true} if the {@link #getSourceType() Java source type} is natively supported by your database,
   *         {@code false} otherwise (default). If not supported, the Java source type will be {@link #toTarget(Object)
   *         converted} to the {@link #getTargetType() DB target type}. For JDBC native support means that the
   *         {@link #getSourceType() Java source type} can be directly set via
   *         {@link PreparedStatement#setObject(int, Object)} (e.g. for {@link BigInteger} or {@link Instant}).
   */
  public boolean isNativeTypeSupport() {

    return this.nativeTypeSupport;
  }

  /**
   * <b>ATTENTION</b>: This is an internal API that should only be used by dialects.
   *
   * @param newNativeTypeSupport the new {@link #isNativeTypeSupport() native type support}.
   * @return the {@link DbType} with the given {@link #isNativeTypeSupport() native type support}.
   */
  public SELF withNativeTypeSupport(boolean newNativeTypeSupport) {

    if (this.nativeTypeSupport == newNativeTypeSupport) {
      return self();
    }
    return create(this.name, this.size, this.scale, this.format, this.suffix, newNativeTypeSupport);
  }

  /**
   * @param newName the new {@link #getName() name}.
   * @param newSize the new {@link #getSize() size}.
   * @param newScale the new {@link #getScale() scale}.
   * @param newFormat the new {@link #getFormat() format}.
   * @param newSuffix the new {@link #getSuffix() suffix}.
   * @param newNativeTypeSupport TODO
   * @return the new instance of this class.
   */
  protected abstract SELF create(String newName, Integer newSize, Integer newScale, String newFormat, String newSuffix,
      boolean newNativeTypeSupport);

  /**
   * @return {@code true} if this type is numeric, {@code false} otherwise.
   */
  public final boolean isNumeric() {

    return isDecimal() || isInteger();
  }

  /**
   * @return {@code true} if this type is a numeric decimal type, {@code false} otherwise.
   */
  public final boolean isDecimal() {

    int code = getCode();
    return switch (code) {
      case Types.DECIMAL, Types.DOUBLE, Types.FLOAT -> true;
      default -> false;
    };
  }

  /**
   * @return {@code true} if this type is a numeric integer type (not decimal), {@code false} otherwise.
   */
  public final boolean isInteger() {

    int code = getCode();
    return switch (code) {
      case Types.BIGINT, Types.INTEGER, Types.NUMERIC, Types.REAL, Types.SMALLINT, Types.TINYINT -> true;
      default -> false;
    };
  }

  /**
   * @return {@code true} if this type is technically temporal (date and time related), {@code false} otherwise.
   */
  public final boolean isTemporal() {

    int code = getCode();
    return switch (code) {
      case Types.DATE, Types.TIME, Types.TIME_WITH_TIMEZONE, Types.TIMESTAMP, Types.TIMESTAMP_WITH_TIMEZONE -> true;
      default -> false;
    };
  }

  /**
   * @return {@code true} if this type is technically textual ({@link String}), {@code false} otherwise.
   */
  public final boolean isText() {

    int code = getCode();
    return switch (code) {
      case Types.CHAR, Types.VARCHAR, Types.CLOB, Types.LONGNVARCHAR, Types.LONGVARCHAR -> true;
      default -> false;
    };
  }

  /**
   * This method sets the parameter at the given {@code index} to the given {@code value}. E.g. if this {@link DbType}
   * is for a simple regular {@link String} this method would more or less be equivalent to:
   *
   * <pre>
   * if (value == null) {
   *   statement.{@link PreparedStatement#setNull(int, int) setNull}(index, {@link #getCode()});
   * } else {
   *   statement.{@link PreparedStatement#setString(int, String) setString}(index, value);
   * }
   * </pre>
   *
   * @param statement the {@link PreparedStatement} where to set the parameter.
   * @param index the index position of the parameter to set. <b>ATTENTION</b>: JDBC index of parameters starts with
   *        {@code 1} (and not with {@code 0}) what is often causing confusion for developers.
   * @param value the value to bind to the parameter at the given {@code index}.
   * @throws SQLException if JDBC failed.
   */
  public void setJavaValue(PreparedStatement statement, int index, J value) throws SQLException {

    D target = null;
    if (value == null) {
      target = toTargetNull();
    } else if (this.nativeTypeSupport) {
      statement.setObject(index, value);
      return;
    } else {
      target = toTarget(value);
    }
    if (target == null) {
      int code = getCode();
      if (code == 0) {
        code = Types.NULL;
      }
      statement.setNull(index, code);
    } else {
      setDbValue(statement, index, target);
    }
  }

  /**
   * @param resultSet the JDBC {@link ResultSet}.
   * @param columnIndex the column index. See {@link ResultSet#getObject(int)}. <b>ATTENTION</b>: JDBC index of
   *        parameters starts with {@code 1} (and not with {@code 0}) what is often causing confusion for developers.
   * @return the requested Java value.
   * @throws SQLException if JDBC failed.
   */
  @SuppressWarnings("unchecked")
  public final J getJavaValue(ResultSet resultSet, int columnIndex) throws SQLException {

    if (this.nativeTypeSupport) {
      return (J) resultSet.getObject(columnIndex);
    }
    D dbValue = getDbValue(resultSet, columnIndex);
    if (dbValue == null) {
      return toSourceNull();
    }
    return toSource(dbValue);
  }

  /**
   * @param resultSet the JDBC {@link ResultSet}.
   * @param columnLabel the column label. See {@link ResultSet#getObject(String)}.
   * @return the requested Java value.
   * @throws SQLException if JDBC failed.
   */
  @SuppressWarnings("unchecked")
  public final J getJavaValue(ResultSet resultSet, String columnLabel) throws SQLException {

    if (this.nativeTypeSupport) {
      return (J) resultSet.getObject(columnLabel);
    }
    D dbValue = getDbValue(resultSet, columnLabel);
    if (dbValue == null) {
      return toSourceNull();
    }
    return toSource(dbValue);
  }

  /**
   * This method sets the parameter at the given {@code index} to the given {@code value}. It behaves like
   * {@link #setJavaValue(PreparedStatement, int, Object)} but without potential {@link #toTarget(Object) conversion to
   * native DB type}.
   *
   * @param statement the {@link PreparedStatement} where to set the parameter.
   * @param index the index position of the parameter to set. <b>ATTENTION</b>: JDBC index of parameters starts with
   *        {@code 1} (and not with {@code 0}) what is often causing confusion for developers.
   * @param value the value to bind to the parameter at the given {@code index}.
   * @throws SQLException if JDBC failed.
   * @see #setJavaValue(PreparedStatement, int, Object)
   */
  public void setDbValue(PreparedStatement statement, int index, D value) throws SQLException {

    if (value == null) {
      int code = getCode();
      if (code == 0) {
        code = Types.NULL;
      }
      statement.setNull(index, code);
    } else {
      setDbValueNonNull(statement, index, value);
    }
  }

  /**
   * @param resultSet the JDBC {@link ResultSet}.
   * @param columnIndex the column index. See {@link ResultSet#getObject(int)}. <b>ATTENTION</b>: JDBC index of
   *        parameters starts with {@code 1} (and not with {@code 0}) what is often causing confusion for developers.
   * @return the requested database value.
   * @throws SQLException if JDBC failed.
   */
  public abstract D getDbValue(ResultSet resultSet, int columnIndex) throws SQLException;

  /**
   * @param resultSet the JDBC {@link ResultSet}.
   * @param columnLabel the column label. See {@link ResultSet#getObject(String)}.
   * @return the requested database value.
   * @throws SQLException if JDBC failed.
   */
  public abstract D getDbValue(ResultSet resultSet, String columnLabel) throws SQLException;

  /**
   * This method sets the parameter at the given {@code index} to the given {@code value} that is not {@code null}. E.g.
   * for {@link #getTargetType() DB target type} of {@link String} this method needs to be implemented as:
   *
   * <pre>
   * statement.{@link PreparedStatement#setString(int, String) setString}(index, value);
   * </pre>
   *
   * @param statement the {@link PreparedStatement} where to set the parameter.
   * @param index the index position of the parameter to set. <b>ATTENTION</b>: JDBC index of parameters starts with
   *        {@code 1} (and not with {@code 0}) what is often causing confusion for developers.
   * @param value the value to bind to the parameter at the given {@code index}.
   * @throws SQLException if JDBC failed.
   * @see #setJavaValue(PreparedStatement, int, Object)
   */
  protected abstract void setDbValueNonNull(PreparedStatement statement, int index, D value) throws SQLException;

  @Override
  public String getDeclaration() {

    if (this.declaration == null) {
      this.declaration = buildDeclaration();
    }
    return this.declaration;
  }

  String buildDeclaration() {

    if ((this.format == null) && (this.size == null) && (this.suffix == null)) {
      return this.name;
    }
    StringBuilder sb = new StringBuilder(this.name);
    buildDeclarationFormat(sb);
    buildDeclarationSize(sb);
    buildDeclarationSuffix(sb);
    return sb.toString();
  }

  void buildDeclarationSuffix(StringBuilder sb) {

    if (this.suffix != null) {
      sb.append(' ');
      sb.append(this.suffix);
    }
  }

  void buildDeclarationSize(StringBuilder sb) {

    if (this.size != null) {
      sb.append('(');
      sb.append(this.size);
      if (this.scale != null) {
        sb.append(',');
        sb.append(this.scale);
      }
      sb.append(')');
    }
  }

  void buildDeclarationFormat(StringBuilder sb) {

    if (this.format != null) {
      sb.append('(');
      sb.append(this.format);
      sb.append(')');
    }
  }

  @Override
  public String toString() {

    return getDeclaration();
  }

  /**
   * @param code the {@link #getCode() code}.
   * @return the requested {@link DbType} or {@code null} if not found.
   */
  public static DbType<?, ?, ?> get(int code) {

    return CODE_MAP.get(Integer.valueOf(code));
  }

  /**
   * @param <J> type of the {@link #getSourceType() Java source type}.
   * @param sourceType the {@link #getSourceType() Java source type}.
   * @return the requested {@link DbType} or {@code null} if not found.
   */
  @SuppressWarnings("unchecked")
  public static <J> DbType<J, ?, ?> get(Class<J> sourceType) {

    return (DbType<J, ?, ?>) TYPE_MAP.get(ValueType.getNonPrimitiveType(sourceType));
  }

  /**
   * @param name the {@link #getName() name}.
   * @return the requested {@link DbType} or {@code null} if not found.
   */
  public static DbType<?, ?, ?> get(String name) {

    return NAME_MAP.get(name);
  }

  /**
   * @return a {@link Stream} of all {@link DbType}s registered by {@link #getSourceType() source type}.
   */
  public static Stream<DbType<?, ?, ?>> getAll() {

    return TYPE_MAP.values().stream();
  }

}
