package com.withwiz.plankton.interfaces.network.message2;

/**
 * Response message object common interface
 */
public interface IResponseMessage<TYPE_RAW_DATA> extends IMessage<TYPE_RAW_DATA> {

    /**
     * get message body
     *
     * @return IResponseMessageBody
     */
    @Override
    IResponseMessageBody<TYPE_RAW_DATA> getBody();
}
