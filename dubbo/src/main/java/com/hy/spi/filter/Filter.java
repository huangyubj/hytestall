package com.hy.spi.filter;

import com.alibaba.dubbo.common.extension.SPI;

@SPI
public interface Filter {
    Object invoke(Object ... args);
}
