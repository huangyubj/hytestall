package com.hy.entity.frute;

import com.hy.entity.Fruit;
import com.hy.entity.bag.OrangeBag;

/**
 *
 */
public class Orange implements Fruit {

    private int price;

    private String name;

    public Orange(int price, String name) {
        this.price = price;
        this.name = name;
    }

    public void pack(OrangeBag bag){
        bag.pack();
    }
    public void setPrice(int price) {
        this.price = price;
    }

    public int price() {
        return price;
    }

    public void draw() {
        System.out.print("湖南冰糖橙");
    }

}
