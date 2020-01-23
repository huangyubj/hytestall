package concurrent.delayqueue;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.DelayQueue;

public class TestDelayedQueue {
	private static DelayQueue<DelayedTest<ExamVO>> queue = new DelayQueue<DelayedTest<ExamVO>>();

	public static void main(String[] args) throws InterruptedException {
		PutThread put = new PutThread();
		TakeThread take = new TakeThread();
		put.start();
		take.start();
		Thread.sleep(10000);
		put.interrupt();
		Thread.sleep(10000);
		take.interrupt();
	}

	static class PutThread extends Thread{
		@Override
		public void run() {
			try {
				while(!Thread.currentThread().isInterrupted()){
					Random random = new Random();
					long delaytime = random.nextInt(10) * 1000l;
					ExamVO e = new ExamVO("我是延迟" + delaytime, delaytime+"", delaytime);
					System.out.println("put 进Queue：" + e.toString());
					queue.put(new DelayedTest<ExamVO>(delaytime, e));
					Thread.sleep(1000l);

				}
			} catch (InterruptedException e1) {
				System.err.println("--PutThread--中断了");
			}
		}
	}

	static class TakeThread extends Thread{
		@Override
		public void run() {
			try {
				while(!Thread.currentThread().isInterrupted()){
					System.out.println("take from queue:"+queue.take().getObj().toString());
					System.err.println(new Date().toString());
				}
			} catch (InterruptedException e1) {
				System.err.println("--TakeThread-中断了");
			}
		}
	}
}
