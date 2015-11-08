package com.withwiz.plankton.network;

/**
 * Network utility processing status codes.<BR/>
 * Created by uni4love on 2010. 2. 2..
 */
public interface INetworkStatus
{
	/**
	 * status: undefined
	 */
	public static final int	SC_UNDEFINED					= -1;

	/**
	 * network status: unknown host
	 */
	public static final int	SC_UNKNOWN_HOST					= 9000;

	/**
	 * network status: connection timeout
	 */
	public static final int	SC_CONNECTION_TIMEOUT			= 9001;

	/**
	 * network status: socket timeout
	 */
	public static final int	SC_SOCKET_TIMEOUT				= 9002;

	/**
	 * network status: network unreachable
	 */
	public static final int	SC_NETWORK_UNREACHABLE			= 9003;

	/**
	 * network status: no route to host
	 */
	public static final int	SC_NO_ROUTE_TO_HOST				= 9004;

	/**
	 * network status: client protocol crash value
	 */
	public static final int	SC_CLIENT_PROTOCOL_CRASH_VALUE	= 9005;

	/**
	 * network status: input/output error
	 */
	public static final int	SC_IO_ERROR						= 9006;

	/**
	 * network status: invalid URL
	 */
	public static final int	SC_INVALID_URL					= 9010;

	/**
	 * network status: security error
	 */
	public static final int	SC_SECURITY_ERROR				= 9011;

	/**
	 * network status: socket error
	 */
	public static final int	SC_SOCKET_ERROR					= 9012;

}
