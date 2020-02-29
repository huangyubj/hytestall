package com.hy.create.singleton;

import java.util.concurrent.atomic.AtomicInteger;

public class SessionCountStatic {
    private AtomicInteger count = new AtomicInteger(0);


    private SessionCountStatic(){
    }

    /**
     * 静态内部类式，利用初始化clinit指令的线程安全性来保证单例
     * @return
     */
    public static SessionCountStatic getInstance(){
        return Instance.instance;
    }

    private static class Instance{
        private static SessionCountStatic instance = new SessionCountStatic();
    }

    /***以下是业务方法***/
    public int plus(){
        return count.incrementAndGet();
    }

    public int decrease(){
        return count.decrementAndGet();
    }

    public void showMessage(){
        System.out.println("当前人数："+this.count.get());
    }


}
