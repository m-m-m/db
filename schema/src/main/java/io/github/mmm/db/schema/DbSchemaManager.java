/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.schema;

import java.time.Instant;
import java.util.function.Predicate;

import io.github.mmm.db.schema.impl.DbSchemaManagerImpl;
import io.github.mmm.db.source.DbSource;

/**
 * Component to automatically manage the schema in the database.<br>
 * <h2>Objective</h2>
 *
 * A database needs to have a well-defined schema fitting to your application code to make things work properly. Newer
 * releases of your code (product) may need a modified schema requiring according migrations to be performed. This
 * component solves such challenges by tracking the version of your schema in the database and applying the needed
 * migrations automatically.
 *
 * <h2>Structure</h2>
 *
 * All you need to do is provide the migration scripts for your schema in the following structure:
 *
 * <pre>
 * / db/changelog
 * └──/ «source»
 *    ├──/ 1.0
 *    |  ├── 001-Create_Foo.sql
 *    |  ├── 002-Insert_Foo.sql
 *    |  └── 003-Create_Bar.sql
 *    ├──/ 1.1
 *    |  ├── 001-Alter_Foo.sql
 *    |  ├── 002-Index_Bar.sql
 *    |  └── 003-Insert_Bar.sql
 *    └──/ always
 *       ├──/ before
 *       |  └── 001-Repair.sql
 *       └──/ after
 *          └── 001-View_Some.sql
 * </pre>
 *
 * The general idea is that you create a folder for each of your product releases below {@code db/changelog/«source»}
 * and add sequentially numbered migration scripts that will be executed in order. The placeholder {@code «source»}
 * refers to the {@link io.github.mmm.db.source.DbSource} and should typically be {@code default}.
 *
 * <h2>Setup</h2>
 *
 * Now when you {@link #setup() setup} the schema, this schema manager will find the resources from the above structure.
 * It will check the database and create a table named {@value #TABLE_NAME} if it does not yet exist (assuming a fresh
 * setup in a new database).
 *
 * It collects all scripts (excluding those from the folder {@code always}) sorted by their path and iterates them. For
 * each script it will do the following:
 * <ul>
 * <li>It checks if the script is relevant (see section {@code Dialects}). Otherwise the script is ignored and we
 * continue with the next one.</li>
 * <li>It computes a checksum of the normalised script (comments removed, newlines and whitespaces ignored, all keyword
 * characters in lower-case)</li>
 * <li>Check in the {@code schema_version} table if that script was already executed. If that is the case, only the hash
 * of the script will be verified but the script execution will be skipped.</li>
 * <li>Otherwise the script will be executed.</li>
 * <li>After the script was executed a row is inserted into the {@code schema_version} table containing the
 * {@link Module#getName() module name}, the path of the file relative to the {@code db/changelog/«source»} folder (e.g.
 * {@code 1.0/001-Create_Foo.sql}), the current {@link Instant timestamp}, the checksum (see above), the current
 * database user, and the status (success or failure).</li>
 * <li>In case the script failed, the schema manager aborts after inserting that row with an exception. Otherwise, it
 * continues to the next script until the end so your schema is up-to-date.</li>
 * </ul>
 *
 * <h2>Always</h2>
 *
 * A special treatment exists for the folder named {@link #FOLDER_ALWAYS always}. The scripts contained here are
 * executed every time when the {@link #setup()} is called. The scripts from the sub-folder {@link #FOLDER_BEFORE
 * before} are always executed before any regular script and the ones from the sub-folder {@link #FOLDER_AFTER after} at
 * the end after the regular scripts. Such scripts need to contain statements that are repeatable (e.g. "CREATE OR
 * REPLACE VIEW ..."). If you have multiple such scripts they are again sorted in alphabetical order. Before scripts
 * should only be used for emergency repairs (e.g. if you had been forced to rename your module, or you could not find
 * another way and had to change an existing script and need to update its checksum in potentially existing databases).
 * Since these scripts are designed to change, no hashes are computed and their execution is not audited in the
 * {@code schema_version} table.
 *
 * <h2>Dialects</h2>
 *
 * Typically your scripts are named {@code «filename».sql} where {@code «filename»} does not contain the dot character.
 * However, you can also add files named {@code «filename».«dialect».sql} for dialect specific scripts. E.g. if you add
 * {@code 001-Create_Foo.postgres.sql} to the above structure besides {@code 001-Create_Foo.sql}, that script will be
 * used for PostGreSQL and for other databases the generic script is used. Other examples for {@code «dialect»} are h2,
 * sqlite, mysql, mariadb, etc. In case some {@code «filename».«dialect».sql} script exists but none is matching your
 * current database a warning is logged. In case that happens for all scripts so no regular script is actually executed,
 * the {@link #setup()} fails. If you think nothing should happen and you do not want to have the warning or error,
 * simply add an empty generic {@code «filename».sql} script.
 *
 * <h2>Modularisation</h2>
 *
 * You can even modularize your application: Split your code into multiple modules that group entities, repositories,
 * and their migration scripts together. In such case you will get the same result as if all scripts are merged into a
 * single module. However, if you have clashing filenames (should be strictly avoided), the {@link Module#getName()
 * module name} helps to make them unique. To align the proper sort order you should use the same release version
 * folders in all your modules. Otherwise you would get into trouble when your migration scripts from one module depends
 * on another module (e.g. for a foreign key constraint).
 *
 * <h2>Hash-Verification</h2>
 *
 * As described above for scripts already executed a hash is stored in the {@code schema_version} table. On every
 * {@link #setup() setup} that hash is compared to verify that the file contents did not change. In case the hash
 * changed, this can be handled in differnt ways depending on the mode configured in the
 * {@link io.github.mmm.db.source.DbSource} property {@code schema_hash_conflict_mode}:
 * <ul>
 * <li>fail: exit the {@link #setup()} with an {@link Exception}.</li>
 * <li>log: log an error and continue.</li>
 * </ul>
 *
 * <h2>Conventions</h2>
 *
 * It is highly recommended to stick to the conventions implied by the example given in the section {@code Structure}
 * above. It will also work if you use four digits for the sequential numbers or even a date-time pattern like
 * YYMMDD_HHmm. Theoretically you could even create more sub-folders and it might still work but this is not guranteed
 * to work in future releases.
 */
public interface DbSchemaManager {

  /** The name of the table for the schema and changelog metadata. */
  String TABLE_NAME = "DbSchemaChangeLog";

  /** The name of the column in table {@value #TABLE_NAME} for the checksum. */
  String COLUMN_NAME_CHECKSUM = "Checksum";

  /** The name of the column in table {@value #TABLE_NAME} for the database user that installed the script. */
  String COLUMN_NAME_USER = "User";

  /** The name of the column in table {@value #TABLE_NAME} for the timestamp when the script was installed. */
  String COLUMN_NAME_TIMESTAMP = "Timestamp";

  /** The name of the column in table {@value #TABLE_NAME} for the duration the script execution took in seconds. */
  String COLUMN_NAME_DURATION = "Duration";

  /**
   * The name of the column in table {@value #TABLE_NAME} for the path to the script that was installed. The path will
   * exclude the prefix "db/changelog/«source»/".
   */
  String COLUMN_NAME_PATH = "Path";

  /** The name of the column in table {@value #TABLE_NAME} for the flag indicating if the script was successful. */
  String COLUMN_NAME_SUCCESS = "Success";

  /** Name of the root folder {@value}. */
  String FOLDER_DB = "db";

  /** Name of folder {@value} that is the sub-folder of {@link #FOLDER_DB}. */
  String FOLDER_CHANGELOG = "changelog";

  /** Name of the folder containing the scripts that are always executed. */
  String FOLDER_ALWAYS = "always";

  /** Name of the folder containing the scripts that are always executed before the regular scripts. */
  String FOLDER_BEFORE = "before";

  /** Name of the folder containing the scripts that are always executed after the regular scripts. */
  String FOLDER_AFTER = "after";

  /** Path {@value}. */
  String PATH_DB_CHANGELOG = FOLDER_DB + '/' + FOLDER_CHANGELOG + '/';

  /** Path {@value}. */
  String PATH_ALWAYS_BEFORE = FOLDER_ALWAYS + '/' + FOLDER_BEFORE;

  /** Path {@value}. */
  String PATH_ALWAYS_AFTER = FOLDER_ALWAYS + '/' + FOLDER_AFTER;

  /**
   * Setup or update the database schema to the latest version.
   */
  default void setup() {

    setup(_ -> true, DbSource.get());
  }

  /**
   * @param moduleAcceptor the {@link Predicate} {@link Predicate#test(Object) deciding} which {@link Module} to accept
   *        ({@code true}) or to reject and ignore for changelog scanning ({@code false}).
   * @param source the {@link DbSource} to use. Typically {@link DbSource#get()}. If you really need multiple
   *        {@link DbSource}s you can provide an additional one here.
   */
  void setup(Predicate<Module> moduleAcceptor, DbSource source);

  /**
   * @return the {@link DbChangeLog} as the model representing the {@link DbSchemaManager described structure}.
   */
  DbChangeLog getChangeLog();

  /**
   * @return the singleton instance of this {@link DbSchemaManager}.
   */
  static DbSchemaManager get() {

    return DbSchemaManagerImpl.INSTANCE;
  }

}
