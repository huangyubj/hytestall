package com.hy.spi.filter.card;

import com.alibaba.dubbo.common.extension.Adaptive;
import com.hy.api.service.CardService;

@Adaptive("bd")
public class BdCardService implements CardService {
    public void say() {
        System.out.println("hello bd");
    }

    public void send() {
        System.out.println("send bd");
    }
}
