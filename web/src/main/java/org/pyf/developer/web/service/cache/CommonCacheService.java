package org.pyf.developer.web.service.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentMap;

/**
 * ${DESCRIPTION}
 * Created by hanqf on 2018/10/11 17:47.
 */

public class CommonCacheService implements ICacheService {
    @Autowired
    CacheManager cacheManager;

    @Override
    public Cache getCache(String name){
        return cacheManager.getCache(name);
    }

    /**
     * 描述 : <获得缓存名称集合>. <br>
     * <p>
     * <使用方法说明>
     * </p>
     *
     * @return
     */
    @Override
    public Iterator<String> getCacheNames() {
        Collection<String> cacheNames = cacheManager.getCacheNames();
        Iterator<String> iterator = cacheNames.iterator();
        return iterator;
    }
    @Override
    public ConcurrentMap<Object, Object> getNativeCache(String name){
        ConcurrentMapCache cache = (ConcurrentMapCache)getCache(name);
        return cache.getNativeCache();
    }

    @Override
    public boolean deleteByKey(String name,String key) {
        ConcurrentMapCache cache = (ConcurrentMapCache)getCache(name);
        cache.evict(key);
        return true;
    }
}
