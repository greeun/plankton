package com.withwiz.plankton.network.http.message;

import com.withwiz.plankton.util.StringUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * HTTP header parameter: "Content-Disposition" definition.<BR/>
 */
public class ContentDisposition
{
    /**
     * name
     */
    public static String CONTENT_DISPOSITION_NAME = "Content-Disposition";

    /**
     * type: attachment
     */
    public static String TYPE_ATTACHMENT = "attachment";

    /**
     * type: inline
     */
    public static String TYPE_INLINE = "inline";

    /**
     * type: extension-token
     */
    public static String TYPE_EXTENSION_TOKEN = "extension-token";

    /**
     * parameter: filename
     */
    public static String PARM_FILENAME = "filename";

    /**
     * parameter: creation-date
     */
    public static String PARM_CREATION_DATE = "creation-date";

    /**
     * parameter: modification-date
     */
    public static String PARM_MODIFICATION_DATE = "modification-date";

    /**
     * parameter: read-date
     */
    public static String PARM_READ_DATE = "read-date";

    /**
     * parameter: size
     */
    public static String PARM_SIZE = "size";

    /**
     * type
     */
    private String type = null;

    /**
     * parameters
     */
    private Map<String, String> parameters = null;

    /**
     * constructor
     */
    public ContentDisposition()
    {
        parameters = new HashMap<String, String>();
    }

    /**
     * return Content-Disposition object.<BR/>
     *
     * @param data
     *            data
     * @return ContentDisposition
     */
    public static ContentDisposition getContentDisposition(String data)
    {
        ContentDisposition cd = new ContentDisposition();
        String[] parameters = data.split(";");
        // type
        cd.setType(parameters[0]);
        // parameter
        String[] keyValue = null;

        for (int i = 1; i < parameters.length; i++)
        {
            keyValue = parameters[i].split("=");
            cd.addParameter(keyValue[0].trim(),
                    StringUtil.getMidWithoutDelimeter(keyValue[1].trim(),
                            "\"", "\""));
        }
        return cd;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public void addParameter(String key, String value)
    {
        parameters.put(key, value);
    }

    public String getParameterValue(String key)
    {
        return parameters.get(key);
    }

    public boolean containsKey(String key)
    {
        return parameters.containsKey(key);
    }

    @Override
    public String toString()
    {
        StringBuffer sb = new StringBuffer(CONTENT_DISPOSITION_NAME);
        sb.append(": ").append(type).append(";");
        for (Map.Entry entry : parameters.entrySet())
        {
            sb.append(" ").append(entry.getKey()).append("=")
                    .append(entry.getValue()).append(";");
        }
        return sb.toString();
    }
}
