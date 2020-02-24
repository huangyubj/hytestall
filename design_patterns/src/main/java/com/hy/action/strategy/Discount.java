package com.hy.action.strategy;

/**
 * 策略模式
 * 定义一系列的算法,把它们一个个封装起来, 并且使它们可相互替换。
 * 主要解决：在有多种算法相似的情况下，使用 if...else 所带来的复杂和难以维护。
 */
public interface Discount {
    public int calculate(int money);
}
