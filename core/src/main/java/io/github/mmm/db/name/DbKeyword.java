/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.name;

import java.util.Set;

import io.github.mmm.base.sort.SortOrder;

/**
 * Declares all common keywords for database statements. Mainly derived from
 * <a href="https://en.wikipedia.org/wiki/List_of_SQL_reserved_words">wikipedia</a> but also manually extended with
 * missing keywords.
 */
public interface DbKeyword {

  /** Keyword {@value}. */
  String ABORT = "ABORT";

  /** Keyword {@value}. */
  String ABORTSESSION = "ABORTSESSION";

  /** Keyword {@value}. */
  String ABS = "ABS";

  /** Keyword {@value}. */
  String ABSENT = "ABSENT";

  /** Keyword {@value}. */
  String ABSOLUTE = "ABSOLUTE";

  /** Keyword {@value}. */
  String ACCESS = "ACCESS";

  /** Keyword {@value}. */
  String ACCESSIBLE = "ACCESSIBLE";

  /** Keyword {@value}. */
  String ACCESS_LOCK = "ACCESS_LOCK";

  /** Keyword {@value}. */
  String ACCOUNT = "ACCOUNT";

  /** Keyword {@value}. */
  String ACOS = "ACOS";

  /** Keyword {@value}. */
  String ACOSH = "ACOSH";

  /** Keyword {@value}. */
  String ACTION = "ACTION";

  /** Keyword {@value}. */
  String ADD = "ADD";

  /** Keyword {@value}. */
  String ADD_MONTHS = "ADD_MONTHS";

  /** Keyword {@value}. */
  String ADMIN = "ADMIN";

  /** Keyword {@value}. */
  String AFTER = "AFTER";

  /** Keyword {@value}. */
  String AGGREGATE = "AGGREGATE";

  /** Keyword {@value}. */
  String ALIAS = "ALIAS";

  /** Keyword {@value}. */
  String ALL = "ALL";

  /** Keyword {@value}. */
  String ALLOCATE = "ALLOCATE";

  /** Keyword {@value}. */
  String ALLOW = "ALLOW";

  /** Keyword {@value}. */
  String ALTER = "ALTER";

  /** Keyword {@value}. */
  String ALTERAND = "ALTERAND";

  /** Keyword {@value}. */
  String AMP = "AMP";

  /** Keyword {@value}. */
  String ANALYSE = "ANALYSE";

  /** Keyword {@value}. */
  String ANALYZE = "ANALYZE";

  /** Keyword {@value}. */
  String AND = "AND";

  /** Keyword {@value}. */
  String ANSIDATE = "ANSIDATE";

  /** Keyword {@value}. */
  String ANY = "ANY";

  /** Keyword {@value}. */
  String ANY_VALUE = "ANY_VALUE";

  /** Keyword {@value}. */
  String ARE = "ARE";

  /** Keyword {@value}. */
  String ARRAY = "ARRAY";

  /** Keyword {@value}. */
  String ARRAY_AGG = "ARRAY_AGG";

  /** Keyword {@value}. */
  String ARRAY_EXISTS = "ARRAY_EXISTS";

  /** Keyword {@value}. */
  String ARRAY_MAX_CARDINALITY = "ARRAY_MAX_CARDINALITY";

  /** Keyword {@value}. */
  String AS = "AS";

  /** Keyword {@value}. */
  String ASC = SortOrder.ASC.name();

  /** Keyword {@value}. */
  String ASENSITIVE = "ASENSITIVE";

  /** Keyword {@value}. */
  String ASIN = "ASIN";

  /** Keyword {@value}. */
  String ASINH = "ASINH";

  /** Keyword {@value}. */
  String ASSERTION = "ASSERTION";

  /** Keyword {@value}. */
  String ASSOCIATE = "ASSOCIATE";

  /** Keyword {@value}. */
  String ASUTIME = "ASUTIME";

  /** Keyword {@value}. */
  String ASYMMETRIC = "ASYMMETRIC";

  /** Keyword {@value}. */
  String AT = "AT";

  /** Keyword {@value}. */
  String ATAN = "ATAN";

  /** Keyword {@value}. */
  String ATAN2 = "ATAN2";

  /** Keyword {@value}. */
  String ATANH = "ATANH";

  /** Keyword {@value}. */
  String ATOMIC = "ATOMIC";

  /** Keyword {@value}. */
  String AUDIT = "AUDIT";

  /** Keyword {@value}. */
  String AUTHORIZATION = "AUTHORIZATION";

  /** Keyword {@value}. */
  String AUX = "AUX";

  /** Keyword {@value}. */
  String AUXILIARY = "AUXILIARY";

  /** Keyword {@value}. */
  String AVE = "AVE";

  /** Keyword {@value}. */
  String AVERAGE = "AVERAGE";

  /** Keyword {@value}. */
  String AVG = "AVG";

  /** Keyword {@value}. */
  String BACKUP = "BACKUP";

  /** Keyword {@value}. */
  String BEFORE = "BEFORE";

  /** Keyword {@value}. */
  String BEGIN = "BEGIN";

  /** Keyword {@value}. */
  String BEGIN_FRAME = "BEGIN_FRAME";

  /** Keyword {@value}. */
  String BEGIN_PARTITION = "BEGIN_PARTITION";

  /** Keyword {@value}. */
  String BETWEEN = "BETWEEN";

  /** Keyword {@value}. */
  String BIGINT = "BIGINT";

  /** Keyword {@value}. */
  String BINARY = "BINARY";

  /** Keyword {@value}. */
  String BIT = "BIT";

  /** Keyword {@value}. */
  String BLOB = "BLOB";

  /** Keyword {@value}. */
  String BOOLEAN = "BOOLEAN";

  /** Keyword {@value}. */
  String BOTH = "BOTH";

  /** Keyword {@value}. */
  String BREADTH = "BREADTH";

  /** Keyword {@value}. */
  String BREAK = "BREAK";

  /** Keyword {@value}. */
  String BROWSE = "BROWSE";

  /** Keyword {@value}. */
  String BT = "BT";

  /** Keyword {@value}. */
  String BTRIM = "BTRIM";

  /** Keyword {@value}. */
  String BUFFERPOOL = "BUFFERPOOL";

  /** Keyword {@value}. */
  String BULK = "BULK";

  /** Keyword {@value}. */
  String BUT = "BUT";

  /** Keyword {@value}. */
  String BY = "BY";

  /** Keyword {@value}. */
  String BYTE = "BYTE";

  /** Keyword {@value}. */
  String BYTEINT = "BYTEINT";

  /** Keyword {@value}. */
  String BYTES = "BYTES";

  /** Keyword {@value}. */
  String CALL = "CALL";

  /** Keyword {@value}. */
  String CALLED = "CALLED";

  /** Keyword {@value}. */
  String CAPTURE = "CAPTURE";

  /** Keyword {@value}. */
  String CARDINALITY = "CARDINALITY";

  /** Keyword {@value}. */
  String CASCADE = "CASCADE";

  /** Keyword {@value}. */
  String CASCADED = "CASCADED";

  /** Keyword {@value}. */
  String CASE = "CASE";

  /** Keyword {@value}. */
  String CASESPECIFIC = "CASESPECIFIC";

  /** Keyword {@value}. */
  String CASE_N = "CASE_N";

  /** Keyword {@value}. */
  String CAST = "CAST";

  /** Keyword {@value}. */
  String CATALOG = "CATALOG";

  /** Keyword {@value}. */
  String CCSID = "CCSID";

  /** Keyword {@value}. */
  String CD = "CD";

  /** Keyword {@value}. */
  String CEIL = "CEIL";

  /** Keyword {@value}. */
  String CEILING = "CEILING";

  /** Keyword {@value}. */
  String CHANGE = "CHANGE";

  /** Keyword {@value}. */
  String CHAR = "CHAR";

  /** Keyword {@value}. */
  String CHAR2HEXINT = "CHAR2HEXINT";

  /** Keyword {@value}. */
  String CHARACTER = "CHARACTER";

  /** Keyword {@value}. */
  String CHARACTERS = "CHARACTERS";

  /** Keyword {@value}. */
  String CHARACTER_LENGTH = "CHARACTER_LENGTH";

  /** Keyword {@value}. */
  String CHARS = "CHARS";

  /** Keyword {@value}. */
  String CHAR_LENGTH = "CHAR_LENGTH";

  /** Keyword {@value}. */
  String CHECK = "CHECK";

  /** Keyword {@value}. */
  String CHECKPOINT = "CHECKPOINT";

  /** Keyword {@value}. */
  String CLASS = "CLASS";

  /** Keyword {@value}. */
  String CLASSIFIER = "CLASSIFIER";

  /** Keyword {@value}. */
  String CLOB = "CLOB";

  /** Keyword {@value}. */
  String CLONE = "CLONE";

  /** Keyword {@value}. */
  String CLOSE = "CLOSE";

  /** Keyword {@value}. */
  String CLUSTER = "CLUSTER";

  /** Keyword {@value}. */
  String CLUSTERED = "CLUSTERED";

  /** Keyword {@value}. */
  String CM = "CM";

  /** Keyword {@value}. */
  String COALESCE = "COALESCE";

  /** Keyword {@value}. */
  String COLLATE = "COLLATE";

  /** Keyword {@value}. */
  String COLLATION = "COLLATION";

  /** Keyword {@value}. */
  String COLLECT = "COLLECT";

  /** Keyword {@value}. */
  String COLLECTION = "COLLECTION";

  /** Keyword {@value}. */
  String COLLID = "COLLID";

  /** Keyword {@value}. */
  String COLUMN = "COLUMN";

  /** Keyword {@value}. */
  String COLUMN_VALUE = "COLUMN_VALUE";

  /** Keyword {@value}. */
  String COMMENT = "COMMENT";

  /** Keyword {@value}. */
  String COMMIT = "COMMIT";

  /** Keyword {@value}. */
  String COMPLETION = "COMPLETION";

  /** Keyword {@value}. */
  String COMPRESS = "COMPRESS";

  /** Keyword {@value}. */
  String COMPUTE = "COMPUTE";

  /** Keyword {@value}. */
  String CONCAT = "CONCAT";

  /** Keyword {@value}. */
  String CONCURRENTLY = "CONCURRENTLY";

  /** Keyword {@value}. */
  String CONDITION = "CONDITION";

  /** Keyword {@value}. */
  String CONFLICT = "CONFLICT";

  /** Keyword {@value}. */
  String CONNECT = "CONNECT";

  /** Keyword {@value}. */
  String CONNECTION = "CONNECTION";

  /** Keyword {@value}. */
  String CONSTRAINT = "CONSTRAINT";

  /** Keyword {@value}. */
  String CONSTRAINTS = "CONSTRAINTS";

  /** Keyword {@value}. */
  String CONSTRUCTOR = "CONSTRUCTOR";

  /** Keyword {@value}. */
  String CONTAINS = "CONTAINS";

  /** Keyword {@value}. */
  String CONTAINSTABLE = "CONTAINSTABLE";

  /** Keyword {@value}. */
  String CONTENT = "CONTENT";

  /** Keyword {@value}. */
  String CONTINUE = "CONTINUE";

  /** Keyword {@value}. */
  String CONVERT = "CONVERT";

  /** Keyword {@value}. */
  String CONVERT_TABLE_HEADER = "CONVERT_TABLE_HEADER";

  /** Keyword {@value}. */
  String COPY = "COPY";

  /** Keyword {@value}. */
  String CORR = "CORR";

  /** Keyword {@value}. */
  String CORRESPONDING = "CORRESPONDING";

  /** Keyword {@value}. */
  String COS = "COS";

  /** Keyword {@value}. */
  String COSH = "COSH";

  /** Keyword {@value}. */
  String COUNT = "COUNT";

  /** Keyword {@value}. */
  String COVAR_POP = "COVAR_POP";

  /** Keyword {@value}. */
  String COVAR_SAMP = "COVAR_SAMP";

  /** Keyword {@value}. */
  String CREATE = "CREATE";

  /** Keyword {@value}. */
  String CROSS = "CROSS";

  /** Keyword {@value}. */
  String CS = "CS";

  /** Keyword {@value}. */
  String CSUM = "CSUM";

  /** Keyword {@value}. */
  String CT = "CT";

  /** Keyword {@value}. */
  String CUBE = "CUBE";

  /** Keyword {@value}. */
  String CUME_DIST = "CUME_DIST";

  /** Keyword {@value}. */
  String CURRENT = "CURRENT";

  /** Keyword {@value}. */
  String CURRENT_CATALOG = "CURRENT_CATALOG";

  /** Keyword {@value}. */
  String CURRENT_DATE = "CURRENT_DATE";

  /** Keyword {@value}. */
  String CURRENT_DEFAULT_TRANSFORM_GROUP = "CURRENT_DEFAULT_TRANSFORM_GROUP";

  /** Keyword {@value}. */
  String CURRENT_LC_CTYPE = "CURRENT_LC_CTYPE";

  /** Keyword {@value}. */
  String CURRENT_PATH = "CURRENT_PATH";

  /** Keyword {@value}. */
  String CURRENT_ROLE = "CURRENT_ROLE";

  /** Keyword {@value}. */
  String CURRENT_ROW = "CURRENT_ROW";

  /** Keyword {@value}. */
  String CURRENT_SCHEMA = "CURRENT_SCHEMA";

  /** Keyword {@value}. */
  String CURRENT_SERVER = "CURRENT_SERVER";

  /** Keyword {@value}. */
  String CURRENT_TIME = "CURRENT_TIME";

  /** Keyword {@value}. */
  String CURRENT_TIMESTAMP = "CURRENT_TIMESTAMP";

  /** Keyword {@value}. */
  String CURRENT_TIMEZONE = "CURRENT_TIMEZONE";

  /** Keyword {@value}. */
  String CURRENT_TRANSFORM_GROUP_FOR_TYPE = "CURRENT_TRANSFORM_GROUP_FOR_TYPE";

  /** Keyword {@value}. */
  String CURRENT_USER = "CURRENT_USER";

  /** Keyword {@value}. */
  String CURRVAL = "CURRVAL";

  /** Keyword {@value}. */
  String CURSOR = "CURSOR";

  /** Keyword {@value}. */
  String CV = "CV";

  /** Keyword {@value}. */
  String CYCLE = "CYCLE";

  /** Keyword {@value}. */
  String DATA = "DATA";

  /** Keyword {@value}. */
  String DATABASE = "DATABASE";

  /** Keyword {@value}. */
  String DATABASES = "DATABASES";

  /** Keyword {@value}. */
  String DATABLOCKSIZE = "DATABLOCKSIZE";

  /** Keyword {@value}. */
  String DATE = "DATE";

  /** Keyword {@value}. */
  String DATEFORM = "DATEFORM";

  /** Keyword {@value}. */
  String DATEFROM = "DATEFROM";

  /** Keyword {@value}. */
  String DAY = "DAY";

  /** Keyword {@value}. */
  String DAYS = "DAYS";

  /** Keyword {@value}. */
  String DAY_HOUR = "DAY_HOUR";

  /** Keyword {@value}. */
  String DAY_MICROSECOND = "DAY_MICROSECOND";

  /** Keyword {@value}. */
  String DAY_MINUTE = "DAY_MINUTE";

  /** Keyword {@value}. */
  String DAY_SECOND = "DAY_SECOND";

  /** Keyword {@value}. */
  String DBCC = "DBCC";

  /** Keyword {@value}. */
  String DBINFO = "DBINFO";

  /** Keyword {@value}. */
  String DEALLOCATE = "DEALLOCATE";

  /** Keyword {@value}. */
  String DEC = "DEC";

  /** Keyword {@value}. */
  String DECFLOAT = "DECFLOAT";

  /** Keyword {@value}. */
  String DECIMAL = "DECIMAL";

  /** Keyword {@value}. */
  String DECLARE = "DECLARE";

  /** Keyword {@value}. */
  String DEFAULT = "DEFAULT";

  /** Keyword {@value}. */
  String DEFERRABLE = "DEFERRABLE";

  /** Keyword {@value}. */
  String DEFERRED = "DEFERRED";

  /** Keyword {@value}. */
  String DEFINE = "DEFINE";

  /** Keyword {@value}. */
  String DEGREES = "DEGREES";

  /** Keyword {@value}. */
  String DEL = "DEL";

  /** Keyword {@value}. */
  String DELAYED = "DELAYED";

  /** Keyword {@value}. */
  String DELETE = "DELETE";

  /** Keyword {@value}. */
  String DENSE_RANK = "DENSE_RANK";

  /** Keyword {@value}. */
  String DENY = "DENY";

  /** Keyword {@value}. */
  String DEPTH = "DEPTH";

  /** Keyword {@value}. */
  String DEREF = "DEREF";

  /** Keyword {@value}. */
  String DESC = SortOrder.DESC.name();

  /** Keyword {@value}. */
  String DESCRIBE = "DESCRIBE";

  /** Keyword {@value}. */
  String DESCRIPTOR = "DESCRIPTOR";

  /** Keyword {@value}. */
  String DESTROY = "DESTROY";

  /** Keyword {@value}. */
  String DESTRUCTOR = "DESTRUCTOR";

  /** Keyword {@value}. */
  String DETERMINISTIC = "DETERMINISTIC";

  /** Keyword {@value}. */
  String DIAGNOSTIC = "DIAGNOSTIC";

  /** Keyword {@value}. */
  String DIAGNOSTICS = "DIAGNOSTICS";

  /** Keyword {@value}. */
  String DICTIONARY = "DICTIONARY";

  /** Keyword {@value}. */
  String DISABLE = "DISABLE";

  /** Keyword {@value}. */
  String DISABLED = "DISABLED";

  /** Keyword {@value}. */
  String DISALLOW = "DISALLOW";

  /** Keyword {@value}. */
  String DISCONNECT = "DISCONNECT";

  /** Keyword {@value}. */
  String DISK = "DISK";

  /** Keyword {@value}. */
  String DISTINCT = "DISTINCT";

  /** Keyword {@value}. */
  String DISTINCTROW = "DISTINCTROW";

  /** Keyword {@value}. */
  String DISTRIBUTED = "DISTRIBUTED";

  /** Keyword {@value}. */
  String DIV = "DIV";

  /** Keyword {@value}. */
  String DO = "DO";

  /** Keyword {@value}. */
  String DOCUMENT = "DOCUMENT";

  /** Keyword {@value}. */
  String DOMAIN = "DOMAIN";

  /** Keyword {@value}. */
  String DOUBLE = "DOUBLE";

  /** Keyword {@value}. */
  String DROP = "DROP";

  /** Keyword {@value}. */
  String DSSIZE = "DSSIZE";

  /** Keyword {@value}. */
  String DUAL = "DUAL";

  /** Keyword {@value}. */
  String DUMP = "DUMP";

  /** Keyword {@value}. */
  String DYNAMIC = "DYNAMIC";

  /** Keyword {@value}. */
  String EACH = "EACH";

  /** Keyword {@value}. */
  String ECHO = "ECHO";

  /** Keyword {@value}. */
  String EDITPROC = "EDITPROC";

  /** Keyword {@value}. */
  String ELEMENT = "ELEMENT";

  /** Keyword {@value}. */
  String ELSE = "ELSE";

  /** Keyword {@value}. */
  String ELSEIF = "ELSEIF";

  /** Keyword {@value}. */
  String EMPTY = "EMPTY";

  /** Keyword {@value}. */
  String ENABLED = "ENABLED";

  /** Keyword {@value}. */
  String ENCLOSED = "ENCLOSED";

  /** Keyword {@value}. */
  String ENCODING = "ENCODING";

  /** Keyword {@value}. */
  String ENCRYPTION = "ENCRYPTION";

  /** Keyword {@value}. */
  String END = "END";

  /** Keyword {@value}. */
  String ENDING = "ENDING";

  /** Keyword {@value}. */
  String END_EXEC = "END-EXEC";

  /** Keyword {@value}. */
  String END_FRAME = "END_FRAME";

  /** Keyword {@value}. */
  String END_PARTITION = "END_PARTITION";

  /** Keyword {@value}. */
  String EQ = "EQ";

  /** Keyword {@value}. */
  String EQUALS = "EQUALS";

  /** Keyword {@value}. */
  String ERASE = "ERASE";

  /** Keyword {@value}. */
  String ERRLVL = "ERRLVL";

  /** Keyword {@value}. */
  String ERROR = "ERROR";

  /** Keyword {@value}. */
  String ERRORFILES = "ERRORFILES";

  /** Keyword {@value}. */
  String ERRORTABLES = "ERRORTABLES";

  /** Keyword {@value}. */
  String ESCAPE = "ESCAPE";

  /** Keyword {@value}. */
  String ESCAPED = "ESCAPED";

  /** Keyword {@value}. */

  String ET = "ET";

  /** Keyword {@value}. */
  String EVERY = "EVERY";

  /** Keyword {@value}. */
  String EXCEPT = "EXCEPT";

  /** Keyword {@value}. */
  String EXCEPTION = "EXCEPTION";

  /** Keyword {@value}. */
  String EXCLUSIVE = "EXCLUSIVE";

  /** Keyword {@value}. */
  String EXEC = "EXEC";

  /** Keyword {@value}. */
  String EXECUTE = "EXECUTE";

  /** Keyword {@value}. */
  String EXISTS = "EXISTS";

  /** Keyword {@value}. */
  String EXIT = "EXIT";

  /** Keyword {@value}. */
  String EXP = "EXP";

  /** Keyword {@value}. */
  String EXPLAIN = "EXPLAIN";

  /** Keyword {@value}. */
  String EXTERNAL = "EXTERNAL";

  /** Keyword {@value}. */
  String EXTRACT = "EXTRACT";

  /** Keyword {@value}. */
  String FALLBACK = "FALLBACK";

  /** Keyword {@value}. */
  String FALSE = "FALSE";

  /** Keyword {@value}. */
  String FASTEXPORT = "FASTEXPORT";

  /** Keyword {@value}. */
  String FENCED = "FENCED";

  /** Keyword {@value}. */
  String FETCH = "FETCH";

  /** Keyword {@value}. */
  String FIELDPROC = "FIELDPROC";

  /** Keyword {@value}. */
  String FILE = "FILE";

  /** Keyword {@value}. */
  String FILLFACTOR = "FILLFACTOR";

  /** Keyword {@value}. */
  String FILTER = "FILTER";

  /** Keyword {@value}. */
  String FINAL = "FINAL";

  /** Keyword {@value}. */
  String FIRST = "FIRST";

  /** Keyword {@value}. */
  String FIRST_VALUE = "FIRST_VALUE";

  /** Keyword {@value}. */
  String FLOAT = "FLOAT";

  /** Keyword {@value}. */
  String FLOAT4 = "FLOAT4";

  /** Keyword {@value}. */
  String FLOAT8 = "FLOAT8";

  /** Keyword {@value}. */
  String FLOOR = "FLOOR";

  /** Keyword {@value}. */
  String FOR = "FOR";

  /** Keyword {@value}. */
  String FORCE = "FORCE";

  /** Keyword {@value}. */
  String FOREIGN = "FOREIGN";

  /** Keyword {@value}. */
  String FORMAT = "FORMAT";

  /** Keyword {@value}. */
  String FOUND = "FOUND";

  /** Keyword {@value}. */
  String FRAME_ROW = "FRAME_ROW";

  /** Keyword {@value}. */
  String FREE = "FREE";

  /** Keyword {@value}. */
  String FREESPACE = "FREESPACE";

  /** Keyword {@value}. */
  String FREETEXT = "FREETEXT";

  /** Keyword {@value}. */
  String FREETEXTTABLE = "FREETEXTTABLE";

  /** Keyword {@value}. */
  String FREEZE = "FREEZE";

  /** Keyword {@value}. */
  String FROM = "FROM";

  /** Keyword {@value}. */
  String FULL = "FULL";

  /** Keyword {@value}. */
  String FULLTEXT = "FULLTEXT";

  /** Keyword {@value}. */
  String FUNCTION = "FUNCTION";

  /** Keyword {@value}. */
  String FUSION = "FUSION";

  /** Keyword {@value}. */
  String GE = "GE";

  /** Keyword {@value}. */
  String GENERAL = "GENERAL";

  /** Keyword {@value}. */
  String GENERATED = "GENERATED";

  /** Keyword {@value}. */
  String GET = "GET";

  /** Keyword {@value}. */
  String GIVE = "GIVE";

  /** Keyword {@value}. */
  String GLOBAL = "GLOBAL";

  /** Keyword {@value}. */
  String GO = "GO";

  /** Keyword {@value}. */
  String GOTO = "GOTO";

  /** Keyword {@value}. */
  String GRANT = "GRANT";

  /** Keyword {@value}. */
  String GRAPHIC = "GRAPHIC";

  /** Keyword {@value}. */
  String GREATEST = "GREATEST";

  /** Keyword {@value}. */
  String GROUP = "GROUP";

  /** Keyword {@value}. */
  String GROUPING = "GROUPING";

  /** Keyword {@value}. */
  String GROUPS = "GROUPS";

  /** Keyword {@value}. */
  String GT = "GT";

  /** Keyword {@value}. */
  String HANDLER = "HANDLER";

  /** Keyword {@value}. */
  String HASH = "HASH";

  /** Keyword {@value}. */
  String HASHAMP = "HASHAMP";

  /** Keyword {@value}. */
  String HASHBAKAMP = "HASHBAKAMP";

  /** Keyword {@value}. */
  String HASHBUCKET = "HASHBUCKET";

  /** Keyword {@value}. */
  String HASHROW = "HASHROW";

  /** Keyword {@value}. */
  String HAVING = "HAVING";

  /** Keyword {@value}. */
  String HELP = "HELP";

  /** Keyword {@value}. */
  String HIGH_PRIORITY = "HIGH_PRIORITY";

  /** Keyword {@value}. */
  String HOLD = "HOLD";

  /** Keyword {@value}. */
  String HOLDLOCK = "HOLDLOCK";

  /** Keyword {@value}. */
  String HOST = "HOST";

  /** Keyword {@value}. */
  String HOUR = "HOUR";

  /** Keyword {@value}. */
  String HOURS = "HOURS";

  /** Keyword {@value}. */
  String HOUR_MICROSECOND = "HOUR_MICROSECOND";

  /** Keyword {@value}. */
  String HOUR_MINUTE = "HOUR_MINUTE";

  /** Keyword {@value}. */
  String HOUR_SECOND = "HOUR_SECOND";

  /** Keyword {@value}. */
  String IDENTIFIED = "IDENTIFIED";

  /** Keyword {@value}. */
  String IDENTITY = "IDENTITY";

  /** Keyword {@value}. */
  String IDENTITYCOL = "IDENTITYCOL";

  /** Keyword {@value}. */
  String IDENTITY_INSERT = "IDENTITY_INSERT";

  /** Keyword {@value}. */
  String IF = "IF";

  /** Keyword {@value}. */
  String IGNORE = "IGNORE";

  /** Keyword {@value}. */
  String ILIKE = "ILIKE";

  /** Keyword {@value}. */
  String IMMEDIATE = "IMMEDIATE";

  /** Keyword {@value}. */
  String IN = "IN";

  /** Keyword {@value}. */
  String INCLUSIVE = "INCLUSIVE";

  /** Keyword {@value}. */
  String INCONSISTENT = "INCONSISTENT";

  /** Keyword {@value}. */
  String INCREMENT = "INCREMENT";

  /** Keyword {@value}. */
  String INDEX = "INDEX";

  /** Keyword {@value}. */
  String INDICATOR = "INDICATOR";

  /** Keyword {@value}. */
  String INFILE = "INFILE";

  /** Keyword {@value}. */
  String INHERIT = "INHERIT";

  /** Keyword {@value}. */
  String INITIAL = "INITIAL";

  /** Keyword {@value}. */
  String INITIALIZE = "INITIALIZE";

  /** Keyword {@value}. */
  String INITIALLY = "INITIALLY";

  /** Keyword {@value}. */
  String INITIATE = "INITIATE";

  /** Keyword {@value}. */
  String INNER = "INNER";

  /** Keyword {@value}. */
  String INOUT = "INOUT";

  /** Keyword {@value}. */
  String INPUT = "INPUT";

  /** Keyword {@value}. */
  String INS = "INS";

  /** Keyword {@value}. */
  String INSENSITIVE = "INSENSITIVE";

  /** Keyword {@value}. */
  String INSERT = "INSERT";

  /** Keyword {@value}. */
  String INSTEAD = "INSTEAD";

  /** Keyword {@value}. */
  String INT = "INT";

  /** Keyword {@value}. */
  String INT1 = "INT1";

  /** Keyword {@value}. */
  String INT2 = "INT2";

  /** Keyword {@value}. */
  String INT3 = "INT3";

  /** Keyword {@value}. */
  String INT4 = "INT4";

  /** Keyword {@value}. */
  String INT8 = "INT8";

  /** Keyword {@value}. */
  String INTEGER = "INTEGER";

  /** Keyword {@value}. */
  String INTEGERDATE = "INTEGERDATE";

  /** Keyword {@value}. */
  String INTERSECT = "INTERSECT";

  /** Keyword {@value}. */
  String INTERSECTION = "INTERSECTION";

  /** Keyword {@value}. */
  String INTERVAL = "INTERVAL";

  /** Keyword {@value}. */
  String INTO = "INTO";

  /** Keyword {@value}. */
  String IO_AFTER_GTIDS = "IO_AFTER_GTIDS";

  /** Keyword {@value}. */
  String IO_BEFORE_GTIDS = "IO_BEFORE_GTIDS";

  /** Keyword {@value}. */
  String IS = "IS";

  /** Keyword {@value}. */
  String ISNULL = "ISNULL";

  /** Keyword {@value}. */
  String ISOBID = "ISOBID";

  /** Keyword {@value}. */
  String ISOLATION = "ISOLATION";

  /** Keyword {@value}. */
  String ITERATE = "ITERATE";

  /** Keyword {@value}. */
  String JAR = "JAR";

  /** Keyword {@value}. */
  String JOIN = "JOIN";

  /** Keyword {@value}. */
  String JOURNAL = "JOURNAL";

  /** Keyword {@value}. */
  String JSON = "JSON";

  /** Keyword {@value}. */
  String JSON_ARRAY = "JSON_ARRAY";

  /** Keyword {@value}. */
  String JSON_ARRAYAGG = "JSON_ARRAYAGG";

  /** Keyword {@value}. */
  String JSON_EXISTS = "JSON_EXISTS";

  /** Keyword {@value}. */
  String JSON_OBJECT = "JSON_OBJECT";

  /** Keyword {@value}. */
  String JSON_OBJECTAGG = "JSON_OBJECTAGG";

  /** Keyword {@value}. */
  String JSON_QUERY = "JSON_QUERY";

  /** Keyword {@value}. */
  String JSON_SCALAR = "JSON_SCALAR";

  /** Keyword {@value}. */
  String JSON_SERIALIZE = "JSON_SERIALIZE";

  /** Keyword {@value}. */
  String JSON_TABLE = "JSON_TABLE";

  /** Keyword {@value}. */
  String JSON_TABLE_PRIMITIVE = "JSON_TABLE_PRIMITIVE";

  /** Keyword {@value}. */
  String JSON_VALUE = "JSON_VALUE";

  /** Keyword {@value}. */
  String KEEP = "KEEP";

  /** Keyword {@value}. */
  String KEY = "KEY";

  /** Keyword {@value}. */
  String KEYS = "KEYS";

  /** Keyword {@value}. */
  String KILL = "KILL";

  /** Keyword {@value}. */
  String KURTOSIS = "KURTOSIS";

  /** Keyword {@value}. */
  String LABEL = "LABEL";

  /** Keyword {@value}. */
  String LAG = "LAG";

  /** Keyword {@value}. */
  String LANGUAGE = "LANGUAGE";

  /** Keyword {@value}. */
  String LARGE = "LARGE";

  /** Keyword {@value}. */
  String LAST = "LAST";

  /** Keyword {@value}. */
  String LAST_VALUE = "LAST_VALUE";

  /** Keyword {@value}. */
  String LATERAL = "LATERAL";

  /** Keyword {@value}. */
  String LC_CTYPE = "LC_CTYPE";

  /** Keyword {@value}. */
  String LE = "LE";

  /** Keyword {@value}. */
  String LEAD = "LEAD";

  /** Keyword {@value}. */
  String LEADING = "LEADING";

  /** Keyword {@value}. */
  String LEAST = "LEAST";

  /** Keyword {@value}. */
  String LEAVE = "LEAVE";

  /** Keyword {@value}. */
  String LEFT = "LEFT";

  /** Keyword {@value}. */
  String LESS = "LESS";

  /** Keyword {@value}. */
  String LEVEL = "LEVEL";

  /** Keyword {@value}. */
  String LIKE = "LIKE";

  /** Keyword {@value}. */
  String LIKE_REGEX = "LIKE_REGEX";

  /** Keyword {@value}. */
  String LIMIT = "LIMIT";

  /** Keyword {@value}. */
  String LINEAR = "LINEAR";

  /** Keyword {@value}. */
  String LINENO = "LINENO";

  /** Keyword {@value}. */
  String LINES = "LINES";

  /** Keyword {@value}. */
  String LISTAGG = "LISTAGG";

  /** Keyword {@value}. */
  String LN = "LN";

  /** Keyword {@value}. */
  String LOAD = "LOAD";

  /** Keyword {@value}. */
  String LOADING = "LOADING";

  /** Keyword {@value}. */
  String LOCAL = "LOCAL";

  /** Keyword {@value}. */
  String LOCALE = "LOCALE";

  /** Keyword {@value}. */
  String LOCALTIME = "LOCALTIME";

  /** Keyword {@value}. */
  String LOCALTIMESTAMP = "LOCALTIMESTAMP";

  /** Keyword {@value}. */
  String LOCATOR = "LOCATOR";

  /** Keyword {@value}. */
  String LOCATORS = "LOCATORS";

  /** Keyword {@value}. */
  String LOCK = "LOCK";

  /** Keyword {@value}. */
  String LOCKING = "LOCKING";

  /** Keyword {@value}. */
  String LOCKMAX = "LOCKMAX";

  /** Keyword {@value}. */
  String LOCKSIZE = "LOCKSIZE";

  /** Keyword {@value}. */
  String LOG = "LOG";

  /** Keyword {@value}. */
  String LOG10 = "LOG10";

  /** Keyword {@value}. */
  String LOGGING = "LOGGING";

  /** Keyword {@value}. */
  String LOGON = "LOGON";

  /** Keyword {@value}. */
  String LONG = "LONG";

  /** Keyword {@value}. */
  String LONGBLOB = "LONGBLOB";

  /** Keyword {@value}. */
  String LONGTEXT = "LONGTEXT";

  /** Keyword {@value}. */
  String LOOP = "LOOP";

  /** Keyword {@value}. */
  String LOWER = "LOWER";

  /** Keyword {@value}. */
  String LOW_PRIORITY = "LOW_PRIORITY";

  /** Keyword {@value}. */
  String LPAD = "LPAD";

  /** Keyword {@value}. */
  String LT = "LT";

  /** Keyword {@value}. */
  String LTRIM = "LTRIM";

  /** Keyword {@value}. */
  String MACRO = "MACRO";

  /** Keyword {@value}. */
  String MAINTAINED = "MAINTAINED";

  /** Keyword {@value}. */
  String MAP = "MAP";

  /** Keyword {@value}. */
  String MASTER_BIND = "MASTER_BIND";

  /** Keyword {@value}. */
  String MASTER_SSL_VERIFY_SERVER_CERT = "MASTER_SSL_VERIFY_SERVER_CERT";

  /** Keyword {@value}. */
  String MATCH = "MATCH";

  /** Keyword {@value}. */
  String MATCHES = "MATCHES";

  /** Keyword {@value}. */
  String MATCH_NUMBER = "MATCH_NUMBER";

  /** Keyword {@value}. */
  String MATCH_RECOGNIZE = "MATCH_RECOGNIZE";

  /** Keyword {@value}. */
  String MATERIALIZED = "MATERIALIZED";

  /** Keyword {@value}. */
  String MAVG = "MAVG";

  /** Keyword {@value}. */
  String MAX = "MAX";

  /** Keyword {@value}. */
  String MAXEXTENTS = "MAXEXTENTS";

  /** Keyword {@value}. */
  String MAXIMUM = "MAXIMUM";

  /** Keyword {@value}. */
  String MAXVALUE = "MAXVALUE";

  /** Keyword {@value}. */
  String MCHARACTERS = "MCHARACTERS";

  /** Keyword {@value}. */
  String MDIFF = "MDIFF";

  /** Keyword {@value}. */
  String MEDIUMBLOB = "MEDIUMBLOB";

  /** Keyword {@value}. */
  String MEDIUMINT = "MEDIUMINT";

  /** Keyword {@value}. */
  String MEDIUMTEXT = "MEDIUMTEXT";

  /** Keyword {@value}. */
  String MEMBER = "MEMBER";

  /** Keyword {@value}. */
  String MERGE = "MERGE";

  /** Keyword {@value}. */
  String METHOD = "METHOD";

  /** Keyword {@value}. */
  String MICROSECOND = "MICROSECOND";

  /** Keyword {@value}. */
  String MICROSECONDS = "MICROSECONDS";

  /** Keyword {@value}. */
  String MIDDLEINT = "MIDDLEINT";

  /** Keyword {@value}. */
  String MIN = "MIN";

  /** Keyword {@value}. */
  String MINDEX = "MINDEX";

  /** Keyword {@value}. */
  String MINIMUM = "MINIMUM";

  /** Keyword {@value}. */
  String MINUS = "MINUS";

  /** Keyword {@value}. */
  String MINUTE = "MINUTE";

  /** Keyword {@value}. */
  String MINUTES = "MINUTES";

  /** Keyword {@value}. */
  String MINUTE_MICROSECOND = "MINUTE_MICROSECOND";

  /** Keyword {@value}. */
  String MINUTE_SECOND = "MINUTE_SECOND";

  /** Keyword {@value}. */
  String MINVALUE = "MINVALUE";

  /** Keyword {@value}. */
  String MLINREG = "MLINREG";

  /** Keyword {@value}. */
  String MLOAD = "MLOAD";

  /** Keyword {@value}. */
  String MLSLABEL = "MLSLABEL";

  /** Keyword {@value}. */
  String MOD = "MOD";

  /** Keyword {@value}. */
  String MODE = "MODE";

  /** Keyword {@value}. */
  String MODIFIES = "MODIFIES";

  /** Keyword {@value}. */
  String MODIFY = "MODIFY";

  /** Keyword {@value}. */
  String MODULE = "MODULE";

  /** Keyword {@value}. */
  String MONITOR = "MONITOR";

  /** Keyword {@value}. */
  String MONRESOURCE = "MONRESOURCE";

  /** Keyword {@value}. */
  String MONSESSION = "MONSESSION";

  /** Keyword {@value}. */
  String MONTH = "MONTH";

  /** Keyword {@value}. */
  String MONTHS = "MONTHS";

  /** Keyword {@value}. */
  String MSUBSTR = "MSUBSTR";

  /** Keyword {@value}. */
  String MSUM = "MSUM";

  /** Keyword {@value}. */
  String MULTISET = "MULTISET";

  /** Keyword {@value}. */
  String NAMED = "NAMED";

  /** Keyword {@value}. */
  String NAMES = "NAMES";

  /** Keyword {@value}. */
  String NATIONAL = "NATIONAL";

  /** Keyword {@value}. */
  String NATURAL = "NATURAL";

  /** Keyword {@value}. */
  String NCHAR = "NCHAR";

  /** Keyword {@value}. */
  String NCLOB = "NCLOB";

  /** Keyword {@value}. */
  String NE = "NE";

  /** Keyword {@value}. */
  String NESTED_TABLE_ID = "NESTED_TABLE_ID";

  /** Keyword {@value}. */
  String NEW = "NEW";

  /** Keyword {@value}. */
  String NEW_TABLE = "NEW_TABLE";

  /** Keyword {@value}. */
  String NEXT = "NEXT";

  /** Keyword {@value}. */
  String NEXTVAL = "NEXTVAL";

  /** Keyword {@value}. */
  String NO = "NO";

  /** Keyword {@value}. */
  String NOAUDIT = "NOAUDIT";

  /** Keyword {@value}. */
  String NOCHECK = "NOCHECK";

  /** Keyword {@value}. */
  String NOCOMPRESS = "NOCOMPRESS";

  /** Keyword {@value}. */
  String NONCLUSTERED = "NONCLUSTERED";

  /** Keyword {@value}. */
  String NONE = "NONE";

  /** Keyword {@value}. */
  String NORELY = "NORELY";

  /** Keyword {@value}. */
  String NORMALIZE = "NORMALIZE";

  /** Keyword {@value}. */
  String NOT = "NOT";

  /** Keyword {@value}. */
  String NOTHING = "NOTHING";

  /** Keyword {@value}. */
  String NOTNULL = "NOTNULL";

  /** Keyword {@value}. */
  String NOWAIT = "NOWAIT";

  /** Keyword {@value}. */
  String NO_WRITE_TO_BINLOG = "NO_WRITE_TO_BINLOG";

  /** Keyword {@value}. */
  String NTH_VALUE = "NTH_VALUE";

  /** Keyword {@value}. */
  String NTILE = "NTILE";

  /** Keyword {@value}. */
  String NULL = "NULL";

  /** Keyword {@value}. */
  String NULLABLE = "NULLABLE";

  /** Keyword {@value}. */
  String NULLIF = "NULLIF";

  /** Keyword {@value}. */
  String NULLIFZERO = "NULLIFZERO";

  /** Keyword {@value}. */
  String NULLS = "NULLS";

  /** Keyword {@value}. */
  String NUMBER = "NUMBER";

  /** Keyword {@value}. */
  String NUMERIC = "NUMERIC";

  /** Keyword {@value}. */
  String NUMPARTS = "NUMPARTS";

  /** Keyword {@value}. */
  String OBID = "OBID";

  /** Keyword {@value}. */
  String OBJECT = "OBJECT";

  /** Keyword {@value}. */
  String OBJECTS = "OBJECTS";

  /** Keyword {@value}. */
  String OCCURRENCES_REGEX = "OCCURRENCES_REGEX";

  /** Keyword {@value}. */
  String OCTET_LENGTH = "OCTET_LENGTH";

  /** Keyword {@value}. */
  String OF = "OF";

  /** Keyword {@value}. */
  String OFF = "OFF";

  /** Keyword {@value}. */
  String OFFLINE = "OFFLINE";

  /** Keyword {@value}. */
  String OFFSET = "OFFSET";

  /** Keyword {@value}. */
  String OFFSETS = "OFFSETS";

  /** Keyword {@value}. */
  String OLD = "OLD";

  /** Keyword {@value}. */
  String OLD_TABLE = "OLD_TABLE";

  /** Keyword {@value}. */
  String OMIT = "OMIT";

  /** Keyword {@value}. */
  String ON = "ON";

  /** Keyword {@value}. */
  String ONE = "ONE";

  /** Keyword {@value}. */
  String ONLINE = "ONLINE";

  /** Keyword {@value}. */
  String ONLY = "ONLY";

  /** Keyword {@value}. */
  String OPEN = "OPEN";

  /** Keyword {@value}. */
  String OPENDATASOURCE = "OPENDATASOURCE";

  /** Keyword {@value}. */
  String OPENQUERY = "OPENQUERY";

  /** Keyword {@value}. */
  String OPENROWSET = "OPENROWSET";

  /** Keyword {@value}. */
  String OPENXML = "OPENXML";

  /** Keyword {@value}. */
  String OPERATION = "OPERATION";

  /** Keyword {@value}. */
  String OPTIMIZATION = "OPTIMIZATION";

  /** Keyword {@value}. */
  String OPTIMIZE = "OPTIMIZE";

  /** Keyword {@value}. */
  String OPTIMIZER_COSTS = "OPTIMIZER_COSTS";

  /** Keyword {@value}. */
  String OPTION = "OPTION";

  /** Keyword {@value}. */
  String OPTIONALLY = "OPTIONALLY";

  /** Keyword {@value}. */
  String OR = "OR";

  /** Keyword {@value}. */
  String ORDER = "ORDER";

  /** Keyword {@value}. */
  String ORDINALITY = "ORDINALITY";

  /** Keyword {@value}. */
  String ORGANIZATION = "ORGANIZATION";

  /** Keyword {@value}. */
  String OUT = "OUT";

  /** Keyword {@value}. */
  String OUTER = "OUTER";

  /** Keyword {@value}. */
  String OUTFILE = "OUTFILE";

  /** Keyword {@value}. */
  String OUTPUT = "OUTPUT";

  /** Keyword {@value}. */
  String OVER = "OVER";

  /** Keyword {@value}. */
  String OVERLAPS = "OVERLAPS";

  /** Keyword {@value}. */
  String OVERLAY = "OVERLAY";

  /** Keyword {@value}. */
  String OVERRIDE = "OVERRIDE";

  /** Keyword {@value}. */
  String PACKAGE = "PACKAGE";

  /** Keyword {@value}. */
  String PAD = "PAD";

  /** Keyword {@value}. */
  String PADDED = "PADDED";

  /** Keyword {@value}. */
  String PARAMETER = "PARAMETER";

  /** Keyword {@value}. */
  String PARAMETERS = "PARAMETERS";

  /** Keyword {@value}. */
  String PART = "PART";

  /** Keyword {@value}. */
  String PARTIAL = "PARTIAL";

  /** Keyword {@value}. */
  String PARTITION = "PARTITION";

  /** Keyword {@value}. */
  String PARTITIONED = "PARTITIONED";

  /** Keyword {@value}. */
  String PARTITIONING = "PARTITIONING";

  /** Keyword {@value}. */
  String PASSWORD = "PASSWORD";

  /** Keyword {@value}. */
  String PATH = "PATH";

  /** Keyword {@value}. */
  String PATTERN = "PATTERN";

  /** Keyword {@value}. */
  String PCTFREE = "PCTFREE";

  /** Keyword {@value}. */
  String PER = "PER";

  /** Keyword {@value}. */
  String PERCENT = "PERCENT";

  /** Keyword {@value}. */
  String PERCENTILE_CONT = "PERCENTILE_CONT";

  /** Keyword {@value}. */
  String PERCENTILE_DISC = "PERCENTILE_DISC";

  /** Keyword {@value}. */
  String PERCENT_RANK = "PERCENT_RANK";

  /** Keyword {@value}. */
  String PERIOD = "PERIOD";

  /** Keyword {@value}. */
  String PERM = "PERM";

  /** Keyword {@value}. */
  String PERMANENT = "PERMANENT";

  /** Keyword {@value}. */
  String PIECESIZE = "PIECESIZE";

  /** Keyword {@value}. */
  String PIVOT = "PIVOT";

  /** Keyword {@value}. */
  String PLACING = "PLACING";

  /** Keyword {@value}. */
  String PLAN = "PLAN";

  /** Keyword {@value}. */
  String PORTION = "PORTION";

  /** Keyword {@value}. */
  String POSITION = "POSITION";

  /** Keyword {@value}. */
  String POSITION_REGEX = "POSITION_REGEX";

  /** Keyword {@value}. */
  String POSTFIX = "POSTFIX";

  /** Keyword {@value}. */
  String POWER = "POWER";

  /** Keyword {@value}. */
  String PRECEDES = "PRECEDES";

  /** Keyword {@value}. */
  String PRECISION = "PRECISION";

  /** Keyword {@value}. */
  String PREFIX = "PREFIX";

  /** Keyword {@value}. */
  String PREORDER = "PREORDER";

  /** Keyword {@value}. */
  String PREPARE = "PREPARE";

  /** Keyword {@value}. */
  String PRESERVE = "PRESERVE";

  /** Keyword {@value}. */
  String PREVVAL = "PREVVAL";

  /** Keyword {@value}. */
  String PRIMARY = "PRIMARY";

  /** Keyword {@value}. */
  String PRINT = "PRINT";

  /** Keyword {@value}. */
  String PRIOR = "PRIOR";

  /** Keyword {@value}. */
  String PRIQTY = "PRIQTY";

  /** Keyword {@value}. */
  String PRIVATE = "PRIVATE";

  /** Keyword {@value}. */
  String PRIVILEGES = "PRIVILEGES";

  /** Keyword {@value}. */
  String PROC = "PROC";

  /** Keyword {@value}. */
  String PROCEDURE = "PROCEDURE";

  /** Keyword {@value}. */
  String PROFILE = "PROFILE";

  /** Keyword {@value}. */
  String PROGRAM = "PROGRAM";

  /** Keyword {@value}. */
  String PROPORTIONAL = "PROPORTIONAL";

  /** Keyword {@value}. */
  String PROTECTION = "PROTECTION";

  /** Keyword {@value}. */
  String PSID = "PSID";

  /** Keyword {@value}. */
  String PTF = "PTF";

  /** Keyword {@value}. */
  String PUBLIC = "PUBLIC";

  /** Keyword {@value}. */
  String PURGE = "PURGE";

  /** Keyword {@value}. */
  String QUALIFIED = "QUALIFIED";

  /** Keyword {@value}. */
  String QUALIFY = "QUALIFY";

  /** Keyword {@value}. */
  String QUANTILE = "QUANTILE";

  /** Keyword {@value}. */
  String QUERY = "QUERY";

  /** Keyword {@value}. */
  String QUERYNO = "QUERYNO";

  /** Keyword {@value}. */
  String RADIANS = "RADIANS";

  /** Keyword {@value}. */
  String RAISERROR = "RAISERROR";

  /** Keyword {@value}. */
  String RANDOM = "RANDOM";

  /** Keyword {@value}. */
  String RANGE = "RANGE";

  /** Keyword {@value}. */
  String RANGE_N = "RANGE_N";

  /** Keyword {@value}. */
  String RANK = "RANK";

  /** Keyword {@value}. */
  String RAW = "RAW";

  /** Keyword {@value}. */
  String READ = "READ";

  /** Keyword {@value}. */
  String READS = "READS";

  /** Keyword {@value}. */
  String READTEXT = "READTEXT";

  /** Keyword {@value}. */
  String READ_WRITE = "READ_WRITE";

  /** Keyword {@value}. */
  String REAL = "REAL";

  /** Keyword {@value}. */
  String RECONFIGURE = "RECONFIGURE";

  /** Keyword {@value}. */
  String RECURSIVE = "RECURSIVE";

  /** Keyword {@value}. */
  String REF = "REF";

  /** Keyword {@value}. */
  String REFERENCES = "REFERENCES";

  /** Keyword {@value}. */
  String REFERENCING = "REFERENCING";

  /** Keyword {@value}. */
  String REFRESH = "REFRESH";

  /** Keyword {@value}. */
  String REGEXP = "REGEXP";

  /** Keyword {@value}. */
  String REGR_AVGX = "REGR_AVGX";

  /** Keyword {@value}. */
  String REGR_AVGY = "REGR_AVGY";

  /** Keyword {@value}. */
  String REGR_COUNT = "REGR_COUNT";

  /** Keyword {@value}. */
  String REGR_INTERCEPT = "REGR_INTERCEPT";

  /** Keyword {@value}. */
  String REGR_R2 = "REGR_R2";

  /** Keyword {@value}. */
  String REGR_SLOPE = "REGR_SLOPE";

  /** Keyword {@value}. */
  String REGR_SXX = "REGR_SXX";

  /** Keyword {@value}. */
  String REGR_SXY = "REGR_SXY";

  /** Keyword {@value}. */
  String REGR_SYY = "REGR_SYY";

  /** Keyword {@value}. */
  String RELATIVE = "RELATIVE";

  /** Keyword {@value}. */
  String RELEASE = "RELEASE";

  /** Keyword {@value}. */
  String RELY = "RELY";

  /** Keyword {@value}. */
  String RENAME = "RENAME";

  /** Keyword {@value}. */
  String REPEAT = "REPEAT";

  /** Keyword {@value}. */
  String REPLACE = "REPLACE";

  /** Keyword {@value}. */
  String REPLICATION = "REPLICATION";

  /** Keyword {@value}. */
  String REPOVERRIDE = "REPOVERRIDE";

  /** Keyword {@value}. */
  String REQUEST = "REQUEST";

  /** Keyword {@value}. */
  String REQUIRE = "REQUIRE";

  /** Keyword {@value}. */
  String RESIGNAL = "RESIGNAL";

  /** Keyword {@value}. */
  String RESOURCE = "RESOURCE";

  /** Keyword {@value}. */
  String RESTART = "RESTART";

  /** Keyword {@value}. */
  String RESTORE = "RESTORE";

  /** Keyword {@value}. */
  String RESTRICT = "RESTRICT";

  /** Keyword {@value}. */
  String RESULT = "RESULT";

  /** Keyword {@value}. */
  String RESULT_SET_LOCATOR = "RESULT_SET_LOCATOR";

  /** Keyword {@value}. */
  String RESUME = "RESUME";

  /** Keyword {@value}. */
  String RET = "RET";

  /** Keyword {@value}. */
  String RETRIEVE = "RETRIEVE";

  /** Keyword {@value}. */
  String RETURN = "RETURN";

  /** Keyword {@value}. */
  String RETURNING = "RETURNING";

  /** Keyword {@value}. */
  String RETURNS = "RETURNS";

  /** Keyword {@value}. */
  String REVALIDATE = "REVALIDATE";

  /** Keyword {@value}. */
  String REVERT = "REVERT";

  /** Keyword {@value}. */
  String REVOKE = "REVOKE";

  /** Keyword {@value}. */
  String RIGHT = "RIGHT";

  /** Keyword {@value}. */
  String RIGHTS = "RIGHTS";

  /** Keyword {@value}. */
  String RLIKE = "RLIKE";

  /** Keyword {@value}. */
  String ROLE = "ROLE";

  /** Keyword {@value}. */
  String ROLLBACK = "ROLLBACK";

  /** Keyword {@value}. */
  String ROLLFORWARD = "ROLLFORWARD";

  /** Keyword {@value}. */
  String ROLLUP = "ROLLUP";

  /** Keyword {@value}. */
  String ROUND_CEILING = "ROUND_CEILING";

  /** Keyword {@value}. */
  String ROUND_DOWN = "ROUND_DOWN";

  /** Keyword {@value}. */
  String ROUND_FLOOR = "ROUND_FLOOR";

  /** Keyword {@value}. */
  String ROUND_HALF_DOWN = "ROUND_HALF_DOWN";

  /** Keyword {@value}. */
  String ROUND_HALF_EVEN = "ROUND_HALF_EVEN";

  /** Keyword {@value}. */
  String ROUND_HALF_UP = "ROUND_HALF_UP";

  /** Keyword {@value}. */
  String ROUND_UP = "ROUND_UP";

  /** Keyword {@value}. */
  String ROUTINE = "ROUTINE";

  /** Keyword {@value}. */
  String ROW = "ROW";

  /** Keyword {@value}. */
  String ROWCOUNT = "ROWCOUNT";

  /** Keyword {@value}. */
  String ROWGUIDCOL = "ROWGUIDCOL";

  /** Keyword {@value}. */
  String ROWID = "ROWID";

  /** Keyword {@value}. */
  String ROWNUM = "ROWNUM";

  /** Keyword {@value}. */
  String ROWS = "ROWS";

  /** Keyword {@value}. */
  String ROWSET = "ROWSET";

  /** Keyword {@value}. */
  String ROW_NUMBER = "ROW_NUMBER";

  /** Keyword {@value}. */
  String RPAD = "RPAD";

  /** Keyword {@value}. */
  String RTRIM = "RTRIM";

  /** Keyword {@value}. */
  String RULE = "RULE";

  /** Keyword {@value}. */
  String RUN = "RUN";

  /** Keyword {@value}. */
  String RUNNING = "RUNNING";

  /** Keyword {@value}. */
  String SAMPLE = "SAMPLE";

  /** Keyword {@value}. */
  String SAMPLEID = "SAMPLEID";

  /** Keyword {@value}. */
  String SAVE = "SAVE";

  /** Keyword {@value}. */
  String SAVEPOINT = "SAVEPOINT";

  /** Keyword {@value}. */
  String SCHEMA = "SCHEMA";

  /** Keyword {@value}. */
  String SCHEMAS = "SCHEMAS";

  /** Keyword {@value}. */
  String SCOPE = "SCOPE";

  /** Keyword {@value}. */
  String SCRATCHPAD = "SCRATCHPAD";

  /** Keyword {@value}. */
  String SCROLL = "SCROLL";

  /** Keyword {@value}. */
  String SEARCH = "SEARCH";

  /** Keyword {@value}. */
  String SECOND = "SECOND";

  /** Keyword {@value}. */
  String SECONDS = "SECONDS";

  /** Keyword {@value}. */
  String SECOND_MICROSECOND = "SECOND_MICROSECOND";

  /** Keyword {@value}. */
  String SECQTY = "SECQTY";

  /** Keyword {@value}. */
  String SECTION = "SECTION";

  /** Keyword {@value}. */
  String SECURITY = "SECURITY";

  /** Keyword {@value}. */
  String SECURITYAUDIT = "SECURITYAUDIT";

  /** Keyword {@value}. */
  String SEEK = "SEEK";

  /** Keyword {@value}. */
  String SEL = "SEL";

  /** Keyword {@value}. */
  String SELECT = "SELECT";

  /** Keyword {@value}. */
  String SEMANTICKEYPHRASETABLE = "SEMANTICKEYPHRASETABLE";

  /** Keyword {@value}. */
  String SEMANTICSIMILARITYDETAILSTABLE = "SEMANTICSIMILARITYDETAILSTABLE";

  /** Keyword {@value}. */
  String SEMANTICSIMILARITYTABLE = "SEMANTICSIMILARITYTABLE";

  /** Keyword {@value}. */
  String SENSITIVE = "SENSITIVE";

  /** Keyword {@value}. */
  String SEPARATOR = "SEPARATOR";

  /** Keyword {@value}. */
  String SEQUENCE = "SEQUENCE";

  /** Keyword {@value}. */
  String SESSION = "SESSION";

  /** Keyword {@value}. */
  String SESSION_USER = "SESSION_USER";

  /** Keyword {@value}. */
  String SET = "SET";

  /** Keyword {@value}. */
  String SETRESRATE = "SETRESRATE";

  /** Keyword {@value}. */
  String SETS = "SETS";

  /** Keyword {@value}. */
  String SETSESSRATE = "SETSESSRATE";

  /** Keyword {@value}. */
  String SETUSER = "SETUSER";

  /** Keyword {@value}. */
  String SHARE = "SHARE";

  /** Keyword {@value}. */
  String SHOW = "SHOW";

  /** Keyword {@value}. */
  String SHUTDOWN = "SHUTDOWN";

  /** Keyword {@value}. */
  String SIGNAL = "SIGNAL";

  /** Keyword {@value}. */
  String SIMILAR = "SIMILAR";

  /** Keyword {@value}. */
  String SIMPLE = "SIMPLE";

  /** Keyword {@value}. */
  String SIN = "SIN";

  /** Keyword {@value}. */
  String SINH = "SINH";

  /** Keyword {@value}. */
  String SIZE = "SIZE";

  /** Keyword {@value}. */
  String SKEW = "SKEW";

  /** Keyword {@value}. */
  String SKIP = "SKIP";

  /** Keyword {@value}. */
  String SMALLINT = "SMALLINT";

  /** Keyword {@value}. */
  String SOME = "SOME";

  /** Keyword {@value}. */
  String SOUNDEX = "SOUNDEX";

  /** Keyword {@value}. */
  String SOURCE = "SOURCE";

  /** Keyword {@value}. */
  String SPACE = "SPACE";

  /** Keyword {@value}. */
  String SPATIAL = "SPATIAL";

  /** Keyword {@value}. */
  String SPECIFIC = "SPECIFIC";

  /** Keyword {@value}. */
  String SPECIFICTYPE = "SPECIFICTYPE";

  /** Keyword {@value}. */
  String SPOOL = "SPOOL";

  /** Keyword {@value}. */
  String SQL = "SQL";

  /** Keyword {@value}. */
  String SQLEXCEPTION = "SQLEXCEPTION";

  /** Keyword {@value}. */
  String SQLSTATE = "SQLSTATE";

  /** Keyword {@value}. */
  String SQLTEXT = "SQLTEXT";

  /** Keyword {@value}. */
  String SQLWARNING = "SQLWARNING";

  /** Keyword {@value}. */
  String SQL_BIG_RESULT = "SQL_BIG_RESULT";

  /** Keyword {@value}. */
  String SQL_CALC_FOUND_ROWS = "SQL_CALC_FOUND_ROWS";

  /** Keyword {@value}. */
  String SQL_SMALL_RESULT = "SQL_SMALL_RESULT";

  /** Keyword {@value}. */
  String SQRT = "SQRT";

  /** Keyword {@value}. */
  String SS = "SS";

  /** Keyword {@value}. */
  String SSL = "SSL";

  /** Keyword {@value}. */
  String STANDARD = "STANDARD";

  /** Keyword {@value}. */
  String START = "START";

  /** Keyword {@value}. */
  String STARTING = "STARTING";

  /** Keyword {@value}. */
  String STARTUP = "STARTUP";

  /** Keyword {@value}. */
  String STATE = "STATE";

  /** Keyword {@value}. */
  String STATEMENT = "STATEMENT";

  /** Keyword {@value}. */
  String STATIC = "STATIC";

  /** Keyword {@value}. */
  String STATISTICS = "STATISTICS";

  /** Keyword {@value}. */
  String STAY = "STAY";

  /** Keyword {@value}. */
  String STDDEV_POP = "STDDEV_POP";

  /** Keyword {@value}. */
  String STDDEV_SAMP = "STDDEV_SAMP";

  /** Keyword {@value}. */
  String STEPINFO = "STEPINFO";

  /** Keyword {@value}. */
  String STOGROUP = "STOGROUP";

  /** Keyword {@value}. */
  String STORED = "STORED";

  /** Keyword {@value}. */
  String STORES = "STORES";

  /** Keyword {@value}. */
  String STRAIGHT_JOIN = "STRAIGHT_JOIN";

  /** Keyword {@value}. */
  String STRING_CS = "STRING_CS";

  /** Keyword {@value}. */
  String STRUCTURE = "STRUCTURE";

  /** Keyword {@value}. */
  String STYLE = "STYLE";

  /** Keyword {@value}. */
  String SUBMULTISET = "SUBMULTISET";

  /** Keyword {@value}. */
  String SUBSCRIBER = "SUBSCRIBER";

  /** Keyword {@value}. */
  String SUBSET = "SUBSET";

  /** Keyword {@value}. */
  String SUBSTR = "SUBSTR";

  /** Keyword {@value}. */
  String SUBSTRING = "SUBSTRING";

  /** Keyword {@value}. */
  String SUBSTRING_REGEX = "SUBSTRING_REGEX";

  /** Keyword {@value}. */
  String SUCCEEDS = "SUCCEEDS";

  /** Keyword {@value}. */
  String SUCCESSFUL = "SUCCESSFUL";

  /** Keyword {@value}. */
  String SUM = "SUM";

  /** Keyword {@value}. */
  String SUMMARY = "SUMMARY";

  /** Keyword {@value}. */
  String SUSPEND = "SUSPEND";

  /** Keyword {@value}. */
  String SYMMETRIC = "SYMMETRIC";

  /** Keyword {@value}. */
  String SYNONYM = "SYNONYM";

  /** Keyword {@value}. */
  String SYSDATE = "SYSDATE";

  /** Keyword {@value}. */
  String SYSTEM = "SYSTEM";

  /** Keyword {@value}. */
  String SYSTEM_TIME = "SYSTEM_TIME";

  /** Keyword {@value}. */
  String SYSTEM_USER = "SYSTEM_USER";

  /** Keyword {@value}. */
  String SYSTIMESTAMP = "SYSTIMESTAMP";

  /** Keyword {@value}. */
  String TABLE = "TABLE";

  /** Keyword {@value}. */
  String TABLESAMPLE = "TABLESAMPLE";

  /** Keyword {@value}. */
  String TABLESPACE = "TABLESPACE";

  /** Keyword {@value}. */
  String TAN = "TAN";

  /** Keyword {@value}. */
  String TANH = "TANH";

  /** Keyword {@value}. */
  String TBL_CS = "TBL_CS";

  /** Keyword {@value}. */
  String TEMPORARY = "TEMPORARY";

  /** Keyword {@value}. */
  String TERMINATE = "TERMINATE";

  /** Keyword {@value}. */
  String TERMINATED = "TERMINATED";

  /** Keyword {@value}. */
  String TEXT = "TEXT";

  /** Keyword {@value}. */
  String TEXTSIZE = "TEXTSIZE";

  /** Keyword {@value}. */
  String THAN = "THAN";

  /** Keyword {@value}. */
  String THEN = "THEN";

  /** Keyword {@value}. */
  String THRESHOLD = "THRESHOLD";

  /** Keyword {@value}. */
  String TIME = "TIME";

  /** Keyword {@value}. */
  String TIMESTAMP = "TIMESTAMP";

  /** Keyword {@value}. */
  String TIMEZONE_HOUR = "TIMEZONE_HOUR";

  /** Keyword {@value}. */
  String TIMEZONE_MINUTE = "TIMEZONE_MINUTE";

  /** Keyword {@value}. */
  String TINYBLOB = "TINYBLOB";

  /** Keyword {@value}. */
  String TINYINT = "TINYINT";

  /** Keyword {@value}. */
  String TINYTEXT = "TINYTEXT";

  /** Keyword {@value}. */
  String TITLE = "TITLE";

  /** Keyword {@value}. */
  String TO = "TO";

  /** Keyword {@value}. */
  String TOP = "TOP";

  /** Keyword {@value}. */
  String TRACE = "TRACE";

  /** Keyword {@value}. */
  String TRAILING = "TRAILING";

  /** Keyword {@value}. */
  String TRAN = "TRAN";

  /** Keyword {@value}. */
  String TRANSACTION = "TRANSACTION";

  /** Keyword {@value}. */
  String TRANSLATE = "TRANSLATE";

  /** Keyword {@value}. */
  String TRANSLATE_CHK = "TRANSLATE_CHK";

  /** Keyword {@value}. */
  String TRANSLATE_REGEX = "TRANSLATE_REGEX";

  /** Keyword {@value}. */
  String TRANSLATION = "TRANSLATION";

  /** Keyword {@value}. */
  String TREAT = "TREAT";

  /** Keyword {@value}. */
  String TRIGGER = "TRIGGER";

  /** Keyword {@value}. */
  String TRIM = "TRIM";

  /** Keyword {@value}. */
  String TRIM_ARRAY = "TRIM_ARRAY";

  /** Keyword {@value}. */
  String TRUE = "TRUE";

  /** Keyword {@value}. */
  String TRUNCATE = "TRUNCATE";

  /** Keyword {@value}. */
  String TRY_CONVERT = "TRY_CONVERT";

  /** Keyword {@value}. */
  String TSEQUAL = "TSEQUAL";

  /** Keyword {@value}. */
  String TYPE = "TYPE";

  /** Keyword {@value}. */
  String UC = "UC";

  /** Keyword {@value}. */
  String UESCAPE = "UESCAPE";

  /** Keyword {@value}. */
  String UID = "UID";

  /** Keyword {@value}. */
  String UNDEFINED = "UNDEFINED";

  /** Keyword {@value}. */
  String UNDER = "UNDER";

  /** Keyword {@value}. */
  String UNDO = "UNDO";

  /** Keyword {@value}. */
  String UNION = "UNION";

  /** Keyword {@value}. */
  String UNIQUE = "UNIQUE";

  /** Keyword {@value}. */
  String UNKNOWN = "UNKNOWN";

  /** Keyword {@value}. */
  String UNLOCK = "UNLOCK";

  /** Keyword {@value}. */
  String UNNEST = "UNNEST";

  /** Keyword {@value}. */
  String UNPIVOT = "UNPIVOT";

  /** Keyword {@value}. */
  String UNSIGNED = "UNSIGNED";

  /** Keyword {@value}. */
  String UNTIL = "UNTIL";

  /** Keyword {@value}. */
  String UPD = "UPD";

  /** Keyword {@value}. */
  String UPDATE = "UPDATE";

  /** Keyword {@value}. */
  String UPDATETEXT = "UPDATETEXT";

  /** Keyword {@value}. */
  String UPPER = "UPPER";

  /** Keyword {@value}. */
  String UPPERCASE = "UPPERCASE";

  /** Keyword {@value}. */
  String UPSERT = "UPSERT";

  /** Keyword {@value}. */
  String USAGE = "USAGE";

  /** Keyword {@value}. */
  String USE = "USE";

  /** Keyword {@value}. */
  String USER = "USER";

  /** Keyword {@value}. */
  String USING = "USING";

  /** Keyword {@value}. */
  String UTC_DATE = "UTC_DATE";

  /** Keyword {@value}. */
  String UTC_TIME = "UTC_TIME";

  /** Keyword {@value}. */
  String UTC_TIMESTAMP = "UTC_TIMESTAMP";

  /** Keyword {@value}. */
  String UUID = "UUID";

  /** Keyword {@value}. */
  String VALIDATE = "VALIDATE";

  /** Keyword {@value}. */
  String VALIDPROC = "VALIDPROC";

  /** Keyword {@value}. */
  String VALUE = "VALUE";

  /** Keyword {@value}. */
  String VALUES = "VALUES";

  /** Keyword {@value}. */
  String VALUE_OF = "VALUE_OF";

  /** Keyword {@value}. */
  String VARBINARY = "VARBINARY";

  /** Keyword {@value}. */
  String VARBYTE = "VARBYTE";

  /** Keyword {@value}. */
  String VARCHAR = "VARCHAR";

  /** Keyword {@value}. */
  String VARCHAR2 = "VARCHAR2";

  /** Keyword {@value}. */
  String VARCHARACTER = "VARCHARACTER";

  /** Keyword {@value}. */
  String VARGRAPHIC = "VARGRAPHIC";

  /** Keyword {@value}. */
  String VARIABLE = "VARIABLE";

  /** Keyword {@value}. */
  String VARIADIC = "VARIADIC";

  /** Keyword {@value}. */
  String VARIANT = "VARIANT";

  /** Keyword {@value}. */
  String VARYING = "VARYING";

  /** Keyword {@value}. */
  String VAR_POP = "VAR_POP";

  /** Keyword {@value}. */
  String VAR_SAMP = "VAR_SAMP";

  /** Keyword {@value}. */
  String VCAT = "VCAT";

  /** Keyword {@value}. */
  String VERBOSE = "VERBOSE";

  /** Keyword {@value}. */
  String VERSIONING = "VERSIONING";

  /** Keyword {@value}. */
  String VIEW = "VIEW";

  /** Keyword {@value}. */
  String VIRTUAL = "VIRTUAL";

  /** Keyword {@value}. */
  String VOLATILE = "VOLATILE";

  /** Keyword {@value}. */
  String VOLUMES = "VOLUMES";

  /** Keyword {@value}. */
  String WAIT = "WAIT";

  /** Keyword {@value}. */
  String WAITFOR = "WAITFOR";

  /** Keyword {@value}. */
  String WHEN = "WHEN";

  /** Keyword {@value}. */
  String WHENEVER = "WHENEVER";

  /** Keyword {@value}. */
  String WHERE = "WHERE";

  /** Keyword {@value}. */
  String WHILE = "WHILE";

  /** Keyword {@value}. */
  String WIDTH_BUCKET = "WIDTH_BUCKET";

  /** Keyword {@value}. */
  String WINDOW = "WINDOW";

  /** Keyword {@value}. */
  String WITH = "WITH";

  /** Keyword {@value}. */
  String WITHIN = "WITHIN";

  /** Keyword {@value}. */
  String WITHIN_GROUP = "WITHIN_GROUP";

  /** Keyword {@value}. */
  String WITHOUT = "WITHOUT";

  /** Keyword {@value}. */
  String WLM = "WLM";

  /** Keyword {@value}. */
  String WORK = "WORK";

  /** Keyword {@value}. */
  String WRITE = "WRITE";

  /** Keyword {@value}. */
  String WRITETEXT = "WRITETEXT";

  /** Keyword {@value}. */
  String XML = "XML";

  /** Keyword {@value}. */
  String XMLCAST = "XMLCAST";

  /** Keyword {@value}. */
  String XMLEXISTS = "XMLEXISTS";

  /** Keyword {@value}. */
  String XMLNAMESPACES = "XMLNAMESPACES";

  /** Keyword {@value}. */
  String XOR = "XOR";

  /** Keyword {@value}. */
  String YEAR = "YEAR";

  /** Keyword {@value}. */
  String YEARS = "YEARS";

  /** Keyword {@value}. */
  String YEAR_MONTH = "YEAR_MONTH";

  /** Keyword {@value}. */
  String ZEROFILL = "ZEROFILL";

  /** Keyword {@value}. */
  String ZEROIFNULL = "ZEROIFNULL";

  /** Keyword {@value}. */
  String ZONE = "ZONE";

  /** Keyword {@value}. */
  String ALTER_TABLE = ALTER + " " + TABLE;

  /** Keyword {@value}. */
  String CREATE_INDEX = CREATE + " " + INDEX;

  /** Keyword {@value}. */
  String CREATE_OR_REPLACE = CREATE + " " + OR + " " + REPLACE;

  /** Keyword {@value}. */
  String CREATE_SEQUENCE = CREATE + " " + SEQUENCE;

  /** Keyword {@value}. */
  String CREATE_TABLE = CREATE + " " + TABLE;

  /** Keyword {@value}. */
  String DOUBLE_PRECISION = DOUBLE + " " + PRECISION;

  /** Keyword {@value}. */
  String FOREIGN_KEY = FOREIGN + " " + KEY;

  /** Keyword {@value}. */
  String FULL_OUTER_JOIN = FULL + " " + OUTER + " " + JOIN;

  /** Keyword {@value}. */
  String GROUP_BY = GROUP + " " + BY;

  /** Keyword {@value}. */
  String IF_NOT_EXISTS = IF + " " + NOT + " " + EXISTS;

  /** Keyword {@value}. */
  String INCREMENT_BY = INCREMENT + " " + BY;

  /** Keyword {@value}. */
  String INITIALLY_DEFERRED = INITIALLY + " " + DEFERRED;

  /** Keyword {@value}. */
  String INITIALLY_IMMEDIATE = INITIALLY + " " + IMMEDIATE;

  /** Keyword {@value}. */
  String INNER_JOIN = INNER + " " + JOIN;

  /** Keyword {@value}. */
  String INSERT_INTO = INSERT + " " + INTO;

  /** Keyword {@value}. */
  String IS_NOT_NULL = IS + " " + NOT + " " + NULL;

  /** Keyword {@value}. */
  String IS_NULL = IS + " " + NULL;

  /** Keyword {@value}. */
  String LEFT_JOIN = LEFT + " " + JOIN;

  /** Keyword {@value}. */
  String MATERIALIZED_VIEW = MATERIALIZED + " " + VIEW;

  /** Keyword {@value}. */
  String NOT_DEFERRABLE = NOT + " " + DEFERRABLE;

  /** Keyword {@value}. */
  String NOT_NULL = NOT + " " + NULL;

  /** Keyword {@value}. */
  String ON_CONFLICT = ON + " " + CONFLICT;

  /** Keyword {@value}. */
  String ON_CONFLICT_DO = ON + " " + CONFLICT + " " + DO;

  /** Keyword {@value}. */
  String ON_CONFLICT_DO_NOTHING = ON + " " + CONFLICT + " " + DO + " " + NOTHING;

  /** Keyword {@value}. */
  String ON_DELETE_CASCADE = ON + " " + DELETE + " " + CASCADE;

  /** Keyword {@value}. */
  String ON_DELETE_SET_NULL = ON + " " + DELETE + " " + SET + " " + NULL;

  /** Keyword {@value}. */
  String ORDER_BY = ORDER + " " + BY;

  /** Keyword {@value}. */
  String OUTER_JOIN = OUTER + " " + JOIN;

  /** Keyword {@value}. */
  String PRIMARY_KEY = PRIMARY + " " + KEY;

  /** Keyword {@value}. */
  String RIGHT_JOIN = RIGHT + " " + JOIN;

  /** Keyword {@value}. */
  String START_WITH = START + " " + WITH;

  /** Keyword {@value}. */
  String UNION_ALL = UNION + " " + ALL;

  /** Keyword {@value}. */
  String WITH_LOCAL_TIME_ZONE = WITH + " " + LOCAL + " " + TIME + " " + ZONE;

  /** Keyword {@value}. */
  String WITH_TIME_ZONE = WITH + " " + TIME + " " + ZONE;

  /** The {@link Set} of all keywords. */
  Set<String> ALL_KEYWORDS = Set.of(ABORT, ABORTSESSION, ABS, ABSENT, ABSOLUTE, ACCESS, ACCESS_LOCK, ACCESSIBLE,
      ACCOUNT, ACOS, ACOSH, ACTION, ADD, ADD_MONTHS, ADMIN, AFTER, AGGREGATE, ALIAS, ALL, ALLOCATE, ALLOW, ALTER,
      ALTERAND, AMP, ANALYSE, ANALYZE, AND, ANSIDATE, ANY, ANY_VALUE, ARE, ARRAY, ARRAY_AGG, ARRAY_EXISTS,
      ARRAY_MAX_CARDINALITY, AS, ASC, ASENSITIVE, ASIN, ASINH, ASSERTION, ASSOCIATE, ASUTIME, ASYMMETRIC, AT, ATAN,
      ATAN2, ATANH, ATOMIC, AUDIT, AUTHORIZATION, AUX, AUXILIARY, AVE, AVERAGE, AVG, BACKUP, BEFORE, BEGIN, BEGIN_FRAME,
      BEGIN_PARTITION, BETWEEN, BIGINT, BINARY, BIT, BLOB, BOOLEAN, BOTH, BREADTH, BREAK, BROWSE, BT, BTRIM, BUFFERPOOL,
      BULK, BUT, BY, BYTE, BYTEINT, BYTES, CALL, CALLED, CAPTURE, CARDINALITY, CASCADE, CASCADED, CASE, CASE_N,
      CASESPECIFIC, CAST, CATALOG, CCSID, CD, CEIL, CEILING, CHANGE, CHAR, CHAR2HEXINT, CHARACTER, CHARACTERS,
      CHARACTER_LENGTH, CHARS, CHAR_LENGTH, CHECK, CHECKPOINT, CLASS, CLASSIFIER, CLOB, CLONE, CLOSE, CLUSTER,
      CLUSTERED, CM, COALESCE, COLLATE, COLLATION, COLLECT, COLLECTION, COLLID, COLUMN, COLUMN_VALUE, COMMENT, COMMIT,
      COMPLETION, COMPRESS, COMPUTE, CONCAT, CONCURRENTLY, CONDITION, CONFLICT, CONNECT, CONNECTION, CONSTRAINT,
      CONSTRAINTS, CONSTRUCTOR, CONTAINS, CONTAINSTABLE, CONTENT, CONTINUE, CONVERT, CONVERT_TABLE_HEADER, COPY, CORR,
      CORRESPONDING, COS, COSH, COUNT, COVAR_POP, COVAR_SAMP, CREATE, CROSS, CS, CSUM, CT, CUBE, CUME_DIST, CURRENT,
      CURRENT_CATALOG, CURRENT_DATE, CURRENT_DEFAULT_TRANSFORM_GROUP, CURRENT_LC_CTYPE, CURRENT_PATH, CURRENT_ROLE,
      CURRENT_ROW, CURRENT_SCHEMA, CURRENT_SERVER, CURRENT_TIME, CURRENT_TIMESTAMP, CURRENT_TIMEZONE,
      CURRENT_TRANSFORM_GROUP_FOR_TYPE, CURRENT_USER, CURRVAL, CURSOR, CV, CYCLE, DATA, DATABASE, DATABASES,
      DATABLOCKSIZE, DATE, DATEFORM, DATEFROM, DAY, DAYS, DAY_HOUR, DAY_MICROSECOND, DAY_MINUTE, DAY_SECOND, DBCC,
      DBINFO, DEALLOCATE, DEC, DECFLOAT, DECIMAL, DECLARE, DEFAULT, DEFERRABLE, DEFERRED, DEFINE, DEGREES, DEL, DELAYED,
      DELETE, DENSE_RANK, DENY, DEPTH, DEREF, DESC, DESCRIBE, DESCRIPTOR, DESTROY, DESTRUCTOR, DETERMINISTIC,
      DIAGNOSTIC, DIAGNOSTICS, DICTIONARY, DISABLE, DISABLED, DISALLOW, DISCONNECT, DISK, DISTINCT, DISTINCTROW,
      DISTRIBUTED, DIV, DO, DOCUMENT, DOMAIN, DOUBLE, DROP, DSSIZE, DUAL, DUMP, DYNAMIC, EACH, ECHO, EDITPROC, ELEMENT,
      ELSE, ELSEIF, EMPTY, ENABLED, ENCLOSED, ENCODING, ENCRYPTION, END, ENDING, END_EXEC, END_FRAME, END_PARTITION, EQ,
      EQUALS, ERASE, ERRLVL, ERROR, ERRORFILES, ERRORTABLES, ESCAPE, ESCAPED, ET, EVERY, EXCEPT, EXCEPTION, EXCLUSIVE,
      EXEC, EXECUTE, EXISTS, EXIT, EXP, EXPLAIN, EXTERNAL, EXTRACT, FALLBACK, FALSE, FASTEXPORT, FENCED, FETCH,
      FIELDPROC, FILE, FILLFACTOR, FILTER, FINAL, FIRST, FIRST_VALUE, FLOAT, FLOAT4, FLOAT8, FLOOR, FOR, FORCE, FOREIGN,
      FORMAT, FOUND, FRAME_ROW, FREE, FREESPACE, FREETEXT, FREETEXTTABLE, FREEZE, FROM, FULL, FULLTEXT, FUNCTION,
      FUSION, GE, GENERAL, GENERATED, GET, GIVE, GLOBAL, GO, GOTO, GRANT, GRAPHIC, GREATEST, GROUP, GROUPING, GROUPS,
      GT, HANDLER, HASH, HASHAMP, HASHBAKAMP, HASHBUCKET, HASHROW, HAVING, HELP, HIGH_PRIORITY, HOLD, HOLDLOCK, HOST,
      HOUR, HOURS, HOUR_MICROSECOND, HOUR_MINUTE, HOUR_SECOND, IDENTIFIED, IDENTITY, IDENTITYCOL, IDENTITY_INSERT, IF,
      IGNORE, ILIKE, IMMEDIATE, IN, INCLUSIVE, INCONSISTENT, INCREMENT, INDEX, INDICATOR, INFILE, INHERIT, INITIAL,
      INITIALIZE, INITIALLY, INITIATE, INNER, INOUT, INPUT, INS, INSENSITIVE, INSERT, INSTEAD, INT, INT1, INT2, INT3,
      INT4, INT8, INTEGER, INTEGERDATE, INTERSECT, INTERSECTION, INTERVAL, INTO, IO_AFTER_GTIDS, IO_BEFORE_GTIDS, IS,
      ISNULL, ISOBID, ISOLATION, ITERATE, JAR, JOIN, JOURNAL, JSON, JSON_ARRAY, JSON_ARRAYAGG, JSON_EXISTS, JSON_OBJECT,
      JSON_OBJECTAGG, JSON_QUERY, JSON_SCALAR, JSON_SERIALIZE, JSON_TABLE, JSON_TABLE_PRIMITIVE, JSON_VALUE, KEEP, KEY,
      KEYS, KILL, KURTOSIS, LABEL, LAG, LANGUAGE, LARGE, LAST, LAST_VALUE, LATERAL, LC_CTYPE, LE, LEAD, LEADING, LEAST,
      LEAVE, LEFT, LESS, LEVEL, LIKE, LIKE_REGEX, LIMIT, LINEAR, LINENO, LINES, LISTAGG, LN, LOAD, LOADING, LOCAL,
      LOCALE, LOCALTIME, LOCALTIMESTAMP, LOCATOR, LOCATORS, LOCK, LOCKING, LOCKMAX, LOCKSIZE, LOG, LOG10, LOGGING,
      LOGON, LONG, LONGBLOB, LONGTEXT, LOOP, LOWER, LOW_PRIORITY, LPAD, LT, LTRIM, MACRO, MAINTAINED, MAP, MASTER_BIND,
      MASTER_SSL_VERIFY_SERVER_CERT, MATCH, MATCHES, MATCH_NUMBER, MATCH_RECOGNIZE, MATERIALIZED, MAVG, MAX, MAXEXTENTS,
      MAXIMUM, MAXVALUE, MCHARACTERS, MDIFF, MEDIUMBLOB, MEDIUMINT, MEDIUMTEXT, MEMBER, MERGE, METHOD, MICROSECOND,
      MICROSECONDS, MIDDLEINT, MIN, MINDEX, MINIMUM, MINUS, MINUTE, MINUTES, MINUTE_MICROSECOND, MINUTE_SECOND,
      MINVALUE, MLINREG, MLOAD, MLSLABEL, MOD, MODE, MODIFIES, MODIFY, MODULE, MONITOR, MONRESOURCE, MONSESSION, MONTH,
      MONTHS, MSUBSTR, MSUM, MULTISET, NAMED, NAMES, NATIONAL, NATURAL, NCHAR, NCLOB, NE, NESTED_TABLE_ID, NEW,
      NEW_TABLE, NEXT, NEXTVAL, NO, NOAUDIT, NOCHECK, NOCOMPRESS, NONCLUSTERED, NONE, NORELY, NORMALIZE, NOT, NOTHING,
      NOTNULL, NOWAIT, NO_WRITE_TO_BINLOG, NTH_VALUE, NTILE, NULL, NULLABLE, NULLIF, NULLIFZERO, NULLS, NUMBER, NUMERIC,
      NUMPARTS, OBID, OBJECT, OBJECTS, OCCURRENCES_REGEX, OCTET_LENGTH, OF, OFF, OFFLINE, OFFSET, OFFSETS, OLD,
      OLD_TABLE, OMIT, ON, ONE, ONLINE, ONLY, OPEN, OPENDATASOURCE, OPENQUERY, OPENROWSET, OPENXML, OPERATION,
      OPTIMIZATION, OPTIMIZE, OPTIMIZER_COSTS, OPTION, OPTIONALLY, OR, ORDER, ORDINALITY, ORGANIZATION, OUT, OUTER,
      OUTFILE, OUTPUT, OVER, OVERLAPS, OVERLAY, OVERRIDE, PACKAGE, PAD, PADDED, PARAMETER, PARAMETERS, PART, PARTIAL,
      PARTITION, PARTITIONED, PARTITIONING, PASSWORD, PATH, PATTERN, PCTFREE, PER, PERCENT, PERCENTILE_CONT,
      PERCENTILE_DISC, PERCENT_RANK, PERIOD, PERM, PERMANENT, PIECESIZE, PIVOT, PLACING, PLAN, PORTION, POSITION,
      POSITION_REGEX, POSTFIX, POWER, PRECEDES, PRECISION, PREFIX, PREORDER, PREPARE, PRESERVE, PREVVAL, PRIMARY, PRINT,
      PRIOR, PRIQTY, PRIVATE, PRIVILEGES, PROC, PROCEDURE, PROFILE, PROGRAM, PROPORTIONAL, PROTECTION, PSID, PTF,
      PUBLIC, PURGE, QUALIFIED, QUALIFY, QUANTILE, QUERY, QUERYNO, RADIANS, RAISERROR, RANDOM, RANGE, RANGE_N, RANK,
      RAW, READ, READS, READTEXT, READ_WRITE, REAL, RECONFIGURE, RECURSIVE, REF, REFERENCES, REFERENCING, REFRESH,
      REGEXP, REGR_AVGX, REGR_AVGY, REGR_COUNT, REGR_INTERCEPT, REGR_R2, REGR_SLOPE, REGR_SXX, REGR_SXY, REGR_SYY,
      RELATIVE, RELEASE, RELY, RENAME, REPEAT, REPLACE, REPLICATION, REPOVERRIDE, REQUEST, REQUIRE, RESIGNAL, RESOURCE,
      RESTART, RESTORE, RESTRICT, RESULT, RESULT_SET_LOCATOR, RESUME, RET, RETRIEVE, RETURN, RETURNING, RETURNS,
      REVALIDATE, REVERT, REVOKE, RIGHT, RIGHTS, RLIKE, ROLE, ROLLBACK, ROLLFORWARD, ROLLUP, ROUND_CEILING, ROUND_DOWN,
      ROUND_FLOOR, ROUND_HALF_DOWN, ROUND_HALF_EVEN, ROUND_HALF_UP, ROUND_UP, ROUTINE, ROW, ROWCOUNT, ROWGUIDCOL, ROWID,
      ROWNUM, ROWS, ROWSET, ROW_NUMBER, RPAD, RTRIM, RULE, RUN, RUNNING, SAMPLE, SAMPLEID, SAVE, SAVEPOINT, SCHEMA,
      SCHEMAS, SCOPE, SCRATCHPAD, SCROLL, SEARCH, SECOND, SECONDS, SECOND_MICROSECOND, SECQTY, SECTION, SECURITY,
      SECURITYAUDIT, SEEK, SEL, SELECT, SEMANTICKEYPHRASETABLE, SEMANTICSIMILARITYDETAILSTABLE, SEMANTICSIMILARITYTABLE,
      SENSITIVE, SEPARATOR, SEQUENCE, SESSION, SESSION_USER, SET, SETRESRATE, SETS, SETSESSRATE, SETUSER, SHARE, SHOW,
      SHUTDOWN, SIGNAL, SIMILAR, SIMPLE, SIN, SINH, SIZE, SKEW, SKIP, SMALLINT, SOME, SOUNDEX, SOURCE, SPACE, SPATIAL,
      SPECIFIC, SPECIFICTYPE, SPOOL, SQL, SQLEXCEPTION, SQLSTATE, SQLTEXT, SQLWARNING, SQL_BIG_RESULT,
      SQL_CALC_FOUND_ROWS, SQL_SMALL_RESULT, SQRT, SS, SSL, STANDARD, START, STARTING, STARTUP, STATE, STATEMENT,
      STATIC, STATISTICS, STAY, STDDEV_POP, STDDEV_SAMP, STEPINFO, STOGROUP, STORED, STORES, STRAIGHT_JOIN, STRING_CS,
      STRUCTURE, STYLE, SUBMULTISET, SUBSCRIBER, SUBSET, SUBSTR, SUBSTRING, SUBSTRING_REGEX, SUCCEEDS, SUCCESSFUL, SUM,
      SUMMARY, SUSPEND, SYMMETRIC, SYNONYM, SYSDATE, SYSTEM, SYSTEM_TIME, SYSTEM_USER, SYSTIMESTAMP, TABLE, TABLESAMPLE,
      TABLESPACE, TAN, TANH, TBL_CS, TEMPORARY, TERMINATE, TERMINATED, TEXT, TEXTSIZE, THAN, THEN, THRESHOLD, TIME,
      TIMESTAMP, TIMEZONE_HOUR, TIMEZONE_MINUTE, TINYBLOB, TINYINT, TINYTEXT, TITLE, TO, TOP, TRACE, TRAILING, TRAN,
      TRANSACTION, TRANSLATE, TRANSLATE_CHK, TRANSLATE_REGEX, TRANSLATION, TREAT, TRIGGER, TRIM, TRIM_ARRAY, TRUE,
      TRUNCATE, TRY_CONVERT, TSEQUAL, TYPE, UC, UESCAPE, UID, UNDEFINED, UNDER, UNDO, UNION, UNIQUE, UNKNOWN, UNLOCK,
      UNNEST, UNPIVOT, UNSIGNED, UNTIL, UPD, UPDATE, UPDATETEXT, UPPER, UPPERCASE, UPSERT, USAGE, USE, USER, USING,
      UTC_DATE, UTC_TIME, UTC_TIMESTAMP, UUID, VALIDATE, VALIDPROC, VALUE, VALUES, VALUE_OF, VARBINARY, VARBYTE,
      VARCHAR, VARCHAR2, VARCHARACTER, VARGRAPHIC, VARIABLE, VARIADIC, VARIANT, VARYING, VAR_POP, VAR_SAMP, VCAT,
      VERBOSE, VERSIONING, VIEW, VIRTUAL, VOLATILE, VOLUMES, WAIT, WAITFOR, WHEN, WHENEVER, WHERE, WHILE, WIDTH_BUCKET,
      WINDOW, WITH, WITHIN, WITHIN_GROUP, WITHOUT, WLM, WORK, WRITE, WRITETEXT, XML, XMLCAST, XMLEXISTS, XMLNAMESPACES,
      XOR, YEAR, YEARS, YEAR_MONTH, ZEROFILL, ZEROIFNULL, ZONE);

}
