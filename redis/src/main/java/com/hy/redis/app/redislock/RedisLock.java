package com.hy.redis.app.redislock;

import com.hy.redis.utils.FileUtil;
import redis.clients.jedis.Jedis;

import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class RedisLock implements Lock {
    private final String LOCK_KEY = "lock_key";
    private ThreadLocal<String> local = new ThreadLocal<String>();
    private Jedis jedis = new Jedis("127.0.0.1", 6379);
    public void lock() {
        if(tryLock()){
            return;
        }
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lock();
    }

    public void lockInterruptibly() throws InterruptedException {

    }

    /**
     * 超时保证死锁能解锁
     * 设置锁和超时时间保证原子操作
     * @return
     */
    public boolean tryLock() {
        String uui = UUID.randomUUID().toString();
        String result = jedis.set(LOCK_KEY, uui, "NX","PX", 3000);
        if("OK".equals(result)){
            //为锁生成一个随机标识，用于释放锁的时候验证只能当前现场进行释放
            local.set(uui);
            return true;
        }
        return false;
    }

    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    /**
     * 采用lua脚本释放锁，保证原子性
     */
    public void unlock() {
        String script = FileUtil.getScript(RedisLock.class.getResource("/unlock.lua").getPath());
        String uui = UUID.randomUUID().toString();
        String del = jedis.eval(script, Arrays.asList(LOCK_KEY), Arrays.asList(local.get())).toString();
    }

    public Condition newCondition() {
        return null;
    }
}
