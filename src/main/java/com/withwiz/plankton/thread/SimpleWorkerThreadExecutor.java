package com.withwiz.plankton.thread;

/**
 * SimpleWorkThread executor class.<BR>
 * Created by uni4love on 2010. 5. 30..
 */
public class SimpleWorkerThreadExecutor
{
	/**
	 * Runnable instance
	 */
	protected ASimpleWorkerThread	runnable		= null;

	/**
	 * thread timeout
	 */
	protected int					finishTimeout	= -1;

	/**
	 * thread check interval
	 */
	protected int					interval		= -1;

	/**
	 * interval counter
	 */
	protected int					intervalCounter	= 0;

	/**
	 * constructor
	 *
	 * @param runnable
	 *            Runnable object
	 */
	public SimpleWorkerThreadExecutor(ASimpleWorkerThread runnable,
			int finishTimeout, int checkInterval)
	{
		this.runnable = runnable;
		this.finishTimeout = finishTimeout;
		this.interval = checkInterval;
	}

	public void startInSync()
	{
		Thread thread = new Thread(runnable);
		thread.start();
		int currentTime = 0;
		do
		{
			intervalCounter++;
			try
			{
				Thread.sleep(interval);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			if ((currentTime += interval) > finishTimeout)
			{
				break;
			}
		} while (!runnable.isFinished());
	}

	public ASimpleWorkerThread getRunnabler()
	{
		return runnable;
	}

	public void setRunnabler(ASimpleWorkerThread runnabler)
	{
		this.runnable = runnabler;
	}

	public int getFinishTimeout()
	{
		return finishTimeout;
	}

	public void setFinishTimeout(int finishTimeout)
	{
		this.finishTimeout = finishTimeout;
	}

	public int getInterval()
	{
		return interval;
	}

	public void setInterval(int interval)
	{
		this.interval = interval;
	}

	public int getIntervalCounter()
	{
		return intervalCounter;
	}

	public void setIntervalCounter(int intervalCounter)
	{
		this.intervalCounter = intervalCounter;
	}
}
