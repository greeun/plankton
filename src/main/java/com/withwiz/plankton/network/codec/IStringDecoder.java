package com.withwiz.plankton.network.codec;

import com.withwiz.plankton.network.exception.CodecException;

/**
 * Weigh Object string decoder interface
 */
public interface IStringDecoder<TYPE_DECODED> extends IDecoder<String, TYPE_DECODED> {
    /**
     * create a TYPE_DECODED instance from string text
     *
     * @param source source
     * @return TYPE_DECODED
     */
    @Override
    TYPE_DECODED decode(String source) throws CodecException;
}
