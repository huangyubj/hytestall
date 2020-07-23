package concurrent.singlegc;

public class SingleGC {

    private byte[] bytes;

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    private Info info;

    private  SingleGC(){
        bytes = new byte[2*1024*1024];
    }

    public static SingleGC getInstance(){
        return Instance.instance;
    }

    private static class Instance {
         static SingleGC instance = new SingleGC();
    }

    public static void destroy(){
        getInstance().info = null;
        Instance.instance = null;
        System.out.println("实例变空");
    }
}
