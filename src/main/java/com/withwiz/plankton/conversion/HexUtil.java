package com.withwiz.plankton.conversion;

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
    public static int hexStringToInt(String hexString) {
        return Integer.parseInt(hexString.replaceFirst("^0x", ""), 16);
    }

    /**
     * convert hex string to text
     *
     * @param hex hex string
     * @return text
     */
    public static String hexToString(String hex) {

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
     * convert int to Hex string
     *
     * @param value int value
     * @return hex string
     */
    public static String intToHexString(int value) {
        return Integer.toHexString(value);
    }

    /**
     * convert int to Hex string
     *
     * @param value int value
     * @param digit digit
     * @return hex string
     */
    public static String intToHexString(int value, int digit) {
        return String.format("%0" + (digit * 2) + "X", value);
    }

    /**
     * int value to hex string and hex string to int
     *
     * @param intValue
     * @return hex int value
     */
    public static int intToHexInt(int intValue) {
        return Integer.parseInt(intToHexString(intValue));
    }

    /**
     * test main
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(HexUtil.intToHexString(1193046, 8));
    }
}
