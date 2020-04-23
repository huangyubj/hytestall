package concurrent;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

public class TestSimple {
    private static ConcurrentHashMap<String, Integer> count = new ConcurrentHashMap<String, Integer>();
    private static String key1 = "test1";
    private static String key2 = "test2";
    private static CountDownLatch countDownLatch = new CountDownLatch(1);
    private static int num = 20;

    public static void main(String[] args) {
        for (int i = 0; i < num; i++) {
            new Thread(new Test1()).start();
            new Thread(new Test2()).start();
        }
        countDownLatch.countDown();
    }
    /**
     * ConcurrentHashMap 的put是原子操作，线程中的run代码不是原子操作，
     * 必然会出现线程安全问题
     */
    private static class Test1 implements Runnable{

        public void run() {
            try {
                countDownLatch.await();
                for (int i = 0; i < 10; i++) {
                    Integer ct = count.get(key1);
                    if(ct != null){
                        count.put(key1, ct + 1);
                    }else{
                        count.put(key1, 1);
                    }
                    System.out.println("test1---" + count.get(key1));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static class Test2 implements Runnable{

        public void run() {
            try {
                countDownLatch.await();
                for (int i = 0; i < 10; i++) {
                    synchronized (count){
                        Integer ct = count.get(key2);
                        if(ct != null){
                            count.put(key2, ct + 1);
                        }else{
                            count.put(key2, 1);
                        }
                        System.out.println("test2-----" + count.get(key2));
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void test1(){
        float a = 0.125f;
        double b = 0.125d;
        System.out.println((a - b) == 0.0);
    }

    @Test
    public void test2(){
        double c = 0.8;
        double d = 0.7;
        double e = 0.6;
        System.out.println(c-d == d-e);
        System.out.println(c-d == 0.1);
    }
    @Test
    public void test3(){
        //Infinity 无穷
        System.out.println(1.0 / 0);
    }
    @Test
    public void test4(){
        //NAN
        System.out.println(0.0 / 0.0);
    }
    @Test
    public void test5(){
        A a = new A();
//        a.f(null);
        //优先基本类型，都是则占位大小少的优先
        //其次基本类型对象，都是则占位大小少的优先
        a.g(1);
//        a.g(new Integer(2));
    }
    class A {
        void f(String s){
            System.out.println("A str---"+s);
        }
        void f(Integer i){
            System.out.println("A in---"+i);
        }
//        void g(int i){
//            System.out.println("A int---"+i);
//        }

        void g(double dou){
            System.out.println("A dou---"+dou);
        }

        void g(float flo){
            System.out.println("A floa---"+flo);
        }

        void g(Integer in){
            System.out.println("A i---"+in);
        }
        void g(Double d){
            System.out.println("A d---"+d);
        }
        void g(Float f){
            System.out.println("A f---"+f);
        }
    }

    <String, T, Alibaba> String get(String string, T t, Alibaba alibaba) { return string; }
}
