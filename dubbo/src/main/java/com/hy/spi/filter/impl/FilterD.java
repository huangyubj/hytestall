package com.hy.spi.filter.impl;

import com.alibaba.dubbo.common.extension.Activate;
import com.hy.spi.filter.Filter;


@Activate(group = {"D","E"}, order = 4, value = "D")
public class FilterD implements Filter {

    public Object invoke(Object... args) {
        System.out.println("执行了FilterD");
        return null;
    }
}
