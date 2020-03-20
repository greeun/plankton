package com.withwiz.plankton.thread;

/**
 * Simple WorkerThread class<BR>
 * Created by uni4love on 2010. 5. 29..
 */
abstract public class ASimpleWorkerThread<T> implements Runnable
{
	/**
	 * thread completion status
	 */
	protected boolean isFinished = false;

	/**
	 * return completion status.<BR>
	 *
	 * @return status
	 */
	public boolean isFinished()
	{
		return isFinished;
	}

	/**
	 * set completion status.<BR>
	 * 
	 * @param isFinished completion status
	 */
	public void setFinished(boolean isFinished)
	{
		this.isFinished = isFinished;
	}

	/**
	 * return result object.<BR>
	 * 
	 * @return result object
	 *
	 */
	abstract public T getResult();
}
