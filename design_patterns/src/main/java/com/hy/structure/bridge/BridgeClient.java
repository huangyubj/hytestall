package com.hy.structure.bridge;

import com.hy.structure.bridge.bag.BagAbstraction;
import com.hy.structure.bridge.bag.SmallBag;
import com.hy.structure.bridge.material.Material;
import com.hy.structure.bridge.material.Paper;

/**
 * 园丁采摘水果
 * 可以用多种纸袋包装
 * 可以用多种箱子装水果，桥接实现N种组合
 * 桥接模式(bridge)
 * 通过提供抽象化和实现化之间的桥接结构，解耦
 * 构造注入，组合N种结果（桥A通向A1 A2 A3多地，桥B通向B1 B2 B3 多地，A引入B接口/抽象类，A就能与B组出从An-->Bn的N种组合）
 *
 */
public class BridgeClient {

    public static void main(String[] args) {

        //袋子型号
        BagAbstraction bag = new SmallBag();

        //袋子材质
        Material material = new Paper();
        bag.setMaterial(material);

        //开始采摘
        bag.pick();
    }
}
