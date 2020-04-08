package com.hy.spi.filter.impl;

import com.alibaba.dubbo.common.extension.Activate;
import com.hy.spi.filter.Filter;


@Activate(group = {"A","B","C","D","E"}, order = 1)
public class FilterA implements Filter {

    public Object invoke(Object... args) {
        System.out.println("执行了FilterA");
        return null;
    }
}
