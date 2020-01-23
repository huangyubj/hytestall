package com.hy.redis.app.redislock;

import com.hy.redis.utils.FileUtil;
import redis.clients.jedis.Jedis;

import java.util.concurrent.CountDownLatch;

public class RedisLockTest {
    private static int THREADS_NUM = 30;
    private static Jedis jedis = new Jedis("127.0.0.1", 6379);;
    private static CountDownLatch c = new CountDownLatch(THREADS_NUM);
    public static void main(String[] args) {
        RedisLock test = new RedisLock();
        System.out.println("服务正在运行: "+jedis.ping());
        //模拟有20张票，30个人来抢
        jedis.set("ticktsnum", "20");
        for (int i = 0; i < THREADS_NUM; i++) {
            new TicktThread().start();
        }
    }
    static class TicktThread extends Thread{

        @Override
        public void run() {
            c.countDown();
            System.out.println(Thread.currentThread().getName()+"-> 开始买票");
            RedisLock lock = null;
            try {
                c.await();
                lock = new RedisLock();
                lock.lock();
                Thread.sleep(500);
                long flag = (Long)jedis.eval(FileUtil.getScript(RedisLock.class.getResource("/buy.lua").getPath()));
                if(flag > 0){
                    System.out.println(System.currentTimeMillis() + ":" + Thread.currentThread().getName()+"-> 买票成功");
                }else{
                    System.out.println(System.currentTimeMillis() + ":" + Thread.currentThread().getName()+"-> 买票失败");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if(lock != null){
                    lock.unlock();
                }
            }
        }
    }
}
