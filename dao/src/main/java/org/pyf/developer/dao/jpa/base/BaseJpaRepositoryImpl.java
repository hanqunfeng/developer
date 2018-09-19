package org.pyf.developer.dao.jpa.base;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.pyf.developer.bean.one.model.auth.SystemUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.QuerydslJpaRepository;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * ${DESCRIPTION}
 * Created by hanqf on 2018/8/29 11:06.
 */


public class BaseJpaRepositoryImpl<T, ID extends Serializable> extends QuerydslJpaRepository<T, ID> implements BaseJpaRepository<T, ID> {

    //通过构造方法初始化EntityManager
    private final EntityManager entityManager;


    public BaseJpaRepositoryImpl(JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    //具体方法实现，这里使用了一个自定义工具类BaseSpecs
    @Override
    public Page<T> findByAuto(T example, Pageable pageable) {
        return findAll(BaseSpecs.byAuto(entityManager, example), pageable);
    }

    @Override
    public List<T> findByAuto(T example) {
        return findAll(BaseSpecs.byAuto(entityManager, example));
    }

    @Override
    public List<T> findByAuto(Specification<T> specification) {

        return findAll(specification);

    }

    @Override
    public List<T> findAll(List<BooleanExpression> list) {

        BooleanExpression booleanExpression = list.get(0);
        for(int i = 1;i<list.size();i++){
            booleanExpression = booleanExpression.and(list.get(i));
        }

        return this.findAll(booleanExpression);
    }

    @Override
    public Page<T> findAll(List<BooleanExpression> list, Pageable pageable) {
        Page<T> resultPage = null;
        if(list.isEmpty()){
            resultPage = this.findAll(pageable);
        }else {
            BooleanExpression booleanExpression = list.get(0);
            for(int i = 1;i<list.size();i++){
                booleanExpression = booleanExpression.and(list.get(i));
            }
            resultPage = this.findAll(booleanExpression,pageable);
        }

        return resultPage;
    }

    @Override
    public List<?> findByHql(String hql) {
        return (List<?>)entityManager.createQuery(hql).getResultList();
    }

    @Override
    public T findByIdNew(ID id) {
        T t = null;
        Optional<T> optional = this.findById(id);
        if(optional.isPresent())
            t = optional.get();

        return t;

    }
}