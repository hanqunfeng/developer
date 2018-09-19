package org.pyf.developer.service;

import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * ${DESCRIPTION}
 * Created by hanqf on 2018/9/13 11:10.
 */

@Service
public class DaoServiceImpl implements IDaoService {

    @PersistenceContext
    //此处使用@Autowired也可以的，不过这里推荐使用@PersistenceContext，因为其提供了相关配置参数，可以控制初始化的过程，比如@PersistenceContext(unitName = "entityManagerFactory")
    private  EntityManager entityManager;


    @Override
    public List<?> getListByHql(String hql) {
        return entityManager.createQuery(hql).getResultList();
    }

    @Override
    public Object merge(Object object) {
        return entityManager.merge(object);
    }
}
