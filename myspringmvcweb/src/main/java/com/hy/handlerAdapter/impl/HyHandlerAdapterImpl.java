package com.hy.handlerAdapter.impl;

import com.hy.annotation.HyService;
import com.hy.argument.ArgumentResolver;
import com.hy.handlerAdapter.HyHandlerAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@HyService
public class HyHandlerAdapterImpl implements HyHandlerAdapter {

    private List<ArgumentResolver> argumentResolvers;

    @Override
    public Object[] hand(HttpServletRequest request, HttpServletResponse response, Method method, Map<String, Object> beans) {
        Class[]  paramClazzs = method.getParameterTypes();
        Object[] args = new Object[paramClazzs.length];
        initBeansOfType(beans, ArgumentResolver.class);
        for (int i = 0; i < paramClazzs.length; i++) {
            for (ArgumentResolver argymentResolver:argumentResolvers) {
                if(argymentResolver.support(paramClazzs[i], i, method)){
                    args[i] = argymentResolver.argumentResolver(request, response, paramClazzs[i], i, method);
                }
            }
        }
        return args;
    }

    private void initBeansOfType(Map<String, Object> beans, Class<ArgumentResolver> argumentResolverClass) {
        if(argumentResolvers == null){
            synchronized (this){
                if(argumentResolvers == null){
                    argumentResolvers = new ArrayList<>();
                    for (Map.Entry<String, Object> entry:beans.entrySet()) {
                        Class[] interfacesfsArr = entry.getValue().getClass().getInterfaces();
                        for (Class clazz: interfacesfsArr) {
                            if(clazz.isAssignableFrom(argumentResolverClass)){
                                argumentResolvers.add((ArgumentResolver) entry.getValue());
                            }
                        }
                    }
                }
            }
        }
    }
}
