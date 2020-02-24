package com.hy.structure.bridge.bag;

import com.hy.structure.bridge.material.Material;

/**
 * 采摘容器
 */
public abstract class BagAbstraction {
    protected Material material;
//    protected Material material;

    public void setMaterial(Material material){
        this.material = material;
    }

    //采摘
    public abstract void pick();

}
