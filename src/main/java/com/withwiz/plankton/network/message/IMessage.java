package com.withwiz.plankton.network.message;

/**
 * Message object common interface
 */
public interface IMessage extends IMessagePart {

    /**
     * get message header
     *
     * @return IMessageHeader
     */
    IMessageHeader getHeader();

    /**
     * get message body
     *
     * @return IMessageBody
     */
    IMessageBody getBody();
}
