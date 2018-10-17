package org.pyf.developer.dao.mongo;

import org.pyf.developer.bean.one.model.auth.SystemLogger;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * ${DESCRIPTION}
 * Created by hanqf on 2018/10/17 12:54.
 */

@RepositoryRestResource(collectionResourceRel = "SystemLogger", path = "logger")
public interface SystemLoggerMongoRepository extends MongoRepository<SystemLogger, Long> {
}
