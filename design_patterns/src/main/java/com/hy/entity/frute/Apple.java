package com.hy.entity.frute;

import com.hy.entity.Fruit;
import com.hy.entity.bag.AppleBag;

/**
 *
 */
public class Apple implements Fruit {

    private int price = 25;

    public Apple() {
    }

    public void pack(AppleBag bag){
        bag.pack();
    }
    public void setPrice(int price) {
        this.price = price;
    }

    public int price() {
        return price;
    }

    public void draw() {
        System.out.print("红富士苹果");
    }

}
