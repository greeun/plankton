package com.withwiz.plankton.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * This class supports proxy function for InputStream.<BR/>
 * Created by uni4love on 2011. 05. 06..
 */
public class ProxyInputStream extends InputStream
{
	/**
	 * source InputStream
	 */
	private InputStream				sourceInputStream	= null;

	/**
	 * OutputStream
	 */
	private ByteArrayOutputStream	outputStream		= null;

	/**
	 * size
	 */
	private long					size				= 0l;

	/**
	 * constructor
	 *
	 * @param sourceInputStream
	 *            InputStream
	 */
	public ProxyInputStream(InputStream sourceInputStream)
	{
		this.sourceInputStream = sourceInputStream;
		this.outputStream = new ByteArrayOutputStream();
		try
		{
			size = readFully();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public int read() throws IOException
	{
		int value = sourceInputStream.read();
		outputStream.write(value);
		return value;
	}

	@Override
	public void close() throws IOException
	{
		if (outputStream != null)
		{
			outputStream.close();
			outputStream = null;
		}
		super.close();
	}

	/**
	 * read fully from InputStream.
	 *
	 * @return read data length
	 * @throws java.io.IOException
	 */
	public long readFully() throws IOException
	{
		long totalLength = 0L;

		if (sourceInputStream.markSupported())
		{
			sourceInputStream.reset();
		}

		int readLength = 0;
		byte[] buffer = new byte['က'];
		while ((readLength = sourceInputStream.read(buffer)) != -1)
		{
			outputStream.write(buffer, 0, readLength);
			totalLength += readLength;
		}

		return totalLength;
	}

	/**
	 * return saved byte[]<BR/>
	 *
	 * @return byte[]
	 */
	public byte[] getData()
	{
		return outputStream.toByteArray();
	}

	/**
	 * return ByteArrayInputStream for saved byte[]<BR/>
	 *
	 * @return ByteArrayInputStream
	 */
	public ByteArrayInputStream getNewInputStream()
	{
		return new ByteArrayInputStream(outputStream.toByteArray());
	}

	/**
	 * return size.<BR/>
	 * 
	 * @return size
	 */
	public long getSize()
	{
		return size;
	}
}
