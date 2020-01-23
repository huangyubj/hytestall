package com.hy.redis.app.redpckage;

import com.alibaba.fastjson.JSONObject;
import com.hy.redis.utils.FileUtil;
import com.hy.redis.utils.JedisUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

/**
 * lua 抢红包测试
 * 利用lua原子性保证安全
 * 1.构建一个list红包队列、抢过红包的人set、抢完的红包详情hash
 * 2.lua抢红包，统一从一个方向取红包，没有了就取出来一个nil
 */
public class TestRedPackge {
    //存放待抢的红包信息
    private static final String RED_PCK_POOL_NAME = "redpkpool";
    //存放抢过的人员信息
    private static final String RED_PERSON_SET_NAME = "redpkuserpool";
    //存储抢过的红包详情
    private static final String RED_PK_DETAIL_NAME = "redpkdetailpool";

    public static void main(String[] args) {
        TestRedPackge test = new TestRedPackge();
        test.initRedPool(new BigDecimal(7.3), 10);
        test.getRedPck(20);
//        test.testlua();
    }
    private JedisUtils jedis;
    private Random r;
    public TestRedPackge(){
        //jedis客户端采用socket连接实现，没处理线程安全的，redis连接是个稀缺资源，并发情况下最好采用连接池
        jedis = new JedisUtils("127.0.0.1",6379, "12345678");
        jedis.flushDB();
        jedis.scriptFlush();
        r = new Random();
    }
    public void initRedPool(BigDecimal money, int nums){
        BigDecimal[] proportions = new BigDecimal[nums];
        BigDecimal total = new BigDecimal(0);
        for (int i = 0; i < nums; i++) {
            BigDecimal prop = new BigDecimal(r.nextInt(10) + 0.1);
            proportions[i] = prop;
            total = total.add(prop);
        }
        DecimalFormat df1 = new DecimalFormat("0.00");
        JSONObject json = new JSONObject();
        for (int i = 0; i < nums; i++) {
            BigDecimal b = money.multiply(proportions[i]).divide(total, 2);
            String mey = df1.format(b.floatValue());
            json.clear();
            json.put("id", i);
            json.put("money", mey);
            jedis.rpush(RED_PCK_POOL_NAME, json.toJSONString());
            System.out.println(json.toJSONString());
        }
    }

    /**
     * 模拟
     */
    public void getRedPck(final int threadNums){
        final CountDownLatch threadC = new CountDownLatch(threadNums);
        for (int i = 0; i < threadNums; i++) {
            new Thread(){
                @Override
                public void run() {
                    String userid = UUID.randomUUID().toString();
                    String info = String.valueOf(jedis.eval(FileUtil.getScript(TestRedPackge.class.getResource("/redpackege.lua").getPath()),
                            4, userid, RED_PCK_POOL_NAME, RED_PERSON_SET_NAME, RED_PK_DETAIL_NAME));
                    if("none".equals(info)){
                        System.out.println(userid + "没有了");
                    }else if("exist".equals(info)){
                        System.out.println(userid + "已经抢过了");
                    }else{
                        System.out.println(userid + "抢到红包-->" + info);
                    }
                    threadC.countDown();
                }
            }.start();
        }
        try {
            threadC.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void testlua(){
        String userid = "8087583c-e0c3-418c-ada4-4302cf4d3ae8";//UUID.randomUUID().toString();
        System.out.println(userid);
        String info = String.valueOf(jedis.eval(FileUtil.getScript(TestRedPackge.class.getResource("/redpackege.lua").getPath()),
                4, userid, RED_PCK_POOL_NAME, RED_PERSON_SET_NAME, RED_PK_DETAIL_NAME));
        System.out.println(info);
    }
}
