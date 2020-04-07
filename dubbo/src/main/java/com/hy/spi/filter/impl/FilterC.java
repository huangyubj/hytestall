package com.hy.spi.filter.impl;

import com.alibaba.dubbo.common.extension.Activate;
import com.hy.spi.filter.Filter;


@Activate(group = {"C","D","E"}, order = 3, value = "C")
public class FilterC implements Filter {

    public Object invoke(Object... args) {
        System.out.println("执行了FilterC");
        return null;
    }
}
