/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.name;

import java.util.Objects;

import io.github.mmm.base.text.CaseSyntax;
import io.github.mmm.db.name.impl.DbNamingStrategyCaseSyntax;
import io.github.mmm.db.name.impl.DbNamingStrategyDefault;
import io.github.mmm.entity.bean.EntityBean;

/**
 * Interface to define the naming strategy to map {@link EntityBean}s to a database.
 *
 * @see #getName(DbObject)
 * @since 1.0.0
 */
public interface DbNamingStrategy {

  /**
   * @return the maximum length for names of DDL elements (tables, columns, constraints, indexes, etc.) supported by the
   *         underlying database. To avoid trouble you should name your entities and properties short but precise.
   *         However, if you somehow exceed the limit, your names will be truncated automatically.
   */
  default int getMaximumNameLength() {

    return 128;
  }

  /**
   * @param name the suggested name of a DDL element.
   * @return the given {@code name} clipped to ensure {@link #getMaximumNameLength()} is not exceeded.
   */
  default String clipName(String name) {

    int max = getMaximumNameLength();
    if (name.length() > max) {
      return name.substring(0, max);
    }
    return name;
  }

  /**
   * This method should typically not be called directly from outside.
   *
   * @param name the original name.
   * @param dbObject the optional {@link DbObject}. May be used to determine the type of database object or
   *        {@link DbObject#hasJavaName()}.
   * @return the given name converted to the desired syntax and conventions for the target database or application
   *         design preferences. E.g. "CustomerAddress" may be converted to "CUSTOMER_ADDRESS".
   */
  default String convertName(String name, DbObject dbObject) {

    return name;
  }

  /**
   * @param name the original name to format for database usage.
   * @param dbObject the optional {@link DbObject}. May be used to determine the type of database object or
   *        {@link DbObject#hasJavaName()}. Never call {@link DbObject#getName()} or
   *        {@link DbObject#getName(DbNamingStrategy)} from this method to prevent infinity loops.
   * @return the given {@code name} mapped to the database according to this naming convention.
   */
  default String getName(String name, DbObject dbObject) {

    return clipName(convertName(name, dbObject));
  }

  /**
   * @param name the original name to format for database usage.
   * @param dbObject the optional {@link DbObject}. May be used to determine the type of database object or
   *        {@link DbObject#hasJavaName()}. Never call {@link DbObject#getName()} or
   *        {@link DbObject#getName(DbNamingStrategy)} from this method to prevent infinity loops.
   * @return the given {@code name} mapped to the database according to this naming convention.
   */
  default DbQualifiedName getName(DbQualifiedName name, DbObject dbObject) {

    Objects.requireNonNull(name);
    String localName = name.getName();
    String formattedName = getName(localName, dbObject);
    return name.withName(formattedName);
  }

  /**
   * @param dbObject the {@link DbObject} to get the name of.
   * @return the {@link DbQualifiedName} of the given {@link DbObject} or auto-generated if not
   *         {@link DbObject#getRawName() available} and {@link #getName(DbQualifiedName, DbObject) formatted} according
   *         to this naming strategy.
   */
  DbQualifiedName getName(DbObject dbObject);

  /**
   * @param entity the {@link EntityBean} to map to a database table.
   * @return the physical table name.
   */
  default DbQualifiedName getTableName(EntityBean entity) {

    DbEntityNameInfo nameInfo = DbEntityNameMapper.get().getTable(entity);
    return getName(nameInfo);
  }

  /**
   * @return the default {@link DbNamingStrategy}.
   */
  static DbNamingStrategy of() {

    return DbNamingStrategyDefault.INSTANCE;
  }

  /**
   * @param caseSyntax the {@link CaseSyntax} to use.
   * @return the {@link DbNamingStrategy} converting to the given {@link CaseSyntax}.
   */
  static DbNamingStrategy of(CaseSyntax caseSyntax) {

    return DbNamingStrategyCaseSyntax.of(caseSyntax);
  }

  /**
   * @return the {@link DbNamingStrategy} for legacy
   *         <a href="https://en.wikipedia.org/wiki/Relational_database">RDBMS</a>.
   * @deprecated will be removed. Use explicit {@link #of(CaseSyntax)} per dialect.
   */
  @Deprecated
  static DbNamingStrategy ofRdbms() {

    return of(CaseSyntax.UPPER_SNAKE_CASE);
  }
}
