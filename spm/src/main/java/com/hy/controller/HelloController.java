package com.hy.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    private Logger log = LoggerFactory.getLogger(HelloController.class);
    @RequestMapping(value="/hello", method = RequestMethod.GET)
    public Object sayHello(String msg) {
        log.info("test log");
        return "hello" + msg;
    }


    @RequestMapping(value = "/errortest", method = RequestMethod.GET)
    public Object sayError() {
        int i = 10/0;
        return "error";
    }

    @RequestMapping("/404.do")
    public Object error_404() {
        return "你要找的页面，不见了";
    }
}
