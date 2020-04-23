package com.hy.service;

import com.hy.entity.Messageflow;

public interface YuebaoService {

    //执行支付动作，减少月报内的金额
    void doPay(Messageflow  messageflow);

    void sendMessage(Messageflow messageflow);

    String updateMessageflow(Messageflow messageflow);
}
