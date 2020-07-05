package com.hy.service.impl;


import com.hy.annotation.HyService;
import com.hy.service.HelloService;

@HyService
public class HelloServiceImpl implements HelloService {

    @Override
    public void sayHello() {
        System.out.println("ssssssssssssssss");
    }
}