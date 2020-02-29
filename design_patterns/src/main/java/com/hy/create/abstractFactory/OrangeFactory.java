package com.hy.create.abstractFactory;

import com.hy.entity.Bag;
import com.hy.entity.Fruit;
import com.hy.entity.bag.OrangeBag;
import com.hy.entity.frute.Orange;

/**
 * 水果工厂
 */
public class OrangeFactory extends AbstractFactory{

    @Override
    public Fruit getFruit() {
        return new Orange(8, "hy");
    }

    @Override
    public Bag getBag() {
        return new OrangeBag();
    }
}
