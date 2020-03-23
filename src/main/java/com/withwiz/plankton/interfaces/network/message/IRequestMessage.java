package com.withwiz.plankton.interfaces.network.message;

/**
 * Request message object common interface
 */
public interface IRequestMessage extends IMessage {
    /**
     * get message header
     *
     * @return IRequestMessageHeader
     */
    @Override
    IRequestMessageHeader getHeader();

    /**
     * get message body
     *
     * @return IRequestMessageBody
     */
    @Override
    IRequestMessageBody getBody();
}
