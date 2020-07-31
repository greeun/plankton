package com.withwiz.plankton.conversion;

import java.nio.ByteBuffer;

/**
 * byte utility class
 */
public class ByteUtil {
    /**
     * convert bytearray to binary
     *
     * @param ba byte array
     * @return binary string
     */
    public static String toBinaryString(byte[] ba) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ba.length; ++i) {
            sb.append(toBinaryString(ba[i]));
        }
        return sb.toString();
    }

    /**
     * convert byte to binary string
     *
     * @param b byte
     * @return binary string
     */
    public static String toBinaryString(byte b) {
        StringBuilder sb = new StringBuilder("00000000");
        for (int bit = 0; bit < 8; bit++) {
            if (((b >> bit) & 1) > 0) {
                sb.setCharAt(7 - bit, '1');
            }
        }
        return sb.toString();
    }

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

    /**
     * convert 8 bytes to long
     *
     * @param ba byte[]
     * @return long
     */
    public static long toLong(byte[] ba) {
        long result = 0;
        for (int i = 7; i >= 0; i--) {
            result <<= 8;
            result |= (ba[i] & 0xFF);
        }
        return result;
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

    /* 4or2 bytearray  to int 변환*/

    /**
     * convert byte[] to int
     *
     * @param ba        byte[]
     * @param lengthDiv length for dividing
     * @return int value
     */
    public static int toInt(byte[] ba, int lengthDiv) {
        int byteInt = 0;
        if (lengthDiv == 2) {
            byteInt = ((ba[1] & 0xFF) << 8) | (ba[0] & 0xFF);
        } else if (lengthDiv == 4) {
            byteInt = ba[0] & 0xFF |
                    (ba[1] & 0xFF) << 8 |
                    (ba[2] & 0xFF) << 16 |
                    (ba[3] & 0xFF) << 24;
        }
        return byteInt;
    }

    /**
     * convert byte[] to int
     *
     * @param ba byte[]
     * @return int value
     */
    public static int toInt(byte[] ba) {
        int value = 0;
        int length = ba.length;  // length of byte array
        for (int i = 0; i < length; i++) {
            int shift = (length - 1 - i) * 8;
            value += (ba[i] & 0x000000FF) << shift;
        }
        return value;
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
        temp = byteOrder(temp, order);
        return temp;
    }

    /**
     * convert big or little endian (내부적으로 사용하는 함수)
     *
     * @param value value
     * @param order big(1) or little endian(0)
     * @return byte[]
     */
    public static byte[] byteOrder(byte[] value, int order) {
        int idx = value.length;
        byte[] Temp = new byte[idx];
        //BIG_EDIAN
        if (order == 1) {
            Temp = value;
        } else if (order == 0) {
            for (int i = 0; i < idx; i++) {
                Temp[i] = value[idx - (i + 1)];
            }
        }
        return Temp;
    }

    /**
     * convert binary String to byte
     *
     * @param s binary string
     * @return byte
     **/
    public static byte binaryStringToByte(String s) {
        byte ret = 0, total = 0;
        for (int i = 0; i < 8; ++i) {
            ret = (s.charAt(7 - i) == '1') ? (byte) (1 << i) : 0;
            total = (byte) (ret | total);
        }
        return total;
    }

    /**
     * convert binary String to bytearray
     *
     * @param s binary string
     * @return byte[]
     */
    public static byte[] binaryStringToByteArray(String s) {
        int count = s.length() / 8;
        byte[] b = new byte[count];
        for (int i = 1; i < count; ++i) {
            String t = s.substring((i - 1) * 8, i * 8);
            b[i - 1] = binaryStringToByte(t);
        }
        return b;
    }

    /**
     * convert byte array to hex String
     *
     * @param buf byte array
     * @return hex string
     */
    public static String toHexString(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            sb.append(Integer.toHexString(0x0100 + (buf[i] & 0x00FF)).substring(1));
        }
        return sb.toString();
    }

    /**
     * test main
     *
     * @param args
     */
    public static void main(String[] args) {
        int i = 100;
        System.out.println("value: " + i + " --> " + IntUtil.toHexString(i));
        System.out.println("value: " + i + " --> " + IntUtil.toHexString(i));
        System.out.println("value: " + i + " --> " + ByteUtil.toHexString(ByteUtil.toByteArray(i, 4)));
        System.out.println("value: " + i + " --> " + ByteUtil.toHexString(ByteUtil.toByteArray2(i, 4)));
    }
}
