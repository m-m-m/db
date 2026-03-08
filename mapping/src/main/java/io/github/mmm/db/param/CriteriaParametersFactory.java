package io.github.mmm.db.param;

import io.github.mmm.entity.bean.typemapping.TypeMapping;
import io.github.mmm.property.criteria.CriteriaParameters;

/**
 * Interface for a factory to {@link #create(TypeMapping) create} instances of {@link CriteriaParameters}.
 */
public interface CriteriaParametersFactory {

  /**
   * @param typeMapping the {@link TypeMapping}.
   * @return a new instance of {@link CriteriaParameters}.
   */
  CriteriaParameters<?> create(TypeMapping typeMapping);

}
