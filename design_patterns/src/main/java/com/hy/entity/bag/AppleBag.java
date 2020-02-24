package com.hy.entity.bag;


import com.hy.entity.Bag;

/**
 * 苹果包装
 */
public class AppleBag implements Bag {

    public void pack() {
        System.out.print("--苹果使用纸箱包装");
    }
}
