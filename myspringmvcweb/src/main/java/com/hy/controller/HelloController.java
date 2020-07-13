package com.hy.controller;

import com.hy.annotation.HyController;
import com.hy.annotation.HyParame;
import com.hy.annotation.HyRequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@HyController
@HyRequestMapping("/test")
public class HelloController {

    @HyRequestMapping("/hello")
    public String hello(HttpServletRequest request, HttpServletResponse response, @HyParame("msg") String msg){
        try {
            PrintWriter pw = response.getWriter();
            pw.write("hello,"+msg);
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "hello,"+msg;
    }
}
