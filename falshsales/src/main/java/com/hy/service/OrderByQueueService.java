package com.hy.service;

import com.hy.entity.Catalog;

public interface OrderByQueueService extends OrderService{

    void cleanQueue();

    int blokingQueuePlaceOrder(Integer id);

    int redisQueuePlaceOrder(Integer id);

    int rabbitMqPlaceOrder(Integer id);

}
