package com.withwiz.plankton.network.codec;

import com.withwiz.plankton.network.exception.CodecException;

/**
 * Weigh Object string decoder interface
 */
public interface IByteArrayDecoder<TYPE_DECODED> extends IDecoder<byte[], TYPE_DECODED> {
    /**
     * create a TYPE_DECODED instance from byte array
     *
     * @param source source
     * @return TYPE_ENCODED
     */
    @Override
    TYPE_DECODED decode(byte[] source) throws CodecException;
}
