package com.withwiz.plankton.network.message2;

/**
 * Legacy request extended object common interface
 */
public interface IDefaultRequestEx<TYPE_RAW_DATA> extends IDefaultRequest<TYPE_RAW_DATA> {
    /**
     * get message tail
     *
     * @return ILegacyRequestTail
     */
    IDefaultRequestTail<TYPE_RAW_DATA> getTail();
}
