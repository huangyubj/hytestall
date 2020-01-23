package com.hy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = {Application.class})
@RunWith(SpringRunner.class)
public class RedisTest {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void testRedis(){
        ListOperations<String, String> listOperations =  redisTemplate.opsForList();
        System.out.println(listOperations.leftPush("sptest", "this is sptest"));
        System.out.println(listOperations.rightPop("sptest"));
        BoundListOperations<String, String> boundListOperations = redisTemplate.boundListOps("sptest");
        System.out.println(boundListOperations.leftPushAll("test1", "test2","test3"));;
        System.out.println(boundListOperations.leftPop());
    }
}
