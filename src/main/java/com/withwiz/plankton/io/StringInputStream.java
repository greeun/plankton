package com.withwiz.plankton.io;

import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;

/**
 * StringInputStream class<BR>
 * Created by uni4love on 2010. 5. 9..
 */
public class StringInputStream extends ByteArrayInputStream
{
	/**
	 * string source
	 */
	protected String	source;

	/**
	 * constructor
	 * 
	 * @param source
	 *            string source
	 */
	public StringInputStream(String source)
	{
		this(source, Charset.defaultCharset());
	}

	/**
	 * constructor
	 * 
	 * @param source
	 *            string source
	 * @param charsetName
	 *            charset name
	 */
	public StringInputStream(String source, String charsetName)
	{
        this(source, Charset.forName(charsetName));
	}

	/**
	 * constructor
	 * 
	 * @param source
	 *            string source
	 * @param charset
	 *            Charset
	 */
	public StringInputStream(String source, Charset charset)
	{
		super(source.getBytes(charset));
		this.source = source;
	}
}
