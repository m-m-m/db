package io.github.mmm.db.criteria;

import java.util.Collection;
import java.util.Objects;

import io.github.mmm.db.name.DbKeyword;
import io.github.mmm.db.name.DbQualifiedName;
import io.github.mmm.value.CriteriaObject;
import io.github.mmm.value.PropertyPath;

/**
 * {@link CriteriaObject} representing the next value of a database sequence.
 */
public final class SequenceNextValue implements CriteriaObject<Long> {

  private final DbQualifiedName sequenceName;

  /**
   * The constructor.
   *
   * @param sequenceName the {@link DbQualifiedName} of the database sequence.
   */
  public SequenceNextValue(DbQualifiedName sequenceName) {

    super();
    Objects.requireNonNull(sequenceName);
    this.sequenceName = sequenceName;
  }

  /**
   * @return sequenceName
   */
  public DbQualifiedName getSequenceName() {

    return this.sequenceName;
  }

  @Override
  public void collectProperties(Collection<PropertyPath<?>> properties) {

    // no properties
  }

  @Override
  public String toString() {

    return DbKeyword.NEXTVAL + "(" + this.sequenceName + ")";
  }

}
