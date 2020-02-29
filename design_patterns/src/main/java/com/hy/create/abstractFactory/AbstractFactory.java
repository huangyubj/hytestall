package com.hy.create.abstractFactory;

import com.hy.entity.Bag;
import com.hy.entity.Fruit;

/**
 * 抽象水果工厂
 */
public abstract class AbstractFactory {

    public abstract Fruit getFruit();

    public abstract Bag getBag();

}
