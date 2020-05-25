package com.hy.controller;

import com.hy.entity.Catalog;
import com.hy.service.OrderByLockService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
public class FlashsalesByLockController {
    private static int test_user_num = 10000;
    private static int test_catalog_num = 100;

    private static int corePoolSize = Runtime.getRuntime().availableProcessors();

    private ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, corePoolSize+1,1000,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(test_user_num));

    Logger logger = LoggerFactory.getLogger(FlashsalesByLockController.class);
    @Autowired
    private OrderByLockService orderByLockService;

    @ApiOperation("0---java api锁，适用于单机")
    @PostMapping("/apiLockPlaceOrder")
    public String apiLockPlaceOrder(){
        String result = doTestPlaceOrder("apiLockPlaceOrder");
        return result;
    }
    @ApiOperation("1---java aop,around 前后可以使用各种锁、队列、限流进行功能增强，将业务与功能分离，便于扩展")
    @PostMapping("/aopLockPlaceOrder")
    public String aopLockPlaceOrder(){
        String result = doTestPlaceOrder("aopLockPlaceOrder");
        return result;
    }

    @ApiOperation("2---数据库锁，数据库承受压力比较大")
    @PostMapping("/dataBaseLockPlaceOrder")
    public String dataBaseLockPlaceOrder(){
        String result = doTestPlaceOrder("dataBaseLockPlaceOrder");
        return result;
    }

    @ApiOperation("3---redis分布式锁")
    @PostMapping("/redisLockPlaceOrder")
    public String redisLockPlaceOrder(){
        String result = doTestPlaceOrder("redisLockPlaceOrder");
        return result;
    }

    @ApiOperation("4---zk分布式锁")
    @PostMapping("/zkLockPlaceOrder")
    public String zkLockPlaceOrder(){
        String result = doTestPlaceOrder("zkLockPlaceOrder");
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
                    case "apiLockPlaceOrder":
                        orderId = orderByLockService.apiLockPlaceOrder(catalog.getId());
                        break;
                    case "dataBaseLockPlaceOrder":
                        orderId = orderByLockService.dataBaseLockPlaceOrder(catalog.getId());
                        break;
                    case "aopLockPlaceOrder":
                        orderId = orderByLockService.aopLockPlaceOrder(catalog.getId());
                        break;
                    case "redisLockPlaceOrder":
                        orderId = orderByLockService.redisLockPlaceOrder(catalog.getId());
                        break;
                    case "zkLockPlaceOrder":
                        orderId = orderByLockService.zkLockPlaceOrder(catalog.getId());
                        break;

                }
                if(orderId != -1){
                    logger.info("type={} id={},成功++++++++", type, orderId);
                }else{
                    logger.info("{}} id={},失败--------", type, orderId);
                }
                downLatch.countDown();
            });
        }
        try {
            downLatch.await();
            Catalog catalogNew = orderByLockService.selectCatalog(catalog.getId());
            logger.info(catalogNew.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    @GetMapping("/initCatalog")
    public Catalog initGoodsInfo(String type) {
        Catalog catalog = new Catalog();
        catalog.setName("测试商品_"+ type + UUID.randomUUID().toString().substring(0, 8));
        catalog.setTotal(test_catalog_num);
        catalog.setSold(0);
        orderByLockService.addCatalogInfo(catalog);
        return catalog;
    }
}
