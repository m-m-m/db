/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.repository.operation;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import io.github.mmm.base.exception.RuntimeIoException;
import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.marshall.StructuredFormatHelper;
import io.github.mmm.marshall.StructuredFormatProvider;
import io.github.mmm.marshall.StructuredReader;
import io.github.mmm.marshall.StructuredWriter;

/// Interface with the operations to [read][#read(Path)] and [write][#write(Path)] all contained
/// [entities][EntityBean] to the local disc.
///
/// @since 1.0.0
public interface EntityDiscOperations {

  /// Loads an entire array of [entities][EntityBean] from the given [StructuredReader] and inserts them into
  /// this repository.
  ///
  /// @param reader the [StructuredReader] to read the array of entities from.
  /// @see #read(Path)
  void read(StructuredReader reader);

  /// @param path the [Path] to the file with all [entities][EntityBean] to load into this repository.
  /// @see #write(Path)
  /// @see #read(StructuredReader)
  default void read(Path path) {

    StructuredFormatProvider provider = StructuredFormatHelper.getProvider(path);
    try (InputStream in = Files.newInputStream(path)) {
      StructuredReader reader = provider.create().reader(in);
      read(reader);
    } catch (IOException e) {
      throw new RuntimeIoException(e);
    }
  }

  /// @param writer the [StructuredWriter] to write all entities of this repository to.
  /// @see #write(Path)
  void write(StructuredWriter writer);

  /// @param path the [Path] to the file where to write all [entities][EntityBean] of this repository.
  /// @see #read(Path)
  /// @see #write(StructuredWriter)
  default void write(Path path) {

    StructuredFormatProvider provider = StructuredFormatHelper.getProvider(path);
    try (OutputStream out = Files.newOutputStream(path)) {
      StructuredWriter writer = provider.create().writer(out);
      write(writer);
    } catch (IOException e) {
      throw new RuntimeIoException(e);
    }
  }

}
