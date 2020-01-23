package concurrent.delayqueue;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayedTest<T> implements Delayed{
	
	private long initialDelay;
	
	private T obj;
	
	public DelayedTest(long initialDelay, T obj) {
		super();
		this.initialDelay = TimeUnit.NANOSECONDS.convert(initialDelay, TimeUnit.MILLISECONDS) + System.nanoTime();
		this.obj = obj;
	}

	public long getInitialDelay() {
		return initialDelay;
	}

	public T getObj() {
		return obj;
	}

	@Override
	public int compareTo(Delayed o) {
		long time = getDelay(TimeUnit.NANOSECONDS) - o.getDelay(TimeUnit.NANOSECONDS);
		return time > 0 ? 1 : (time == 0 ? 0 : -1);
	}

	@Override
	public long getDelay(TimeUnit unit) {
		return unit.convert(this.initialDelay - System.nanoTime(), TimeUnit.NANOSECONDS);
	}

}
