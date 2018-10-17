package org.pyf.developer.dao.mongo.base;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * ${DESCRIPTION}
 * Created by hanqf on 2018/10/17 17:40.
 */

@NoRepositoryBean
public interface BaseJpaMongoRepository<T,ID extends Serializable> extends MongoRepository<T, ID>, QuerydslPredicateExecutor<T>, Serializable{
}
