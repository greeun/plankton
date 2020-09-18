package com.withwiz.plankton.network.message2;

/**
 * Commons function interface of message part
 */
public interface IMessagePart<TYPE_RAW_DATA> {
    /**
     * get byte[] for message part.<BR>
     *
     * @return byte[] bytes
     */
    TYPE_RAW_DATA getRawData();
}