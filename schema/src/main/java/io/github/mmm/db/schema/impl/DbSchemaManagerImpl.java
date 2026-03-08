/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.schema.impl;

import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.mmm.base.resource.ModuleAccess;
import io.github.mmm.base.resource.ModuleScanner;
import io.github.mmm.base.resource.ResourceFile;
import io.github.mmm.db.schema.DbChangeLog;
import io.github.mmm.db.schema.DbChangeLogSource;
import io.github.mmm.db.schema.DbSchemaManager;
import io.github.mmm.db.source.DbSource;
import io.github.mmm.db.tx.DbTransactionExecutor;

/**
 * Implementation of {@link DbSchemaManager}.
 */
public class DbSchemaManagerImpl implements DbSchemaManager {

  private static final Logger LOG = LoggerFactory.getLogger(DbSchemaManagerImpl.class);

  /** @see DbSchemaManager#get() */
  public static final DbSchemaManager INSTANCE = new DbSchemaManagerImpl();

  private static final Pattern SCRIPT_PATTERN = Pattern.compile(PATH_DB_CHANGELOG +
  // ..1..................2........................................................................3..........4
      "([a-z][a-z0-9_-]*)/(always/before|always/after|[0-9][0-9a-zA-Z._-]+)/[0-9]{2}[0-9a-zA-Z._-]*(.[a-z]+)?.(sql)");

  private static final int GROUP_SOURCE = 1;

  private static final int GROUP_VERSION = 2;

  private static final int GROUP_DB_TYPE = 3;

  private static final int GROUP_EXTENSION = 4;

  private DbChangeLogImpl changeLog;

  @Override
  public DbChangeLog getChangeLog() {

    if (this.changeLog == null) {
      DbChangeLogImpl newChangeLog = new DbChangeLogImpl();
      for (ModuleAccess moduleAccess : ModuleScanner.get().getAll()) {
        if (!moduleAccess.isInternalModule()) {
          String name = moduleAccess.getResolved().name();
          LOG.debug("Scanning module {} for changelog...", name);
          List<ResourceFile> scripts = moduleAccess
              .findResources(p -> p.startsWith(PATH_DB_CHANGELOG) && p.endsWith(".sql"));
          for (ResourceFile file : scripts) {
            visitFile(file, newChangeLog);
          }
        }
      }
      this.changeLog = newChangeLog;
    }
    return this.changeLog;
  }

  private boolean visitFile(ResourceFile file, DbChangeLogImpl newChangeLog) {

    String name = file.getPath();
    Matcher matcher = SCRIPT_PATTERN.matcher(name);
    if (matcher.matches()) {
      String source = matcher.group(GROUP_SOURCE);
      DbChangeLogSourceImpl sourceNode = newChangeLog.getOrCreate(source);
      String version = matcher.group(GROUP_VERSION);
      DbChangeLogScriptsImpl scriptsNode;
      if (PATH_ALWAYS_BEFORE.equals(version)) {
        scriptsNode = sourceNode.getBeforeScripts();
      } else if (PATH_ALWAYS_AFTER.equals(version)) {
        scriptsNode = sourceNode.getAfterScripts();
      } else {
        scriptsNode = sourceNode.getOrCreate(version);
      }
      String dbType = matcher.group(GROUP_DB_TYPE);
      String extension = matcher.group(GROUP_EXTENSION);
      DbChangeLogScriptImpl script = new DbChangeLogScriptImpl(file, dbType, extension);
      scriptsNode.add(script);
    }
    return true;
  }

  @Override
  public void setup(Predicate<Module> moduleAcceptor, DbSource source) {

    DbChangeLog log = getChangeLog();
    DbChangeLogSource logSource = log.get(source);
    if (logSource == null) {
      throw new IllegalStateException(
          "No changelog found for source " + source + " only available for: " + log.getSources());
    }
    DbTransactionExecutor executor = DbTransactionExecutor.get(source);
    DbSchemaManagerExecution execution = new DbSchemaManagerExecution(moduleAcceptor, source, logSource);
    executor.doInTx(execution);
  }

}
