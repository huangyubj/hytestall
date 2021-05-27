package com.hy.aop.impl;


import com.hy.aop.ExprService;
import org.springframework.stereotype.Service;

@Service
public class ExprServiceImpl implements ExprService {
    @Override
    public int multiply(int a, int b) {
        int result = a*b;
        System.out.println("multiply:" + result);
        return result;
    }

    @Override
    public int add(int a, int b) {
        int result = a+b;
        System.out.println("add:" + result);
        return result;
    }
    @Override
    public int divide(int a, int b) {
        int result = a/b;
        System.out.println("divide:" + result);
        return result;
    }

    @Override
    public int subtract(int a, int b) {
        int result = a-b;
        System.out.println("subtract:" + result);
        return result;
    }
}
