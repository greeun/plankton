package com.withwiz.plankton.network.codec;

import com.withwiz.plankton.network.exception.CodecException;

/**
 * decoder interface
 */
public interface IDecoder<TYPE_SOURCE, TYPE_DECODED> {
    /**
     * create a TYPE_DECODED instance from byte array
     *
     * @param source source
     * @return TYPE_DECODED
     */
    TYPE_DECODED decode(TYPE_SOURCE source) throws CodecException;
}
