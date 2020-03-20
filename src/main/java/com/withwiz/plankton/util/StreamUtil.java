package com.withwiz.plankton.util;

import com.withwiz.plankton.io.StringInputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Stream utility class.<BR>
 * Created by uni4love on 2009. 9. 16..
 */
public class StreamUtil
{
	/**
	 * default buffer size
	 */
	private static final int	DEFAULT_BUFFER_SIZE	= 1024;

	/**
	 * InputStream to byte[]
	 *
	 * @param is
	 *            InputStream
	 * @param bufferSize
	 *            read buffer size
	 * @return byte[]
	 */
	public static byte[] toByteArray(InputStream is, int bufferSize)
			throws IOException
	{
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		copy(is, output, bufferSize);
		return output.toByteArray();
	}

	/**
	 * InputStream to byte[]
	 *
	 * @param is
	 *            InputStream
	 * @return byte[]
	 */
	public static byte[] toByteArray(InputStream is) throws IOException
	{
		return toByteArray(is, DEFAULT_BUFFER_SIZE);
	}

	/**
	 * copy inputStream to outputStream
	 *
	 * @param inputStream
	 *            InputStream
	 * @param outputStream
	 *            OutputStream
	 * @param bufferSize
	 *            read buffer size
	 * @return 복사한 크기
	 */
	public static long copy(InputStream inputStream, OutputStream outputStream,
			int bufferSize) throws IOException
	{
		byte[] buffer = new byte[bufferSize];
		return copy(inputStream, outputStream, buffer);
	}

	/**
	 * copy inputStream to outputStream
	 *
	 * @param inputStream
	 *            InputStream
	 * @param outputStream
	 *            OutputStream
	 * @param buffer
	 *            buffer
	 * @return read length
	 * @throws java.io.IOException
	 */
	public static long copy(InputStream inputStream, OutputStream outputStream,
			byte[] buffer) throws IOException
	{
		long totalLength = 0L;
		int readLength = 0;

		while ((readLength = inputStream.read(buffer)) != -1)
		{
			outputStream.write(buffer, 0, readLength);
			totalLength += readLength;
		}
		return totalLength;
	}

	/**
	 * copy inputStream to outputStream
	 *
	 * @param inputStream
	 *            InputStream
	 * @param outputStream
	 *            OutputStream
	 * @return read length
	 * @throws java.io.IOException
	 */
	public static long copy(InputStream inputStream, OutputStream outputStream)
			throws IOException
	{
		return copy(inputStream, outputStream, new byte['က']);
	}

	/**
	 * test main
	 *
	 * @param args
	 */
	public static void main(String[] args)
	{
		String str = "o12heo1ihdo12lhdn;l1irh   2o;gfib.wkv na;wlbgnf;h3gf' o;2fjn2[";
		StringInputStream is = new StringInputStream(str);
		System.out.println("source string:\n" + str);
		try
		{
			System.out.println("target byte[]:\n"
					+ new String(StreamUtil.toByteArray(is)));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
