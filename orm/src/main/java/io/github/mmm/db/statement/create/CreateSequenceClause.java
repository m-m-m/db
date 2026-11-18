/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.statement.create;

import io.github.mmm.db.name.DbQualifiedName;
import io.github.mmm.db.statement.AbstractDbClause;
import io.github.mmm.db.statement.DbMainClause;
import io.github.mmm.db.statement.DbStartClause;

/// A [CreateSequenceClause] of a [CreateSequenceStatement].
///
/// @since 1.0.0
public class CreateSequenceClause extends AbstractDbClause implements DbStartClause, DbMainClause<Void> {

  private final DbQualifiedName name;

  private final CreateSequenceStatement statement;

  private Long startWith;

  private Integer incrementBy;

  private Long minValue;

  private Long maxValue;

  private Boolean cycle;

  /// The constructor.
  ///
  /// @param sequenceName the [#getName()].
  public CreateSequenceClause(String sequenceName) {

    this(new DbQualifiedName(sequenceName, null, null));
  }

  /// The constructor.
  ///
  /// @param sequenceName the [#getName()].
  public CreateSequenceClause(DbQualifiedName sequenceName) {

    super();
    this.name = sequenceName;
    this.statement = new CreateSequenceStatement(this);
  }

  /// @return the [DbQualifiedName] of the sequence to create.
  public DbQualifiedName getName() {

    return this.name;
  }

  /// @param start the [start with][#getStartWith()] value.
  /// @return the [CreateSequenceClause] for fluent API calls.
  public CreateSequenceClause startWith(long start) {

    assert (this.startWith == null);
    this.startWith = Long.valueOf(start);
    return this;
  }

  /// @return the [start with][#startWith(long)] value of the sequence or `null` if undefined.
  public Long getStartWith() {

    return this.startWith;
  }

  /// @param increment the [increment by][#getIncrementBy()] value.
  /// @return the [CreateSequenceClause] for fluent API calls.
  public CreateSequenceClause incrementBy(int increment) {

    assert (this.incrementBy == null);
    this.incrementBy = Integer.valueOf(increment);
    return this;
  }

  /// @return the [increment by][#incrementBy(int)] value of the sequence or `null` if undefined.
  public Integer getIncrementBy() {

    return this.incrementBy;
  }

  /// @param min the [minimum value][#getMinValue()].
  /// @return the [CreateSequenceClause] for fluent API calls.
  public CreateSequenceClause minValue(long min) {

    assert (this.minValue == null);
    this.minValue = Long.valueOf(min);
    return this;
  }

  /// @return the [minimum value][#minValue(long)] of the sequence or `null` if undefined (NOMINVALUE).
  public Long getMinValue() {

    return this.minValue;
  }

  /// @param max the [maximum value][#getMaxValue()].
  /// @return the [CreateSequenceClause] for fluent API calls.
  public CreateSequenceClause maxValue(long max) {

    assert (this.maxValue == null);
    this.maxValue = Long.valueOf(max);
    return this;
  }

  /// @return the [maximum value][#maxValue(long)] of the sequence or `null` if undefined (NOMAXVALUE).
  public Long getMaxValue() {

    return this.maxValue;
  }

  /// Sets [cycle][#getCycle()] to [Boolean#TRUE].
  ///
  /// @return the [CreateSequenceClause] for fluent API calls.
  public CreateSequenceClause cycle() {

    assert (this.cycle == null);
    this.cycle = Boolean.TRUE;
    return this;
  }

  /// Sets [cycle][#getCycle()] to [Boolean#FALSE].
  ///
  /// @return the [CreateSequenceClause] for fluent API calls.
  public CreateSequenceClause nocycle() {

    assert (this.cycle == null);
    this.cycle = Boolean.FALSE;
    return this;
  }

  /// @return a [Boolean#TRUE] for [#cycle()], [Boolean#FALSE] for [#nocycle()] and `null` if undefined.
  public Boolean getCycle() {

    return this.cycle;
  }

  @Override
  public CreateSequenceStatement get() {

    return this.statement;
  }

}
