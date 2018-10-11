package org.pyf.developer.web.service.cache;

import org.pyf.developer.web.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * ${DESCRIPTION}
 * Created by hanqf on 2018/10/11 18:06.
 */


public class RedisCacheService implements ICacheService {

    @Autowired
    CacheManager cacheManager;

    @Autowired
    RedisUtils redisUtils;


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
        ConcurrentMap<Object, Object> map = new ConcurrentHashMap<>();

        Set<String> keys = redisUtils.getAllKeyByPattern(name);
        for(String key:keys){
            Object object = redisUtils.getObject(key);
            map.put(key,object);
        }
        return map;
    }

    @Override
    public boolean deleteByKey(String name,String key) {
        return redisUtils.removeObject(key);
    }
}
