package com.hy.service;

import org.springframework.stereotype.Service;

public interface YuebaoService {

    //执行支付动作，减少月报内的金额
    public void doPay();
}
