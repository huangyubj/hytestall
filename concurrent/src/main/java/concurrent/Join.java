package concurrent;

public class Join extends Thread{
    int i;
    Thread previousThread; //上一个线程
    public Join(Thread previousThread,int i){
        this.previousThread=previousThread;
        this.i=i;
    }
    @Override
    public void run() {
        try {
            previousThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("num:"+i);
    }
    public static void main(String[] args) {
        Thread previousThread=Thread.currentThread();
        for(int i=0;i<10;i++){
            Join joinDemo=new Join(previousThread,i);
            joinDemo.start();
            previousThread=joinDemo;
        }
    }

}

