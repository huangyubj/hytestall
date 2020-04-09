package com.hy.spi.filter.card;

import com.alibaba.dubbo.common.extension.Adaptive;
import com.hy.api.service.CardService;

@Adaptive("ad")
public class AdCardService implements CardService {
    public void say() {
        System.out.println("hello ad");
    }

    public void send() {
        System.out.println("send ad");
    }
}
