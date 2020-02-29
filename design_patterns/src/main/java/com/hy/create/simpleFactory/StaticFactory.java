package com.hy.create.simpleFactory;

import com.hy.entity.Fruit;
import com.hy.entity.frute.Apple;
import com.hy.entity.frute.Banana;
import com.hy.entity.frute.Orange;

/**
 * 简单工厂模式 --- 静态工厂模式
 */
public class StaticFactory {
    public static final int TYPE_APPLE = 1;//苹果
    public static final int TYPE_ORANGE = 2;//桔子
    public static final int TYPE_BANANA = 3;//香蕉

    public static Fruit getFruit(int type){
        if(TYPE_APPLE == type){
            return new Apple();
        } else if(TYPE_ORANGE == type){
            return new Orange(8, "hy");
        } else if(TYPE_BANANA == type){
            return new Banana(3);
        }
        return null;
    }

    /**
     * 多方法工厂
     * @return
     */
    public static Fruit getFruitApple(){
        return new Apple();
    }

    public static Fruit getFruitOrange(){
        return new Orange(8, "hy");
    }

    public static Fruit getFruitBanana(){
        return new Banana(3);
    }

}
