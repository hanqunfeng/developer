package org.pyf.developer.web.service.cache;

import org.springframework.cache.Cache;

import java.util.Iterator;
import java.util.concurrent.ConcurrentMap;

/**
 * ${DESCRIPTION}
 * Created by hanqf on 2018/10/11 18:05.
 */


public interface ICacheService {
    public Cache getCache(String name);
    public Iterator<String> getCacheNames();
    public ConcurrentMap<Object, Object> getNativeCache(String name);
    public boolean deleteByKey(String name,String key);
}
