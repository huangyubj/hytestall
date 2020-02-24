package com.hy.create.builder;


import com.hy.entity.frute.Apple;
import com.hy.entity.frute.Banana;
import com.hy.entity.frute.Orange;

/**
 *
 */
public class HolidayBuilder implements Builder {
    private FruitMeal fruitMeal = new FruitMeal();

    public void buildApple(int price) {
        Apple apple = new Apple();
        apple.setPrice(price);
        fruitMeal.setApple(apple);
    }

    public void buildBanana(int price) {
        Banana fruit = new Banana(15);
        fruit.setPrice(price);
        fruitMeal.setBanana(fruit);
    }

    public void buildOrange(int price) {
        Orange fruit = new Orange(80, "hy");
        fruit.setPrice(price);
        fruitMeal.setOrange(fruit);
    }

    public FruitMeal getFruitMeal() {
        fruitMeal.setDiscount(15);//折扣价格对一个套餐来，是固定的
        fruitMeal.init();
        return fruitMeal;
    }
}
