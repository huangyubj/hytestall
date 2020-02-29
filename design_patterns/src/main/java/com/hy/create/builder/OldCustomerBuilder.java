package com.hy.create.builder;

import com.hy.entity.frute.Apple;
import com.hy.entity.frute.Banana;
import com.hy.entity.frute.Orange;

/**
 *
 */
public class OldCustomerBuilder implements Builder {
    private FruitMeal fruitMeal = new FruitMeal();

    public void buildApple(int price) {
        Apple apple = new Apple();
        apple.setPrice(price);
        fruitMeal.setApple(apple);
    }

    public void buildBanana(int price) {
        Banana fruit = new Banana(33);
        fruit.setPrice(price);
        fruitMeal.setBanana(fruit);
    }

    public void buildOrange(int price) {
        Orange fruit = new Orange(66,"hy");
        fruit.setPrice(price);
        fruitMeal.setOrange(fruit);
    }

    public FruitMeal getFruitMeal() {
        fruitMeal.setDiscount(10);
        fruitMeal.init();
        return fruitMeal;
    }
}
