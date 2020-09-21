package com.withwiz.plankton.network.http.message;

/**
 * HTTP Request message interface.<BR/>
 * Created by uni4love on 2012.07.20..
 */
public interface IHttpRequestMessage extends IHttpMessage
{
	/**
	 * status: undefined
	 */
	int UNDEFINED = -1;

	/**
	 * return attachment file path
	 *
	 * @return file path
	 */
	String getAttachFilePath();

	/**
	 * return body name
	 *
	 * @return name
	 */
	String getBodyName();

	/**
	 * return body type
	 *
	 * @return type
	 */
	int getBodyType();

	/**
	 * set connection timeout.<BR/>
	 * @param connectionTimeout
     */
	void setConnectionTimeout(int connectionTimeout);

	/**
	 * return connection timeout.<BR/>
	 *
	 * @return connection timeout(milliseconds)
	 */
	int getConnectionTimeout();

	/**
	 * set socket timeout.<BR/>
	 */
	void setSocketTimeout(int socketTimeout);

	/**
	 * return socket timeout.<BR/>
	 *
	 * @return socket timeout(milliseconds)
	 */
	int getSocketTimeout();

	/**
	 * set network buffer size.<BR/>
	 */
	void setNetworkBufferSize(int networkBufferSize);

	/**
	 * return network buffer size.<BR/>
	 *
	 * @return network buffer size(bytes)
	 */
	int getNetworkBufferSize();

	/**
	 * set trust ssl.<BR/>
	 * @param isTrustSsl use or not
     */
	void setTrustSsl(boolean isTrustSsl);

	/**
	 * return use or not for trusting ssl.<BR/>
	 * @return
     */
	boolean isTrustSsl();

}
