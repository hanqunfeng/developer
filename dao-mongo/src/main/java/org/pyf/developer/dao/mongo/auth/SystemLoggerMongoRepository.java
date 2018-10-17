package org.pyf.developer.dao.mongo.auth;

import org.pyf.developer.bean.one.model.auth.SystemLogger;
import org.pyf.developer.dao.mongo.base.BaseJpaMongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * ${DESCRIPTION}
 * Created by hanqf on 2018/10/17 12:54.
 */

@RepositoryRestResource(collectionResourceRel = "SystemLogger", path = "logger")
public interface SystemLoggerMongoRepository extends BaseJpaMongoRepository<SystemLogger, Long> {
}
