package com.hy.structure.facade;

import com.hy.structure.bridge.bag.BagAbstraction;
import com.hy.structure.bridge.bag.SmallBag;
import com.hy.structure.bridge.material.Material;
import com.hy.structure.bridge.material.Paper;

/**
 * 采摘服务
 */
public class PickService {

    /**
     * 采摘水果
     */
    public void doPick(){
        //袋子型号
        BagAbstraction pickBag = new SmallBag();

        //袋子材质
        Material material = new Paper();
        pickBag.setMaterial(material);

        //开始采摘
        pickBag.pick();
    }

    public void doOther(){
        System.out.println("其它服务");
    }
}
