package com.hy.controller;

import com.hy.entity.Catalog;
import com.hy.service.OrderByLockService;
import com.hy.service.OrderByQueueService;
import com.hy.service.OrderService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 采用锁的形式，保证库存的一致性
 *
 */
@Controller
@ResponseBody
public class FlashsalesByQueueController {
    private static int test_user_num = 10000;
    private static int test_catalog_num = 100;

    private static int corePoolSize = Runtime.getRuntime().availableProcessors();

    private ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, corePoolSize+1,100,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(test_catalog_num * 2));

    Logger logger = LoggerFactory.getLogger(FlashsalesByQueueController.class);
    @Autowired
    private OrderByQueueService orderByQueueService;

    @ApiOperation("0---blokingQueue 做队列，生产者与消费者模式,blockingqueue 本身是线程安全的，所以不用考虑线程问题")
    @PostMapping("/blokingQueuePlaceOrder")
    public String blokingQueuePlaceOrder(){
        String result = doTestPlaceOrder("blokingQueuePlaceOrder");
        return result;
    }

    @ApiOperation("1---redis 队列，利用redis的单线程安全，保证分布式的线程安全，库存不超卖")
    @PostMapping("/redisQueuePlaceOrder")
    public String redisQueuePlaceOrder(){
        String result = doTestPlaceOrder("redisQueuePlaceOrder");
        return result;
    }

    @ApiOperation("2--rabbitMq 队列，做发布订阅处理")
    @PostMapping("/rabbitMqPlaceOrder")
    public String rabbitMqPlaceOrder(){
        String result = doTestPlaceOrder("rabbitMqPlaceOrder");
        return result;
    }

    /**
     * 模拟秒杀
     * @param type
     * @return
     */
    private String doTestPlaceOrder(String type){
        String result = null;
        Catalog catalog = initGoodsInfo(type);
        CountDownLatch downLatch = new CountDownLatch(test_user_num);
        for (int i = 0; i < test_user_num; i++) {
            executor.execute(() ->{
                int orderId = -1;
                switch (type){
                    case "blokingQueuePlaceOrder":
                        orderId = orderByQueueService.blokingQueuePlaceOrder(catalog.getId());
                        break;
                    case "redisQueuePlaceOrder":
                        orderByQueueService.cleanQueue();
                        orderId = orderByQueueService.redisQueuePlaceOrder(catalog.getId());
                        break;
                    case "rabbitMqPlaceOrder":
                        orderId = orderByQueueService.rabbitMqPlaceOrder(catalog.getId());
                        break;

                }
                if(orderId != -1){
                    logger.info("{} id={},成功++++++++", type, orderId);
                }else{
                    logger.info("{}} id={},失败--------", type, orderId);
                }
                downLatch.countDown();
            });
        }
        try {
            downLatch.await();
            Catalog catalogNew = orderByQueueService.selectCatalog(catalog.getId());
            logger.info(catalogNew.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    private Catalog initGoodsInfo(String type) {
        Catalog catalog = new Catalog();
        catalog.setName("测试商品_"+ type + UUID.randomUUID().toString().substring(0, 8));
        catalog.setTotal(test_catalog_num);
        catalog.setSold(0);
        orderByQueueService.addCatalogInfo(catalog);
        return catalog;
    }
}
