/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.param;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Iterator;

import io.github.mmm.base.exception.ObjectMismatchException;
import io.github.mmm.base.io.AppendableWriter;
import io.github.mmm.db.mapping.UnmappedTypeException;
import io.github.mmm.db.type.DbType;
import io.github.mmm.entity.bean.typemapping.TypeMapping;
import io.github.mmm.property.criteria.CriteriaExpression;
import io.github.mmm.property.criteria.CriteriaParameters;
import io.github.mmm.property.criteria.Literal;
import io.github.mmm.property.enumeration.ReadableEnumProperty;
import io.github.mmm.value.PropertyPath;
import io.github.mmm.value.converter.TypeMapper;

/// [CriteriaParameters] using [CriteriaParameterImpl].
///
/// @since 1.0.0
public abstract class AbstractCriteriaParameters implements CriteriaParameters<CriteriaParameterImpl<?>> {

  private final TypeMapping typeMapping;

  private CriteriaParameterImpl<?> first;

  private CriteriaParameterImpl<?> last;

  /// The constructor.
  ///
  /// @param dialect the [TypeMapping].
  public AbstractCriteriaParameters(TypeMapping dialect) {

    super();
    this.typeMapping = dialect;
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public void onLiteral(Literal<?> literal, AppendableWriter out, CriteriaExpression<?> parent) {

    if (literal != null) {
      Object value = literal.get();
      if (value != null) {
        int index = 0;
        if (this.last != null) {
          index = this.last.getIndex() + 1;
        }
        DbType dbType = null;
        while (dbType == null) {
          Class<?> valueClass = value.getClass();
          TypeMapper typeMapper = getTypeMapper(valueClass, parent);
          if (typeMapper == null) {
            throw new UnmappedTypeException(valueClass);
          } else if (typeMapper instanceof DbType type) {
            dbType = type;
          } else if (typeMapper.next() == null) {
            value = typeMapper.toTarget(value);
          } else {
            throw new ObjectMismatchException(valueClass, "atomic type");
          }
        }
        CriteriaParameterImpl<?> param = createParameter(index, value, dbType, parent);
        if (this.last == null) {
          this.first = param;
          this.last = param;
        } else {
          this.last.append(param);
          this.last = param;
        }
        out.append(param.getPlaceholder());
        return;
      }
    }
    out.append("null");
  }

  private TypeMapper<?, ?> getTypeMapper(Class<?> valueClass, CriteriaExpression<?> expression) {

    if (valueClass.isEnum() && (expression != null)) {
      PropertyPath<?> propertyPath = expression.findFirstProperty();
      if (propertyPath instanceof ReadableEnumProperty<?> property) {
        TypeMapper<?, ?> typeMapper = property.getTypeMapper();
        if (typeMapper != null) {
          return typeMapper;
        }
      }
    }
    return this.typeMapping.getTypeMapper(valueClass);
  }

  /// @param <V> type of the `value`.
  /// @param index the [index][CriteriaParameterImpl#getIndex()].
  /// @param value the [value][CriteriaParameterImpl#getValue()].
  /// @param dbType the the [database type][CriteriaParameterImpl#getDbType()].
  /// @param parent the parent [CriteriaExpression] (see [#onLiteral(Literal, AppendableWriter, CriteriaExpression)]).
  /// @return the new [CriteriaParameterImpl].
  protected abstract <V> CriteriaParameterImpl<V> createParameter(int index, V value, DbType<V, ?, ?> dbType, CriteriaExpression<?> parent);

  @Override
  public Iterator<CriteriaParameterImpl<?>> iterator() {

    if (this.first == null) {
      return Collections.emptyIterator();
    }
    return new CriteriaParameterIterator(this.first);
  }

  /// @param statement the [PreparedStatement].
  /// @param connection the JDBC [Connection].
  /// @throws SQLException on error.
  @SuppressWarnings("exports")
  public void apply(PreparedStatement statement, Connection connection) throws SQLException {

    for (CriteriaParameterImpl<?> param : this) {
      param.apply(statement, connection);
    }
  }

}
