package com.hy.service;

import com.hy.entity.Messageflow;

public interface YuebaoService {

    /**
     * 执行支付动作
     * 1.插入消息
     * 2.发送消息
     */
    void doPay(Messageflow  messageflow);

    void sendMessage(Messageflow messageflow);

    String updateMessageflow(Messageflow messageflow);
}
