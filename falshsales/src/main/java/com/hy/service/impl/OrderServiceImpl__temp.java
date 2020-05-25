//package com.hy.service.impl;
//
//import com.hy.dao.CatalogMapper;
//import com.hy.dao.SalesOrderMapper;
//import com.hy.entity.Catalog;
//import com.hy.entity.SalesOrder;
//import com.hy.service.OrderService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.script.RedisScript;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.sql.Date;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.ArrayBlockingQueue;
//import java.util.concurrent.BlockingQueue;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.atomic.AtomicBoolean;
//
//@Transactional(rollbackFor = Exception.class)
//@Service
//public class OrderServiceImpl__temp implements OrderService {
//    private static final String CATALOG_TOTAL = "catalog_total";
//    private static final String CATALOG_SOLD = "catalog_sold";
//    private static final String LOCKED_SOLD = "locked_catalog_sold";
//
//    private BlockingQueue<Catalog> catalogQueue = new ArrayBlockingQueue(1000);
//
//
//
//    private AtomicBoolean isInitQueueHandler = new AtomicBoolean(false);
//    private AtomicBoolean isInitRedisCache = new AtomicBoolean(false);
//    private AtomicBoolean isinitRedisQueueCache = new AtomicBoolean(false);
//    private static final int threadNumbers = 4;
//    ExecutorService executorService = Executors.newFixedThreadPool(threadNumbers);
//
//    @Autowired
//    private CatalogMapper catalogMapper;
//    @Autowired
//    private SalesOrderMapper salesOrderMapper;
//    @Autowired
//    RedisTemplate<String, String> redisTemplate;
//    @Autowired
//    private RedisScript lockcatalogScript;
//
//    @Override
//    public Integer addGoodsInfo(Catalog catalog) {
//        int id = catalogMapper.insertSelective(catalog);
//        redisTemplate.opsForValue().set(CATALOG_TOTAL + catalog.getId(), catalog.getTotal().toString());
//        redisTemplate.opsForValue().set(CATALOG_SOLD + catalog.getId(), catalog.getSold().toString());
//        return id;
//    }
//
//    private void initRedisCache(){
//        if(isInitRedisCache.compareAndSet(false, true)){
//            List<Catalog> list = catalogMapper.selectAll();
//            Map<String, String> caches = new HashMap<>();
//            for (int i = 0; i < list.size(); i++) {
//                Catalog catalog = list.get(i);
//                caches.put(CATALOG_TOTAL + catalog.getId(), String.valueOf(catalog.getTotal()));
//                caches.put(CATALOG_SOLD + catalog.getId(), String.valueOf(catalog.getSold()));
//            }
//            redisTemplate.opsForValue().multiSet(caches);
//        }
//    }
//
//    /**
//     * 直接数修改据库缓存处理
//     * @param catalogid
//     * @return
//     */
//    @Override
//    public Integer placeOrer(Integer catalogid) {
//        initRedisCache();
//        Integer total = Integer.parseInt(redisTemplate.opsForValue().get(CATALOG_TOTAL + catalogid));
//        Integer sold = Integer.parseInt(redisTemplate.opsForValue().get(CATALOG_SOLD + catalogid));
//        if(total.equals(sold)){
//            throw new RuntimeException("ALL SOLD OUT: " + catalogid);
//        }
//        Catalog catalog = catalogMapper.selectByPrimaryKey(catalogid);
//        catalog.setSold(catalog.getSold() +1);
//        SalesOrder salesOrder = new SalesOrder();
//        salesOrder.setCid(catalogid);
//        salesOrder.setName(catalog.getName());
//        salesOrder.setCreateTime(new Date(System.currentTimeMillis()));
//        catalogMapper.updateByPrimaryKey(catalog);
//        salesOrderMapper.insertSelective(salesOrder);
//        redisTemplate.opsForValue().increment(CATALOG_SOLD + catalogid, 1);
//        return salesOrder.getId();
//    }
//
//    @Override
//    public Integer placeOrerWithDatabase(Integer catalogid) {
//        int lock = catalogMapper.updateCatalognum(catalogid, 1);
//        int id = -1;
//        if(lock != 1)
//            throw new RuntimeException("ALL SOLD OUT: " + catalogid);
//        try {
//            Catalog catalog = catalogMapper.selectByPrimaryKey(catalogid);
//            SalesOrder salesOrder = new SalesOrder();
//            salesOrder.setCid(catalogid);
//            salesOrder.setName(catalog.getName());
//            salesOrder.setCreateTime(new Date(System.currentTimeMillis()));
//            id = salesOrderMapper.insertSelective(salesOrder);
//        }catch (Exception e){
//            catalogMapper.updateCatalognum(catalogid, -1);
//            e.printStackTrace();
//        }
//
//        return id;
//    }
//
//    private void initRedisQueueCache(){
//        if(isinitRedisQueueCache.compareAndSet(false, true)){
//            List<Catalog> list = catalogMapper.selectAll();
//            Map<String, String> caches = new HashMap<>();
//            for (int i = 0; i < list.size(); i++) {
//                Catalog catalog = list.get(i);
//                caches.put(CATALOG_TOTAL + catalog.getId(), String.valueOf(catalog.getTotal()));
//                caches.put(CATALOG_SOLD + catalog.getId(), String.valueOf(catalog.getSold()));
//                caches.put(LOCKED_SOLD + catalog.getId(), String.valueOf(catalog.getSold()));
//            }
//            redisTemplate.opsForValue().multiSet(caches);
//        }
//    }
//    /**
//     * 通过阻塞队列处理
//     * @param catalogid
//     * @return
//     */
//    @Override
//    public Integer placeOrerWithqueue(Integer catalogid) {
//        initRedisQueueCache();
//        initQueueHandleCatalog();
//        boolean lock = false;
//        //锁定库存 + 1，如果已处理库存+锁定库存 == 库存，则秒杀完成
//        lock = (boolean) redisTemplate.execute(lockcatalogScript,Collections.EMPTY_LIST, CATALOG_TOTAL + catalogid, CATALOG_SOLD + catalogid);
//        try {
//            if(lock){
//                Catalog catalog = catalogMapper.selectByPrimaryKey(catalogid);
//                catalogQueue.put(catalog);
//            }else{
//                //TODO 秒杀完了
//                return -1;
//            }
//        }catch (InterruptedException e){
//            e.printStackTrace();
//
//        }
//        return 0;
//    }
//
//    private void initQueueHandleCatalog() {
//        if(isInitQueueHandler.compareAndSet(false, true)){
//            for (int i = 0; i < threadNumbers; i++) {
//                executorService.execute(new CatalogHandller());
//            }
//        }
//    }
//
//    private class CatalogHandller implements Runnable{
//        @Override
//        public void run() {
//            for (;;){
//                try {
//                    Catalog catalog = catalogQueue.take();
//                    catalog.setSold(catalog.getSold() +1);
//                    SalesOrder salesOrder = new SalesOrder();
//                    salesOrder.setCid(catalog.getId());
//                    salesOrder.setName(catalog.getName());
//                    salesOrder.setCreateTime(new Date(System.currentTimeMillis()));
//                    catalogMapper.updateByPrimaryKey(catalog);
//                    salesOrderMapper.insertSelective(salesOrder);
//                    redisTemplate.opsForValue().increment(CATALOG_SOLD + catalog.getId(), 1);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//    /**
//     *
//     * @param id
//     * @return
//     */
//    @Override
//    public Integer placeOrerWithMq(int id) {
//        initRedisCache();
//        //TODO 采用消息队列生成订单，发送消息到订单服务中生成订单，接收确认处理返回
//        return null;
//    }
//
//
//}
