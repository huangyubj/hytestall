package com.hy.service;

import com.hy.entity.Catalog;

public interface OrderByLockService extends OrderService{
    int apiLockPlaceOrder(int calogId);

    int dataBaseLockPlaceOrder(Integer id);

    int aopLockPlaceOrder(Integer id);


    int redisLockPlaceOrder(Integer id);

    int zkLockPlaceOrder(Integer id);
}
