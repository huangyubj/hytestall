package com.hy.entity.frute;

import com.hy.entity.Fruit;
import com.hy.entity.bag.BananaBag;

/**
 *
 */
public class Banana implements Fruit {

    private int price;

    public Banana(int price) {
        this.price = price;
    }

    public void pack(BananaBag bag){
        bag.pack();
    }
    public void setPrice(int price) {
        this.price = price;
    }

    public int price() {
        return price;
    }

    public void draw() {
        System.out.print("热带大香蕉");
    }

}
