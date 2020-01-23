package com.hy.quartz.job;

import org.springframework.stereotype.Component;

@Component
public class SimpleService {
    public void exec(){
        System.out.println("SimpleService业务方法执行...." + ",线程号："+Thread.currentThread().getName());
    }
}
