package com.withwiz.plankton.network.message2;

/**
 * Default message interface
 */
public interface IDefaultMessage<TYPE_RAW_DATA> extends IMessage<TYPE_RAW_DATA> {
    /**
     * get header
     *
     * @return IDefaultMessageHeader
     */
    IDefaultMessageHeader<TYPE_RAW_DATA> getHeader();

    /**
     * get body
     *
     * @return IDefaultMessageBody
     */
    @Override
    IDefaultMessageBody<TYPE_RAW_DATA> getBody();
}
