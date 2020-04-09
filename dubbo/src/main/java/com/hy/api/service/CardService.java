package com.hy.api.service;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.Adaptive;
import com.alibaba.dubbo.common.extension.SPI;

@SPI("c")
public interface CardService {
    @Adaptive("key")
    void say(String msg, URL url);
    void say();
    void send();
}
