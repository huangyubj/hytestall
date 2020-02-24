package com.hy.action.strategy;

/**
 * 满减
 */
public class FullDiscount implements Discount {
    public int calculate(int money) {
        if (money > 200){
            System.out.println("优惠减免20元");
            return money - 20;
        }
        return money;
    }
}
