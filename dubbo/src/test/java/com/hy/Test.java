package com.hy;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.alibaba.dubbo.rpc.service.EchoService;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.hy.api.service.CardService;
import com.hy.spi.filter.Filter;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class Test {

    @org.junit.Test
    public void testAdaptive(){
        ExtensionLoader<CardService> extensionLoader = ExtensionLoader.getExtensionLoader(CardService.class);
        extensionLoader.getDefaultExtension().say();
        CardService cardService = extensionLoader.getAdaptiveExtension();
//        cardService.say();
        //Adpative 定义的value为参数名称，对应参数值为 SPI配置文件对应的名称
        //注解方法，构建一个String的代码串， Dubbo通过  javassist 或 jdk 编译生成class，
        URL url = URL.valueOf("test://localhost/test?key=b");
        System.out.println(url.getParameter("key","c"));;
        cardService.say("hello adaptive", url);
    }

    @org.junit.Test
    public void testActivate(){
        ExtensionLoader<Filter> extensionLoader = ExtensionLoader.getExtensionLoader(Filter.class);
        extensionLoader.getExtension("a").invoke();
        URL url = URL.valueOf("test://localhost/test");
        url = url.addParameter("asd", "66666");
        url = url.addParameter("diyFilter", "c,d,-a");
        //key 是从URL中的参数名称来获取值进行自定义过滤。名称按","分割，"-名称" 表示不加载
        //gourp用来过滤分组
        //URL中的参数是用来过滤vlue的，如果Activate配置了value，需要满足URL中的参数key
        List<Filter> list = extensionLoader.getActivateExtension(url, "diyFilter", "B");
        System.out.println("----"+list.size());
        for (Filter ft : list) {
            ft.invoke();
        }
    }
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
