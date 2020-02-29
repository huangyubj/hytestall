package com.hy.create.abstractFactory;

import com.hy.entity.Bag;
import com.hy.entity.Fruit;
import com.hy.entity.bag.BananaBag;
import com.hy.entity.frute.Banana;

/**
 * 水果工厂
 */
public class BananaFactory extends AbstractFactory{

    @Override
    public Fruit getFruit() {
        return new Banana(3);
    }

    @Override
    public Bag getBag() {
        return new BananaBag();
    }
}
