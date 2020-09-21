package com.withwiz.plankton.network.http.message;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * HTTP message interface.<BR>
 * Created by uni4love on 2010. 5. 4..
 */
public interface IHttpMessage
{
	/**
	 * http method: get
	 */
	int METHOD_GET = 1;

	/**
	 * http method: post
	 */
	int METHOD_POST = 2;

	/**
	 * http method: put
	 */
	int METHOD_PUT = 3;

	/**
	 * http method: delete
	 */
	int METHOD_DELETE = 4;

	/**
	 * add a header parameter.<BR>
	 *
	 * @param key
	 *            key
	 * @param value
	 *            value
	 */
	void addHeaderParameter(String key, String value);

	/**
	 * add header parameters.<BR>
	 *
	 * @param headerParameters
	 *            header parameters
	 */
	void addHeaderParameters(Map<String, String> headerParameters);

	/**
	 * return a header parameter from header values.<BR>
	 *
	 * @param key
	 *            key
	 * @return value
	 */
	String getHeaderValue(String key);

	/**
	 * return names key String[] of header parameters.<BR>
	 *
	 * @return key String[]
	 */
	String[] getHeaderNames();

	/**
	 * return header parameters size.<BR>
	 *
	 * @return size
	 */
	int getHeaderSize();

	/**
	 * add a parameter.<BR>
	 *
	 * @param key
	 *            key
	 * @param value
	 *            value
	 */
	void addParameter(String key, String value);

	/**
	 * add parameters.<BR>
	 * 
	 * @param parameters
	 *            parameter list
	 */
	void addParameters(Map<String, String> parameters);

	/**
	 * return a parameter value.<BR>
	 *
	 * @param key
	 *            key
	 * @return value
	 */
	String getParameterValue(String key);

	/**
	 * return names key String[] fo parameters.<BR>
	 *
	 * @return key String[]
	 */
	String[] getParameterNames();

	/**
	 * return parameter size.<BR>
	 *
	 * @return size
	 */
	int getParameterSize();

	/**
	 * return InputStream for HTTP body.<BR>
	 *
	 * @return body InputStream
	 */
	InputStream getBodyInputStream();

	/**
	 * set InputStream for HTTP body.<BR>
	 *
	 * @param inputStream
	 *            body InputStream
	 */
	void setBodyInputStream(InputStream inputStream);

	/**
	 * return use Https or not.<BR>
	 *
	 * @return use or not
	 */
	boolean isHttps();

	/**
	 * set use Https or not.<BR>
	 *
	 * @param isHttps
	 *            use or not
	 */
	void setHttps(boolean isHttps);

	/**
	 * return Http method type.<BR>
	 *
	 * @return method type
	 */
	int getMethod();

	/**
	 * set Http method type.<BR>
	 */
	void setMethod(int method);

	/**
	 * return Http status code.<BR>
	 *
	 * @return status code
	 */
	int getStatusCode();

	/**
	 * set Http status code.<BR>
	 *
	 * @param statusCode
	 *            status code
	 */
	void setStatusCode(int statusCode);

	/**
	 * return text encoding type.<BR>
	 *
	 * @return encoding
	 */
	String getTextEncoding();

	/**
	 * set text encoding type.<BR>
	 *
	 * @param encoding
	 *            encoding type
	 */
	void setTextEncoding(String encoding);

	/**
	 * return URL.<BR>
	 *
	 * @return URL object
	 */
	URL getUrl();

	/**
	 * set URL.<BR>
	 *
	 * @param url
	 *            URL object
	 */
	void setUrl(URL url) throws MalformedURLException;

	/**
	 * return service url text.<BR>
	 *
	 * @return service url
	 */
	String getUrlString();

	/**
	 * return body length<BR>
	 *
	 * @return body length
	 */
	long getBodyLength();

	/**
	 * return use attachment file or not.<BR>
	 * 
	 * @return use or not
	 */
	boolean isExistAttachment();

	/**
	 * return attachment file name
	 * 
	 * @return attachment file name
	 */
	String getAttachmentFilename();

	/**
	 * return body byte[]<BR>
	 * 
	 * @return byte[]
	 */
	byte[] getBodyByteArray();

	/**
	 * return Content-Disposition object.<BR>
	 * 
	 * @return ContextDisposition
	 */
	ContentDisposition getContentDisposition();

	/**
	 * set object for "Content-Disposition"<BR>
	 * 
	 * @param contentDisposition
	 *            ContentDisposition object for "Content-Disposition"
	 */
	void setContentDisposition(ContentDisposition contentDisposition);

	/**
	 * return use or not for SSL trusting.<BR>
	 *
	 * @return use or not
	 */
	boolean isTrustSsl();

	/**
	 * set use or not for SSL trusting.<BR>
	 *
	 * @param isSslTrust
	 *            use or not
	 */
	void setTrustSSl(boolean isSslTrust);
}
