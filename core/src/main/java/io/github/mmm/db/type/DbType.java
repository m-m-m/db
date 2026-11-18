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

/// Datatype of the database.
///
/// @param <J> type of [Java source type][#getSourceType()].
/// @param <D> type of the [database target type][#getTargetType()].
/// @param <SELF> type of this class itself for fluent API calls.
///
/// @since 1.0.0
@SuppressWarnings("exports")
public abstract class DbType<J, D, SELF extends DbType<J, D, SELF>> extends AtomicTypeMapper<J, D> {

  private static final Map<Integer, DbType<?, ?, ?>> CODE_MAP = new HashMap<>();

  private static final Map<Class<?>, DbType<?, ?, ?>> TYPE_MAP = new HashMap<>();

  private static final Map<String, DbType<?, ?, ?>> NAME_MAP = new HashMap<>();

  /// Array type.
  public static final DbTypeArray ARRAY = DbTypeArray.INSTANCE;

  /// [BigDecimal] type with [maximum number of digits][#getSize()].
  public static final DbTypeBigDecimal BIG_DECIMAL = DbTypeBigDecimal.INSTANCE;

  /// [BigInteger] type with [maximum number of digits][#getSize()].
  public static final DbTypeBigInteger2BigDecimal BIG_INTEGER = DbTypeBigInteger2BigDecimal.INSTANCE;

  // /** Binary type for a fixed length {@code java.sql.Blob} as {@code byte[]}. */
  // public static final DbType<byte[]> BINARY = new DbType<>(Types.BINARY, byte[].class, DbKeyword.BINARY);
  //
  // /** Bit type for a fixed number of bits with {@link #getSize() size} in the range of 1-64. */
  // public static final DbType<BitSet> BIT = new DbType<>(Types.BIT, BitSet.class, DbKeyword.BIT);

  /// [java.sql.Blob] type with variable length and a maximum [size][#getSize()] in the range of 1-65535.
  public static final DbTypeBlob BLOB = DbTypeBlob.INSTANCE;

  /// [Boolean] type.
  public static final DbTypeBoolean BOOLEAN = DbTypeBoolean.INSTANCE;

  /// [Byte] type with [maximum number of digits][#getSize()] in the range of 1-3.
  public static final DbTypeByte BYTE = DbTypeByte.INSTANCE;

  // /** Char type for a fixed length {@link String}. */
  // public static final DbType<char[]> CHAR = new DbType<>(Types.CHAR, char[].class, DbKeyword.CHAR);

  /// [java.sql.Clob] type with variable length and a maximum [size][#getSize()].
  public static final DbTypeClob CLOB = DbTypeClob.INSTANCE;

  /// [Double] type with [maximum number of digits][#getSize()] in the range of 1-24.
  public static final DbTypeDouble DOUBLE = DbTypeDouble.INSTANCE;

  /// [Duration] type.
  public static final DbTypeDuration2LongMillis DURATION = DbTypeDuration2LongMillis.INSTANCE;

  /// [Float] type with [maximum number of digits][#getSize()] in the range of 1-24.
  public static final DbTypeFloat FLOAT = DbTypeFloat.INSTANCE;

  /// [Instant] type.
  public static final DbTypeInstant2Timestamp INSTANT = DbTypeInstant2Timestamp.INSTANCE;

  /// [Integer] type with [maximum number of digits][#getSize()] in the range of 1-10.
  public static final DbTypeInteger INTEGER = DbTypeInteger.INSTANCE;

  /// [LocalDate] type.
  public static final DbTypeLocalDate2Date LOCAL_DATE = DbTypeLocalDate2Date.INSTANCE;

  /// [LocalDateTime] type.
  public static final DbTypeLocalDateTime2Timestamp LOCAL_DATE_TIME = DbTypeLocalDateTime2Timestamp.INSTANCE;

  /// [LocalTime] type.
  public static final DbTypeLocalTime2Time LOCAL_TIME = DbTypeLocalTime2Time.INSTANCE;

  /// [Long] type with [size][#getSize()] as the maximum number of digits in the range of 1-20.

  public static final DbTypeLong LONG = DbTypeLong.INSTANCE;

  /// Long[] type.
  public static final DbTypeLongArray LONG_ARRAY = DbTypeLongArray.INSTANCE;

  // /** {@link BigInteger} type with {@link #getSize() maximum number of digits}. */
  // public static final DbSimpleType<Number> NUMBER = new DbType<>(Types.NUMERIC, Number.class, DbKeyword.NUMBER);

  /// [OffsetDateTime] type.
  public static final DbTypeOffsetDateTime2String OFFSET_DATE_TIME = DbTypeOffsetDateTime2String.INSTANCE;

  /// [OffsetTime] type.
  public static final DbTypeOffsetTime2String OFFSET_TIME = DbTypeOffsetTime2String.INSTANCE;

  /// [Double] type based on SQL [Types#REAL].
  public static final DbTypeReal REAL = DbTypeReal.INSTANCE;

  /// Row-ID type.
  public static final DbTypeRowId ROWID = DbTypeRowId.INSTANCE;

  /// [Short] type with [maximum number of digits][#getSize()] in the range of 1-5.
  public static final DbTypeShort SHORT = DbTypeShort.INSTANCE;

  /// [String] type with variable length and maximum [size][#getSize()].
  public static final DbTypeString STRING = DbTypeString.INSTANCE;

  // /** {@link java.sql.Blob} type with variable length and a maximum {@link #getSize() size} in the range of 1-65535.
  // */
  // public static final DbType<Blob> VARBINARY = new DbType<>(Types.VARBINARY, Blob.class, DbKeyword.VARBINARY, false);

  /// [java.sql.SQLXML] type.
  public static final DbTypeSqlXml SQL_XML = DbTypeSqlXml.INSTANCE;

  /// [String] type with variable length and maximum [size][#getSize()].
  public static final DbTypeText TEXT = DbTypeText.INSTANCE;

  /// [java.util.UUID] type based on SQL [Types#REAL].
  public static final DbTypeUuid UUID = DbTypeUuid.INSTANCE;

  /// [Year] type.
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

  DbType(String name, Integer size, Integer scale, String format, String suffix, boolean nativeTypeSupport, DbTypeRegistration registration) {

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

  /// @return this object itself for fluent API calls.
  @SuppressWarnings("unchecked")
  protected SELF self() {

    return (SELF) this;
  }

  /// @return the type of this column as [SQL type constant][java.sql.Types].
  public abstract int getCode();

  /// @return the type of this column as [String].
  public String getName() {

    return this.name;
  }

  /// @return the default value for [name][#getName()].
  abstract String getDefaultName();

  /// @return the default value for [suffix][#getSuffix].
  String getDefaultSuffix() {

    return null;
  }

  /// **ATTENTION**: This is an internal API that should only be used by dialects.
  ///
  /// @param newName the new [name][#getName()].
  /// @return the [DbType] with the given [name][#getName()].
  public SELF withName(String newName) {

    if (this.name.equals(newName)) {
      return self();
    }
    return create(newName, this.size, this.scale, this.format, this.suffix, this.nativeTypeSupport);
  }

  /// @return the size or `null` if not defined.
  public Integer getSize() {

    return this.size;
  }

  /// @param newSize the new [size][#getSize()].
  /// @return the [DbType] with the given [size][#getSize()].
  public SELF withSize(Integer newSize) {

    if (Objects.equals(this.size, newSize)) {
      return self();
    }
    return create(this.name, newSize, this.scale, this.format, this.suffix, this.nativeTypeSupport);
  }

  /// @return the number of digits after the decimal point or `null` if not defined.
  public Integer getScale() {

    return this.scale;
  }

  /// @param newScale the new [scale][#getScale()].
  /// @return the [DbType] with the given [scale][#getScale()].
  public SELF withScale(Integer newScale) {

    if (Objects.equals(this.scale, newScale)) {
      return self();
    }
    return create(this.name, this.size, newScale, this.format, this.suffix, this.nativeTypeSupport);
  }

  /// @return the number of digits after the decimal point or `null` if not defined.
  public String getFormat() {

    return this.format;
  }

  /// @param newFormat the new [format][#getFormat()].
  /// @return the [DbType] with the given [format][#getFormat()].
  public SELF withFormat(String newFormat) {

    if (Objects.equals(this.format, newFormat)) {
      return self();
    }
    return create(this.name, this.size, this.scale, newFormat, this.suffix, this.nativeTypeSupport);
  }

  /// @return the type suffix (e.g. "WITH TIME ZONE").
  @Override
  public String getSuffix() {

    return this.suffix;
  }

  /// **ATTENTION**: This is an internal API that should only be used by dialects.
  ///
  /// @param newSuffix the new [suffix][#getSuffix()].
  /// @return the [DbType] with the given [suffix][#getSuffix()]..
  public SELF withSuffix(String newSuffix) {

    if (Objects.equals(this.suffix, newSuffix)) {
      return self();
    }
    return create(this.name, this.size, this.scale, this.format, newSuffix, this.nativeTypeSupport);
  }

  /// @return `true` if the [Java source type][#getSourceType()] is natively supported by your database, `false` otherwise (default). If not supported, the Java
  ///         source type will be [converted][#toTarget(Object)] to the [DB target type][#getTargetType()]. For JDBC native support means that the [Java source
  ///         type][#getSourceType()] can be directly set via [PreparedStatement#setObject(int, Object)] (e.g. for [BigInteger] or [Instant]).
  public boolean isNativeTypeSupport() {

    return this.nativeTypeSupport;
  }

  /// **ATTENTION**: This is an internal API that should only be used by dialects.
  ///
  /// @param newNativeTypeSupport the new [native type support][#isNativeTypeSupport()].
  /// @return the [DbType] with the given [native type support][#isNativeTypeSupport()].
  public SELF withNativeTypeSupport(boolean newNativeTypeSupport) {

    if (this.nativeTypeSupport == newNativeTypeSupport) {
      return self();
    }
    return create(this.name, this.size, this.scale, this.format, this.suffix, newNativeTypeSupport);
  }

  /// @param newName the new [name][#getName()].
  /// @param newSize the new [size][#getSize()].
  /// @param newScale the new [scale][#getScale()].
  /// @param newFormat the new [format][#getFormat()].
  /// @param newSuffix the new [suffix][#getSuffix()].
  /// @param newNativeTypeSupport TODO
  /// @return the new instance of this class.
  protected abstract SELF create(String newName, Integer newSize, Integer newScale, String newFormat, String newSuffix, boolean newNativeTypeSupport);

  /// @return `true` if this type is numeric, `false` otherwise.
  public final boolean isNumeric() {

    return isDecimal() || isInteger();
  }

  /// @return `true` if this type is a numeric decimal type, `false` otherwise.
  public final boolean isDecimal() {

    int code = getCode();
    return switch (code) {
      case Types.DECIMAL, Types.DOUBLE, Types.FLOAT -> true;
      default -> false;
    };
  }

  /// @return `true` if this type is a numeric integer type (not decimal), `false` otherwise.
  public final boolean isInteger() {

    int code = getCode();
    return switch (code) {
      case Types.BIGINT, Types.INTEGER, Types.NUMERIC, Types.REAL, Types.SMALLINT, Types.TINYINT -> true;
      default -> false;
    };
  }

  /// @return `true` if this type is technically temporal (date and time related), `false` otherwise.
  public final boolean isTemporal() {

    int code = getCode();
    return switch (code) {
      case Types.DATE, Types.TIME, Types.TIME_WITH_TIMEZONE, Types.TIMESTAMP, Types.TIMESTAMP_WITH_TIMEZONE -> true;
      default -> false;
    };
  }

  /// @return `true` if this type is technically textual ([String]), `false` otherwise.
  public final boolean isText() {

    int code = getCode();
    return switch (code) {
      case Types.CHAR, Types.VARCHAR, Types.CLOB, Types.LONGNVARCHAR, Types.LONGVARCHAR -> true;
      default -> false;
    };
  }

  /// This method sets the parameter at the given `index` to the given `value`. E.g. if this [DbType] is for a simple regular [String] this method would more or
  /// less be equivalent to:
  ///
  /// ```
  /// if (value == null) {
  ///   statement.[setNull][PreparedStatement#setNull(int, int)](index, [#getCode()]);
  /// } else {
  ///   statement.[setString][PreparedStatement#setString(int, String)](index, value);
  /// }
  /// ```
  ///
  /// @param statement the [PreparedStatement] where to set the parameter.
  /// @param index the index position of the parameter to set. **ATTENTION**: JDBC index of parameters starts with `1` (and not with `0`) what is often causing
  ///        confusion for developers.
  /// @param value the value to bind to the parameter at the given `index`.
  /// @throws SQLException if JDBC failed.
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

  /// @param resultSet the JDBC [ResultSet].
  /// @param columnIndex the column index. See [ResultSet#getObject(int)]. **ATTENTION**: JDBC index of parameters starts with `1` (and not with `0`) what is
  ///        often causing confusion for developers.
  /// @return the requested Java value.
  /// @throws SQLException if JDBC failed.
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

  /// @param resultSet the JDBC [ResultSet].
  /// @param columnLabel the column label. See [ResultSet#getObject(String)].
  /// @return the requested Java value.
  /// @throws SQLException if JDBC failed.
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

  /// This method sets the parameter at the given `index` to the given `value`. It behaves like [#setJavaValue(PreparedStatement, int, Object)] but without
  /// potential [conversion to native DB type][#toTarget(Object)].
  ///
  /// @param statement the [PreparedStatement] where to set the parameter.
  /// @param index the index position of the parameter to set. **ATTENTION**: JDBC index of parameters starts with `1` (and not with `0`) what is often causing
  ///        confusion for developers.
  /// @param value the value to bind to the parameter at the given `index`.
  /// @throws SQLException if JDBC failed.
  /// @see #setJavaValue(PreparedStatement, int, Object)
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

  /// @param resultSet the JDBC [ResultSet].
  /// @param columnIndex the column index. See [ResultSet#getObject(int)]. **ATTENTION**: JDBC index of parameters starts with `1` (and not with `0`) what is
  ///        often causing confusion for developers.
  /// @return the requested database value.
  /// @throws SQLException if JDBC failed.
  public abstract D getDbValue(ResultSet resultSet, int columnIndex) throws SQLException;

  /// @param resultSet the JDBC [ResultSet].
  /// @param columnLabel the column label. See [ResultSet#getObject(String)].
  /// @return the requested database value.
  /// @throws SQLException if JDBC failed.
  public abstract D getDbValue(ResultSet resultSet, String columnLabel) throws SQLException;

  /// This method sets the parameter at the given `index` to the given `value` that is not `null`. E.g. for [DB target type][#getTargetType()] of [String] this
  /// method needs to be implemented as:
  ///
  /// ```
  /// statement.[setString][PreparedStatement#setString(int, String)](index, value);
  /// ```
  ///
  /// @param statement the [PreparedStatement] where to set the parameter.
  /// @param index the index position of the parameter to set. **ATTENTION**: JDBC index of parameters starts with `1` (and not with `0`) what is often causing
  ///        confusion for developers.
  /// @param value the value to bind to the parameter at the given `index`.
  /// @throws SQLException if JDBC failed.
  /// @see #setJavaValue(PreparedStatement, int, Object)
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

  /// @param code the [code][#getCode()].
  /// @return the requested [DbType] or `null` if not found.
  public static DbType<?, ?, ?> get(int code) {

    return CODE_MAP.get(Integer.valueOf(code));
  }

  /// @param <J> type of the [Java source type][#getSourceType()].
  /// @param sourceType the [Java source type][#getSourceType()].
  /// @return the requested [DbType] or `null` if not found.
  @SuppressWarnings("unchecked")
  public static <J> DbType<J, ?, ?> get(Class<J> sourceType) {

    return (DbType<J, ?, ?>) TYPE_MAP.get(ValueType.getNonPrimitiveType(sourceType));
  }

  /// @param name the [name][#getName()].
  /// @return the requested [DbType] or `null` if not found.
  public static DbType<?, ?, ?> get(String name) {

    return NAME_MAP.get(name);
  }

  /// @return a [Stream] of all [DbType]s registered by [source type][#getSourceType()].
  public static Stream<DbType<?, ?, ?>> getAll() {

    return TYPE_MAP.values().stream();
  }

}
