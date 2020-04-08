package com.hy.spi;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;

@Activate(group = {Constants.PROVIDER, Constants.CONSUMER})
public class OptimusPrime implements Robot {

    public void sayHello() {
        System.out.println("Hello, I am Optimus Prime.");
    }
}
