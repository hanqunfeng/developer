package org.pyf.developer.dao.jpa.base;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

/**
 * ${DESCRIPTION}
 * Created by hanqf on 2018/8/28 18:03.
 */

@NoRepositoryBean //接口不参与jpa的代理
public interface BaseJpaRepository<T,ID extends Serializable> extends JpaRepository<T,ID>, JpaSpecificationExecutor<T>,QuerydslPredicateExecutor<T>, Serializable {

    Page<T> findByAuto(T example, Pageable pageable);

    List<T> findByAuto(T example);

    public List<T> findByAuto(Specification<T> specification);

    List<T> findAll(List<BooleanExpression> list);
    Page<T> findAll(List<BooleanExpression> list, Pageable pageable);

    List<?> findByHql(String hql);

    T findByIdNew(ID id);

    public void lazyInitialize(Class<T> entityClazz,List<T> l, String[] fields);

    public void lazyInitialize(T obj, String[] fields);
}
