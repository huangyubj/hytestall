package com.hy.spi.filter.impl;

import com.alibaba.dubbo.common.extension.Activate;
import com.hy.spi.filter.Filter;


@Activate(group = "E", order = 5)
public class FilterE implements Filter {

    public Object invoke(Object... args) {
        System.out.println("执行了FilterE");
        return null;
    }
}
