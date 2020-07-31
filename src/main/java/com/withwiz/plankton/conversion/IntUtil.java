package com.withwiz.plankton.conversion;

/**
 * integer utility class
 */
public class IntUtil {
    /**
     * convert int to Hex string
     *
     * @param value int value
     * @return hex string
     */
    public static String toHexString(int value) {
        return Integer.toHexString(value);
    }

    /**
     * int value to hex string and hex string to int
     *
     * @param intValue  int value
     * @return hex int value
     */
    public static int toHexInt(int intValue) {
        return Integer.parseInt(toHexString(intValue));
    }

    /**
     * convert int to Hex string
     *
     * @param value int value
     * @param digit digit
     * @return hex string
     */
    public static String toHexString(int value, int digit) {
        return String.format("%0" + (digit * 2) + "X", value);
    }
}
