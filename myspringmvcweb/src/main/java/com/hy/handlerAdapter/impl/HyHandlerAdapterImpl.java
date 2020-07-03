package com.hy.handlerAdapter.impl;

import com.hy.handlerAdapter.HyHandlerAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;

public class HyHandlerAdapterImpl implements HyHandlerAdapter {
    @Override
    public Object[] hand(HttpServletRequest request, HttpServletResponse response, Method method, Map<String, Object> beans) {


        return new Object[0];
    }
}
