package com.withwiz.plankton.interfaces.network.message2;

/**
 * Message object common interface
 */
public interface IMessage<TYPE_RAW_DATA> extends IMessagePart<TYPE_RAW_DATA> {
    /**
     * get message body
     *
     * @return IMessageBody
     */
    IMessageBody<TYPE_RAW_DATA> getBody();
}
