package org.pyf.developer.dao.jpa.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * ${DESCRIPTION}
 * Created by hanqf on 2018/8/29 12:10.
 */


public class BaseRepositoryFactoryBean<T extends JpaRepository<S, ID>, S, ID extends Serializable> extends JpaRepositoryFactoryBean<T, S, ID> {

    public BaseRepositoryFactoryBean(Class<? extends T> repositoryInterface) {
        super(repositoryInterface);
    }

    @Override
    protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
        return new BaseRepositoryFactory(entityManager);
    }
}

class BaseRepositoryFactory extends JpaRepositoryFactory {
    public BaseRepositoryFactory(EntityManager entityManager) {
        super(entityManager);
    }


    //指定实现类

    @Override
    protected SimpleJpaRepository<?, ?> getTargetRepository(RepositoryInformation information, EntityManager entityManager) {
        //BaseJpaRepositoryImpl customRepository = new BaseJpaRepositoryImpl((Class) information.getDomainType(), entityManager);
        BaseJpaRepositoryImpl customRepository = new BaseJpaRepositoryImpl(JpaEntityInformationSupport.getEntityInformation((Class) information.getDomainType(), entityManager), entityManager);
        return customRepository;
    }

    //指定实现类类型
    @Override
    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
        return BaseJpaRepositoryImpl.class;
    }
}
