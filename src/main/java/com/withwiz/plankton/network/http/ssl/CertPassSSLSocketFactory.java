package com.withwiz.plankton.network.http.ssl;

import org.apache.http.conn.ssl.SSLSocketFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * SSLSocketFactory overrided.<BR/>
 * Created by uni4love on 2010. 5. 1..
 */
public class CertPassSSLSocketFactory extends SSLSocketFactory
{
	SSLContext	sslContext	= SSLContext.getInstance("TLS");

	/**
	 * constructor
	 * @param truststore trust store
	 * @param isVerifyHostname
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 * @throws KeyStoreException
	 * @throws UnrecoverableKeyException
     */
	public CertPassSSLSocketFactory(KeyStore truststore, boolean isVerifyHostname)
			throws NoSuchAlgorithmException, KeyManagementException,
			KeyStoreException, UnrecoverableKeyException
	{
		this(truststore);
		if(!isVerifyHostname)
			this.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
	}

	/**
	 * constructor
	 * @param truststore trust store
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 * @throws KeyStoreException
	 * @throws UnrecoverableKeyException
     */
	public CertPassSSLSocketFactory(KeyStore truststore)
			throws NoSuchAlgorithmException, KeyManagementException,
            KeyStoreException, UnrecoverableKeyException
	{
		super(truststore);

		TrustManager tm = new X509TrustManager()
		{
			public void checkClientTrusted(X509Certificate[] chain,
					String authType) throws CertificateException
			{
				//nothing
			}

			public void checkServerTrusted(X509Certificate[] chain,
					String authType) throws CertificateException
			{
				//nothing
			}

			public X509Certificate[] getAcceptedIssuers()
			{
				return null;
			}
		};

		sslContext.init(null, new TrustManager[] { tm }, null);
		// sslContext.init(null, new TrustManager[] { tm }, new SecureRandom());
	}

	@Override
	public Socket createSocket(Socket socket, String host, int port,
			boolean autoClose) throws IOException, UnknownHostException
	{
		return sslContext.getSocketFactory().createSocket(socket, host, port,
				autoClose);
	}

	@Override
	public Socket createSocket() throws IOException
	{
		return sslContext.getSocketFactory().createSocket();
	}


}
