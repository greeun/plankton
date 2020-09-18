package com.withwiz.plankton.network.message2;

/**
 * Request message object common interface
 */
public interface IRequestMessage<TYPE_RAW_DATA> extends IMessage<TYPE_RAW_DATA> {
    /**
     * get message body
     *
     * @return IRequestMessageBody
     */
    @Override
    IRequestMessageBody<TYPE_RAW_DATA> getBody();
}
