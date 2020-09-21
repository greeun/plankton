package com.withwiz.plankton.network.http.message;

import com.withwiz.plankton.io.ProxyInputStream;
import com.withwiz.plankton.network.INetworkStatus;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * IHttpMessage interface implements.<BR/>
 * Created by uni4love on 2010.05.08..
 */
abstract public class HttpMessage extends HttpStatusExtended
		implements IHttpMessage
{
	/**
	 * body type: undefined
	 */
	public static final int BODY_TYPE_UNDEFINED = -1;

	/**
	 * body type: string
	 */
	public static final int BODY_TYPE_STRING = 1;

	/**
	 * body type: byte[]
	 */
	public static final int BODY_TYPE_BYTE_ARRAY = 2;

	/**
	 * multipart
	 */
	public static final int BODY_TYPE_MULTIPART = 3;

	/**
	 * protocol: http
	 */
	protected static final String PROTOCOL_HTTP = "http";

	/**
	 * protocol: https
	 */
    protected static final String PROTOCOL_HTTPS = "https";

	/**
	 * protocol divider
	 */
    protected static final String PROTOCOL_DIVIDER = "://";

	/**
	 * code-strings repository
	 */
	private static Map<Integer, String> methodStrings = null;

	/**
	 * initializing status code strings.
	 */
	static
	{
		methodStrings = new HashMap<Integer, String>();
		Field[] fields = DefaultHttpMessage.class.getFields();
		for (Field field : fields)
		{
			try
			{
				if (field.getName().startsWith("METHOD_"))
				{
					addMethodString(field.getInt(DefaultHttpMessage.class),
							field.getName().replace("METHOD_", ""));
				}
			}
			catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * URL
	 */
	protected URL url = null;

	/**
	 * http method
	 */
	protected int method = METHOD_GET;

	/**
	 * content-type
	 */
	protected String contentType = null;

	/**
	 * status code
	 */
	protected int statusCode = -1;

	/**
	 * header parameter list
	 */
	protected Map<String, String> headers = null;

	/**
	 * encoding
	 */
	protected String textEncoding = "UTF-8";

	/**
	 * body parameter list
	 */
	protected Map<String, String> parameters = null;

	/**
	 * "Content-Disposition" header parameter object
	 */
	protected ContentDisposition contentDisposition = null;

	/**
	 * body InputStream
	 */
	protected InputStream bodyInputStream = null;

    /**
     * using HTTPS
     */
    protected boolean isHttps = false;

    /**
     * Non trust
     */
    protected boolean isTrustSsl = true;

    /**
     * return use state https protocol
     *
     * @return sdtate
     */
    @Override
    public boolean isHttps()
    {
        return isHttps;
    }

    /**
     * set use or not https protocol
     *
     * @param https
     *            use or not
     */
    @Override
    public void setHttps(boolean https)
    {

        isHttps = https;
    }

	/**
	 * return string text related with HTTP method code.
	 *
	 * @param code
	 *            method code
	 * @return method string
	 */
	public static String getMethodString(int code)
	{
		return methodStrings.get(code);
	}

	/**
	 * return HTTP method code Set instance.<BR/>
	 *
	 * @return Set
	 */
	public static Set getMethodSet()
	{
		return methodStrings.keySet();
	}

	/**
	 * add a string for code.<BR/>
	 * 
	 * @param code
	 *            code
	 * @param string
	 *            string for code
	 */
	public static void addMethodString(int code, String string)
	{
		methodStrings.put(code, string);
	}

    /**
     * add a header parameter.<BR/>
     *
     * @param key
     *            key
     * @param value
     *            value
     */
    @Override
    public void addHeaderParameter(String key, String value)
    {
        headers.put(key, value);
    }

    /**
     * add header parameters.<BR/>
     * @param parameters header parameters
     */
    @Override
    public void addHeaderParameters(Map<String, String> parameters)
    {
        for (Map.Entry<String,String> entry : parameters.entrySet())
        {
            headers.put(entry.getKey(), entry.getValue());
        }
    }

    /**
     * return value related with key.<BR/>
     *
     * @param key
     *            key
     * @return value
     */
    @Override
    public String getHeaderValue(String key)
    {
        return headers.get(key);
    }

    /**
     * return key list array from header parameter list.<BR/>
     *
     * @return key String[]
     */
    @Override
    public String[] getHeaderNames()
    {
        if (headers != null)
        {
            return headers.keySet().toArray(new String[headers.size()]);
        }
        return null;
    }

    /**
     * return header parameter size.<BR/>
     *
     * @return size
     */
    @Override
    public int getHeaderSize()
    {
        return headers.size();
    }

    /**
     * add a parameter.<BR/>
     *
     * @param key
     *            key
     * @param value
     *            value
     */
    @Override
    public void addParameter(String key, String value)
    {
        parameters.put(key, value);
    }

    /**
     * add parameters.<BR/>
     *
     * @param parameters parameters
     */
    @Override
    public void addParameters(Map<String, String> parameters)
    {
        for (Map.Entry<String,String> entry : parameters.entrySet())
        {
            headers.put(entry.getKey(), entry.getValue());
        }
    }

    /**
     * return value related with key.<BR/>
     *
     * @param key
     *            key
     * @return value
     */
    @Override
    public String getParameterValue(String key)
    {
        return parameters.get(key);
    }

    /**
     * return key list array from parameter list.<BR/>
     *
     * @return key String[]
     */
    @Override
    public String[] getParameterNames()
    {
        if (parameters != null)
        {
            return parameters.keySet().toArray(new String[parameters.size()]);
        }
        return null;
    }

    /**
     * return parameter size.<BR/>
     *
     * @return size
     */
    @Override
    public int getParameterSize()
    {
        return parameters != null ? parameters.size() : 0;
    }

    /**
     * return InputStream for body.<BR/>
     *
     * @return InputStream
     */
    @Override
    public InputStream getBodyInputStream()
    {
        return bodyInputStream;
    }

    /**
     * set InputStream for body.<BR/>
     *
     * @param inputStream
     *            InputStream for body
     */
    @Override
    public void setBodyInputStream(InputStream inputStream)
    {
        this.bodyInputStream = inputStream;
    }

    /**
     * return body contents as byte[].<BR/>
     *
     * @return body content byte[]
     */
    public byte[] getBodyByteArray()
    {
        ProxyInputStream pis = null;
        try
        {
            pis = new ProxyInputStream(this.bodyInputStream);
            if (this.bodyInputStream != null)
                this.bodyInputStream.close();

            this.bodyInputStream = pis.getNewInputStream();
            return pis.getData();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            try
            {
                if (pis != null)
                {
                    pis.close();
                    pis = null;
                }
            }
            catch (IOException e2)
            {
                e2.printStackTrace();
            }
        }
        return null;
    }
    /**
     * return using HTTP method.<BR/>
     *
     * @return HTTP method
     */
    @Override
    public int getMethod()
    {

        return method;
    }

    /**
     * set HTTP method.<BR/>
     *
     * @param method
     *            HTTP method
     */
    @Override
    public void setMethod(int method)
    {
        this.method = method;
    }

    /**
     * return status code.<BR/>
     *
     * @return status code
     */
    @Override
    public int getStatusCode()
    {
        return statusCode;
    }

    /**
     * set status code.<BR/>
     *
     * @param statusCode
     *            status code
     */
    @Override
    public void setStatusCode(int statusCode)
    {
        this.statusCode = statusCode;
    }

    /**
     * return header list.<BR/>
     *
     * @return headers map
     */
    public Map<String, String> getHeaders()
    {
        return headers;
    }

    /**
     * set header list.<BR/>
     *
     * @param headers
     *            headers map
     */
    public void setHeaders(Map<String, String> headers)
    {
        this.headers = headers;
    }

    /**
     * return use or not for attaching file.<BR/>
     *
     * @return use or not
     */
    @Override
    public boolean isExistAttachment()
    {
        return (contentDisposition != null && contentDisposition.getType()
                .equalsIgnoreCase(ContentDisposition.TYPE_ATTACHMENT)) ? true
                : false;
    }

    /**
     * return attachement file name.<BR/>
     *
     * @return attach file name
     */
    @Override
    public String getAttachmentFilename()
    {
        return contentDisposition
                .getParameterValue(ContentDisposition.PARM_FILENAME);
    }

    /**
     * set attachment file name.<BR/>
     *
     * @param attachmentFileName
     *
     */
    public void setAttachmentFileName(String attachmentFileName)
    {
        contentDisposition.addParameter(ContentDisposition.PARM_FILENAME,
                attachmentFileName);
    }

    /**
     * return body length<BR/>
     *
     * @return body length
     */
    @Override
    public long getBodyLength()
    {
        return Long.parseLong(getHeaderValue("Content-Length"));
    }

    /**
     * return use or not for SSL trusting.<BR/>
     *
     * @return use or not
     */
    @Override
    public boolean isTrustSsl()
    {
        return isTrustSsl;
    }

    /**
     * set use or not for SSL trusting.<BR/>
     *
     * @param isTrustSsl
     *            use or not
     */
    @Override
    public void setTrustSSl(boolean isTrustSsl)
    {
        this.isTrustSsl = isTrustSsl;
    }

    @Override
    public String toString()
    {
        StringBuffer sb = new StringBuffer("=== ").append(getName() != null ? getName() : this.getClass().getName())
                .append("\n");
        if (statusCode != INetworkStatus.SC_UNDEFINED)
            sb.append("-status code: ").append(statusCode).append("\n");
        sb.append("-url: ").append(getUrlString()).append("\n");
        sb.append("-method: ")
                .append(DefaultHttpMessage.getMethodString(method))
                .append("\n");
        sb.append("-headers: ").append(headers).append("\n");
        if (contentDisposition != null)
        {
            sb.append("--").append(contentDisposition).append("\n");
        }
        if (parameters.size() > 0)
            sb.append("-parameters: ").append(parameters).append("\n");
        if (textEncoding != null)
            sb.append("-text-encoding: ").append(textEncoding).append("\n");
        return sb.toString();
    }

    /**
     * user define message name.<BR/>
     * @return name
     */
    abstract public String getName();
}
