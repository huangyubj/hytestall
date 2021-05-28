package concurrent;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReadWriteLock;

public class TestSimple {
    public static void main(String[] args) {
        ExecutorService service = new ThreadPoolExecutor(2, 10,60000,TimeUnit.MILLISECONDS,new ArrayBlockingQueue<>(100000));
        for (int i = 0; i < 10000; i++) {
            service.execute(new Runnable() {
                @Override
                public void run() {
                    while (true){
                        try {
                            Thread.sleep(1000);
                            System.out.println(Thread.currentThread().getName() + "执行完成");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }

    }
    private static final int COUNT_BITS = Integer.SIZE - 3;
    private static final int CAPACITY   = (1 << COUNT_BITS) - 1;
    @Test
    public void testtt(){
        Connection connection =
                String a = "123";
        String b = "123";
        System.out.println(a.equals(a));
        System.out.println(a == b);
        ThreadLocal local = new ThreadLocal();

        System.out.println(CAPACITY);
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
        System.out.println(UUID.randomUUID());
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
