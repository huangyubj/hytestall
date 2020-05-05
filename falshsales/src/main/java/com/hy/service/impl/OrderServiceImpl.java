package com.hy.service.impl;

import com.hy.dao.CatalogMapper;
import com.hy.dao.SalesOrderMapper;
import com.hy.entity.Catalog;
import com.hy.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = Exception.class)
@Service
public class OrderServiceImpl implements OrderService {
    private static final String CATALOG_TOTAL = "catalog_total";
    private static final String CATALOG_SOLD = "catalog_sold";
    @Autowired
    private CatalogMapper catalogMapper;
    @Autowired
    private SalesOrderMapper salesOrderMapper;
    @Autowired


    RedisTemplate<String, String> redisTemplate;
    @Override
    public void initGoodsInfo() {
        Catalog catalog = new Catalog();
        catalog.setName("mac");
        catalog.setTotal(1000);
        catalog.setSold(0);
        catalogMapper.insert(catalog);
        redisTemplate.opsForValue().set(CATALOG_TOTAL + catalog.getId(), catalog.getTotal().toString());
        redisTemplate.opsForValue().set(CATALOG_SOLD + catalog.getId(), catalog.getSold().toString());
//        log.info("redis value:{}", redisTemplate.opsForValue().get(CATALOG_TOTAL + catalog.getId()));
        handleCatalog();
    }

    private void handleCatalog() {

    }
}
