package com.withwiz.plankton.conversion;

import java.nio.ByteBuffer;

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

    /**
     * convert int to byte[]
     *
     * @param value           int value
     * @param sizeOfByteArray size(2 or 4)
     * @return byte[]
     */
    public static byte[] toByteArray(int value, int sizeOfByteArray) {
        byte[] byteArray = new byte[sizeOfByteArray];
        if (sizeOfByteArray == 2) {
            byteArray[0] = (byte) value;
            byteArray[1] = (byte) (value >>> 8);
        } else if (sizeOfByteArray == 4) {
            byteArray[0] = (byte) (value >> 24);
            byteArray[1] = (byte) (value >> 16);
            byteArray[2] = (byte) (value >> 8);
            byteArray[3] = (byte) (value);
        }
        return byteArray;
    }

    /**
     * convert int to byte[]
     *
     * @param value           int value
     * @param sizeOfByteArray byte[] size
     * @return byte[]
     */
    public static byte[] toByteArray2(int value, int sizeOfByteArray) {
        byte[] ba = new byte[sizeOfByteArray];
        for (int i = 0; i < sizeOfByteArray; i++) {
            int offset = (ba.length - 1 - i) * 8;
            ba[i] = (byte) ((value >>> offset) & 0xFF);
        }
        return ba;
    }

    /**
     * convert int to byte[]
     *
     * @param value int value
     * @return byte[]
     */
    public static byte[] toByteArray(int value) {
        return ByteBuffer.allocate(4).putInt(value).array();
    }

    /* shot to byte 변환 */

    /**
     * conver short to bytes[]
     *
     * @param Value short value
     * @param order big(1) or little endian(0)
     * @return byte[]
     */
    public static byte[] toByteArray(short Value, int order) {
        byte[] temp;
        temp = new byte[]{(byte) ((Value & 0xFF00) >> 8), (byte) (Value & 0x00FF)};
        temp = ByteUtil.byteOrder(temp, order);
        return temp;
    }
}
