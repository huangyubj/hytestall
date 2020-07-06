package com.hy.handlerAdapter.impl;

import com.hy.annotation.HyService;
import com.hy.handlerAdapter.HyHandlerAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;

@HyService
public class HyHandlerAdapterImpl implements HyHandlerAdapter {
    @Override
    public Object[] hand(HttpServletRequest request, HttpServletResponse response, Method method, Map<String, Object> beans) {
        Class[]  paramClazzs = method.getParameterTypes();
        Object[] args = new Object[paramClazzs.length];
        Map<String, Object> argumentResolvers =getBeansOfType(beans,
                ArgumentResolver.class);
        return args;
    }
}
