package io.github.mmm.db.schema.impl;

import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.Instant;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.mmm.base.exception.ObjectMismatchException;
import io.github.mmm.base.resource.ResourceFile;
import io.github.mmm.base.version.VersionNumber;
import io.github.mmm.binary.BinaryType;
import io.github.mmm.db.repository.EntityRepositoryManager;
import io.github.mmm.db.schema.DbChangeLogScript;
import io.github.mmm.db.schema.DbChangeLogScripts;
import io.github.mmm.db.schema.DbChangeLogSource;
import io.github.mmm.db.schema.DbChangeLogVersion;
import io.github.mmm.db.schema.orm.DbSchemaChangeLog;
import io.github.mmm.db.schema.orm.DbSchemaChangeLogRepository;
import io.github.mmm.db.schema.orm.DbSchemaChangeLogStatus;
import io.github.mmm.db.source.DbSource;
import io.github.mmm.db.source.DbSourceConfig;
import io.github.mmm.db.spi.access.AbstractDbAccess;
import io.github.mmm.db.spi.access.DbAccess;
import io.github.mmm.db.statement.DbNativeStatement;
import io.github.mmm.db.statement.DbNativeStatementParser;
import io.github.mmm.db.tx.spi.AbstractDbTransaction;
import io.github.mmm.scanner.CharReaderScanner;

/**
 * Single transactional execution of a schema migration script.
 */
public class DbSchemaManagerExecution implements Runnable {

  private static final Logger LOG = LoggerFactory.getLogger(DbSchemaManagerExecution.class);

  private final Predicate<Module> moduleAcceptor;

  private final DbSource source;

  private final DbChangeLogSource logSource;

  private final DbSchemaChangeLogRepository repository;

  private final AbstractDbAccess dbAccess;

  private final String scriptPrefix;

  DbSchemaManagerExecution(Predicate<Module> moduleAcceptor, DbSource source, DbChangeLogSource logSource) {

    super();
    this.moduleAcceptor = moduleAcceptor;
    this.source = source;
    this.logSource = logSource;
    this.repository = EntityRepositoryManager.get().get(DbSchemaChangeLogRepository.class);
    this.dbAccess = (AbstractDbAccess) DbAccess.get(DbSourceConfig.of(this.source));
    this.scriptPrefix = "db/changelog/" + this.source.getId() + "/";
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

    this.repository.createTable();
    this.repository.createSequence();
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
    String path = getFilePath(file);
    if (!this.moduleAcceptor.test(module)) {
      LOG.debug("Ignoring script {} since its module {} is not accepted.", path, module.getName());
      return;
    }
    String dbType = script.getDbType();
    if (dbType != null) {
      DbSourceConfig config = DbSourceConfig.of(this.source);
      String type = config.getType();
      if (!dbType.equals(type)) {
        LOG.debug("Ignoring script {} since DB type {} does not match current type {}", path, dbType, type);
        return;
      }
    }
    List<DbNativeStatement> sqls = file.processAsReader(this::parseScript);
    String checksum = null;
    DbSchemaChangeLog changeLog = null;
    if (track) {
      checksum = generateChecksum(sqls);
      changeLog = this.repository.findByPk(path);
      if (changeLog != null) {
        String expectedChecksum = changeLog.Checksum().get();
        if (checksum.equals(expectedChecksum)) {
          LOG.debug("Script {} matches checksum {}", path, expectedChecksum);
          return;
        } else {
          throw new ObjectMismatchException("Checksum of " + path, checksum, expectedChecksum);
        }
      }
    }
    Instant start = Instant.now();
    AbstractDbTransaction tx = AbstractDbTransaction.get(this.source);
    DbSchemaChangeLogStatus status;
    String details = "";
    RuntimeException error = null;
    try {
      long total = 0;
      for (DbNativeStatement statement : sqls) {
        long count = this.dbAccess.executeSql(statement, null, false);
        LOG.debug("Executed SQL affected {} rows: {}", count, statement);
        total += count;
      }
      tx.commit();
      status = DbSchemaChangeLogStatus.SUCCESS;
      details = "Executed affecting " + total + " row(s).";
    } catch (Exception e) {
      status = DbSchemaChangeLogStatus.FAILED;
      details = e.toString();
      error = new IllegalStateException("Schema migration script failed: " + path, e);
      LOG.error("Failed to execute script {}", path, e);
    }
    if (track) {
      Instant end = Instant.now();
      Duration duration = Duration.between(start, end);
      changeLog = DbSchemaChangeLog.of();
      changeLog.setPath(path);
      changeLog.Timestamp().set(start);
      changeLog.Duration().set(duration);
      changeLog.Checksum().set(checksum);
      changeLog.Status().set(status);
      changeLog.Details().set(details);
      this.repository.save(changeLog);
      tx.commit();
      if (error != null) {
        throw error;
      }
    }
  }

  private String getFilePath(ResourceFile file) {

    String path = file.getPath();
    assert path.startsWith(this.scriptPrefix);
    return path.substring(this.scriptPrefix.length());
  }

  private List<DbNativeStatement> parseScript(BufferedReader reader) {

    CharReaderScanner scanner = new CharReaderScanner(reader);
    return DbNativeStatementParser.get().parseAll(scanner);
  }

  private String generateChecksum(List<DbNativeStatement> sqls) {

    try {
      MessageDigest digest = MessageDigest.getInstance("sha256");
      for (DbNativeStatement statement : sqls) {
        String sqlLower = statement.getStatement().toLowerCase(Locale.ROOT);
        digest.update(sqlLower.getBytes(StandardCharsets.UTF_8));
      }
      BinaryType checksum = new BinaryType(digest.digest());
      return checksum.formatHex();
    } catch (NoSuchAlgorithmException e) {
      throw new IllegalStateException(e);
    }
  }

}
