package com.hy;

import com.alibaba.dubbo.rpc.service.EchoService;
import com.alibaba.dubbo.rpc.service.GenericService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
    /**
     * 回声测试：扫一遍服务是否都已就绪
     */
    @org.junit.Test
    public void test1(){
        ClassPathXmlApplicationContext app = new ClassPathXmlApplicationContext("classpath:dubbo-test.xml");
        app.start();
        Object ret = null;
        try {//reference代理对象，强制转换为EchoService
            EchoService echoService = (EchoService)app.getBean("infoServiceImpl");
            ret = echoService.$echo("ok");
        } catch (Exception e) {
            ret = "not ready";
            e.printStackTrace();
        }
        System.err.println("ret:"+ret);
    }

    /**
     *泛化调用
     *当前项目，没有对应的接口
     */
    @org.junit.Test
    public void test2(){
        ClassPathXmlApplicationContext app = new ClassPathXmlApplicationContext("classpath:dubbo-test.xml");
        app.start();
        GenericService genericService = (GenericService)app.getBean("unknowServiceImpl");
        Object ret = genericService.$invoke("getDetail",new String[]{"java.lang.String"},new Object[]{"1112"});
        System.out.println("调用结果："+ret.toString());

    }
}
