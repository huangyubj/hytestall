package com.hy.action.observer;


public class CustomerObserver implements Observer {

    private String name;

    public CustomerObserver(String name){
        this.name = name;
    }

    public void update() {
        System.out.println(name + "购买青芒");
    }
}
