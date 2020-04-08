package com.hy.spi.filter.impl;

import com.alibaba.dubbo.common.extension.Activate;
import com.hy.spi.filter.Filter;


@Activate(group = {"B","C","D","E"}, order = 2)
public class FilterB implements Filter {

    public Object invoke(Object... args) {
        System.out.println("执行了FilterB");
        return null;
    }
}
