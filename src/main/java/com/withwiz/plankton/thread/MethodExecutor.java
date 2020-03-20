package com.withwiz.plankton.thread;

import java.util.concurrent.*;

/**
 * Method executor class in thread.<BR>
 * Created by uni4love on 2014. 7. 3..
 */
public class MethodExecutor
{
	/**
	 * thread pool
	 */
	private static final ExecutorService THREAD_POOL = Executors
			.newCachedThreadPool();

	public static <T> T timedCall(Callable<T> callable, long timeout,
			TimeUnit timeUnit) throws InterruptedException, ExecutionException,
					TimeoutException
	{
		FutureTask<T> task = new FutureTask<T>(callable);
		THREAD_POOL.execute(task);
		return task.get(timeout, timeUnit);
	}
}
