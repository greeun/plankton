package com.withwiz.plankton.network.http.message;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

import java.net.URI;

/**
 * This implements HTTP DELETE method that has HTTP request body.<BR/>
 * Created by uni4love on 2010. 8. 20..
 */
public class HttpDeleteWithBody extends HttpEntityEnclosingRequestBase
{
	/**
	 * constructor
	 */
	public HttpDeleteWithBody()
	{
	}

	/**
	 * constructor
	 *
	 * @param uri
	 *            URI object
	 */
	public HttpDeleteWithBody(URI uri)
	{
		setURI(uri);
	}

	/**
	 * constructor<BR/>
	 * 
	 * @param uri
	 *            URI string
	 */
	public HttpDeleteWithBody(String uri)
	{
		setURI(URI.create(uri));
	}

	/**
	 * return method.<BR/>
	 *
	 * @return method
	 */
	@Override
	public String getMethod()
	{
		return "DELETE";
	}
}
