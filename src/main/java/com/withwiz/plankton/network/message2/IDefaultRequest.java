package com.withwiz.plankton.network.message2;

/**
 * Legacy request object common interface
 */
public interface IDefaultRequest<TYPE_RAW_DATA>
        extends IDefaultMessage<TYPE_RAW_DATA>, IRequestMessage<TYPE_RAW_DATA> {
    /**
     * get message header
     *
     * @return IDefaultRequestHeader
     */
    @Override
    IDefaultRequestHeader<TYPE_RAW_DATA> getHeader();

    /**
     * get message body
     *
     * @return IDefaultRequestBody
     */
    @Override
    IDefaultRequestBody<TYPE_RAW_DATA> getBody();
}
