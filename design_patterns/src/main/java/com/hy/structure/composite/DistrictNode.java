package com.hy.structure.composite;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class DistrictNode extends Node{
    private List<Node> children = new ArrayList<Node>();

    public DistrictNode(String name) {
        super(name);
    }

    @Override
    public List<Node> getChildren() {
        return children;
    }

    public void addChild(Node node){
        children.add(node);
    }

    public void delChild(int i){
        children.remove(i);
    }


}
