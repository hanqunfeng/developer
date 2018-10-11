package org.pyf.developer.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.pyf.developer.web.service.cache.RedisCacheService;
import org.pyf.developer.web.utils.RedisUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * ${DESCRIPTION}
 * Created by hanqf on 2018/10/11 17:36.
 */

@ConditionalOnProperty(prefix="redis.cache.log",name = "enabled",havingValue = "true")
@Configuration
@EnableCaching
@Slf4j
public class RedisConfig {
    //如果注解式缓存要使用redis，则开启这个bean即可，同时注释掉上面的bean
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory){
        log.info("RedisCacheManager");
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofHours(1));//失效时间一小时
        return RedisCacheManager
                .builder(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
                .cacheDefaults(redisCacheConfiguration).build();
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory)
    {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(redisConnectionFactory);
        //设置序列化方式，默认使用JdkSerializationRedisSerializer序列化，以下使用json序列化
        //这里说明一下，如果使用json方式进行存储，会导致对象中的集合子对象转化出现问题，比如嵌套对象时
        template.setKeySerializer(new StringRedisSerializer());
        //template.setValueSerializer(jackson2JsonRedisSerializer);
        //template.setHashKeySerializer(jackson2JsonRedisSerializer);
        //template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

    /**
     * 业务逻辑中要使用redis时可以使用该工具类
     * @return
     */
    @Bean
    public RedisUtils redisUtils(){
        return new RedisUtils();
    }

    @Bean
    public RedisCacheService redisCacheService(){
        return new RedisCacheService();
    }
}
