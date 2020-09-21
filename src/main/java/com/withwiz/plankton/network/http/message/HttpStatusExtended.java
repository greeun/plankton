package com.withwiz.plankton.network.http.message;

import com.withwiz.plankton.network.INetworkStatus;
import org.apache.http.HttpStatus;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Extra status code defined.<BR>
 * Created by uni4love on 2010. 5. 4..
 */
abstract public class HttpStatusExtended implements INetworkStatus, HttpStatus
{
	/**
	 * network status: http host connection error
	 */
	public static final int					SC_HTTP_HOST_CONNECTION_ERROR	= 901;

	/**
	 * network status: https error
	 */
	public static final int					SC_HTTPS_ERROR					= 902;

	/**
	 * CODE strings map
	 */
	protected static Map<Integer, String>	codeStrings						= null;

	static
	{
		codeStrings = new HashMap<Integer, String>();
		Field[] fields = HttpStatusExtended.class.getFields();
		for (Field field : fields)
		{
			try
			{
				if (field.getName().startsWith("SC_"))
				{
					codeStrings.put(field.getInt(HttpStatusExtended.class),
							field.getName().replace("SC_", ""));
				}
			}
			catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * return text for HTTP status code.<BR>
	 *
	 * @param code
	 *            status code
	 * @return text for code
	 */
	public static String getCodeString(int code)
	{
		return codeStrings.get(code);
	}
}
