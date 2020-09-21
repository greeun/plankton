package com.withwiz.plankton.network.http.client;

import com.withwiz.plankton.network.http.message.*;
import com.withwiz.plankton.network.http.message.HttpMessage;
import com.withwiz.plankton.network.http.ssl.CertPassSSLSocketFactory;
import com.withwiz.plankton.io.ProxyInputStream;
import com.withwiz.plankton.io.StringInputStream;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.BasicHttpProcessor;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Vector;

/**
 * This implements IHttpProcessor interface with
 * org.apache.http.client.HttpClient.<BR>
 * Created by uni4love on 2010. 5. 8.
 */
public class DefaultHttpProcessor
		implements IHttpProcessor<IHttpRequestMessage, IHttpResponseMessage>
{
	/**
	 * connection timeout: 10seconds<BR>
	 */
	public static final int DEFAULT_CONNECTION_TIMEOUT = 10000;

	/**
	 * socket timeout: 10seconds<BR>
	 */
	public static final int DEFAULT_SOCKET_TIME = 10000;

	/**
	 * default network buffer size: 8192bytes<BR>
	 */
	public static final int DEFAULT_NETWORK_BUFFER_SIZE = 8192;

	/**
	 * Set if HTTP requests are blocked from being executed on this thread
	 */
	private static final ThreadLocal<Boolean> threadBlocked = new ThreadLocal<Boolean>();

	/**
	 * Interceptor throws an exception if the executing thread is blocked
	 */
	private static final HttpRequestInterceptor threadCheckInterceptor = new HttpRequestInterceptor()
	{
		public void process(HttpRequest request, HttpContext context)
		{
			if (Boolean.TRUE.equals(threadBlocked.get()))
			{
				throw new RuntimeException("This thread forbids HTTP requests");
			}
		}
	};

	/**
	 * allow all SSLSocketFactory
	 */
	private static SSLSocketFactory allowAllSSLSocketFactory = null;

	static
	{
		try
		{
			KeyStore trustStore = KeyStore
					.getInstance(KeyStore.getDefaultType());
			trustStore.load(null, null);
			allowAllSSLSocketFactory = new CertPassSSLSocketFactory(trustStore, false);
		}
		catch (KeyStoreException e)
		{
			e.printStackTrace();
		}
		catch (CertificateException e)
		{
			e.printStackTrace();
		}
		catch (UnrecoverableKeyException e)
		{
			e.printStackTrace();
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (KeyManagementException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * logger
	 */
	Logger log = LoggerFactory.getLogger(DefaultHttpProcessor.class);

	/**
	 * use writing response to file or not.<BR>
	 */
	private boolean isWritingResponseToFile = false;

	/**
	 * file path for saving a response
	 */
	private String responseFilePath = null;

	/**
	 * proxy use or not for reusing response inputstream
	 */
	private boolean isProxyResponseData = false;

	/**
	 * HttpClient instance
	 */
	private HttpClient httpClient = null;

	/**
	 * HttpParams instance
	 */
	private HttpParams httpParameters = null;

	/**
	 * SSLSocketFactory
	 */
	private SSLSocketFactory sslSocketFactory = null;

	/**
	 * constructor
	 */
	public DefaultHttpProcessor()
	{
	}

	/**
	 * return default SSLSocketFactory
	 *
	 * @return SSLSocketFactory
	 */
	private static SSLSocketFactory getDefaultSSLSocketFactory()
	{
		return SSLSocketFactory.getSocketFactory();
	}

	/**
	 * This method create HttpClient instance.<BR>
	 *
	 * @return HttpClient
	 */
	public HttpClient createHttpClient(IHttpRequestMessage requestMessage)
	{
		HttpClient httpClient = createHttpClient(
				requestMessage.getConnectionTimeout() > 0
						? requestMessage.getConnectionTimeout()
						: DEFAULT_CONNECTION_TIMEOUT,
				requestMessage.getSocketTimeout() > 0
						? requestMessage.getSocketTimeout()
						: DEFAULT_SOCKET_TIME,
				requestMessage.getNetworkBufferSize() > 0
						? requestMessage.getNetworkBufferSize()
						: DEFAULT_NETWORK_BUFFER_SIZE,
				requestMessage.isTrustSsl());
		return httpClient;
	}

	/**
	 * HttpClient instance를 생성한다.
	 *
	 * @return HttpClient
	 */
	public HttpClient createHttpClient(int connectionTimeout, int socketTimeout,
			int networkBufferSize, boolean isTrustSsl)
	{

		return createHttpClient(connectionTimeout, socketTimeout,
				networkBufferSize, isTrustSsl ? getDefaultSSLSocketFactory()
						: allowAllSSLSocketFactory);
	}

	/**
	 * HttpClient instance를 생성한다.
	 *
	 * @return HttpClient
	 */
	public HttpClient createHttpClient(int connectionTimeout, int socketTimeout,
			int networkBufferSize, SSLSocketFactory sslSocketFactory)
	{
		HttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setStaleCheckingEnabled(httpParameters, false);
		HttpConnectionParams.setConnectionTimeout(httpParameters,
				connectionTimeout);
		HttpConnectionParams.setSoTimeout(httpParameters, socketTimeout);
		HttpConnectionParams.setSocketBufferSize(httpParameters,
				networkBufferSize);
		HttpClientParams.setRedirecting(httpParameters, false);
		// HttpProtocolParams.setUserAgent(httpParameters, userAgent);

		// socket factory
		// plain
		PlainSocketFactory plainSocketFactory = PlainSocketFactory
				.getSocketFactory();

		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", plainSocketFactory, 80));
		schemeRegistry.register(new Scheme("https", sslSocketFactory, 443));
		// Thread safe client connection manager
		ClientConnectionManager connManager = new ThreadSafeClientConnManager(
				httpParameters, schemeRegistry);
		// create HttpClient instance
		HttpClient httpClient = new DefaultHttpClient(connManager,
				httpParameters)
		{
			@Override
			protected BasicHttpProcessor createHttpProcessor()
			{
				// Add interceptor to prevent making requests from main thread.
				BasicHttpProcessor processor = super.createHttpProcessor();
				processor.addRequestInterceptor(threadCheckInterceptor);
				return processor;
			}

			@Override
			protected HttpContext createHttpContext()
			{
				// Same as DefaultHttpClient.createHttpContext() minus the
				// cookie store.
				HttpContext context = new BasicHttpContext();
				context.setAttribute(ClientContext.AUTHSCHEME_REGISTRY,
						getAuthSchemes());
				context.setAttribute(ClientContext.COOKIESPEC_REGISTRY,
						getCookieSpecs());
				context.setAttribute(ClientContext.CREDS_PROVIDER,
						getCredentialsProvider());
				return context;
			}
		};

		log.debug("ConnectionTimeout: {}, SocketTimeout: {}, NetworkBuffer: {}",
				connectionTimeout, socketTimeout, networkBufferSize);
		return httpClient;
	}

	/**
	 * close this service.<BR>
	 */
	public void close()
	{
		if (httpClient != null)
		{
			httpClient = null;
		}
	}

	/**
	 * return HTTP method instance.<BR>
	 *
	 * @param message
	 *            DefaultHttpMessage
	 * @return HttpRequestBase
	 */
	private HttpRequestBase createRequestHttpMethod(IHttpRequestMessage message)
			throws UnsupportedEncodingException
	{
		HttpRequestBase req = null;
		switch (message.getMethod())
		{
			case IHttpRequestMessage.METHOD_GET:
				req = new HttpGet(message.getUrlString());
				break;
			case IHttpRequestMessage.METHOD_POST:
				req = new HttpPost(message.getUrlString());
				break;
			case IHttpRequestMessage.METHOD_PUT:
				req = new HttpPut(message.getUrlString());
				break;
			case IHttpRequestMessage.METHOD_DELETE:
				req = new HttpDeleteWithBody(message.getUrlString());
				break;
		}

		if (message.getMethod() != IHttpRequestMessage.METHOD_GET)
		{
			// multipart use or not
			if (isMultipart(message))
			{
				MultipartEntity multipartEntity = new MultipartEntity();
				// file
				if (message.getAttachFilePath() != null
						&& message.getAttachFilePath().length() > 0)
				{
					// file
					File file = new File(message.getAttachFilePath());
					FileBody fileBody = new FileBody(file);
					multipartEntity.addPart(file.getName(), fileBody);
				}
				// parameter
				if (message.getParameterSize() > 0)
				{
					// text(parameter)
					StringBody stringBody = null;
					for (String name : message.getParameterNames())
					{
						stringBody = new StringBody(
								message.getParameterValue(name), "text/plain",
								Charset.forName(message.getTextEncoding()));
						multipartEntity.addPart(name, stringBody);
					}
				}
				// // body content
				// if (message.getBodyByteArray() != null
				// && message.getBodyByteArray().length > 0)
				// {
				// // text(parameter)
				// StringBody stringBody = new StringBody(new String(
				// message.getBodyByteArray()), "text/plain",
				// Charset.forName(message.getTextEncoding()));
				// multipartEntity.addPart("text", stringBody);
				// }

				// inputstream
				if (message.getBodyInputStream() != null)
				{
					InputStreamBody inputStreamBody = new InputStreamBody(
							message.getBodyInputStream(),
							message.getBodyName());
					multipartEntity.addPart(message.getBodyName(),
							inputStreamBody);
				}
				((HttpEntityEnclosingRequestBase) req)
						.setEntity(multipartEntity);
			}
			else
			{
				HttpEntity entity = null;
				if (message.getBodyInputStream() != null)
				{
					if (message.getBodyType() == HttpMessage.BODY_TYPE_STRING)
					{
						entity = new StringEntity(
								new String(message.getBodyByteArray()),
								message.getTextEncoding());
					}
//					else if (message
//							.getBodyType() == HttpMessage.BODY_TYPE_BYTE_ARRAY)
//					{
//						entity = new ByteArrayEntity(
//								message.getBodyByteArray());
//					}
					else
					{
						entity = new InputStreamEntity(message.getBodyInputStream());
					}
				}
				else if (message.getParameterSize() > 0)
				{
					Vector<NameValuePair> nameValuePair = new Vector<NameValuePair>();
					for (String name : message.getParameterNames())
					{
						nameValuePair.add(new BasicNameValuePair(name,
								message.getParameterValue(name)));
					}
					try
					{
						entity = new UrlEncodedFormEntity(nameValuePair);
					}
					catch (UnsupportedEncodingException e)
					{
						e.printStackTrace();
					}
				}
				((HttpEntityEnclosingRequestBase) req).setEntity(entity);
			}
		}

		// header
		String[] headerNames = message.getHeaderNames();
		for (String headerName : headerNames)
		{
			req.addHeader(headerName, message.getHeaderValue(headerName));
		}

		return req;
	}

	/**
	 * return use or not multipart.
	 *
	 * @param message
	 *            DefaultHttpRequestMessage
	 * @return use or not
	 */
	private boolean isMultipart(IHttpRequestMessage message)
	{
		if (message.getAttachFilePath() != null
				&& message.getAttachFilePath().length() > 0)
		{
			return true;
		}
		if (message.getBodyType() == HttpMessage.BODY_TYPE_MULTIPART)
		{
			return true;
		}
		return false;
	}

	/**
	 * process for HTTP request.<BR>
	 *
	 * @param requestMessage
	 *            DefaultHttpRequestMessage
	 * @return DefaultHttpResponseMessage
	 */
	@Override
	public IHttpResponseMessage request(IHttpRequestMessage requestMessage)
	{
		IHttpResponseMessage responseMessage = null;
		int status = -1;
		HttpUriRequest httpUriRequest = null;
		InputStream responseBodyInputStream = null;
		Header[] responseHeaders = null;
		try
		{
			// create HttpRequest
			httpUriRequest = createRequestHttpMethod(requestMessage);
			log.debug("RequestMessage:\n{}", requestMessage.toString());
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
			return null;
		}
		// create httpclient
		if (httpClient == null)
		{
			httpClient = createHttpClient(requestMessage);
		}

		try
		{
			// execute httpclient
			HttpResponse response = httpClient.execute(httpUriRequest);
			status = response.getStatusLine().getStatusCode();
			responseHeaders = response.getAllHeaders();

			HttpEntity entity = response.getEntity();
			if (isProxyResponseData)
			{
				ProxyInputStream pis = new ProxyInputStream(
						entity.getContent());
				responseBodyInputStream = pis.getNewInputStream();
			}
			else
			{
				responseBodyInputStream = entity.getContent();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if (httpUriRequest != null)
			{
				httpUriRequest.abort();
			}
			if (e instanceof UnknownHostException)
			{
				status = HttpStatusExtended.SC_UNKNOWN_HOST;
			}
			else if (e instanceof ConnectTimeoutException)
			{
				status = HttpStatusExtended.SC_CONNECTION_TIMEOUT;
			}
			else if (e instanceof SocketTimeoutException)
			{
				status = HttpStatusExtended.SC_SOCKET_TIMEOUT;
			}
			else if (e instanceof SocketException)
			{
				if (e.getMessage().toLowerCase().indexOf("timed out") != -1)
				{
					status = HttpStatusExtended.SC_SOCKET_TIMEOUT;
				}
				else if (e.getMessage().toLowerCase()
						.indexOf("network unreachable") != -1)
				{
					status = HttpStatusExtended.SC_NETWORK_UNREACHABLE;
				}
				else if (e.getMessage().toLowerCase()
						.indexOf("no route to host") != -1)
				{
					status = HttpStatusExtended.SC_NO_ROUTE_TO_HOST;
				}
				else if (e instanceof HttpHostConnectException)
				{
					status = HttpStatusExtended.SC_HTTP_HOST_CONNECTION_ERROR;
				}
				else
				{
					status = HttpStatusExtended.SC_SOCKET_ERROR;
				}
			}
			else if (e instanceof ClientProtocolException)
			{
				status = HttpStatusExtended.SC_CLIENT_PROTOCOL_CRASH_VALUE;
			}
			else if (e instanceof SSLException)
			{
				status = HttpStatusExtended.SC_HTTPS_ERROR;
			}
		}
		finally
		{
			responseMessage = createHttpResponseMessage(requestMessage, status,
					responseHeaders, responseBodyInputStream);
			log.debug("ResponseMessage:\n{}", responseMessage.toString());
		}
		return responseMessage;
	}

	/**
	 * create Response message and return.
	 *
	 * @param requestMessage
	 *            requesst message
	 * @param status
	 *            status code
	 * @param bodyInputStream
	 *            InputStream to HTTP body
	 * @return DefaultHttpResponseMessage
	 */
	private IHttpResponseMessage createHttpResponseMessage(
			IHttpRequestMessage requestMessage, int status, Header[] headers,
			InputStream bodyInputStream)
	{
		IHttpResponseMessage responseMessage = new DefaultHttpResponseMessage();
		responseMessage.setStatusCode(status);
		try
		{
			responseMessage.setUrl(requestMessage.getUrl());
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
		if (headers != null)
		{
			for (Header header : headers)
			{
				responseMessage.addHeaderParameter(header.getName(),
						header.getValue());
				if (header.getName().equalsIgnoreCase(
						ContentDisposition.CONTENT_DISPOSITION_NAME))
				{
					responseMessage.setContentDisposition(ContentDisposition
							.getContentDisposition(header.getValue()));
				}
			}
		}
		responseMessage.setBodyInputStream(bodyInputStream);
		responseMessage.setHttps(requestMessage.isHttps());
		responseMessage.setMethod(requestMessage.getMethod());
		responseMessage.setTextEncoding(requestMessage.getTextEncoding());

		return responseMessage;
	}

	/**
	 * return file path for saving response.<BR>
	 *
	 * @return file path
	 */
	public String getResponseFilePath()
	{
		return responseFilePath;
	}

	/**
	 * set file path for saving response.<BR>
	 *
	 * @param responseFilePath
	 *            file path
	 *
	 */
	public void setResponseFilePath(String responseFilePath)
	{
		this.responseFilePath = responseFilePath;
	}

	/**
	 * return use or not for saving response.<BR>
	 *
	 * @return use or not
	 */
	public boolean isWritingResponseToFile()
	{
		return isWritingResponseToFile;
	}

	/**
	 * set use or not for saving response.<BR>
	 *
	 * @param writingResponseToFile
	 *            use or not
	 */
	public void setWritingResponseToFile(boolean writingResponseToFile)
	{
		isWritingResponseToFile = writingResponseToFile;
	}

	/**
	 * return HttpClient.<BR>
	 *
	 * @return HttpClient instance
	 */
	public HttpClient getHttpClient()
	{
		return httpClient;
	}

	/**
	 * set HttpClient instance.<BR>
	 *
	 * @param httpClient
	 *            HttpClient instance
	 */
	public void setHttpClient(HttpClient httpClient)
	{
		this.httpClient = httpClient;
	}

	/**
	 * return use or not proxy for saving HTTP response.<BR>
	 *
	 * @return use or not
	 */
	public boolean isProxyResponseData()
	{
		return isProxyResponseData;
	}

	/**
	 * set use or not proxy for saving HTTP response.<BR>
	 *
	 * @param isProxyResponseData
	 *            use or not
	 */
	public void setProxyResponseData(boolean isProxyResponseData)
	{
		this.isProxyResponseData = isProxyResponseData;
	}

	/**
	 * test main
	 *
	 * @param args
	 */
	public static void main(String[] args)
	{
		Logger log = LoggerFactory.getLogger(DefaultHttpProcessor.class);

		log.info("=== DefaultHttpProcessor test ===");
		DefaultHttpProcessor http = new DefaultHttpProcessor();
		DefaultHttpRequestMessage req = new DefaultHttpRequestMessage();
		try
		{
			req.setUrl("https://www.naver.com/");
			req.setMethod(DefaultHttpRequestMessage.METHOD_POST);
			// body data
			StringInputStream inputStream = new StringInputStream(
					new String("AAA"));
			req.setBodyInputStream(inputStream);
			req.setTrustSSl(false);
			// req.setUrl("http://www.withwiz.com");
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
		http.setProxyResponseData(false);
		IHttpResponseMessage res = http.request(req);

		log.info("response code: {}", res.getStatusCode());
		switch (res.getStatusCode())
		{
			case HttpStatusExtended.SC_OK:
				log.info("response:\n{}", res.toString());
				break;
			default:
				log.info("response:\n{}", res.toString());
		}
	}
}
