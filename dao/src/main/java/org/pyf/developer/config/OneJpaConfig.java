package org.pyf.developer.config;


import org.pyf.developer.dao.jpa.base.BaseRepositoryFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Map;

/**
 * ${DESCRIPTION}
 * Created by hanqf on 2018/8/29 11:29.
 */
@Configuration
@EnableJpaRepositories(basePackages = "org.pyf.developer.dao.jpa.one",repositoryFactoryBeanClass= BaseRepositoryFactoryBean.class,entityManagerFactoryRef = "entityManagerFactoryOne",transactionManagerRef = "transactionManagerOne")
public class OneJpaConfig {

    //注入书籍数据源
    @Autowired
    @Qualifier("oneDataSource")
    private DataSource oneDataSource;

    //配置EntityManager实体
    @Primary
    @Bean(name = "entityManagerOne")
    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
        return entityManagerFactoryOne(builder).getObject().createEntityManager();
    }

    //配置EntityManager工厂实体
    @Primary
    @Bean(name = "entityManagerFactoryOne")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryOne (EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(oneDataSource)
                .properties(getVendorProperties())
                .packages(new String[]{ "org.pyf.developer.bean.one.model" }) //设置应用creditDataSource的基础包名
                .persistenceUnit("onePersistenceUnit")
                .build();
    }

    //注入jpa配置实体
    @Autowired
    private JpaProperties jpaProperties;

    //获取jpa配置信息
    private Map<String, Object> getVendorProperties() {
        HibernateSettings hibernateSettings = new HibernateSettings();
        return jpaProperties.getHibernateProperties(hibernateSettings);
    }

    //配置事务
    @Primary
    @Bean(name = "transactionManagerOne")
    public PlatformTransactionManager transactionManagerOne(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactoryOne(builder).getObject());
    }

}
