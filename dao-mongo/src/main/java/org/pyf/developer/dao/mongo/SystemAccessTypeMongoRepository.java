package org.pyf.developer.dao.mongo;

import org.pyf.developer.bean.one.model.auth.SystemAccessType;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * ${DESCRIPTION}
 * Created by hanqf on 2018/10/17 12:48.
 */


public interface SystemAccessTypeMongoRepository extends MongoRepository<SystemAccessType, Long> {
}
