package concurrent.proxy;

public class SayHello implements HelloItf{
    @Override
    public void hello(){
        System.out.println("hello");
    }
}
