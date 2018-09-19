package org.pyf.developer.service;

import java.util.List;

/**
 * ${DESCRIPTION}
 * Created by hanqf on 2018/9/13 11:09.
 */


public interface IDaoService {

    List<?> getListByHql(String hql);

    Object merge(Object object);
}
