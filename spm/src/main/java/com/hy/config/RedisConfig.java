package com.hy.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.time.Duration;

@Configuration
@EnableCaching
public class RedisConfig {
    //key的生成，springcache的内容，跟具体实现缓存器无关
    @Bean
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {

                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getSimpleName());
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };
    }
    //由于classloader不通的原因，在使用devtools 热启动的时候会导致缓存的类型转换出现异常
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory){
        return RedisCacheManager.builder(redisConnectionFactory).cacheDefaults(
                    RedisCacheConfiguration.defaultCacheConfig()
                    .entryTtl(Duration.ofSeconds(20)))
                .transactionAware().build();

    }
    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        /**
         * 配置自己的redisTemplate
         * StringRedisTemplate 默认使用使用StringRedisSerializer来序列化
         * RedisTemplate 默认使用JdkSerializationRedisSerializer来序列化
         */
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        //开启默认类型
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"));
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
