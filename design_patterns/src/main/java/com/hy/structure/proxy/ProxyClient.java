package com.hy.structure.proxy;

import com.hy.service.OrderService;

/**
 * 代售进口水果
 * 代理模式
 * 为其他对象提供一种代理以控制对这个对象的访问。
 * 反向代理
 * 用户不知道实现的具体地址，如Nginx的反向代理
 */
public class ProxyClient {
    public static void main(String[] args){
        saveOrder();
    }

    //静态代理
    public static void saveOrder(){
        //本地代理类
        OrderService orderService = new ProxyOrder();

        orderService.saveOrder();

        //其它业务代码。。。。

    }



}
