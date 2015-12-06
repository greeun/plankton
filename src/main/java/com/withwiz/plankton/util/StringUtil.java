package com.withwiz.plankton.util;

import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * String utility class.<BR/>
 * Created by uni4love on 2008. 2. 16..
 */
public class StringUtil
{
	/**
	 * convert hex value String to int type.<BR/>
	 *
	 * @param hexString
	 *            0x.. hex value String
	 * @return int value
	 */
	public static int hexStringToInt(String hexString)
	{
		int temp = 0;
		// 0x.. format
		if (hexString.toUpperCase().startsWith("0X"))
		{
			String str = hexString.substring(2);
			byte[] b2 = new byte[str.length()];
			int square = 0;
			for (int i = 0; i < str.length(); i++)
			{
				if (str.substring(i, i + 1).equals("A")
						|| str.substring(i, i + 1).equals("a"))
					b2[i] = (byte) 0x0A;
				else if (str.substring(i, i + 1).equals("B")
						|| str.substring(i, i + 1).equals("b"))
					b2[i] = (byte) 0x0B;
				else if (str.substring(i, i + 1).equals("C")
						|| str.substring(i, i + 1).equals("c"))
					b2[i] = (byte) 0x0C;
				else if (str.substring(i, i + 1).equals("D")
						|| str.substring(i, i + 1).equals("d"))
					b2[i] = (byte) 0x0D;
				else if (str.substring(i, i + 1).equals("E")
						|| str.substring(i, i + 1).equals("e"))
					b2[i] = (byte) 0x0E;
				else if (str.substring(i, i + 1).equals("F")
						|| str.substring(i, i + 1).equals("f"))
					b2[i] = (byte) 0x0F;
				else
					b2[i] = (byte) (Integer.parseInt(str.substring(i, i + 1))
							& 0x0F);
				// temp = temp + (b2[i] << (4 * (source.length() - i)));
				if ((str.length() - (i + 1)) == 0)
				{
					temp = temp + b2[i];
				}
				else
				{
					square = str.length() - (i + 1);
					temp = temp + (b2[i] * ((int) Math.pow(16, square)));
				}
			}
		}
		else
		{
			temp = Integer.parseInt(hexString);
		}
		return temp;
	}

	/**
	 * convert int value String to hex type.<BR/>
	 *
	 * @param intString
	 *            int value string
	 * @return hex string
	 */
	public static String intStringToHexString(String intString)
	{
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < intString.length(); i++)
		{
			sb.append(Integer.toHexString((int) intString.charAt(i)))
					.append(' ');
		}
		return sb.toString();
	}

	/**
	 * add "0x" in front of hex string.<BR/>
	 *
	 * @param hexString
	 *            int value string
	 * @return hex string
	 */
	public static String add0xFrontHexString(String hexString)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("0x").append(hexString);
		return sb.toString();
	}

	/**
	 * add "0x" in front of int value.<BR/>
	 *
	 * @param intValue
	 *            int value string
	 * @return hex string
	 */
	public static String add0xFrontIntString(int intValue)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("0x").append(intValue);
		return sb.toString();
	}

	/**
	 * put num '0' in front of a number if a number is smaller than '10'
	 *
	 * @param intValue
	 *            (ex) 17)
	 * @param stringLength
	 *            string length with '0'
	 * @return string
	 */
	public static String addZeroFrontValue(int intValue, int stringLength)
	{
		String tempValueString = String.valueOf(intValue);
		String tempValue = "";
		if (stringLength <= tempValueString.length())
		{
			return tempValueString;
		}
		else
		{
			for (int j = 0; j < stringLength - tempValueString.length(); j++)
			{
				tempValue = tempValue + "0";
			}
			tempValue = tempValue + tempValueString;
			return tempValue;
		}
	}

	/**
	 * convert Boolean string to boolean value.<BR/>
	 *
	 * @param booleanString
	 *            boolean string
	 * @return boolean
	 */
	public static boolean booleanStringToBoolean(String booleanString)
	{
		boolean flag = false;
		if (booleanString.toLowerCase().equals("true"))
		{
			flag = true;
		}
		else
		{
			flag = false;
		}
		return flag;
	}

	/**
	 * replace from string to string2.<BR/>
	 *
	 * @param src
	 *            source data
	 * @param from
	 *            replace from source string
	 * @param to
	 *            replace to target string
	 * @return replaced string
	 */
	public static String replace(String src, String from, String to)
	{
		if (src == null)
			return null;
		if (from == null)
			return src;
		if (to == null)
			to = "";
		StringBuffer buf = new StringBuffer();
		for (int pos; (pos = src.indexOf(from)) >= 0;)
		{
			buf.append(src.substring(0, pos));
			buf.append(to);
			src = src.substring(pos + from.length());
		}
		buf.append(src);
		return buf.toString();
	}

	/**
	 * replace from string to string2.<BR/>
	 *
	 * @param source
	 *            source data
	 * @param oldString
	 *            replace from source string
	 * @param newString
	 *            replace to target string
	 * @return replaced string
	 */
	public static String replace2(String source, String oldString,
			String newString)
	{
		if (source == null || source.length() == 0)
		{
			return source;
		}
		final int oldSize = oldString.length();
		final int neoSize = newString.length();
		final StringBuffer sb = new StringBuffer(source);
		int begin = 0;
		int idx = sb.indexOf(oldString, begin);
		while (idx != -1)
		{
			sb.delete(idx, idx + oldSize);
			sb.insert(idx, newString);
			begin = idx + neoSize;
			if (begin < sb.length())
			{
				idx = sb.indexOf(oldString, begin);
			}
			else
			{
				idx = -1;
			}
		}
		return sb.toString();
	}

	/**
	 * return string array from "abc|def|ghi...", exception for delimeter.<BR/>
	 *
	 * @param source
	 *            source data
	 * @param delimeter
	 *            delimeter
	 * @return String[]
	 */
	public static String[] getStringArray(String source, String delimeter)
	{
		String[] ret = null;
		Vector v = new Vector();
		StringTokenizer st = new StringTokenizer(source, delimeter);
		while (st.hasMoreTokens())
		{
			v.add(st.nextToken());
		}
		if (v.size() == 0)
			return null;
		ret = new String[v.size()];
		for (int i = 0; i < ret.length; i++)
		{
			ret[i] = ((String) v.get(i)).trim();
		}
		return ret;
	}

	/**
	 * return isNumbric<BR/>
	 *
	 * @param input
	 *            input string
	 * @return true or false
	 */
	public static boolean isNumeric(String input)
	{
		if (input == null | input.equals(""))
			return false;
		boolean ret = true;
		char ch;
		for (int i = 1; i < input.length(); i++)
		{
			ch = input.charAt(i);
			// if (ch == '+' || ch == '-' || ch == '/' || ch == '*') {
			// continue;
			// }
			if (ch < '0' || ch > '9')
			{
				ret = false;
				break;
			}
		}
		return ret;
	}

	/**
	 * on/off -> true/false<BR/>
	 *
	 * @param input
	 *            input string(ON or OFF)
	 * @return boolean
	 */
	public static boolean isOn(String input)
	{
		if (input.toUpperCase().equals("ON"))
			return true;
		else
			return false;
	}

	/**
	 * return string between leftString and rightString.<BR/>
	 *
	 * @param source
	 *            source string
	 * @param leftString
	 *            left string
	 * @param rightString
	 *            right string
	 * @return string
	 */
	public static String subString(String source, String leftString,
			String rightString)
	{
		return source.substring(
				source.indexOf(leftString) + leftString.length(),
				source.lastIndexOf(rightString));
	}

	/**
	 * return a string the beginning to length.<BR/>
	 *
	 * @param source
	 *            source string
	 * @param nLength
	 *            length for substring
	 * @return string
	 */
	public static String getLeft(String source, int nLength)
	{
		return source.substring(0, nLength);
	}

	/**
	 * return a string the beginning to delimeter position index in string.<BR/>
	 *
	 * @param source
	 *            source string
	 * @param delimeter
	 *            delimeter
	 * @return string
	 */
	public static String getLeft(String source, String delimeter)
	{
		int nLastIndex = source.indexOf(delimeter);
		if (nLastIndex == -1)
			return source;
		else
			return getLeft(source, nLastIndex);
	}

	/**
	 * return string after index.<BR/>
	 *
	 * @param source
	 *            source data
	 * @param startIndex
	 *            start index
	 * @return string
	 */
	public static String getRight(String source, int startIndex)
	{
		return source.substring(startIndex, source.length());
	}

	/**
	 * return string after delimeter.<BR/>
	 *
	 * @param source
	 *            source string
	 * @param sDelimeter
	 *            delimeter
	 * @return string
	 */
	public static String getRight(String source, String sDelimeter)
	{
		int nLastIndex = source.indexOf(sDelimeter);
		if (nLastIndex == -1)
			return source;
		else
			return getRight(source, nLastIndex + sDelimeter.length());
	}

	/**
	 * search string by indexes.<BR/>
	 *
	 * @param source
	 *            source string
	 * @param nStart
	 *            start position index
	 * @param nLength
	 *            length
	 * @return searched string
	 */
	public static String getMid(String source, int nStart, int nLength)
	{
		return source.substring(nStart, nStart + nLength);
	}

	/**
	 * search string by delimeters.<BR/>
	 *
	 * @param source
	 *            source data
	 * @param sStartDelimeter
	 *            start delimeter
	 * @param sEndDelimeter
	 *            end delimeter
	 * @return searched string
	 */
	public static String getMidWithDelimeter(String source,
			String sStartDelimeter, String sEndDelimeter)
	{
		int nStartIndex = source.indexOf(sStartDelimeter);
		if (nStartIndex == -1)
			return null;
		int nEndIndex = source.indexOf(sEndDelimeter, nStartIndex);
		if (nEndIndex == -1)
			return null;
		return source.substring(nStartIndex,
				nEndIndex + sEndDelimeter.length());
	}

	/**
	 * search string by delimeters, excepting for delimeter.<BR/>
	 *
	 * @param source
	 *            source data
	 * @param sStartDelimeter
	 *            start character
	 * @param sEndDelimeter
	 *            end character
	 * @return searched string
	 */
	public static String getMidWithoutDelimeter(String source,
			String sStartDelimeter, String sEndDelimeter)
	{
		int nStartIndex = source.indexOf(sStartDelimeter) + 1;
		if (nStartIndex == -1)
			return null;
		int nEndIndex = source.indexOf(sEndDelimeter, nStartIndex);
		if (nEndIndex == -1)
			return null;
		return source.substring(nStartIndex, nEndIndex);
	}

	/**
	 * return Map data(key-value)<BR/>
	 *
	 * ex)
	 * STB_VER=02.02.06&APP_VER=02.04.06&P2TV_ENG_1_VER=006&P2TV_ENG_2_VER=015
	 * <BR/>
	 *
	 * @param source
	 *            source string
	 * @param itemDivider
	 *            item divider
	 * @param keyValueDelimeter
	 *            key/value delimeter
	 * @return Map
	 */
	public static HashMap getKeyValueMap(String source, String itemDivider,
			String keyValueDelimeter)
	{
		String[] items = getStringArray(source, itemDivider);
		HashMap map = new HashMap();
		String[] keyValue;
		for (String item : items)
		{
			keyValue = getStringArray(item, keyValueDelimeter);
			if (keyValue != null && keyValue[0] != null)
			{
				// if next token Not exist, the value is NULL.
				if (keyValue.length == 1)
				{
					map.put(keyValue[0], "");
				}
				else
				{
					map.put(keyValue[0], keyValue[1]);
				}
			}
		}
		return map;
	}

    /**
     * returns the index of the array is stored for a specific string.<BR/>
     *
     * @param saArray
     *            string array
     * @param sValue
     *            string for searching
     * @return string
     */
    public static int getIndexInStringArray(String[] saArray, String sValue)
    {
        int nRet = -1;
        Vector v = new Vector();
        for (int nIndex = 0; nIndex < saArray.length; nIndex++)
            if (saArray[nIndex].equals(sValue))
            {
                nRet = nIndex;
                break;
            }
        return nRet;
    }

	/**
	 * test main
	 *
	 * @param args
	 */
	public static void main(String[] args) {
        org.slf4j.Logger log = LoggerFactory.getLogger(StringUtil.class);

		String hexString = "0x2b3cd8e";
		String hexStringWithout0x = "0x2b3cd8e";
		String intString = "20";
		int intValue = 20;
		int maxLength = 10;
		int startIndex = 0;
		int length = 2;
		String startString = "#";
		String endString = "$";
		String booleanString = "TRUE";
        String source = "abc#ABC#DEF#GHIJKLMN$OP";
        String source2 = "<sbjt><![CDATA[(미디어 제목)]]></sbjt>";
        String source3 = "STB_VER=02.02.06&APP_VER=02.04.06&P2TV_ENG_1_VER=006&P2TV_ENG_2_VER=015";
		String oldString = "#A";
		String newString = "$E";
		String subStringStart = "<![CDATA[(";
		String subStringEnd = ")]]>";
		String elementDivider = "&";
		String keyValueDivider = "=";


        // StringHexToInt()
        log.debug("hex {} to int : {}", hexString,
                StringUtil.hexStringToInt(hexString));
        // StringToHex()
        log.debug("string {} to hex : {}", intString, StringUtil.intStringToHexString(intString));
        // add0xFrontHexString()
        log.debug("add 0x in front of Hex {} : {}", hexStringWithout0x,
                StringUtil.add0xFrontHexString(hexStringWithout0x));
        // add0xFrontIntString()
        log.debug("add 0x in front of int {} : {}", intString,
                StringUtil.add0xFrontIntString(intValue));
        // addZeroFrontValue()
        log.debug("add 0 in front of {} : {}", intValue,
                StringUtil.addZeroFrontValue(intValue, maxLength));
        // booleanStringToBoolean()
        log.debug("boolean string {} to Boolean : {}", booleanString,
                StringUtil.booleanStringToBoolean(booleanString));
        // replace()
        log.debug("replace {} from {} to {} : {}", source, oldString, newString,
                StringUtil.replace(source, oldString, newString));
        // replace2()
        log.debug("replace2 {} from {} to {} : {}", source, oldString, newString,
                StringUtil.replace2(source, oldString, newString));
        // getLeft()
        log.debug("getLeft from {} by {} : {}", source, "#",
                StringUtil.getLeft(source, "#"));
        // getRight()
        log.debug("getRight from {} by {} : {}", source, "#",
                StringUtil.getRight(source, "#"));
        // getMid()
        log.debug("getMid {} to {} from {} : {}", startIndex, length, source,
                StringUtil.getMid(source, startIndex, length));
        // getMidWithDelimeter()
        log.debug("getMid {} to {} from {} : {}", startString, endString, source, StringUtil
                .getMidWithDelimeter(source, startString, endString));
        // subString()
        log.debug("subString {} to {} string {} : {}", subStringStart, subStringEnd, source2,
                subString(source2, subStringStart, subStringEnd));
        // getKeyValueMap()
        log.debug("getKeyValueMap source: {}", source3);
        Map map = getKeyValueMap(source3, elementDivider, keyValueDivider);
        Set set = map.entrySet();
        Iterator iterator = set.iterator();
        Map.Entry entry = null;
        while (iterator.hasNext()) {
            entry = (Map.Entry) iterator.next();
            log.info("key:value ; {}:{}", entry.getKey(), entry.getValue());
        }
        // getIndexInStringArray()
        log.debug("getKeyValueMap source: {}", StringUtil.getIndexInStringArray(getStringArray(source, "#"), "DEF"));

    }
}
