/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.statement;

import java.util.ArrayList;
import java.util.List;

import io.github.mmm.entity.bean.EntityBean;
import io.github.mmm.property.criteria.PropertyAssignment;

/// [DbClause] containing [assignments][#getAssignments()] like a
/// [io.github.mmm.db.statement.insert.InsertValuesClause]-clause.
///
/// @param <E> type of the [EntityBean] to query.
/// @param <SELF> type of this class itself.
/// @since 1.0.0
public abstract class AssignmentClause<E extends EntityBean, SELF extends AssignmentClause<E, SELF>>
    extends AbstractTypedClause<E, SELF> {

  /// Name of the property [#getAssignments()] for marshaling.
  public static final String NAME_ASSIGNMENTS = "set";

  /// The [List] of [PropertyAssignment]s.
  protected final List<PropertyAssignment<?>> assignments;

  /// The constructor.
  public AssignmentClause() {

    super();
    this.assignments = new ArrayList<>();
  }

  /// @return the [EntityBean].
  protected abstract E getEntity();

  /// @return the [List] of [PropertyAssignment]s.
  public List<PropertyAssignment<?>> getAssignments() {

    return this.assignments;
  }

}
