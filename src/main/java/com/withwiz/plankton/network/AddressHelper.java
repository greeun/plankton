package com.withwiz.plankton.network;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Network address helper class<BR/>
 * Created by uni4love on 2010. 05. 27..
 */
public class AddressHelper
{
	/**
	 * logger
	 */
	private Logger log = LoggerFactory.getLogger(AddressHelper.class);

	/**
	 * return localhost IP address.<BR/>
	 *
	 * @return IP address
	 */
	public static StringBuffer getLocalhostAddress()
	{
		InetAddress iaLocalAddress = null;
		try
		{
			iaLocalAddress = InetAddress.getLocalHost();
		}
		catch (UnknownHostException e)
		{
			e.printStackTrace();
		}
		if (iaLocalAddress == null)
		{
			return null;
		}
		return getIp(iaLocalAddress);
	}

	/**
	 * return IP address(xxx.xxx.xxx.xxx)<BR/>
	 *
	 * @param ia
	 *            InetAddress instance
	 * @return IP address(xxx.xxx.xxx.xxx)
	 */
	public static StringBuffer getIp(InetAddress ia)
	{
		byte[] address = ia.getAddress();
		StringBuffer sb = new StringBuffer();
		// filtering IP
		for (int i = 0; i < address.length; i++)
		{
			int unsignedByte = address[i] < 0 ? address[i] + 256 : address[i];
			sb.append(unsignedByte);
			if ((address.length - i) != 1)
				sb.append(".");
		}
		return sb;
	}

	/**
	 * return port of the socket.<BR/>
	 *
	 * @param socket
	 *            Socket instance
	 * @return port number
	 */
	public static int getPort(Socket socket)
	{
		return socket.getPort();
	}

	/**
	 * return address(IP:PORT) of remote client from socket.<BR/>
	 *
	 * @param socket
	 *            Socket instance
	 * @return address
	 */
	public static StringBuffer getTargetAddress(Socket socket)
	{
		return getIp(socket.getInetAddress()).append(":")
				.append(getPort(socket));
	}

	/**
	 * return IP addresses from domain name.<BR/>
	 *
	 * @param domainName
	 *            domain name
	 * @return IP address array
	 */
	public static String[] getDomain2Ip(String domainName)
	{
		// A domain can have multiple IP.
		InetAddress[] ip;
		String[] ipAddress = null;
		try
		{
			ip = InetAddress.getAllByName(domainName);
			ipAddress = new String[ip.length];
			for (int i = 0; i < ip.length; i++)
			{
				ipAddress[i] = ip[i].getHostAddress();
			}
		}
		catch (Exception ex)
		{
			System.out.println(">>> Error : " + ex.toString());
			ex.printStackTrace();
		}
		return ipAddress;
	}

	/**
	 * return IP addresses from domain name.<BR/>
	 *
	 * @param domainName
	 *            domain name
	 * @return IP address array
	 */
	public static String[] nslookup(String domainName)
	{
		return getDomain2Ip(domainName);
	}

	/**
	 * test main
	 *
	 * @param args
	 */
	public static void main(String args[])
	{
		AddressHelper test = new AddressHelper();
		System.out.println("Localhost InetAddress : "
				+ AddressHelper.getLocalhostAddress());
		String domain = "www.yahoo.com";
		System.out.println("> Your computer's IP address is "
				+ AddressHelper.getLocalhostAddress());
		for (int temp = 0; temp < AddressHelper
				.getDomain2Ip(domain).length; temp++)
		{
			System.out.println(new StringBuffer().append("> \"").append(domain)
					.append("\" domain IP address No.").append(temp + 1)
					.append(" is ").append(getDomain2Ip(domain)[temp]));
		}
		for (int temp = 0; temp < AddressHelper.nslookup(domain).length; temp++)
		{
			System.out.println(new StringBuffer().append("> \"").append(domain)
					.append("\" nslookup result No.").append(temp + 1)
					.append(" is ")
					.append(AddressHelper.nslookup(domain)[temp]));
		}
	}
}
