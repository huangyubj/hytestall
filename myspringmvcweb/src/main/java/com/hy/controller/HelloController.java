package com.hy.controller;

import com.hy.annotation.HyController;
import com.hy.annotation.HyRequestMapping;

@HyController
@HyRequestMapping("/test")
public class HelloController {

    @HyRequestMapping("/hello")
    public String hello(String msg){
        return "hello,"+msg;
    }
}
