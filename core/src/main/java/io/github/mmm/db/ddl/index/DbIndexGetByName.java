/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.ddl.index;

/**
 * API to get {@link DbIndex}es by name.
 *
 * @since 1.0.0
 */
public interface DbIndexGetByName {

  /**
   * @param <I> type of the {@link DbIndex}. Typically {@link DbIndex} itself.
   * @param name the {@link DbIndex#getName() name} of the requested index.
   * @return the {@link DbIndex} with the given name.
   */
  <I extends DbIndex> I getIndex(String name);

}
