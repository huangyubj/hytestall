package com.hy.current;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * redis 分布式锁
 */
@Component
public class RedisLock {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    RedisScript<Boolean> lockScript;

    @Autowired
    RedisScript<Long> unlockScript;

    public boolean lock(String key, String uuid, String sencond){
        boolean locked = false;
        try{
            String millSeconds = String.valueOf(Integer.parseInt(sencond) * 1000);
            locked =redisTemplate.execute(lockScript, Collections.singletonList(key), uuid, millSeconds);
        }catch (Exception e){
            e.printStackTrace();
        }
        return locked;
    }

    public void unLock(String key, String uuid){
        redisTemplate.execute(unlockScript, Collections.singletonList(key), uuid);
    }
}
