package com.withwiz.plankton.conversion;

/**
 * supports for primitive data type
 */
public class PrimitiveSizes {
    public static int sizeof(byte b) {
        return Byte.BYTES;
    }

    public static int sizeof(char c) {
        return Character.BYTES;
    }

    public static int sizeof(int i) {
        return Integer.BYTES;
    }

    public static int sizeof(long l) {
        return Long.BYTES;
    }

    public static int sizeof(short s) {
        return Short.BYTES;
    }

    public static int sizeof(double d) {
        return Double.BYTES;
    }

    public static int sizeof(float f) {
        return Float.BYTES;
    }
}
