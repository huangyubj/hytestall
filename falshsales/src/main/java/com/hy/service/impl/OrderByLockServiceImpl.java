package com.hy.service.impl;

import com.hy.annotation.OrderService;
import com.hy.current.RedisLock;
import com.hy.dao.CatalogMapper;
import com.hy.dao.SalesOrderMapper;
import com.hy.entity.Catalog;
import com.hy.entity.SalesOrder;
import com.hy.service.OrderByLockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 秒杀采用锁来保证超卖性能不如采用队列来处理，队列可以有效避免锁竞争
 *
 */
@Service
public class OrderByLockServiceImpl implements OrderByLockService {

    Logger logger = LoggerFactory.getLogger(OrderByLockServiceImpl.class);
    private static final String LOCKPRE = "good_lock_";

    @Autowired
    private CatalogMapper catalogMapper;

    @Autowired
    private SalesOrderMapper salesOrderMapper;

    @Autowired
    private RedisLock redisLock;

    private Lock lock = new ReentrantLock();

    @Transactional
    @Override
    public int apiLockPlaceOrder(int calogId) {
        lock.lock();
        int orderId = -1;
        try {
            Catalog catalog = catalogMapper.selectByPrimaryKey(calogId);
            if(catalog.getTotal() > catalog.getSold()){
                catalog.setSold(catalog.getSold() + 1);
                SalesOrder salesOrder = new SalesOrder();
                salesOrder.setCid(catalog.getId());
                salesOrder.setName(catalog.getName());
                salesOrder.setCreateTime(new Date(System.currentTimeMillis()));
                salesOrderMapper.insertSelective(salesOrder);
                catalogMapper.updateByPrimaryKey(catalog);
                orderId = salesOrder.getId();
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
        }finally {
            lock.unlock();
        }
        return orderId;
    }

    @Override
    @Transactional
    public int dataBaseLockPlaceOrder(Integer calogId) {
        // 也可以用 select * from catalog where id= #{id} for update 进行锁定
        int num = catalogMapper.updateCatalognum(calogId, 1);
        int orderId = -1;
        if(num == 1){
            try {
                Catalog catalog = catalogMapper.selectByPrimaryKey(calogId);
                SalesOrder salesOrder = new SalesOrder();
                salesOrder.setCid(catalog.getId());
                salesOrder.setName(catalog.getName());
                salesOrder.setCreateTime(new Date(System.currentTimeMillis()));
                salesOrderMapper.insertSelective(salesOrder);
                orderId = salesOrder.getId();
            }catch (Exception e){
                e.printStackTrace();
                //错误做库存回滚
                catalogMapper.updateCatalognum(calogId, -1);
                logger.error(e.getMessage());
            }
        }
        return orderId;
    }

    @OrderService
    @Override
    public int aopLockPlaceOrder(Integer calogId) {
        int orderId = -1;
        try {
            Catalog catalog = catalogMapper.selectByPrimaryKey(calogId);
            SalesOrder salesOrder = new SalesOrder();
            salesOrder.setCid(catalog.getId());
            salesOrder.setName(catalog.getName());
            salesOrder.setCreateTime(new Date(System.currentTimeMillis()));
            salesOrderMapper.insertSelective(salesOrder);
            orderId = salesOrder.getId();
        }catch (Exception e){
            e.printStackTrace();
            //错误做库存回滚
            catalogMapper.updateCatalognum(calogId, -1);
            logger.error(e.getMessage());
        }
        return orderId;
    }

    @Override
    public int redisLockPlaceOrder(Integer id) {
        int orderId = -1;
        boolean locked = false;
        String key = LOCKPRE + id;
        String uuid = String.valueOf(id);
        try {
            //考虑此处可以采用 brpop/blpop 来做锁阻塞队列，无锁进入阻塞，释放锁添加进队列（分布式阻塞锁）
            while (!locked){
                locked = redisLock.lock(key, uuid, "15");
                if(locked){
                    Catalog catalog = catalogMapper.selectByPrimaryKey(id);
                    if(catalog.getTotal() > catalog.getSold()){
                        catalog.setSold(catalog.getSold() + 1);
                        SalesOrder salesOrder = new SalesOrder();
                        salesOrder.setCid(catalog.getId());
                        salesOrder.setName(catalog.getName());
                        salesOrder.setCreateTime(new Date(System.currentTimeMillis()));
                        salesOrderMapper.insertSelective(salesOrder);
                        catalogMapper.updateByPrimaryKey(catalog);
                        orderId = salesOrder.getId();
                    }
                }else{
                    Thread.sleep(10);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(locked){
                redisLock.unLock(key, uuid);
            }
        }



        return orderId;
    }

    @Override
    public int zkLockPlaceOrder(Integer id) {
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
