package io.github.mmm.db.schema.impl;

import java.util.Iterator;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.mmm.base.resource.ResourceFile;
import io.github.mmm.base.version.VersionNumber;
import io.github.mmm.db.schema.DbChangeLogScript;
import io.github.mmm.db.schema.DbChangeLogScripts;
import io.github.mmm.db.schema.DbChangeLogSource;
import io.github.mmm.db.schema.DbChangeLogVersion;
import io.github.mmm.db.source.DbSource;
import io.github.mmm.db.source.DbSourceConfig;
import io.github.mmm.db.tx.DbTransaction;

/**
 * Single transactional execution of a schema migration script.
 */
public class DbSchemaManagerExecution implements Runnable {

  private static final Logger LOG = LoggerFactory.getLogger(DbSchemaManagerExecution.class);

  private final Predicate<Module> moduleAcceptor;

  private final DbSource source;

  private final DbChangeLogSource logSource;

  DbSchemaManagerExecution(Predicate<Module> moduleAcceptor, DbSource source, DbChangeLogSource logSource) {

    super();
    this.moduleAcceptor = moduleAcceptor;
    this.source = source;
    this.logSource = logSource;
  }

  @Override
  public void run() {

    createSchemaTable();
    executeScripts(this.logSource.getBeforeScripts(), false);
    Iterator<VersionNumber> it = this.logSource.getVersions().iterator();
    while (it.hasNext()) {
      VersionNumber version = it.next();
      LOG.info("Starting migration to version {}", version);
      DbChangeLogVersion logVersion = this.logSource.get(version);
      executeScripts(logVersion, true);
    }
    executeScripts(this.logSource.getAfterScripts(), false);
  }

  private void createSchemaTable() {

    // TODO Auto-generated method stub

  }

  private void executeScripts(DbChangeLogScripts scripts, boolean track) {

    Iterator<DbChangeLogScript> it = scripts.getScripts().iterator();
    while (it.hasNext()) {
      DbChangeLogScript script = it.next();
      executeScript(script, track);
    }
  }

  private void executeScript(DbChangeLogScript script, boolean track) {

    ResourceFile file = script.getFile();
    Module module = file.getModuleAccess().get();
    if (!this.moduleAcceptor.test(module)) {
      LOG.debug("Ignoring script {} since its module {} is not accepted.", file.getPath(), module.getName());
      return;
    }
    String dbType = script.getDbType();
    if (dbType != null) {
      DbTransaction tx = DbTransaction.get();
      DbSourceConfig config = DbSourceConfig.of(this.source);
      String type = config.getType();
      if (!dbType.equals(type)) {
        LOG.debug("Ignoring script {} since DB type {} does not match current type {}", file.getPath(), dbType, type);
        return;
      }
    }
    long start = System.currentTimeMillis();
    // TODO execute the SQL statements
    // TODO exception handling, rollback (to checkpoint?) and still insert failed migration?
    long end = System.currentTimeMillis();
    long duration = (end - start) / 1000; // duration in seconds
    if (track) {
      // TODO insert row into schema table
    }
  }

}
