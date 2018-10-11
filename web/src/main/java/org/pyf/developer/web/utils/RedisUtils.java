package org.pyf.developer.web.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Set;

/**
 * ${DESCRIPTION}
 * Created by hanqf on 2018/10/11 17:09.
 */


public class RedisUtils {

    @Autowired
    RedisTemplate redisTemplate;

    public void setObject(String key,Object object){
        redisTemplate.opsForValue().set(key,object);
    }

    public Object getObject(String key){
        return redisTemplate.opsForValue().get(key);
    }

    public Set<String>  getAllKeyByPattern(String pattern ){
        return redisTemplate.keys(pattern+"*");
    }

    public boolean removeObject(String key){
        return redisTemplate.delete(key);
    }
}
