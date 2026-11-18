/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.schema.orm;

/// {@link Enum} with the available options of the {@link DbSchemaChangeLog} {@link DbSchemaChangeLog#Status() status}.
public enum DbSchemaChangeLogStatus {

  /// The script was successfully applied.
  SUCCESS,

  /// The script failed and a repair is required to proceed.
  FAILED,

  /// The script initially failed but has been repaired.
  REPAIRED,

}
