package org.pyf.developer.dao.mongo;

import org.pyf.developer.bean.one.model.auth.SystemUser;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * ${DESCRIPTION}
 * Created by hanqf on 2018/10/17 12:20.
 */


public interface SystemUserMongoRepository extends MongoRepository<SystemUser, String> {


}
