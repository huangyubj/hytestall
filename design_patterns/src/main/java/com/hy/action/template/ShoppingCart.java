package com.hy.action.template;

import com.hy.action.strategy.Discount;
import com.hy.entity.Fruit;

import java.util.ArrayList;
import java.util.List;

/**
 * 购物车费用结算过程
 * 模板方法模式
 * 定义一个操作中的算法的骨架，而将一些步骤延迟到子类中。模板方法使得子类可以不改变一个算法的结构即可重定义该算法的某些特定步骤
 * 如： spring种的Template
 */
public abstract class ShoppingCart {
    private Discount discount;
    private List<Fruit> products = new ArrayList<Fruit>();

    public ShoppingCart(List<Fruit> products){
        this.products = products;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    //提交订单主流程
    public void submitOrder(){
        //计算商品金额
        int money = balance();
        System.out.println("商品总金额为："+money+"元");

        //优惠减免
        money = discount.calculate(money);
        System.out.println("优惠减免后："+ money+"元，");

        //保存订单
        pay(money);

        //送货上门
        sendHome();

    }

    //计算金额
    private int balance(){
        int money = 0;
        System.out.print("商品清单：");
        for (Fruit fruit : products){
            fruit.draw();
            System.out.print(",");
            money += fruit.price();
        }
        return money;
    }

    private void sendHome(){
        System.out.println("三公里以内，免费送货上门");
    }

    //提交保存
    protected abstract void pay(int money);

}
