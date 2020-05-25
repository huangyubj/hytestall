package com.hy.service.impl;

import com.hy.dao.CatalogMapper;
import com.hy.dao.SalesOrderMapper;
import com.hy.entity.Catalog;
import com.hy.entity.SalesOrder;
import com.hy.service.OrderByQueueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class OrderByQueueServiceImpl implements OrderByQueueService {

    Logger logger = LoggerFactory.getLogger(OrderByQueueServiceImpl.class);

    @Autowired
    private CatalogMapper catalogMapper;

    @Autowired
    private SalesOrderMapper salesOrderMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    private ArrayBlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<Integer>(1000);

    private static final String REDIS_SALE_QUEUE_NAME = "redis_sale_queue_";
    private AtomicBoolean isInitQueue = new AtomicBoolean(false);
    private CountDownLatch downLatch = new CountDownLatch(1);

    @Transactional
    @Override
    public int blokingQueuePlaceOrder(Integer id) {
        int orderId = -1;
        //初始化库存队列，正式可采用任务调度系统做定时任务生成库存，开启秒杀,队列大小根据秒杀数量大小做调整
        if(isInitQueue.compareAndSet(false, true)){
            blockingQueue.clear();
            Catalog catalog = catalogMapper.selectByPrimaryKey(id);
            int saleNum = catalog.getTotal() - catalog.getSold();
            for (int i = 0; i < saleNum; i++) {
                blockingQueue.offer(i + 1);
            }
            //测试使用，保证秒杀在秒杀库存信息初始化完成之后开始，否则初始化之前进来的请求都将因为没有库存秒杀失败
            downLatch.countDown();
        }else{
            try {
                downLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Integer saleNum;
        if((saleNum = blockingQueue.poll()) != null){
            try {
                Catalog catalog = catalogMapper.selectByPrimaryKey(id);
                SalesOrder salesOrder = new SalesOrder();
                salesOrder.setCid(catalog.getId());
                salesOrder.setName(catalog.getName());
                salesOrder.setCreateTime(new Date(System.currentTimeMillis()));
                salesOrderMapper.insertSelective(salesOrder);
                //此处需要保证变更被秒数量的原子性
                catalogMapper.updateCatalognum(catalog.getId(), 1);
                orderId = salesOrder.getId();
            }catch (Exception e){
                logger.error(e.getMessage());
                blockingQueue.add(saleNum);
            }
        }
        logger.error("salNum-----" + saleNum);
        return orderId;
    }

    @Override
    public void cleanQueue(){
        isInitQueue.set(false);
    }

    @Transactional
    @Override
    public int redisQueuePlaceOrder(Integer id) {
        int orderId = -1;
        //初始化redis库存队列，正式可采用任务调度系统做定时任务生成库存，开启秒杀,队列大小根据秒杀数量大小做调整
        if(isInitQueue.compareAndSet(false, true)){
            Catalog catalog = catalogMapper.selectByPrimaryKey(id);
            List list = new ArrayList(catalog.getTotal() - catalog.getSold());
            int saleNum = catalog.getTotal() - catalog.getSold();
            for (int i = 0; i < saleNum; i++) {
                list.add(i + 1);
            }
            long l = redisTemplate.opsForList().leftPushAll(REDIS_SALE_QUEUE_NAME + catalog.getId(), list);
            System.out.println("init sold:"+l);
        }
        Integer saleNum = (Integer) redisTemplate.opsForList().rightPop(REDIS_SALE_QUEUE_NAME + id);
        if(saleNum != null){
            try {
                Catalog catalog = catalogMapper.selectByPrimaryKey(id);
                SalesOrder salesOrder = new SalesOrder();
                salesOrder.setCid(catalog.getId());
                salesOrder.setName(catalog.getName());
                salesOrder.setCreateTime(new Date(System.currentTimeMillis()));
                salesOrderMapper.insertSelective(salesOrder);
                //此处需要保证变更被秒数量的原子性
                catalogMapper.updateCatalognum(catalog.getId(), 1);
                orderId = salesOrder.getId();
            }catch (Exception e){
                logger.error(e.getMessage());
                redisTemplate.opsForList().leftPush(REDIS_SALE_QUEUE_NAME, saleNum);
            }
        }
        return orderId;
    }

    @Override
    public int rabbitMqPlaceOrder(Integer id) {
        int orderId = -1;


        return orderId;
    }

    @Override
    public Catalog selectCatalog(Integer id) {
        return catalogMapper.selectByPrimaryKey(id);
    }

    @Override
    public int addCatalogInfo(Catalog catalog) {
        return catalogMapper.insertSelective(catalog);
    }
}
