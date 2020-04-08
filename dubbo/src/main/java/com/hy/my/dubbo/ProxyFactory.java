package com.hy.my.dubbo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyFactory implements InvocationHandler {

    private Class interfaceclass;

    public ProxyFactory(Class interfaceclass) {
        this.interfaceclass = interfaceclass;
    }

    public <T> T getProxyObject(){
        return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{interfaceclass}, this);
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println(method);
        System.out.println("进行编码");
        System.out.println("发送网络请求");
        System.out.println("获取网络请求结果");
        System.out.println("进行解码并返回");
        return null;
    }
}
