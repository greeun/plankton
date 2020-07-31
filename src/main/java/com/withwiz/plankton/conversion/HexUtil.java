package com.withwiz.plankton.conversion;

import org.apache.commons.codec.binary.Hex;

/**
 * Hex util class
 */
public class HexUtil {

    /**
     * convert Hex string to int
     *
     * @param hexString hex string
     * @return int value
     */
    public static int toInt(String hexString) {
        return Integer.parseInt(hexString.replaceFirst("^0x", ""), 16);
    }

    /**
     * convert hex string to text
     *
     * @param hex hex string
     * @return text
     */
    public static String toString(String hex) {

        StringBuilder sb = new StringBuilder();
        StringBuilder temp = new StringBuilder();

        //49204c6f7665204a617661 split into two characters 49, 20, 4c...
        for (int i = 0; i < hex.length() - 1; i += 2) {

            //grab the hex in pairs
            String output = hex.substring(i, (i + 2));
            //convert hex to decimal
            int decimal = Integer.parseInt(output, 16);
            //convert the decimal to character
            sb.append((char) decimal);

            temp.append(decimal);
        }
        System.out.println("Decimal : " + temp.toString());

        return sb.toString();
    }

    /**
     * convert hex String to bytearray
     *
     * @param hexString hex string
     * @return byte[]
     */
    public static byte[] toByteArray(String hexString) {
        if (hexString == null || hexString.length() == 0) {
            return null;
        }
        hexString = hexString.replace("0x", "");

        byte[] ba = new byte[hexString.length() / 2];
        for (int i = 0; i < ba.length; i++) {
            ba[i] = (byte) Integer.parseInt(hexString.substring(2 * i, 2 * i + 2), 16);
        }
        return ba;
    }

    /**
     * test main
     *
     * @param args  arguments
     */
    public static void main(String[] args) {
        int src = 1193046;
        String hexString = IntUtil.toHexString(src, 8);
        System.out.println("src: " + src);
        System.out.println("hex: " + hexString);
        System.out.println("decoded: " + HexUtil.toInt(hexString));
    }
}
