package com.hy.api.service;

import com.alibaba.dubbo.common.extension.SPI;

@SPI("c")
public interface CardService {
    void say();
    void send();
}
