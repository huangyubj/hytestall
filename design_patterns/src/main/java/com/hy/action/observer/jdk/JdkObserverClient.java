package com.hy.action.observer.jdk;

/**
 * 顾客关注了芒果商品，到货时通知他们
 * 观察者模式
 */
public class JdkObserverClient {

    public static void main(String[] args) {
        Mango attentions = new Mango("芒果");

        attentions.addObserver(new Customer("hy"));
        attentions.addObserver(new Customer("ww"));
        attentions.addObserver(new Customer("zs"));
        attentions.addObserver(new Customer("ls"));

        attentions.perform();

    }


}
