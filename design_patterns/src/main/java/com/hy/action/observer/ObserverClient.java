package com.hy.action.observer;

/**
 * 顾客关注了芒果，降价时通知他们
 * 观察者模式
 * 意图：定义对象间的一种一对多的依赖关系，当一个对象的状态发生改变时，所有依赖于它的对象都得到通知并被自动更新。
 */
public class ObserverClient {


    public static void main(String[] args) {
        MangoAttention attentions = new MangoAttention();//目标

        attentions.add(new CustomerObserver("hy"));
        attentions.add(new CustomerObserver("ww"));
        attentions.add(new CustomerObserver("zs"));
        attentions.add(new CustomerObserver("ls"));

        //到货
        attentions.perform();

    }


}
