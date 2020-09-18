package com.withwiz.plankton.network.exception;

/**
 * encode/decode exception
 */
public class CodecException extends Exception {
    /**
     * Creates a new instance.
     */
    public CodecException() {
    }

    /**
     * Creates a new instance.
     */
    public CodecException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new instance.
     */
    public CodecException(String message) {
        super(message);
    }

    /**
     * Creates a new instance.
     */
    public CodecException(Throwable cause) {
        super(cause);
    }
}
