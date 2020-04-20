package concurrent;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

public class Test {
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
}
