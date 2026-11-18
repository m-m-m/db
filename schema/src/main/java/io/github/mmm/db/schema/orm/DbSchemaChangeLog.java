/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.schema.orm;

import java.time.Instant;

import io.github.mmm.base.metainfo.MetaInfos;
import io.github.mmm.bean.BeanFactory;
import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.entity.id.AbstractId;
import io.github.mmm.entity.id.Id;
import io.github.mmm.entity.property.id.PkProperty;
import io.github.mmm.property.enumeration.EnumProperty;
import io.github.mmm.property.string.StringProperty;
import io.github.mmm.property.time.duration.DurationProperty;
import io.github.mmm.property.time.instant.InstantProperty;

/// {@link EntityBean} for {value DbSchemaManager} table.
@MetaInfos({ "pk=string", "revision=none" })
public interface DbSchemaChangeLog extends EntityBean {

  /// @return the path of the script that was installed. The path will exclude the prefix "db/changelog/«source»/".
  default String getPath() {

    Id<?> id = getId();
    if (id == null) {
      return null;
    }
    return id.getAsString();
  }

  /// @param path the new value of [#getPath()].
  @SuppressWarnings({ "rawtypes", "unchecked" })
  default void setPath(String path) {

    PkProperty pk = Id();
    AbstractId id = (AbstractId) pk.get();
    pk.set(id.withPk(path));
  }

  /// @return the {@link Instant} when the script was installed.
  InstantProperty Timestamp();

  /// @return the {@link java.time.Duration} the script execution took.
  DurationProperty Duration();

  /// @return the login of the database user running the script.
  StringProperty Username();

  /// @return the checksum of the (normalised) script.
  StringProperty Checksum();

  /// @return the {@link DbSchemaChangeLogStatus} summarising the state of the script execution.
  EnumProperty<DbSchemaChangeLogStatus> Status();

  /// @return the details of the execution (e.g. error message).
  StringProperty Details();

  /// @return a new instance of [DbSchemaChangeLog].
  public static DbSchemaChangeLog of() {

    return BeanFactory.get().create(DbSchemaChangeLog.class);
  }

}
