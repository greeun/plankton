package com.withwiz.plankton.network.message2;

/**
 * Legacy response object common interface
 */
public interface IDefaultResponse<TYPE_RAW_DATA>
        extends IDefaultMessage<TYPE_RAW_DATA>, IResponseMessage<TYPE_RAW_DATA> {
    /**
     * get message header
     *
     * @return ILegacyRequestHeader
     */
    @Override
    IDefaultResponseHeader<TYPE_RAW_DATA> getHeader();

    /**
     * get message body
     *
     * @return ILegacyRequestBody
     */
    @Override
    IDefaultResponseBody<TYPE_RAW_DATA> getBody();
}
