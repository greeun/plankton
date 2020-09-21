package com.withwiz.plankton.network.http.message;

import java.net.MalformedURLException;

/**
 * HTTP request message.<BR>
 * Created by uni4love on 2010. 5. 8..
 */
public class DefaultHttpRequestMessage extends DefaultHttpMessage implements IHttpRequestMessage
{
	/**
	 * default name
	 */
	protected static final String NAME_DEFAULT = "HTTP_REQUEST_MESSAGE";

	/**
	 * body name
	 */
	protected String bodyName;

	/**
	 * body type
	 */
	protected int bodyType = BODY_TYPE_UNDEFINED;

	/**
	 * attach file path
	 */
	protected String attachFilePath = null;

	/**
	 * connection timeout<BR>
	 * default: 10 seconds
	 */
	private int connectionTimeout = UNDEFINED;

	/**
	 * socket timeout<BR>
	 * default: 10 seconds
	 */
	private int socketTimeout = UNDEFINED;

	/**
	 * network data buffer size<BR>
	 * default: 8k
	 */
	private int networkBufferSize = UNDEFINED;

	/**
	 * constructor
	 */
	public DefaultHttpRequestMessage()
	{
	}

	/**
	 * constructor
	 *
	 * @param url
	 *            request url
	 */
	public DefaultHttpRequestMessage(String url) throws MalformedURLException
	{
		super(url);
	}

	/**
	 * constructor
	 * 
	 * @param name
	 *            name
	 * @param url
	 *            request url
	 * @throws MalformedURLException
	 */
	public DefaultHttpRequestMessage(String name, String url)
			throws MalformedURLException
	{
		super(name, url);
	}

	/**
	 * return attach file path
	 *
	 * @return file path
	 */
	@Override
	public String getAttachFilePath()
	{
		return attachFilePath;
	}

	@Override
	public String getBodyName()
	{
		return bodyName;
	}

	public void setBodyName(String bodyName)
	{
		this.bodyName = bodyName;
	}

	@Override
	public int getBodyType()
	{
		return bodyType;
	}

	public void setBodyType(int bodyType)
	{
		this.bodyType = bodyType;
	}

	/**
	 * return connection timeout.<BR>
	 *
	 * @return connection timeout(milliseconds)
	 */
	@Override
	public int getConnectionTimeout()
	{
		return connectionTimeout;
	}

	/**
	 * set connection timeout<BR>
	 *
	 * @param connectionTimeout
	 *            connection timeout(milliseconds)
	 */
	@Override
	public void setConnectionTimeout(int connectionTimeout)
	{
		this.connectionTimeout = connectionTimeout;
	}

	/**
	 * return socket timeout.<BR>
	 *
	 * @return socket timeout(milliseconds)
	 */
	@Override
	public int getSocketTimeout()
	{
		return socketTimeout;
	}

	/**
	 * set socket timeout.<BR>
	 *
	 * @param socketTimeout
	 *            socket timeout(milliseconds)
	 */
	@Override
	public void setSocketTimeout(int socketTimeout)
	{
		this.socketTimeout = socketTimeout;
	}

	/**
	 * return network buffer size.<BR>
	 *
	 * @return network buffer size(bytes)
	 */
	@Override
	public int getNetworkBufferSize()
	{
		return networkBufferSize;
	}

	@Override
	public void setTrustSsl(boolean isTrustSsl) {

	}

	/**
	 * set network buffer size.<BR>
	 *
	 * @param networkBufferSize
	 *            network buffer size(bytes)
	 */
	@Override
	public void setNetworkBufferSize(int networkBufferSize)
	{
		this.networkBufferSize = networkBufferSize;
	}

	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer(super.toString());
		if (bodyType != BODY_TYPE_UNDEFINED)
			sb.append("-bodyType: ").append(bodyType).append("\n");
		if (attachFilePath != null)
			sb.append("-attachFilePath: ").append(attachFilePath).append("\n");
		return sb.toString();
	}

}
