package com.hy.create.abstractFactory;

import com.hy.entity.Bag;
import com.hy.entity.Fruit;
import com.hy.entity.bag.AppleBag;
import com.hy.entity.frute.Apple;

/**
 * 水果工厂
 */
public class AppleFactory extends AbstractFactory{

    @Override
    public Fruit getFruit() {
        return new Apple();
    }

    @Override
    public Bag getBag() {
        return new AppleBag();
    }
}
