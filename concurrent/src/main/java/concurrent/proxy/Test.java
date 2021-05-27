package concurrent.proxy;

import java.util.HashMap;
import java.util.Map;

/**
 * JDK 代理的对象必须是一个接口对象，否则会报错的
 * CGLIB methodProxy.invokeSuper
 */
public class Test {
    public static void main(String[] args) {
        HelloItf hello = new SayHello();
        JDKProxy proxy = new JDKProxy(hello);
        HelloItf proxyHello = proxy.getProxy();
        proxyHello.hello();

        SayHello helloImpl = new CglibProxy().getProxy(SayHello.class);
        helloImpl.hello();


//        HelloItf helloItf = new CglibProxy2().getProxy(HelloItf.class);
//        helloItf.hello();

    }

    @org.junit.Test
    public void test(){
        Map map = new HashMap();
        for (int i = 0; i < 40; i++) {
            map.put("testMap" + i, i);
        }
    }

    @org.junit.Test
    public void atest(){
        int a = 1;
        int b = 1;
        b = a << 1;
        System.out.println(a);
        System.out.println(b);
    }
}
