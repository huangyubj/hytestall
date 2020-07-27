package concurrent.singlegc;

/**
 * -XX:+PrintGCDetails -Xms20M -Xmx20M
 */
public class SingleGCTest {
    public static void main(String[] args) throws InterruptedException {
        Info info = new Info();
        info.setName("hy");
        SingleGC.getInstance().setInfo(info);
        int i = 0;
        while (true){
            new Obj();
            i++;
            if (i == 10)
                SingleGC.getInstance().setInfo(null);
            Thread.sleep(500);
            if(i == 20)
//                SingleGC.getInstance().setInfo(null);
            //            不清除静态引用，单例不会被回收，，当静态实例被清空，对象才会被回收
                SingleGC.destroy();
        }
    }

    static class Obj{
        private byte[] bytes = new byte[3*1024*1024];
    }
}
