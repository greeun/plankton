package com.withwiz.plankton.network.http.message;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * HTTP common message<BR>
 * Created by uni4love on 2010. 5. 8..
 */
public class DefaultHttpMessage extends HttpMessage
		implements Cloneable
{
	/**
	 * body print length limit
	 */
	private static final int BODY_PRINT_LENGTH_LIMIT = 4096;

	/**
	 * body print message length limit
	 */
	private static final String MESSAGE_BODY_LENGTHS_BIGGER = "The body length is over than "
			+ BODY_PRINT_LENGTH_LIMIT + ".";

	/**
	 * name
	 */
	protected String name = null;



	/**
	 * constructor
	 */
	public DefaultHttpMessage()
	{
		headers = new HashMap<String, String>();
		parameters = new HashMap<String, String>();
	}

	/**
	 * constructor
	 *
	 * @param url
	 *            url
	 */
	public DefaultHttpMessage(String url) throws MalformedURLException
	{
		this();
		setUrl(url);
	}

	/**
	 * constructor
	 *
	 * @param name
	 *            name
	 * @param url
	 *            url
	 */
	public DefaultHttpMessage(String name, String url)
			throws MalformedURLException
	{
		this();
		setName(name);
		setUrl(url);
	}

	/**
	 * return name.<BR>
	 *
	 * @return name
	 */
	@Override
	public String getName()
	{
		return name;
	}

	/**
	 * set this message name.<BR>
	 *
	 * @param name
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * return text encoding.<BR>
	 *
	 * @return text encoding
	 */
	@Override
	public String getTextEncoding()
	{

		return textEncoding;
	}

	public void setTextEncoding(String textEncoding)
	{

		this.textEncoding = textEncoding;
	}

	public Map<String, String> getParameters()
	{

		return parameters;
	}

	public void setParameters(Map<String, String> parameters)
	{

		this.parameters = parameters;
	}

	@Override
	public ContentDisposition getContentDisposition()
	{
		return contentDisposition;
	}

	public void setContentDisposition(ContentDisposition contentDisposition)
	{
		this.contentDisposition = contentDisposition;
	}

	public void setUrl(URL url) throws MalformedURLException
	{
		this.url = url;
		setProtocol(this.url.getProtocol().toLowerCase());
	}

	@Override
	public URL getUrl()
	{
		return url;
	}

	/**
	 * url을 설정한다.
	 *
	 * @param sUrl
	 *            url
	 */
	public void setUrl(String sUrl) throws MalformedURLException
	{
		url = new URL(sUrl);
		setUrl(url);
	}

	/**
	 * URL string을 리턴한다.
	 *
	 * @return URL string
	 */
	@Override
	public String getUrlString()
	{
		return url.toString();
	}

	/**
	 * protocol을 설정한다.
	 *
	 * @param protocol
	 */
	public void setProtocol(String protocol) throws MalformedURLException
	{
		if (protocol.equals(PROTOCOL_HTTP))
		{
			isHttps = false;
		}
		else if (protocol.equals(PROTOCOL_HTTPS))
		{
			isHttps = true;
		}
		else
		{
			throw new MalformedURLException(
					"Not supported protocol: " + protocol);
		}
	}

	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer(super.toString());
		if (bodyInputStream != null)
		{
			sb.append("-body:\n");
			String sBodyLength = getHeaderValue("Content-Length");
			int bodyLength = sBodyLength == null ? 0
					: Integer.parseInt(sBodyLength);
			if (bodyLength > BODY_PRINT_LENGTH_LIMIT)
			{
				sb.append(MESSAGE_BODY_LENGTHS_BIGGER);
			}
			else
			{
				sb.append(new String(getBodyByteArray())).append("\n");
			}
		}
		return sb.toString();
	}

	/**
	 * test main
	 *
	 * @param args
	 */
	public static void main(String[] args)
	{
		Iterator<Map.Entry<Integer, String>> iterator = HttpStatusExtended.codeStrings
				.entrySet().iterator();
		Map.Entry e = null;
		while (iterator.hasNext())
		{
			e = iterator.next();
			System.out.println("CODE " + e.getKey() + ": "
					+ HttpStatusExtended.codeStrings.get(e.getKey()));
		}
	}
}
