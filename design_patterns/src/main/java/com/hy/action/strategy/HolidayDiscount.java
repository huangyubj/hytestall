package com.hy.action.strategy;

/**
 * 假日全免
 */
public class HolidayDiscount implements Discount {
    public int calculate(int money) {
        if (money > 100){
            return money - 20;
        }
        return money;
    }
}
