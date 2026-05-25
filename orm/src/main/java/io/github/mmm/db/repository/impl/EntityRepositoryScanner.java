/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.db.repository.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.mmm.base.resource.ModuleAccess;
import io.github.mmm.base.resource.ResourceMap;
import io.github.mmm.base.resource.ResourceType;
import io.github.mmm.base.resource.ResourceTypeScannerService;
import io.github.mmm.base.type.JavaType;
import io.github.mmm.db.repository.EntityRepository;

/**
 * Implementation of {@link ResourceTypeScannerService} to find all {@link EntityRepository}.
 */
public class EntityRepositoryScanner implements ResourceTypeScannerService {

  private static final Logger LOG = LoggerFactory.getLogger(EntityRepositoryScanner.class);

  @Override
  public boolean includeModule(ModuleAccess module) {

    if (module.isInternalModule()) {
      return false;
    }
    String name = module.getResolved().name();
    if (name.startsWith("ch.qos") || name.startsWith("org.slf4j")) {
      return false;
    }
    return true;
  }

  @Override
  public boolean scanType(ResourceType type, ResourceMap resources) {

    if (type.isInnerType() || type.isPackageInfo() || type.isModuleInfo() || !type.getParent().isOpen()) {
      return false; // fast exclusion
    }
    String name = type.getName();
    if (!name.endsWith("Repository") || name.equals(EntityRepository.class.getName())) {
      return false;
    }
    LOG.trace("Checking if type {} is EntityRepository...", type);
    JavaType javaType = type.loadType();
    if (javaType.isBroken()) {
      return false;
    }
    if (!javaType.getKind().isInterface()) {
      return false;
    }
    Class<?> javaClass = type.loadClass();
    if (!EntityRepository.class.isAssignableFrom(javaClass)) {
      return false;
    }
    return true;
  }

}
