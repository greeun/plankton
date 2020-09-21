package com.withwiz.plankton.network.http.message;

/**
 * HTTP response message.<BR/>
 * Created by uni4love on 2010. 5. 8..
 */
public class DefaultHttpResponseMessage extends DefaultHttpMessage implements IHttpResponseMessage
{
	/**
	 * default name
	 */
	protected static final String 		NAME_DEFAULT = "HTTP_RESPONSE_MESSAGE";

	@Override
	public byte[] getBodyByteArray()
	{
		if (bodyInputStream != null)
		{
			return super.getBodyByteArray();
		}
		else
		{
			return "No Content".getBytes();
		}
	}

	@Override
	public String getName()
	{
		return name == null ? NAME_DEFAULT : name;
	}

	@Override
	public String toString()
	{
		return super.toString();
	}
}
