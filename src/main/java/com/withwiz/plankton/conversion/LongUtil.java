package com.withwiz.plankton.conversion;

/**
 * long utility class
 */
public class LongUtil {
    /**
     * convert long to 8 byte array
     *
     * @param l long
     * @return byte[]
     */
    public static byte[] toBytes(long l) {
        byte[] result = new byte[8];
        for (int i = 7; i >= 0; i--) {
            result[i] = (byte) (l & 0xFF);
            l >>= 8;
        }
        return result;
    }
}
