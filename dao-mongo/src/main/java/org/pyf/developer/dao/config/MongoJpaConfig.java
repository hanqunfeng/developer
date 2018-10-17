package org.pyf.developer.dao.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * ${DESCRIPTION}
 * Created by hanqf on 2018/10/17 18:39.
 */

@Configuration
//@EnableMongoRepositories可以不加的，因为默认就会扫描启动类所在路径及其子路径，纯属为了学习
@EnableMongoRepositories(basePackages = "org.pyf.developer.dao.mongo")
public class MongoJpaConfig {
}
