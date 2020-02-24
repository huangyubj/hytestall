package com.hy.structure.facade;

/**
 * 客户服务一条龙
 * 外观模式
 * 当访问流程比较复杂时，将流程封装成一个接口，提供给外部使用
 * 为子系统中的一组接口提供一个一致的界面，外观模式定义了一个高层接口，这个接口使得这一子系统更加容易使用。
 */
public class OrderFacade {
    private PickService pickService;
    private PackService packService;
    private SendService sendService;


    public OrderFacade(){
        pickService = new PickService();
        packService = new PackService();
        sendService = new SendService();
    }

    /**
     * 客户订单处理类
     */
    public void doOrder(){
        //采摘
        System.out.println("--------------");
        pickService.doPick();

        //包装
        System.out.println("--------------");
        packService.doPack();
        //快递
        System.out.println("--------------");
        sendService.doSend();

    }



}
