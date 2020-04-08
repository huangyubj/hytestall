package com.hy;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.alibaba.dubbo.rpc.service.EchoService;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.hy.spi.Robot;
import com.hy.spi.filter.Filter;
import com.sun.tools.javac.util.ServiceLoader;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Iterator;
import java.util.List;

public class Test {

    @org.junit.Test
    public void sayHelloSPI() throws Exception {
        ServiceLoader<Robot> serviceLoader = ServiceLoader.load(Robot.class);
        System.out.println("Java SPI");
        Iterator iterator = serviceLoader.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
//            serviceLoader.forEach(Robot::sayHello);
    }

    @org.junit.Test
    public void sayHelloDubboSPI() throws Exception {
        //JDK 的SPI 默认加载 META-INF/services/ 下对应的文件
        //Dubbo的SPI 会加载 META-INF/services/、META-INF/dubbo/、META-INF/dubbo/internal/,需要尽心 @SPI  注解接口
        ExtensionLoader<Robot> extensionLoader =
                ExtensionLoader.getExtensionLoader(Robot.class);
        Robot optimusPrime = extensionLoader.getExtension("optimusPrime");
        optimusPrime.sayHello();
        Robot bumblebee = extensionLoader.getExtension("bumblebee");
        bumblebee.sayHello();
    }

    @org.junit.Test
    public void testActivate() throws Exception {
        //获取接口加载器
        ExtensionLoader<Filter> extensionLoader =
                ExtensionLoader.getExtensionLoader(Filter.class);
//        Filter a = extensionLoader.getExtension("A");
//        a.invoke();
        URL url = URL.valueOf("test://localhost/test");
        List<Filter> filters = extensionLoader.getActivateExtension(url,"", "E");
        System.out.println("filter len="+filters.size());
        for(Filter filter : filters){
            filter.invoke();
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
