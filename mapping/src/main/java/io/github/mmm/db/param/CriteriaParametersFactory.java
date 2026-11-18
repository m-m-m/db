package io.github.mmm.db.param;

import io.github.mmm.entity.bean.typemapping.TypeMapping;
import io.github.mmm.property.criteria.CriteriaParameters;

/// Interface for a factory to [create][#create(TypeMapping)] instances of [CriteriaParameters].
public interface CriteriaParametersFactory {

  /// @param typeMapping the [TypeMapping].
  /// @return a new instance of [CriteriaParameters].
  CriteriaParameters<?> create(TypeMapping typeMapping);

}
