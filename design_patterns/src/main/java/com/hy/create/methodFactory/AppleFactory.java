package com.hy.create.methodFactory;

import com.hy.entity.Fruit;
import com.hy.entity.frute.Apple;

/**
 * 工厂方法模式
 */
public class AppleFactory implements FruitFactory{
    public Fruit getFruit(){
        return new Apple();
    }
}
